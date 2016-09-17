package client.session;

import client.poller.Poller;
import client.server.ServerProxy;
import shared.IServerProxy;
import shared.models.ClientModel;

import java.util.Observable;

/**
 * Created by audakel on 9/15/16.
 */
public class SessionManager extends Observable {
    /**
     * Setting up the singleton for all to use
     */
    private static SessionManager mInstance;
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
    IServerProxy mServer;



    /**
     * Lets you grab the session, allows communications to all the parts of the game
     * @return
     */
    public static SessionManager getInstance() {
        if (mInstance == null) mInstance = new SessionManager();
        return mInstance;
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
    public void updateClientModels(ClientModel cm){
        mClientModel = cm;
    }


    /**
     * Grabs a "server" for us so we can talk with the real server
     * @return a server we can use
     */
    public IServerProxy getServer() {
        return mServer;
    }


    public static void setInstance(SessionManager sm) {
        SessionManager.mInstance = sm;
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
