package interfaces.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by User on 26.12.2016.
 */
public interface Dao<T> {
    void read();
    Map<Integer,T> get();
    T get(int id);
    boolean add(T elem);
    boolean add(Map<Integer,T> elem);
    boolean update(int id, T elem);
    boolean remove(int id);
    boolean remove();
}
