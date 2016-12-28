package factories;

import Interfaces.Dao.AutoDao;
import Interfaces.Models.Model;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Properties;

/**
 * Created by User on 28.12.2016.
 */
public class AutoFactory<T extends Model> {
    private static AutoFactory instance;
    private static final String PROPERTIES_PATH = "./src/main/resources/context.properties";

    private AutoDao<T> auto;

    static {
        instance = new AutoFactory();
    }

    private AutoFactory(){
        Properties prop = new Properties();

        try(FileInputStream fin = new FileInputStream(PROPERTIES_PATH)){
            prop.load(fin);
            String dataType = prop.getProperty("data.type");
            String autoClassName = prop.getProperty("auto.class."+dataType);
            auto = (AutoDao<T>) Class.forName(autoClassName).newInstance();
            if(dataType.equals("jdbc")){
                Field connDbF = auto.getClass().getDeclaredField("JDBC_CONNECTION_DB");
                String connSting = prop.getProperty("connection.db");
                String dbName = prop.getProperty("db.name");
                setFinalStatic(connDbF,connSting+dbName);

                Field userF = auto.getClass().getDeclaredField("JDBC_CONNECTION_USER");
                setFinalStatic(userF,prop.getProperty("db.username"));

                Field passF = auto.getClass().getDeclaredField("JDBC_CONNECTION_PASS");
                setFinalStatic(passF,prop.getProperty("db.password"));
            }
        }catch (IOException | IllegalAccessException | InstantiationException | ClassNotFoundException | NoSuchFieldException e){
            throw new IllegalArgumentException(e);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static AutoFactory getInstance(){
        return instance;
    }

    public AutoDao getAuto(){
        return auto;
    }

    static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, newValue);
    }
}
