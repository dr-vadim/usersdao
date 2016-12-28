package factories;

import Interfaces.Dao.AutoDao;
import Interfaces.Models.Model;

import java.io.FileInputStream;
import java.io.IOException;
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
            String autoClassName = prop.getProperty("auto.class");
            auto = (AutoDao<T>) Class.forName(autoClassName).newInstance();
        }catch (IOException | IllegalAccessException | InstantiationException | ClassNotFoundException e){
            throw new IllegalArgumentException(e);
        }
    }

    public static AutoFactory getInstance(){
        return instance;
    }

    public AutoDao getAuto(){
        return auto;
    }
}
