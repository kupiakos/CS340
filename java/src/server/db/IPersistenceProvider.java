package server.db;

import server.plugin.IPlugin;

/**
 * Created by elija on 12/2/2016.
 */
public interface IPersistenceProvider extends IPlugin {

    boolean createDB();

    boolean startTransaction();

    boolean finishTransaction();

    boolean clearDB();

    IUserDAO getUserDAO();

    IGameDAO getGameDAO();

}
