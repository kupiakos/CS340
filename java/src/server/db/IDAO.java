package server.db;

import java.util.List;

/**
 * Created by elija on 12/2/2016.
 */
public interface IDAO<T extends IDAOObject> {

    T findById(int id);

    List<T> findAll();

    boolean insert(T obj);

    boolean update(T obj);

    boolean delete(T obj);

}
