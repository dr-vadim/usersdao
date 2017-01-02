package factories;

import interfaces.dao.AutoDao;
import interfaces.dao.UserDao;
import services.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class ServiceFactory implements Factory{
    private static final ServiceFactory instance;
    private Service service;

    static{
        instance = new ServiceFactory();
    }

    private ServiceFactory(){
        Properties prop = new Properties();
        try(FileInputStream fin = new FileInputStream(PROPERTIES_PATH)){
            prop.load(fin);
            String className = prop.getProperty("service.class");
            Constructor<?> constructor = Class.forName(className).getConstructor(UserDao.class,AutoDao.class);
            UserDao userDao = UsersFactory.getInstance().getUsers();
            AutoDao autoDao = AutoFactory.getInstance().getAuto();
            service = (Service)constructor.newInstance(userDao,autoDao);
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException |
                InstantiationException | InvocationTargetException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static ServiceFactory getInstance() {
        return instance;
    }

    public Service getService(){
        return service;
    }
}
