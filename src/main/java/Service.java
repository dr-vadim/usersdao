import Interfaces.Dao.UserDao;
import models.ModelUser;

import java.util.List;

/**
 * Created by User on 28.12.2016.
 */
public class Service {

    UserDao<ModelUser> userDao;

    public Service(UserDao userDao){
        this.userDao = userDao;
    }

    public boolean isRegistred(ModelUser user){
        List<ModelUser> users = userDao.get();
        for(ModelUser u: users){
            if((u.getName() == user.getName()) && (u.getAge() == user.getAge())){
                return true;
            }
        }
        userDao.add(user);
        return false;
    }
}
