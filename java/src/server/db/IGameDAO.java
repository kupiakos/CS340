package server.db;

import server.models.GameModel;
import shared.models.ICommandAction;

import java.util.List;

/**
 * Created by elija on 12/2/2016.
 */
public abstract class IGameDAO implements IDAO<GameModel> {

    public abstract List<ICommandAction> findAllCommands();

    public abstract boolean insertCommand(ICommandAction command, int gamId);

    public abstract boolean flushCommands();

}
