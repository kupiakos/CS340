package server.db;

/**
 * Created by elija on 12/2/2016.
 */
public interface IPersistenceProvider {

    boolean createDB();

    boolean startTransaction();

    boolean finishTransaction();

    boolean clearDB();

    IUserDAO getUserDAO();

    IGameDAO getGameDAO();

}
