package client.poller;

import client.game.GameManager;
import client.game.IGameManager;
import shared.models.game.ClientModel;

import javax.naming.CommunicationException;
import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Will take care of contacting the server on regular intervals to check for game updates.
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

    private IGameManager gameManager;


    /**
     * A timer that will start contacting the server every
     * {@code SERVER_CONTACT_INTERVAL} secs to check for version updates of the game.
     * Also sets up a ActionListener called poll that is the actual item
     * that will be run every SERVER_CONTACT_INTERVAL secs.
     *
     * @post This provides a timer that will poll the server
     */
    public Poller(IGameManager gameManager) {
        // To init the poller go ahead and check for an update to start
        this.gameManager = gameManager;
        checkForUpdate();

        ActionListener poll = e -> checkForUpdate();
        mTimer = new Timer(SERVER_CONTACT_INTERVAL, poll);
    }

    private void checkForUpdate() {
        // Grab a game manager
        IGameManager gm = getGameManager();
        // Get our version number
        int version;
        try {
            version = gm.getClientModel().getVersion();
        } catch (Exception e) {
            version = 0;
        }
        // Call the server with the number
        ClientModel response = null;
        try {
            response = gm.getServer().gameState(version);
        } catch (CommunicationException e) {
            e.printStackTrace();
        }
        // If new model
        if (response != null) {
            // Update ours
            gm.updateGameManager(response);
        }
    }


    /**
     * Starts the mTimer, which starts up the polling
     *
     * @post the timer will be started
     */
    public void startPoller() {
        mTimer.start();
    }


    /**
     * Stops the mTimer, which stops the polling
     *
     * @post the timer will have stoped
     */
    public void stopPoller() {
        mTimer.stop();
    }


    public IGameManager getGameManager() {
        return gameManager;
    }

    public void setGameManager(IGameManager gameManager) {
        this.gameManager = gameManager;
    }
}
