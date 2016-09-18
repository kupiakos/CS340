package client.game;

import client.facade.FacadeManager;
import client.poller.Poller;
import shared.IServer;
import shared.models.game.ClientModel;

import java.util.Observable;

/**
 * Created by audakel on 9/15/16.
 */
public class GameManager extends Observable {
    /**
     * Setting up the singleton for all to use
     */
    private static GameManager mInstance;
    /**
     * The mPoller that the game will use
     */
    private Poller mPoller;
    /**
     * Game model on clients side for version
     */
    private ClientModel mClientModel;
    /**
     * The server that may be a fake one or a real one
     */
    private IServer mServer;
    /**
     * Handel to a facade for the current game, allows for use on both client and server - with n games.
     */
    private FacadeManager mFacadeManager;


    /**
     * Init stuff for the game manager as needed
     */
    private GameManager(){

    }


    /**
     * Lets you grab the game, allows communications to all the parts of the game
     * @return
     */
    public static GameManager getGame() {
        if (mInstance == null) mInstance = new GameManager();
        return mInstance;
    }

    /**
     * Lets you grab the facade for the game, allows access to the current games model
     * @return
     */
    public FacadeManager getFacade() {
        if (mFacadeManager == null) mFacadeManager = new FacadeManager(mClientModel);
        return mFacadeManager;
    }


    /**
     * Starts the Poller
     */
    public void startPoller() {
        if (mPoller == null) mPoller = new Poller();

        mPoller.setClientModel(mClientModel);
        mPoller.startPoller();
    }

    /**
     * Stops the Poller
     */
    public void stopPoller() {
        mPoller.stopPoller();
        mPoller = null;
    }

    /**
     * When the poller finds out the server has a new version it will call this function to update the client game
     * @param cm the new clientModel
     */
    public void updateGameManager(ClientModel cm){
        mClientModel = cm;
        mFacadeManager.update(cm);
    }


    /**
     * Grabs a "server" for us so we can talk with the real server
     * @return a server we can use
     */

    public IServer getServer() {
        return mServer;
    }


    public static void setInstance(GameManager sm) {
        GameManager.mInstance = sm;
    }

    public Poller getPoller() {
        return mPoller;
    }

    public void setPoller(Poller p) {
        this.mPoller = p;
    }

    public ClientModel getClientModel() {
        return mClientModel;
    }

    public void setmClientModel(ClientModel cm) {
        this.mClientModel = cm;
    }
}
