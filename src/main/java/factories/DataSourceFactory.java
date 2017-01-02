package factories;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DataSourceFactory {
    private static final DataSourceFactory instance;
    private static final String PROPERTIES_PATH = "./src/main/resources/context.properties";

    private DataSource dataSource;
    static{
        instance = new DataSourceFactory();
    }

    private DataSourceFactory(){
        Properties prop = new Properties();
        try(FileInputStream fin = new FileInputStream(PROPERTIES_PATH)){
            prop.load(fin);
            String dbConnStr = prop.getProperty("connection.db");
            String dbUserName = prop.getProperty("db.username");
            String dbPass = prop.getProperty("db.password");
            String dbDriver = prop.getProperty("db.driver");

            DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
            driverManagerDataSource.setDriverClassName(dbDriver);
            driverManagerDataSource.setUrl(dbConnStr);
            driverManagerDataSource.setUsername(dbUserName);
            driverManagerDataSource.setPassword(dbPass);
            dataSource = driverManagerDataSource;

        }catch (IOException e){
            throw new IllegalArgumentException(e);
        }
    }

    public static DataSourceFactory getInstance(){
        return instance;
    }

    public DataSource getDataSource(){
        return dataSource;
    }
}
