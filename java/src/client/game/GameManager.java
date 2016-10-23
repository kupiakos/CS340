package client.game;

import client.poller.Poller;
import client.server.ServerProxy;
import client.utils.ServerAsyncHelper;
import shared.IServer;
import shared.definitions.PlayerIndex;
import shared.facades.FacadeManager;
import shared.models.game.ClientModel;
import shared.models.game.Player;
import shared.models.games.PlayerInfo;

import java.util.Observable;

/**
 * Manages a single game
 */
public class GameManager extends Observable implements IGameManager {
    /**
     * Setting up the singleton for all to use
     */
    private static IGameManager instance;
    /**
     * The poller that the game will use
     */
    private Poller poller;
    /**
     * Game model on clients side for version
     */
    private ClientModel clientModel;
    /**
     * The server that may be a fake one or a real one
     */
    private IServer server;
    /**
     * Handel to a facade for the current game, allows for use on both client and server - with n games.
     */
    private FacadeManager facadeManager;
    /**
     * Info on the games players
     */
    private PlayerInfo playerInfo;

    private ServerAsyncHelper async;

    /**
     * Port that was passed in from the command line
     */
    private String port = "8081";
    /**
     * Host that was passed in from command line i.e. localhost
     */
    private String host = "localhost";

    /**
     * Init stuff for the game manager as needed
     *
     * @post This provides valid operations on GameManager
     */
    GameManager() {
        clientModel = new ClientModel();
        setAsync(new ServerAsyncHelper(this));
        playerInfo = new PlayerInfo();
        facadeManager = new FacadeManager(clientModel);
    }

    /**
     * Lets you grab the game, allows communications to all the parts of the game
     *
     * @post gives a instance of the game
     */
    public static IGameManager getGame() {
        if (instance == null) instance = new GameManager();
        return instance;
    }

    public static void setInstance(IGameManager sm) {
        GameManager.instance = sm;
    }

    /**
     * Get the player the client is currently playing as, <i>not</i> whose turn it is.
     *
     * @return
     */
    public Player getPlayer() {
        return getClientModel().getPlayer(getThisPlayerIndex());
    }

    /**
     * Lets you grab the facade for the game, allows access to the current games model
     *
     * @return FacadeManager with current ClientModel
     */
    public FacadeManager getFacade() {
        if (facadeManager == null) facadeManager = new FacadeManager(clientModel);
        return facadeManager;
    }

    /**
     * Starts the Poller, creates one if needed
     */
    public void startPoller() {
        if (poller == null) poller = new Poller(this);
        poller.startPoller();
    }

    /**
     * Stops the Poller
     */
    public void stopPoller() {
        poller.stopPoller();
        poller = null;
    }

    /**
     * When the poller finds out the server has a new version it will call this
     * function to update the client game
     *
     * @param cm the new clientModel
     */
    public void updateGameManager(ClientModel cm) {
        clientModel = cm;
        facadeManager.update(cm);
        if (cm == null) {
            return;
        }
        setChanged();
        notifyObservers(cm);
    }

    /**
     * Grabs a "server" for us so we can talk with the real server
     *
     * @post a server we can use
     */
    public IServer getServer() {
        //TODO:: Change this to the real server
        if (server == null) server = ServerProxy.getInstance(host, port);

        return server;
    }

    @Override
    public void setServer(IServer server) {
        this.server = server;
    }


    public Poller getPoller() {
        return poller;
    }

    public void setPoller(Poller p) {
        this.poller = p;
    }

    public ClientModel getClientModel() {
        return clientModel;
    }

    public void setClientModel(ClientModel cm) {
        this.clientModel = cm;
    }

    public PlayerInfo getPlayerInfo() {
        if (playerInfo == null) playerInfo = new PlayerInfo();
        return playerInfo;
    }

    public void setPlayerInfo(PlayerInfo playerInfo) {
        this.playerInfo = playerInfo;
    }

    public ServerAsyncHelper getAsync() {
        return async;
    }

    public void setAsync(ServerAsyncHelper async) {
        this.async = async;
    }

    public PlayerIndex getThisPlayerIndex() {
        return getPlayerInfo().getPlayerIndex();
    }

    public void setThisPlayerIndex(PlayerIndex thisPlayerIndex) {
        getPlayerInfo().setPlayerIndex(thisPlayerIndex);
    }

    @Override
    public void setHostPort(String host, String port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public String getPort() {
        return port;
    }
}
