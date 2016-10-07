package client.game;

import shared.models.game.ClientModel;

/**
 * @author audakel on 10/6/16.
 */
public class MockGM extends GameManager {
    private static MockGM instance;
    private ClientModel clientModel;


    /**
     * Init stuff for the game manager as needed
     *
     * @post This provides valid operations on GameManager
     */
    private MockGM() {
        super();
    }

    /**
     * Lets you grab the game, allows communications to all the parts of the game
     *
     * @post gives a instance of the game
     */
    public static MockGM getGame() {
        if (instance == null) instance = new MockGM();
        return instance;
    }

    public ClientModel getClientModel() {
        return clientModel;
    }

    public void setClientModel(ClientModel cm) {
        this.clientModel = cm;
    }

}
