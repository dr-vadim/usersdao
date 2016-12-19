import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by User on 16.12.2016.
 */
public class App {
    public static void main(String[] args) {
        FileAutoDao fad = new FileAutoDao();
        FileUserDao fud = new FileUserDao();
        for (ModelUser u: fud.getAll()) {
            List<ModelAuto> al = fad.getAllByCat(u.getId());
            if(al != null && al.size() > 0)
                fud.addAuto(al,u.getId());
        }
        UserDao ud;

        ud = fud;
        List<ModelUser> listUsers = new ArrayList<>(ud.getAll());
        for(ModelUser u: listUsers){
            System.out.println(u);
            List<ModelAuto> autos = u.getAuto();
            if(autos != null && autos.size() > 0) {
                for (ModelAuto a : autos) {
                    System.out.println("    " + a);
                }
            }
        }
/*
        ud = fad;
        System.out.println("-- Get auto by user with id=2 --");
        List<ModelAuto> autoList = new ArrayList<>(ud.getAllByCat(2));
        for(ModelAuto a: autoList){
                System.out.println(a);
        }
        System.out.println("-- Get auto by id=4 --");
        ModelAuto auto = (ModelAuto) ud.getById(4);
        System.out.println(auto);

        ud = fud;
        System.out.println("-- Get user by id=9 --");
        ModelUser user = (ModelUser) ud.getById(9);
        System.out.println(user);*/
/*
        System.out.println("-- Add users (Ann and Micke) --");
        List<ModelUser> lu = new ArrayList<>();
        lu.add(new ModelUser(6, "Ann", 25));
        lu.add(new ModelUser(7, "Micke", 18));
        ud.add(lu);*/

        /*System.out.println("-- Update color car for user George --");
        ud = fad;
        ud.update(10,new ModelAuto(10, "Citroen", "black", 10));*/
        /*System.out.println("-- Update user name with id=9  --");
        ud = fud;
        ud.update(9, new ModelUser(10, "Saimon Black", 28));*/

        System.out.println("-- Remove user with id=11 with cars --");
        fad.removeByCat(11);
        ud = fud;
        ud.remove(11);

        System.out.println("Add user Valera and car Lexus");
        ud = fud;
        ud.add(new ModelUser(11, "Valera", 24));
        ud = fad;
        ModelAuto a = new ModelAuto(11,"Lexus","grey",11);
        ud.add(a);
        fud.addAuto(a,11);

    }
}
