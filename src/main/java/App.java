import Interfaces.Dao.AutoDao;
import Interfaces.Dao.UserDao;
import daoImpl.PDbAutoDao;
import daoImpl.PDbUserDao;
import factories.AutoFactory;
import factories.UsersFactory;
import models.ModelAuto;
import models.ModelUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 16.12.2016.
 */
public class App {
    public static void main(String[] args) {
        //PDbAutoDao pad = new PDbAutoDao();
        AutoDao<ModelAuto> autoD = AutoFactory.getInstance().getAuto();
        UserDao<ModelUser> userD = UsersFactory.getInstance().getUsers();
        userD.read();
        autoD.read();
        Service service = new Service(userD);
        boolean registred = service.isRegistred(new ModelUser("Vadim",26));
        System.out.println(registred);
        //userD.add(new models.ModelUser("Sveta",25));
        /*boolean result = autoD.add(new models.ModelAuto("Renault","red",7));
        System.out.println("Add auto result:"+result);
        result = autoD.add(new models.ModelAuto("Mercedes","red",7));
        System.out.println("Add auto result:"+result);*/
        //autoD.removeByCat(7);
        //autoD.add(new models.ModelAuto("Mercedes","red",5));
        //autoD.remove(18);
        //autoD.update(19, new ModelAuto("Mercedes","white",5));

        for (ModelUser u: userD.get()) {
            List<ModelAuto> al = autoD.getByCat(u.getId());
            if(al != null && al.size() > 0)
                userD.addAuto(al,u.getId());
        }

        List<ModelUser> listUsers = new ArrayList<>(userD.get());
        for(ModelUser u: listUsers){
            System.out.println(u);
            List<ModelAuto> autos = u.getAuto();
            if(autos != null && autos.size() > 0) {
                for (ModelAuto a : autos) {
                    System.out.println("    " + a);
                }
            }
        }

    }
}
