package factories;

import Interfaces.Dao.UserDao;
import Interfaces.Models.Model;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Properties;

/**
 * Created by User on 28.12.2016.
 */
public class UsersFactory<T extends Model> {
    private static UsersFactory instance;
    private static final String PROPERTIES_PATH = "./src/main/resources/context.properties";

    private UserDao<T> users;

    static {
        instance = new UsersFactory();
    }

    private UsersFactory(){
        Properties prop = new Properties();
        try(FileInputStream fin = new FileInputStream(PROPERTIES_PATH)){
            prop.load(fin);
            String dataType = prop.getProperty("data.type");
            String userClassName = prop.getProperty("user.class."+dataType);
            users = (UserDao<T>) Class.forName(userClassName).newInstance();
            if(dataType.equals("jdbc")){
                System.out.println("data type is jdbc");
                Field connDbF = users.getClass().getDeclaredField("JDBC_CONNECTION_DB");
                String connSting = prop.getProperty("connection.db");
                String dbName = prop.getProperty("db.name");
                setFinalStatic(connDbF,connSting+dbName);

                Field userF = users.getClass().getDeclaredField("JDBC_CONNECTION_USER");
                setFinalStatic(userF,prop.getProperty("db.username"));

                Field passF = users.getClass().getDeclaredField("JDBC_CONNECTION_PASS");
                passF.setAccessible(true);
                setFinalStatic(passF,prop.getProperty("db.password"));
            }
        }catch (IOException | IllegalAccessException | InstantiationException | ClassNotFoundException | NoSuchFieldException e){
            throw new IllegalArgumentException(e);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static UsersFactory getInstance(){
        return instance;
    }

    public UserDao<T> getUsers(){
        return users;
    }

    static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, newValue);
    }
}
