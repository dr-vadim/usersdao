package Interfaces.Dao;

import Interfaces.Models.Model;

import java.util.List;

/**
 * Created by User on 17.12.2016.
 */
public interface UserDao<T extends Model> extends Dao<T> {
    <A extends Model> void addAuto(A auto, int id);
    <A extends Model> void addAuto(List<A> auto, int id);
}
