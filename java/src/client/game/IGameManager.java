package client.game;

import client.data.PlayerInfo;
import client.poller.Poller;
import client.server.MockProxy;
import client.utils.ServerAsyncHelper;
import shared.IServer;
import shared.facades.FacadeManager;
import shared.models.game.ClientModel;

/**
 * @author audakel on 10/6/16.
 */
interface IGameManager {
    /**
     * Lets you grab the game, allows communications to all the parts of the game
     *
     * @post gives a instance of the game
     */
    static GameManager getGame() {
        return null;
    }

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

    Poller getPoller();

    void setPoller(Poller p);

    ClientModel getClientModel() ;

    void setClientModel(ClientModel cm);

    PlayerInfo getPlayerInfo();

    void setPlayerInfo(PlayerInfo playerInfo);
    

    ServerAsyncHelper getAsync();

    void setAsync(ServerAsyncHelper async);
}
