package factories;

import Interfaces.Dao.UserDao;
import Interfaces.Models.Model;

import java.io.FileInputStream;
import java.io.IOException;
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
            String userClassName = prop.getProperty("user.class");
            users = (UserDao<T>) Class.forName(userClassName).newInstance();
        }catch (IOException | IllegalAccessException | InstantiationException | ClassNotFoundException e){
            throw new IllegalArgumentException(e);
        }
    }

    public static UsersFactory getInstance(){
        return instance;
    }

    public UserDao<T> getUsers(){
        return users;
    }
}
