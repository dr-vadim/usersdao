package daoImpl;

import Interfaces.Dao.AutoDao;
import models.ModelAuto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 17.12.2016.
 */
public class FileAutoDao implements AutoDao<ModelAuto> {
    private final String FILE_PATH = "./src/main/resources/auto.txt";
    private final String SEPARATOR = "\t";
    List<ModelAuto> mauto = null;

    FileAutoDao(){
        if(mauto == null || mauto.size() == 0){
            mauto = new ArrayList<>();
            read();
        }
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
                    mauto.add(new ModelAuto(id, model,color,userId));
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
            for (ModelAuto ma: mauto) {
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

    public List<ModelAuto> get() {
        if(mauto == null)
            read();
        return mauto;
    }

    public List<ModelAuto> getByCat(int id) {
        if(mauto == null)
            read();
        List<ModelAuto> malist = new ArrayList<>();
        for (ModelAuto ma: mauto) {
            if(id == ma.getUser())
                malist.add(ma);
        }
        return malist;
    }

    public ModelAuto get(int id) {
        if(mauto == null)
            read();

        for (ModelAuto ma: mauto) {
            if(id == ma.getId()) return ma;
        }
        return null;
    }

    public boolean add(List<ModelAuto> elements){
        boolean result = false;
        if(elements != null && elements.size() > 0){
            result = mauto.addAll(elements);
            writeFile();
        }
        return result;
    }

    public boolean add(ModelAuto element){
        boolean result = false;
        if(element != null){
            result = mauto.add(element);
            writeFile();
        }
        return result;
    }

    public boolean update(int id,ModelAuto auto){
        boolean result = false;
        List<ModelAuto> temp = new ArrayList<>(mauto);
        int index = 0;
        for (ModelAuto ma: mauto) {
            if(id == ma.getId()){
                auto.setId(id);
                System.out.println("Update method");
                temp.set(index, auto);
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
        List<ModelAuto> temp = new ArrayList<>(mauto);
        for (ModelAuto ma: mauto) {
            if(id == ma.getId()){
                result = temp.remove(ma);
            }
        }
        mauto = temp;
        if(result) writeFile();
        return result;
    }

    public boolean removeByCat(int catId){
        boolean result = false;
        if(mauto == null)
            read();
        List<ModelAuto> temp = new ArrayList<>(mauto);
        for (ModelAuto ma: mauto) {
            if(catId == ma.getUser()){
                result = temp.remove(ma);
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
