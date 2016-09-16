package client.poller;

import client.session.SessionManager;
import jdk.nashorn.api.scripting.JSObject;
import shared.models.ClientModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.Timer;

/**
 * Created by audakel on 9/14/16.
 */
public class Poller {
    /**
     * How often we want to call the server in seconds
     */
    private final int SERVER_CONTACT_INTERVAL = 3 * 1000;
    /**
     * Timer to run poller
     */
    private Timer mTimer;
    /**
     * Game model on clients side for version
     */
    private ClientModel clientModel;


    /**
     * Basically a mTimer that will start contacting the server every SERVER_CONTACT_INTERVAL secs to check for version updates of the game
     * Also sets up a ActionListener called poll that is the actual item that will be run every SERVER_CONTACT_INTERVAL secs
     */
    public Poller(){

        ActionListener poll = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // Grab a session manager
                SessionManager sm = SessionManager.getInstance();
                // Get our version number
                String version = sm.getClientModel().getVersion();
                // Call the server with the number
                JSObject response = sm.getServer().getModel(version);
                // If new model
                //TODO :: Actually fix this to the real meathod calls
                if (response != null){
                    // Update ours
                    sm.setmClientModel((ClientModel) response);
                }
            }
        };

        mTimer = new Timer(SERVER_CONTACT_INTERVAL, poll);
    }


    /**
     * Starts the mTimer, which starts up the polling
     */
    public void startPoller(){
        mTimer.start();
    }


    /**
     * Stops the mTimer, which stops the polling
     */
    public void stopPoller() {
        mTimer.stop();
    }

    /**
     * Adds the game model we will use, so we have access to the version
     * @param cm a clientModel
     */
    public void setClientModel(ClientModel cm) {
        clientModel = cm;
    }


}
