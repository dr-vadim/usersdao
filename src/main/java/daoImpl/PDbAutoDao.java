package daoImpl;

import com.sun.rowset.internal.Row;
import interfaces.dao.AutoDao;
import interfaces.models.Model;
import models.ModelAuto;
import models.ModelUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PDbAutoDao implements AutoDao {
    // language=SQL
    private static final String SQL_SELECT_ALL_AUTO = "SELECT * FROM auto";
    //language=SQL
    private static final String SQL_SELECT_AUTO_BY_USER = "SELECT * FROM auto WHERE user_id = ?";
    //language=SQL
    private static final String SQL_SELECT_AUTO_BY_ID = "SELECT * FROM auto WHERE id = ?";
    //language=SQL
    private static final String SQL_UPDATE_AUTO = "UPDATE auto SET name=?,color=?,user_id=? WHERE id=?";
    //language=SQL
    private static final String SQL_INSERT_NEW_AUTO = "INSERT INTO auto (name,color,user_id) VALUES(?,?,?)";
    //language=SQL
    private static final String SQL_DELETE_ALL_AUTO = "DELETE FROM auto";
    //language=SQL
    private static final String SQL_DELETE_AUTO = "DELETE FROM auto WHERE id=?";
    //language=SQL
    private static final String SQL_DELETE_AUTO_BY_USER = "DELETE FROM auto WHERE user_id=?";

    private Map<Integer,ModelAuto> mauto;
    private JdbcTemplate template;

    public PDbAutoDao(DataSource dataSource){
        template = new JdbcTemplate(dataSource);
        mauto = new HashMap<>();
    }

    private RowMapper<ModelAuto> autoRowMapper = (ResultSet rs, int i) -> {

        ModelAuto auto = new ModelAuto(rs.getInt("id"),
                rs.getString("name"), rs.getString("color"),
                new ModelUser(rs.getInt("user_id")));
        mauto.put(auto.getId(),auto);
        return auto;
    };

    private RowMapper<ModelAuto> autoByUserRowMapper = (ResultSet rs, int i) -> {

        ModelAuto auto = new ModelAuto(rs.getInt("id"),
              rs.getString("name"), rs.getString("color"), null)  ;
        int id = auto.getId();
        mauto.put(id,auto);
        return auto;
    };

    public void read() {
        template.query(SQL_SELECT_ALL_AUTO,new Object[]{},autoRowMapper);
    }

    @Override
    public Map<Integer,ModelAuto> get() {
        List<ModelAuto> autoList = template.query(SQL_SELECT_ALL_AUTO,new Object[]{},autoRowMapper);
        Map<Integer, ModelAuto> autoMap = autoList.stream().collect(Collectors.toMap(i -> i.getId(), i -> i));
        return autoMap;
    }

    @Override
    public Map<Integer,ModelAuto> getByUser(int id) {
        List<ModelAuto> autoList = template.query(SQL_SELECT_AUTO_BY_USER,new Object[]{id},autoByUserRowMapper);
        Map<Integer, ModelAuto> autoMap = autoList.stream().collect(Collectors.toMap(i -> i.getId(), i -> i));
        return autoMap;
    }

    @Override
    public ModelAuto get(int id) {
        return template.queryForObject(SQL_SELECT_AUTO_BY_ID,new Object[]{id},autoRowMapper);
    }

    @Override
    public boolean add(Map<Integer,ModelAuto> elements) {
        int count = elements.size();
        int result = 0;
        for(ModelAuto auto: elements.values()) {
            Object[] args = {auto.getModel(),auto.getColor(),auto.getUser().getId()};
            int[] types = { Types.VARCHAR, Types.VARCHAR, Types.INTEGER };
            result += template.update(SQL_INSERT_NEW_AUTO,args,types);
        }
        return (count == result) ? true : false;
    }

    @Override
    public boolean add(ModelAuto auto) {
        int result = 0;
        Object[] args = {auto.getModel(),auto.getColor(),auto.getUser().getId()};
        int[] types = { Types.VARCHAR, Types.VARCHAR, Types.INTEGER };
        result = template.update(SQL_INSERT_NEW_AUTO,args,types);
        return (result > 0) ? true : false;
    }

    @Override
    public boolean update(int id, ModelAuto auto) {
        int result = 0;
        Object[] args = {auto.getModel(),auto.getColor(),auto.getUser().getId(), id};
        int[] types = { Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER };
        result = template.update(SQL_UPDATE_AUTO,args,types);
        return (result > 0) ? true : false;
    }

    @Override
    public boolean remove(int id) {
        int result = 0;
        Object[] args = {id};
        int[] types = { Types.INTEGER };
        result = template.update(SQL_DELETE_AUTO,args,types);
        return (result > 0) ? true : false;
    }

    @Override
    public boolean removeByUser(int userId) {
        int result = 0;
        Object[] args = {userId};
        int[] types = { Types.INTEGER };
        result = template.update(SQL_DELETE_AUTO_BY_USER,args,types);
        return (result > 0) ? true : false;
    }

    @Override
    public boolean remove() {
        int result = 0;
        result = template.update(SQL_DELETE_ALL_AUTO);
        return (result > 0) ? true : false;
    }
}
