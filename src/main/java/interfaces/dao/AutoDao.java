package interfaces.dao;

import models.ModelAuto;

import java.util.List;
import java.util.Map;

/**
 * Created by User on 26.12.2016.
 */
public interface AutoDao extends Dao<ModelAuto> {
    Map<Integer,ModelAuto> getByUser(int userId);
    boolean removeByUser(int userId);
}
