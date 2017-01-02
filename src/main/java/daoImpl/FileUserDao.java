package daoImpl;

import interfaces.models.Model;
import interfaces.dao.UserDao;
import models.ModelAuto;
import models.ModelUser;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by User on 17.12.2016.
 */
public class FileUserDao implements UserDao {
    private final String FILE_PATH = "./src/main/resources/users.txt";
    private final String SEPARATOR = "\t";
    private Map<Integer,ModelUser> usersList = null;

    public FileUserDao(){
        usersList = new HashMap<>();
        read();
    }

    FileUserDao(Map<Integer,ModelUser> users){
        usersList = new HashMap<>(users);
    }

    public void read() {
        int i;

        try(FileReader fr = new FileReader(FILE_PATH)){
            BufferedReader br = new BufferedReader(fr);

            List<String> strList = new ArrayList<>();
            String line;
            try {
                while((line = br.readLine()) != null){
                    strList.add(line);
                }
            }catch (IOException e){
                System.out.println("Exception: " +e);
            }

            for (String str: strList){
                String[] strs = str.split(SEPARATOR);
                try {
                    int id = Integer.valueOf(strs[ModelUser.Fields.ID.ordinal()]);
                    String name = strs[ModelUser.Fields.NAME.ordinal()];
                    int age = Integer.valueOf(strs[ModelUser.Fields.AGE.ordinal()]);
                    usersList.put(id,new ModelUser(id, name,age));
                }catch(Exception e){
                    System.out.println("Exception: "+e.getMessage());
                    continue;
                }
            }
        }catch (FileNotFoundException e){
            System.out.println("File "+FILE_PATH+" not found");
        }catch(IOException e){
            System.out.println("Exception: "+e);
        }
    }


    private void writeFile() {
        if(usersList == null)
            read();

        try (FileWriter fw = new FileWriter(FILE_PATH)){
            String newStr = "";
            for (ModelUser mu: usersList.values()) {
                newStr += mu.toString()+"\n";
            }
            System.out.println(newStr);
            try {
                fw.write(newStr);
            } catch (IOException e) {
                System.out.println("Exception: "+e.getMessage());
            }
        } catch(FileNotFoundException e) {
            System.out.println("Exception: "+e.getMessage());
        } catch (IOException e) {
            System.out.println("Exception: "+e.getMessage());
        }

    }

    public Map<Integer,ModelUser> get() {
        if(usersList == null)
            read();
        return usersList;
    }

    public ModelUser get(int id) {
        if(usersList == null)
            read();

        for (ModelUser mu: usersList.values()) {
            if(id == mu.getId())
                return mu;
        }
        return null;
    }

    public boolean add(Map<Integer,ModelUser> users){
        boolean result = false;
        if(users != null && users.size() > 0){
            usersList.putAll(users);
            writeFile();
            result = true;
        }
        return result;
    }

    public boolean add(ModelUser user){
        boolean result = false;
        if(user != null){
            result = usersList.put(user.getId(),user) != null ? true : false;
            writeFile();
        }
        return result;
    }

    public void addAuto(List<ModelAuto> auto, int userId){
        usersList.get(userId).setAuto(auto);
    }

    public void addAuto(ModelAuto auto, int userId){
        usersList.get(userId).setAuto(auto);
    }

    public boolean update(int id, ModelUser user){
        boolean result = false;
        Map<Integer,ModelUser> temp = new HashMap<>(usersList);
        result = temp.replace(id,user) != null ? true : false;
        if(result){
            usersList = temp;
            writeFile();
        }
        return result;
    }

    public boolean remove(int id) {
        boolean result = false;
        if(usersList == null)
            read();
        Map<Integer,ModelUser> tmp = new HashMap<>(usersList);
        result = tmp.remove(id) != null ? true : false;

        if(result) {
            usersList = tmp;
            writeFile();
        }
        return result;
    }

    public boolean remove() {
        if(usersList == null)
            read();
        usersList.clear();
        writeFile();
        return true;
    }
}
