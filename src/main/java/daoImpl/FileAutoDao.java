package daoImpl;

import interfaces.dao.AutoDao;
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
public class FileAutoDao implements AutoDao {
    private final String FILE_PATH = "./src/main/resources/auto.txt";
    private final String SEPARATOR = "\t";
    Map<Integer,ModelAuto> mauto = null;

    public FileAutoDao(){
        mauto = new HashMap<>();
        read();
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
                    int id = Integer.valueOf(strs[ModelAuto.Fields.ID.ordinal()]);
                    String model = strs[ModelAuto.Fields.MODEL.ordinal()];
                    String color = strs[ModelAuto.Fields.COLOR.ordinal()];
                    int userId = Integer.valueOf(strs[ModelAuto.Fields.USER_ID.ordinal()]);
                    mauto.put(id,new ModelAuto(id, model,color,new ModelUser(userId)));
                }catch(Exception e){
                    System.out.println(e);
                    continue;
                }
            }

        }catch (FileNotFoundException e){
            System.out.println("File "+FILE_PATH+" not found");
            throw new RuntimeException();
        }catch (IOException e){
            System.out.println("File "+FILE_PATH+" not found");
        }
    }

    public void writeFile(){
        if(mauto == null)
            read();

        try (FileWriter fw = new FileWriter(FILE_PATH)){

            String newTxt = "";
            for (ModelAuto ma: mauto.values()) {
                newTxt += ma.toString()+"\n";
            }
            try {
                fw.write(newTxt);
            } catch (IOException e) {
                System.out.println("Exception: "+e.getMessage());
            }
        }catch(FileNotFoundException e) {
            System.out.println("Exception: "+e.getMessage());
        }catch(IOException e) {
            System.out.println("Exception: "+e.getMessage());
        }

    }

    public Map<Integer,ModelAuto> get() {
        if(mauto == null)
            read();
        return mauto;
    }

    public Map<Integer,ModelAuto> getByUser(int id) {
        if(mauto == null)
            read();
        Map<Integer,ModelAuto> malist = new HashMap<>();
        for (ModelAuto ma: mauto.values()) {
            if(id == ma.getUser().getId())
                malist.put(id,ma);
        }
        return malist;
    }

    public ModelAuto get(int id) {
        if(mauto == null)
            read();

        for (ModelAuto ma: mauto.values()) {
            if(id == ma.getId()) return ma;
        }
        return null;
    }

    @Override
    public boolean add(Map<Integer,ModelAuto> elements){
        boolean result = false;
        if(elements != null && elements.size() > 0){
            mauto.putAll(elements);
            writeFile();
            return true;
        }
        return result;
    }

    public boolean add(ModelAuto element){
        boolean result = false;
        if(element != null){
            mauto.put(element.getId(),element);
            writeFile();
            return true;
        }
        return result;
    }

    public boolean update(int id,ModelAuto auto){
        boolean result = false;
        Map<Integer,ModelAuto> temp = new HashMap<>(mauto);
        int index = 0;
        for (ModelAuto ma: mauto.values()) {
            if(id == ma.getId()){
                auto.setId(id);
                System.out.println("Update method");
                temp.replace(id,auto);
                result = true;
            }
            index++;
        }
        mauto = temp;
        if(result) writeFile();
        return result;
    }

    public boolean remove(int id) {
        boolean result = false;
        if(mauto == null)
            read();
        Map<Integer,ModelAuto> temp = new HashMap<>(mauto);
        for (ModelAuto ma: mauto.values()) {
            if(id == ma.getId()){
                ModelAuto auto = temp.remove(id);
                result = auto != null ? true : false;
            }
        }
        mauto = temp;
        if(result) writeFile();
        return result;
    }

    @Override
    public boolean removeByUser(int userId){
        boolean result = false;
        if(mauto == null)
            read();
        Map<Integer,ModelAuto> temp = new HashMap<>(mauto);
        for (ModelAuto ma: mauto.values()) {
            if(userId == ma.getUser().getId()){
                result = temp.remove(ma) != null ? true : false;
            }
        }
        mauto = temp;
        if(result) writeFile();
        return result;
    }

    public boolean remove() {
        if(mauto == null)
            read();
        mauto.clear();
        writeFile();
        return true;
    }
}
