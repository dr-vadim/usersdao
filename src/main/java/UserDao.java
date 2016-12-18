import java.util.List;

/**
 * Created by User on 17.12.2016.
 */
public interface UserDao<T extends Model> {
    void readFile();
    List<T> getAll();
    default List<T> getAllByCat(int id){
        return null;
    }
    T getById(int id);
    boolean add(List<T> element);
    boolean add(T element);
    boolean update(int id, T element);
    boolean remove(int id);
    void removeAll();
}
