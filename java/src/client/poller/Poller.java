package client.poller;

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
     * Basically a mTimer that will start contacting the server every SERVER_CONTACT_INTERVAL secs to check for version updates of the game
     * Also sets up a ActionListener called poll that is the actual item that will be run every SERVER_CONTACT_INTERVAL secs
     */
    public Poller(){

        ActionListener poll = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // Get our version number
                // Call the server with the number
                // If new model
                    // Update ours
            }
        };

        mTimer = new Timer(SERVER_CONTACT_INTERVAL, poll);
    }


    /**
     * Starts the mTimer, which starts up the polling
     */
    public void startTimer(){
        mTimer.start();
    }


    /**
     * Stops the mTimer, which stops the polling
     */
    public void stopPoller() {
        mTimer.stop();
    }

}
