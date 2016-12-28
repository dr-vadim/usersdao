package Interfaces.Dao;

import Interfaces.Models.Model;

import java.util.List;

/**
 * Created by User on 26.12.2016.
 */
public interface Dao<T extends Model> {
    List<T> get();
    T get(int id);
    boolean add(T elem);
    boolean add(List<T> elem);
    boolean update(int id, T elem);
    boolean remove(int id);
    boolean remove();
}
