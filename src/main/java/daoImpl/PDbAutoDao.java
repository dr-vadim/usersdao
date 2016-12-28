package daoImpl;

import Interfaces.Dao.AutoDao;
import models.ModelAuto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PDbAutoDao implements AutoDao<ModelAuto> {
    private static final String JDBC_CONNECTION_DB = null;
    private static final String JDBC_CONNECTION_USER = null;
    private static final String JDBC_CONNECTION_PASS = null;
    List<ModelAuto> mauto = null;

    public PDbAutoDao(){
        if((mauto == null || mauto.size() == 0) && JDBC_CONNECTION_DB != null){
            mauto = new ArrayList<>();
            read();
        }
    }

    public void read() {
        if (mauto == null) mauto = new ArrayList<>();
        try(Connection con = DriverManager.getConnection(JDBC_CONNECTION_DB, JDBC_CONNECTION_USER, JDBC_CONNECTION_PASS)){
            //Загружаем драйвер
            Class.forName("org.postgresql.Driver");
            System.out.println("Драйвер подключен");

            Statement statement = null;
            statement = con.createStatement();
            String query = "Select * From auto";
            try (ResultSet result = statement.executeQuery(query)){
                while(result.next()){
                    int id = result.getInt("id");
                    String model = result.getString("name");
                    String color = result.getString("color");
                    int userId = result.getInt("user_id");
                    mauto.add(new ModelAuto(id, model,color,userId));
                }
            }catch (SQLException e){
                throw new RuntimeException("Sql exception: " +e);
            }catch (Exception e){
                throw new RuntimeException("Exception: " +e);
            }

        }catch (SQLException e){
            throw new RuntimeException("Sql exception: "+e.getMessage());
        }catch (Exception e){
            throw new RuntimeException("Exception: "+e.getMessage());
        }
    }

    @Override
    public List<ModelAuto> get() {
        return mauto;
    }

    @Override
    public List<ModelAuto> getByCat(int id) {
        if(mauto == null)
            read();
        List<ModelAuto> malist = new ArrayList<>();
        for (ModelAuto ma: mauto) {
            if(id == ma.getUser())
                malist.add(ma);
        }
        return malist;
    }

    @Override
    public ModelAuto get(int id) {
        if(mauto == null)
            read();

        for (ModelAuto ma: mauto) {
            if(id == ma.getId()) return ma;
        }
        return null;
    }

    @Override
    public boolean add(List<ModelAuto> elements) {
        try(Connection con = DriverManager.getConnection(JDBC_CONNECTION_DB,JDBC_CONNECTION_USER,JDBC_CONNECTION_PASS)){
            PreparedStatement preparedStatement = null;
            for(ModelAuto auto: elements) {
                preparedStatement = con.prepareStatement("Insert Into auto(name,color,user_id) Values(?,?,?) RETURNING id");
                preparedStatement.setString(1,auto.getModel());
                preparedStatement.setString(2,auto.getColor());
                preparedStatement.setInt(3,auto.getUser());
                ResultSet res = preparedStatement.executeQuery();
                while(res.next()){
                    auto.setId(res.getInt("id"));
                }
                mauto.add(auto);
            }
        }catch (SQLException e){
            throw new IllegalArgumentException("SQL exception: "+e.getMessage());
        }
        return true;
    }

    @Override
    public boolean add(ModelAuto auto) {
        try(Connection con = DriverManager.getConnection(JDBC_CONNECTION_DB,JDBC_CONNECTION_USER,JDBC_CONNECTION_PASS)){
            PreparedStatement preparedStatement = null;
            preparedStatement = con.prepareStatement("Insert Into auto(name,color,user_id) Values(?,?,?) RETURNING id");
            preparedStatement.setString(1,auto.getModel());
            preparedStatement.setString(2,auto.getColor());
            preparedStatement.setInt(3,auto.getUser());
            ResultSet res = preparedStatement.executeQuery();
            while(res.next()){
                auto.setId(res.getInt("id"));
            }
        }catch (SQLException e){
            throw new IllegalArgumentException("SQL exception: "+e.getMessage());
        }
        mauto.add(auto);
        return true;
    }

    @Override
    public boolean update(int id, ModelAuto auto) {
        boolean result = false;
        List<ModelAuto> temp = new ArrayList<>(mauto);
        int index = 0;
        try(Connection con = DriverManager.getConnection(JDBC_CONNECTION_DB,JDBC_CONNECTION_USER,JDBC_CONNECTION_PASS)) {
            PreparedStatement preparedStatement = null;
            preparedStatement = con.prepareStatement("Update auto Set name=?,color=?,user_id=? Where id=?");
            preparedStatement.setString(1,auto.getModel()); // name
            preparedStatement.setString(2,auto.getColor()); // color
            preparedStatement.setInt(3,auto.getUser()); // user_id
            preparedStatement.setInt(4,id); // condition id
            int res = preparedStatement.executeUpdate();
            result = (res > 0) ? true : false;
        }catch(SQLException e){
            throw new IllegalArgumentException("Sql exception: "+e);
        }
        if(result) {
            for (ModelAuto ma : mauto) {
                if (id == ma.getId()) {
                    auto.setId(id);
                    System.out.println("Update method");
                    temp.set(index, auto);
                    result = true;
                }
                index++;
            }
            mauto = temp;
        }
        return result;
    }

    @Override
    public boolean remove(int id) {
        boolean result = false;
        try(Connection con = DriverManager.getConnection(JDBC_CONNECTION_DB,JDBC_CONNECTION_USER,JDBC_CONNECTION_PASS)) {
            PreparedStatement preparedStatement = null;
            preparedStatement = con.prepareStatement("Delete From auto Where id=?");
            preparedStatement.setInt(1,id); // condition id
            int res = preparedStatement.executeUpdate();
            result = (res > 0) ? true : false;
        }catch(SQLException e){
            throw new IllegalArgumentException("Sql exception: "+e);
        }
        if(result) {
            List<ModelAuto> temp = new ArrayList<>(mauto);
            for (ModelAuto ma : mauto) {
                if (id == ma.getId()) {
                    result = temp.remove(ma);
                }
            }
            mauto = temp;
        }
        return result;
    }

    @Override
    public boolean removeByCat(int cat_id) {
        boolean result = false;
        try(Connection con = DriverManager.getConnection(JDBC_CONNECTION_DB,JDBC_CONNECTION_USER,JDBC_CONNECTION_PASS)) {
            PreparedStatement preparedStatement = null;
            preparedStatement = con.prepareStatement("Delete From auto Where user_id=?");
            preparedStatement.setInt(1,cat_id); // condition id
            int res = preparedStatement.executeUpdate();
            result = (res > 0) ? true : false;
            System.out.println("delete result "+result);
        }catch(SQLException e){
            throw new IllegalArgumentException("Sql exception: "+e);
        }
        if(result) {
            List<ModelAuto> temp = new ArrayList<>(mauto);
            for (ModelAuto ma : mauto) {
                if (cat_id == ma.getUser()) {
                    result = temp.remove(ma);
                }
            }
            mauto = temp;
        }
        return result;
    }

    @Override
    public boolean remove() {
        boolean result = false;
        try(Connection con = DriverManager.getConnection(JDBC_CONNECTION_DB,JDBC_CONNECTION_USER,JDBC_CONNECTION_PASS)) {
            Statement statement = null;
            statement = con.createStatement();
            String query = "Delete * From auto RETURNING id";
            int res = statement.executeUpdate(query);
            result = (res > 0) ? true : false;
        }catch(SQLException e){
            throw new IllegalArgumentException("Sql exception: "+e);
        }
        if(result)
            mauto.clear();
        return result;
    }
}
