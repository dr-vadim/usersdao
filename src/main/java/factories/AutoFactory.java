package factories;

import interfaces.dao.AutoDao;
import interfaces.models.Model;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Properties;

/**
 * Created by User on 28.12.2016.
 */
public class AutoFactory<T extends Model> {
    private static AutoFactory instance;
    private static final String PROPERTIES_PATH = "./src/main/resources/context.properties";

    private AutoDao auto;

    static {
        instance = new AutoFactory();
    }

    private AutoFactory(){
        Properties prop = new Properties();

        try(FileInputStream fin = new FileInputStream(PROPERTIES_PATH)){
            prop.load(fin);
            String dataType = prop.getProperty("data.type");
            String autoClassName = prop.getProperty("auto.class."+dataType);
            if(dataType.equals("jdbc")){
                Constructor<?> constructor = Class.forName(autoClassName).getConstructor(DataSource.class);
                auto = (AutoDao) constructor.newInstance(DataSourceFactory.getInstance().getDataSource());
            }else{
                auto = (AutoDao) Class.forName(autoClassName).newInstance();
            }
        }catch (IOException | IllegalAccessException | InstantiationException | ClassNotFoundException e){
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
