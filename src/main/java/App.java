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
        UserDao ud;

        ud = fud;
        List<ModelUser> listUsers = new ArrayList<>(ud.getAll());
        for(ModelUser u: listUsers){
            System.out.println(u);
            for(ModelAuto a: u.getAuto()){
                System.out.println("    "+a);
            }
        }

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
        System.out.println(user);

        /*System.out.println("-- Add users (Ann and Micke) --");
        List<ModelUser> lu = new ArrayList<>();
        lu.add(new ModelUser(6, "Ann", 25));
        lu.add(new ModelUser(7, "Micke", 18));
        ud.add(lu);*/
/*
        System.out.println("-- Add users (Ivan and Saimon) with cars --");
        List<ModelUser> lu = new ArrayList<>();
        lu.add(new ModelUser(8, "Ivan", 55, new ModelAuto(8, "Rols Roys", "grey", 8)));
        ud.add(lu);
        lu.clear();
        lu.add(new ModelUser(9, "Saimon", 21, new ModelAuto(8, "Aston Martin", "white", 9)));
        ud.add(lu);*/
        /*System.out.println("-- Add user George --");
        ud.add(new ModelUser(10, "George Smith", 58));*/
        /*System.out.println("-- Add car for user George --");
        ud = fad;
        ud.add(new ModelAuto(10, "Citroen", "black", 10));*/
        /*System.out.println("-- Update color car for user George --");
        ud = fad;
        ud.update(10,new ModelAuto(10, "Citroen", "black", 10));*/
        /*System.out.println("-- Update user name with id=9  --");
        ud = fud;
        ud.update(9, new ModelUser(10, "Saimon Black", 28));*/

        System.out.println("-- Remove user with id=9 and all his cars  --");
        ud = fud;
        ud.remove(9);


        //System.out.println(Arrays.toString(listUsers.toArray()));

        //System.out.println("-- Remove user with id=6 with cars --");

        //ud.remove(6);
/*
        listUsers = new ArrayList<>(ud.getAll());
        System.out.println(Arrays.toString(listUsers.toArray()));*/


    }
}
