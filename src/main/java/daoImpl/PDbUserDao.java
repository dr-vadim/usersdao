package daoImpl;

import interfaces.dao.AutoDao;
import interfaces.models.Model;
import interfaces.dao.UserDao;
import models.ModelAuto;
import models.ModelUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by User on 26.12.2016.
 */
public class PDbUserDao implements UserDao {
    private static final String TABLE_NAME = "group_users";
    //language=SQL
    private static final String SQL_SELECT_ALL = "SELECT * FROM group_users";
    //language=SQL
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM group_users WHERE id = ?";
    //language=SQL
    private static final String SQL_INSER_NEW = "INSERT INTO group_users(name,age) VALUE (?,?)";
    //language=SQL
    private static final String SQL_INSER_MULTIPLE = "INSERT INTO group_users(name,age) VALUES ";
    //language=SQL
    private static final String SQL_UPDATE_USER = "UPDATE group_users Set name=?,age=? Where id=?";
    //language=SQL
    private static final String SQL_DELETE_USER = "DELETE FROM group_users WHERE id=?";
    //language=SQL
    private static final String SQL_DELETE_ALL = "Delete From group_users";

    private List<ModelUser> usersList = null;
    private JdbcTemplate template;

    public PDbUserDao(DataSource dataSource){
        template = new JdbcTemplate(dataSource);
    }

    private RowMapper<ModelUser> rowMapper = (ResultSet rs, int i) -> {
        ModelUser user = new ModelUser(rs.getInt("id"),rs.getString("name"),
                rs.getInt("age"));
        return user;
    };

    public void read() {

    }
    @Override
    public Map<Integer,ModelUser> get() {
        List<ModelUser> listUser = template.query(SQL_SELECT_ALL,new Object[]{},rowMapper);
        Map<Integer,ModelUser> users =
                listUser.stream().collect(Collectors.toMap(i -> i.getId(),i -> i));
        return users;
    }

    @Override
    public ModelUser get(int id) {
        return template.queryForObject(SQL_SELECT_BY_ID,new Object[]{id},rowMapper);
    }

    @Override
    public boolean add(ModelUser user) {
        int result = 0;
        Object[] args = {user.getName(),user.getAge()};
        int[] types = {Types.VARCHAR,Types.INTEGER};
        result = template.update(SQL_UPDATE_USER,args,types);
        return (result > 0) ? true : false;
    }

    @Override
    public boolean add(Map<Integer,ModelUser> users) {
        int result = 0;
        String[] values = new String[users.size()];
        Object[] args = new Object[users.size() * 2];
        int j = 0;
        int i = 0;
        for(ModelUser user: users.values()){
            values[i] = "(?,?)";
            args[j++] = user.getName();
            args[j++] = user.getAge();
            i++;
        }
        result = template.update(SQL_INSER_MULTIPLE+String.join(",",values),args);
        return (result > 0) ? true : false;
    }

    @Override
    public boolean update(int id, ModelUser user) {
        int result = 0;
        Object[] args = {user.getName(), user.getAge()};
        int[] types = {Types.VARCHAR,Types.INTEGER};
        result = template.update(SQL_UPDATE_USER,args,types);
        return (result > 0) ? true : false;
    }

    @Override
    public boolean remove(int id) {
        int result = 0;
        result = template.update(SQL_DELETE_USER, new Object[]{id},new int[]{Types.INTEGER});
        return (result > 0) ? true : false;
    }

    @Override
    public boolean remove() {
        int result = 0;
        result = template.update(SQL_DELETE_ALL);
        return result > 0 ? true : false;
    }
}
