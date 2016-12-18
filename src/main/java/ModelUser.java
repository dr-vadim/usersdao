import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by User on 16.12.2016.
 */
public class ModelUser implements Model{
    private final String SEPARATOR = "\t";
    private int id;
    private String name;
    private int age;
    private List<ModelAuto> auto = null;

    public static enum Fields{
        ID, NAME, AGE
    }

    ModelUser(int id, String name, int age, List<ModelAuto> auto){
        this(id,name,age);
        this.auto = new ArrayList<>(auto);
    }

    ModelUser(int id, String name, int age, ModelAuto auto){
        this(id,name,age);
        this.auto = new ArrayList<>();
        this.auto.add(auto);
    }

    ModelUser(int id, String name, int age){
        this.id = id;
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

    public void setAuto(List<ModelAuto> auto) {
        this.auto = auto;
    }

    public String toString(){
        return id+SEPARATOR+name+SEPARATOR+age;
    }
}
