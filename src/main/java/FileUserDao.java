import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 17.12.2016.
 */
public class FileUserDao implements UserDao<ModelUser> {
    private final String FILE_PATH = "./src/main/resources/users.txt";
    private final String SEPARATOR = "\t";
    private List<ModelUser> usersList = null;

    FileUserDao(){
        if(usersList == null || usersList.size() == 0){
            usersList = new ArrayList<>();
            readFile();
        }
    }

    FileUserDao(List<ModelUser> users){
        usersList = new ArrayList<ModelUser>(users);
    }

    public void readFile() {
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
                    FileAutoDao fad = new FileAutoDao();
                    List<ModelAuto> ma = new ArrayList<>(fad.getAllByCat(id));
                    usersList.add(new ModelUser(id, name,age,ma));
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
            readFile();

        try (FileWriter fw = new FileWriter(FILE_PATH)){
            String newStr = "";
            for (ModelUser mu: usersList) {
                newStr += mu.toString()+"\n";
            }
            System.out.println(newStr);
            try {
                fw.write(newStr);
            } catch (IOException e) {
                System.out.println("Exception: "+e.getMessage());
            }
            //bw.write("");
        } catch(FileNotFoundException e) {
            System.out.println("Exception: "+e.getMessage());
        } catch (IOException e) {
            System.out.println("Exception: "+e.getMessage());
        }

    }

    public List<ModelUser> getAll() {
        if(usersList == null)
            readFile();
        return usersList;
    }

    public ModelUser getById(int id) {
        if(usersList == null)
            readFile();

        for (ModelUser mu: usersList) {
            if(id == mu.getId())
                return mu;
        }
        return null;
    }

    public boolean add(List<ModelUser> users){
        boolean result = false;
        if(users != null && users.size() > 0){
            FileAutoDao fad = new FileAutoDao();
            for(ModelUser mu: users){
                fad.add(mu.getAuto());
            }
            result = usersList.addAll(users);
            writeFile();
        }
        return result;
    }

    public boolean add(ModelUser user){
        boolean result = false;
        if(user != null){
            FileAutoDao fad = new FileAutoDao();
            List<ModelAuto> mal = user.getAuto();
            if(mal != null && mal.size() > 0)
                fad.add(mal);
            result = usersList.add(user);
            writeFile();
        }
        return result;
    }

    public boolean update(int id, ModelUser user){
        boolean result = false;
        List<ModelUser> temp = new ArrayList<>(usersList);
        int index = 0;
        for(ModelUser u: usersList){
            if(u.getId() == id){
                user.setId(id);
                temp.set(index,user);
                result = true;
            }
            index++;
        }
        usersList = temp;
        if(result) writeFile();
        return result;
    }

    public boolean remove(int id) {
        boolean result = false;
        if(usersList == null)
            readFile();
        FileAutoDao fad = new FileAutoDao();
        List<ModelUser> tmp = new ArrayList<>(usersList);
        for (ModelUser mu: usersList) {
            if(id == mu.getId()){
                fad.removeByCat(mu.getId());
                result = tmp.remove(mu);
            }
        }
        usersList = tmp;
        if(result) writeFile();
        return result;
    }

    public void removeAll() {
        if(usersList == null)
            readFile();
        FileAutoDao fad = new FileAutoDao();
        fad.removeAll();
        usersList.clear();
        writeFile();
    }
}
