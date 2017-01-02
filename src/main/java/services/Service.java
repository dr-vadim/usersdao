package services;

import factories.ServiceFactory;
import interfaces.dao.AutoDao;
import interfaces.dao.UserDao;
import models.ModelAuto;
import models.ModelUser;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by User on 28.12.2016.
 */
public class Service {

    UserDao userDao;
    AutoDao autoDao;

    public Service(UserDao userDao, AutoDao autoDao){
        this.userDao = userDao;
        this.autoDao = autoDao;
    }

    public boolean isRegistred(String login){
        Collection<ModelUser> usersList = userDao.get().values();
        for(ModelUser user: usersList){
            if(user.getName().equals(login))
                return true;
        }
        return false;
    }

    public Map<Integer,ModelUser> getUsers(){
        Map<Integer,ModelUser> users = userDao.get();
        Collection<ModelAuto> autos = autoDao.get().values();
        for (ModelAuto auto: autos){
            users.get(auto.getUser().getId()).setAuto(auto);
        }
        return users;
    }

    public ModelUser getUser(int id){
        ModelUser user = userDao.get(id);
        List<ModelAuto> autos = (List<ModelAuto>)autoDao.getByUser(id).values();
        user.setAuto(autos);
        return user;
    }
}
