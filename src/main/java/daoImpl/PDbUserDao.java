package daoImpl;

import Interfaces.Models.Model;
import Interfaces.Dao.UserDao;
import models.ModelUser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 26.12.2016.
 */
public class PDbUserDao implements UserDao<ModelUser> {
    private static final String JDBC_CONNECTION_DB = "jdbc:postgresql://localhost:5432/usersdao";
    private static final String JDBC_CONNECTION_USER = "postgres";
    private static final String JDBC_CONNECTION_PASS = "13311133";
    private static final String TABLE_NAME = "group_users";
    private List<ModelUser> usersList = null;

    public PDbUserDao(){
        if(usersList == null || usersList.size() == 0){
            usersList = new ArrayList<>();
            read();
        }
    }

    public void read() {
        try(Connection con = DriverManager.getConnection(JDBC_CONNECTION_DB, JDBC_CONNECTION_USER, JDBC_CONNECTION_PASS)){
            //Загружаем драйвер
            Class.forName("org.postgresql.Driver");
            System.out.println("Драйвер подключен");

            Statement statement = null;
            statement = con.createStatement();
            String query = "Select * From "+TABLE_NAME;
            try (ResultSet result = statement.executeQuery(query)){
                while(result.next()){
                    int id = result.getInt("id");
                    String name = result.getString("name");
                    int age = result.getInt("age");
                    usersList.add(new ModelUser(id, name,age));
                }
            }catch (SQLException e){
                System.out.println("Sql exception: " +e);
            }catch (Exception e){
                System.out.println("Exception: " +e);
            }

        }catch (SQLException e){
            throw new RuntimeException("Sql exception: "+e.getMessage());
        }catch (Exception e){
            throw new RuntimeException("Exception: "+e.getMessage());
        }
    }
    @Override
    public List<ModelUser> get() {
        return usersList;
    }

    @Override
    public ModelUser get(int id) {
        if(usersList == null)
            read();

        for (ModelUser mu: usersList) {
            if(id == mu.getId())
                return mu;
        }
        return null;
    }

    @Override
    public boolean add(ModelUser user) {
        try(Connection con = DriverManager.getConnection(JDBC_CONNECTION_DB,JDBC_CONNECTION_USER,JDBC_CONNECTION_PASS)){
            PreparedStatement preparedStatement = null;
            preparedStatement = con.prepareStatement("Insert Into "+TABLE_NAME+"(name,age) Values(?,?) RETURNING id");
            preparedStatement.setString(1,user.getName());
            preparedStatement.setInt(2,user.getAge());
            ResultSet res = preparedStatement.executeQuery();
            while(res.next()) {
                user.setId(res.getInt("id"));
            }
        }catch (SQLException e){
            throw new IllegalArgumentException("SQL exception: "+e.getMessage());
        }
        usersList.add(user);
        return true;
    }

    @Override
    public boolean add(List<ModelUser> users) {
        try(Connection con = DriverManager.getConnection(JDBC_CONNECTION_DB,JDBC_CONNECTION_USER,JDBC_CONNECTION_PASS)){
            for(ModelUser user: users) {
                PreparedStatement preparedStatement = null;
                preparedStatement = con.prepareStatement("Insert Into "+TABLE_NAME+"(name,age) Values(?,?) RETURNING id");
                preparedStatement.setString(1, user.getName());
                preparedStatement.setInt(2, user.getAge());
                ResultSet res = preparedStatement.executeQuery();
                while(res.next()) {
                    user.setId(res.getInt("id"));
                    usersList.add(user);
                }
            }
        }catch (SQLException e){
            throw new IllegalArgumentException("SQL exception: "+e.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(int id, ModelUser user) {
        boolean result = false;
        List<ModelUser> temp = new ArrayList<>(usersList);
        int index = 0;
        try(Connection con = DriverManager.getConnection(JDBC_CONNECTION_DB,JDBC_CONNECTION_USER,JDBC_CONNECTION_PASS)) {
            PreparedStatement preparedStatement = null;
            preparedStatement = con.prepareStatement("Update "+TABLE_NAME+" Set name=?,age=? Where id=?");
            preparedStatement.setString(1,user.getName()); // name
            preparedStatement.setInt(2,user.getAge()); // age
            preparedStatement.setInt(4,id); // condition id
            int res = preparedStatement.executeUpdate();
            result = (res > 0) ? true : false;
        }catch(SQLException e){
            throw new IllegalArgumentException("Sql exception: "+e);
        }
        if(result) {
            for (ModelUser ma : usersList) {
                if (id == ma.getId()) {
                    user.setId(id);
                    System.out.println("Update method");
                    temp.set(index, user);
                    result = true;
                }
                index++;
            }
            usersList = temp;
        }
        return result;
    }

    @Override
    public boolean remove(int id) {
        boolean result = false;
        try(Connection con = DriverManager.getConnection(JDBC_CONNECTION_DB,JDBC_CONNECTION_USER,JDBC_CONNECTION_PASS)) {
            PreparedStatement preparedStatement = null;
            preparedStatement = con.prepareStatement("Delete From "+TABLE_NAME+" Where id=?");
            preparedStatement.setInt(1,id); // condition id
            int res = preparedStatement.executeUpdate();
            result = (res > 0) ? true : false;
        }catch(SQLException e){
            throw new IllegalArgumentException("Sql exception: "+e);
        }
        if(result) {
            List<ModelUser> temp = new ArrayList<>(usersList);
            for (ModelUser ma : usersList) {
                if (id == ma.getId()) {
                    result = temp.remove(ma);
                }
            }
            usersList = temp;
        }
        return result;
    }

    @Override
    public boolean remove() {
        boolean result = false;
        try(Connection con = DriverManager.getConnection(JDBC_CONNECTION_DB,JDBC_CONNECTION_USER,JDBC_CONNECTION_PASS)) {
            Statement statement = null;
            statement = con.createStatement();
            String query = "Delete * From "+TABLE_NAME;
            int res = statement.executeUpdate(query);
            result = (res > 0) ? true : false;
        }catch(SQLException e){
            throw new IllegalArgumentException("Sql exception: "+e);
        }
        if(result)
            usersList.clear();
        return result;
    }

    @Override
    public <A extends Model> void addAuto(A auto, int userId) {
        ModelUser user = get(userId);
        int index = usersList.indexOf(user);
        if(index >= 0) {
            user.setAuto(auto);
            usersList.set(index, user);
        }
    }

    @Override
    public <A extends Model> void addAuto(List<A> auto, int userId) {
        ModelUser user = get(userId);
        int index = usersList.indexOf(user);
        if(index >= 0) {
            user.setAuto(auto);
            usersList.set(index, user);
        }
    }
}
