package server.db.mongodb;

import server.db.IGameDAO;
import server.models.GameModel;
import shared.models.ICommandAction;

import java.util.List;

/**
 * Created by kevin on 12/7/16.
 */
public class MongoGameDAO extends MongoDAO<GameModel> implements IGameDAO {
    @Override
    public List<ICommandAction> findAllCommands() {
        return null;
    }

    @Override
    public boolean insertCommand(ICommandAction command, int gameId) {
        return false;
    }

    @Override
    public boolean flushCommands() {
        return false;
    }
}
