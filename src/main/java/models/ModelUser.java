package models;

import interfaces.models.Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by User on 16.12.2016.
 */
public class ModelUser implements Model {
    private final String SEPARATOR = "\t";
    private int id;
    private String name;
    private int age;
    private List<ModelAuto> auto = new ArrayList<>();;

    public static enum Fields{
        ID, NAME, AGE
    }

    public ModelUser(int id){
        this.id = id;
    }

    public ModelUser(int id, String name, int age, List<ModelAuto> auto){
        this(id,name,age);
        this.auto.addAll(auto);
    }

    public ModelUser(int id, String name, int age, ModelAuto auto){
        this(id,name,age);
        this.auto.add(auto);
    }

    public ModelUser(int id, String name, int age){
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public ModelUser(String name, int age){
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<ModelAuto> getAuto() {
        return auto;
    }

    public <A extends Model> void setAuto(List<A> auto) {
        this.auto.addAll((Collection<? extends ModelAuto>) auto);
    }

    public <A extends Model> void setAuto(A auto){
        this.auto.add((ModelAuto) auto);
    }

    public String toString(){
        return id+SEPARATOR+name+SEPARATOR+age;
    }
}
