package server.db.postgres;

import server.db.IGameDAO;
import server.db.IPersistenceProvider;
import server.db.IUserDAO;

/**
 * Created by elija on 12/2/2016.
 */
public class PostgresProvider implements IPersistenceProvider {

    private PostgresUserDAO userDAO;
    private PostgresGameDAO gameDAO;

    public PostgresProvider() {
        userDAO = new PostgresUserDAO();
        gameDAO = new PostgresGameDAO();
    }

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
        return userDAO;
    }

    @Override
    public IGameDAO getGameDAO() {
        return gameDAO;
    }
}
