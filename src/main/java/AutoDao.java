import java.util.List;

/**
 * Created by User on 26.12.2016.
 */
public interface AutoDao<T extends Model> extends Dao<T> {
    List<T> getByCat(int cat_id);
    boolean removeByCat(int cat_id);
}
