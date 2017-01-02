package factories;

import interfaces.dao.UserDao;
import interfaces.models.Model;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Properties;

public class UsersFactory<T extends Model> {
    private static UsersFactory instance;
    private static final String PROPERTIES_PATH = "./src/main/resources/context.properties";

    private UserDao users;

    static {
        instance = new UsersFactory();
    }

    private UsersFactory(){
        Properties prop = new Properties();
        try(FileInputStream fin = new FileInputStream(PROPERTIES_PATH)){
            prop.load(fin);
            String dataType = prop.getProperty("data.type");
            String userClassName = prop.getProperty("user.class."+dataType);

            if(dataType.equals("jdbc")){
                Constructor<?> constructor = Class.forName(userClassName).getConstructor(DataSource.class);
                users = (UserDao) constructor.newInstance(DataSourceFactory.getInstance().getDataSource());
            }else{
                users = (UserDao) Class.forName(userClassName).newInstance();
            }
        }catch (IOException | IllegalAccessException | InstantiationException | ClassNotFoundException e){
            throw new IllegalArgumentException(e);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static UsersFactory getInstance(){
        return instance;
    }

    public UserDao getUsers(){
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
