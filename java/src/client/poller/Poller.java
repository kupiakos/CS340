package client.poller;

import client.game.GameManager;

import shared.models.game.ClientModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

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
                // Grab a game manager
                GameManager gm = GameManager.getGame();
                // Get our version number
                int version = gm.getClientModel().getVersion();
                // Call the server with the number
                ClientModel response = gm.getServer().gameState(version);
                // If new model
                if (response != null){
                    // Update ours
                    gm.setmClientModel(response);
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
