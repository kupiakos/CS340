package server.db.mongodb;

import server.db.IGameDAO;
import server.db.IPersistenceProvider;
import server.db.IUserDAO;

/**
 * Created by kevin on 12/7/16.
 */
public class MongoProvider implements IPersistenceProvider {
    @Override
    public boolean createDB() {
        return false;
    }

    @Override
    public boolean startTransaction() {
        return false;
    }

    @Override
    public boolean finishTransaction() {
        return false;
    }

    @Override
    public boolean clearDB() {
        return false;
    }

    @Override
    public IUserDAO getUserDAO() {
        return null;
    }

    @Override
    public IGameDAO getGameDAO() {
        return null;
    }
}