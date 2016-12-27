import java.util.List;

/**
 * Created by User on 16.12.2016.
 */
public class ModelAuto implements Model{
    private final String SEPARATOR = "\t";
    private int id;
    private String model;
    private String color;
    private int userId;

    public static enum Fields{
        ID, MODEL, COLOR, USER_ID
    }
    ModelAuto(){

    }

    ModelAuto(int id, String model, String color, int userId){
        this.id = id;
        this.model = model;
        this.color = color;
        this.userId = userId;
    }

    ModelAuto(String model, String color, int userId){
        this.model = model;
        this.color = color;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getUser() {
        return userId;
    }

    public void setUser(int user) {
        this.userId = user;
    }

    public String toString(){
        return id+SEPARATOR+model+SEPARATOR+color+SEPARATOR+userId;
    }
}
