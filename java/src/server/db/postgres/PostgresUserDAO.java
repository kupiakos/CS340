package server.db.postgres;

import server.db.IUserDAO;
import server.models.User;

import java.sql.Connection;
import java.util.List;

/**
 * Created by elija on 12/2/2016.
 */
public class PostgresUserDAO extends IUserDAO {
    private Connection db = null;

    public PostgresUserDAO(Connection db) {
        this.db = db;
    }

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public boolean insert(User obj) {
        return false;
    }

    @Override
    public boolean update(User obj) {
        return false;
    }

    @Override
    public boolean delete(User obj) {
        return false;
    }
}
