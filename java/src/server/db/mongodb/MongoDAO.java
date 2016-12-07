package server.db.mongodb;

import server.db.IDAO;
import server.db.IDAOObject;

import java.util.List;

/**
 * Created by kevin on 12/7/16.
 */
public abstract class MongoDAO<T extends IDAOObject> implements IDAO<T> {


    @Override
    public T findById(int id) {
        return null;
    }

    @Override
    public List<T> findAll() {
        return null;
    }

    @Override
    public boolean insert(T obj) {
        return false;
    }

    @Override
    public boolean update(T obj) {
        return false;
    }

    @Override
    public boolean delete(T obj) {
        return false;
    }
}
