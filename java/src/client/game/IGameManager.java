package client.game;

import client.poller.Poller;
import client.utils.ServerAsyncHelper;
import shared.IServer;
import shared.definitions.PlayerIndex;
import shared.facades.FacadeManager;
import shared.models.game.ClientModel;
import shared.models.game.Player;
import shared.models.games.PlayerInfo;

import java.util.Observer;

/**
 * @author audakel on 10/6/16.
 */
public interface IGameManager {

    static void setInstance(GameManager sm) {

    }

    /**
     * Lets you grab the facade for the game, allows access to the current games model
     *
     * @return FacadeManager with current ClientModel
     */
    FacadeManager getFacade();

    /**
     * Starts the Poller, creates one if needed
     */
    void startPoller(ClientModel cm);

    /**
     * Stops the Poller
     */
    void stopPoller();

    /**
     * When the poller finds out the server has a new version it will call this
     * function to update the client game
     *
     * @param cm the new clientModel
     */
    void updateGameManager(ClientModel cm);

    /**
     * Grabs a "server" for us so we can talk with the real server
     *
     * @post a server we can use
     */
    IServer getServer();

    void setServer(IServer server);

    Poller getPoller();

    void setPoller(Poller p);

    ClientModel getClientModel();

    void setClientModel(ClientModel cm);

    PlayerInfo getPlayerInfo();

    void setPlayerInfo(PlayerInfo playerInfo);

    Player getPlayer();

    ServerAsyncHelper getAsync();

    void setAsync(ServerAsyncHelper async);

    void addObserver(Observer o);

    void deleteObserver(Observer o);

    PlayerIndex getThisPlayerIndex();

    void setThisPlayerIndex(PlayerIndex thisPlayerIndex);
}
