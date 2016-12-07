package server.db;

import server.models.GameModel;
import shared.models.ICommandAction;

import java.util.List;

/**
 * Created by elija on 12/2/2016.
 */
public interface IGameDAO extends IDAO<GameModel> {

    List<ICommandAction> findAllCommands();

    boolean insertCommand(ICommandAction command, int gameId);

    boolean flushCommands();
}
