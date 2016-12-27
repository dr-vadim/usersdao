import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by User on 16.12.2016.
 */
public class App {
    public static void main(String[] args) {
        PDbAutoDao pad = new PDbAutoDao();
        /*
        AutoDao<ModelAuto> autoD = new FileAutoDao();
        UserDao<ModelUser> userD = new FileUserDao();*/

        AutoDao<ModelAuto> autoD = new PDbAutoDao();
        UserDao<ModelUser> userD = new PDbUserDao();

        //userD.add(new ModelUser("Sveta",25));
        /*boolean result = autoD.add(new ModelAuto("Renault","red",7));
        System.out.println("Add auto result:"+result);
        result = autoD.add(new ModelAuto("Mercedes","red",7));
        System.out.println("Add auto result:"+result);*/
        //autoD.removeByCat(7);
        //autoD.add(new ModelAuto("Mercedes","red",5));
        //autoD.remove(18);
        autoD.update(19, new ModelAuto("Mercedes","white",5));

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
