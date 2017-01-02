package models;

import interfaces.models.Model;

/**
 * Created by User on 16.12.2016.
 */
public class ModelAuto implements Model {
    private final String SEPARATOR = "\t";
    private int id;
    private String model;
    private String color;
    private ModelUser user;

    public static enum Fields{
        ID, MODEL, COLOR, USER_ID
    }

    public ModelAuto(int id, String model, String color, ModelUser user){
        this.id = id;
        this.model = model;
        this.color = color;
        this.user = user;
    }

    public ModelAuto(String model, String color, ModelUser user){
        this.model = model;
        this.color = color;
        this.user = user;
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

    public ModelUser getUser() {
        return user;
    }

    public void setUser(ModelUser user) {
        this.user = user;
    }

    public String toString(){
        return id+SEPARATOR+model+SEPARATOR+color+SEPARATOR+user.getId();
    }
}
