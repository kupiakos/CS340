package client.facade;

import shared.models.game.ClientModel;

/**
 * Created by audakel on 9/17/16.
 */
public class FacadeManager {
    /** A reference to the game info in the client model, will update frequently */
    private ClientModel mClientModel;
    /** Handels all the api code for a players turn */
    private Facade turn;
    /** Checks to see if player can chat, than provides api for chatting */
    private Facade chat;
    // TODO:: add more facades here


    /**
     * Init the Facade manager with the starting clientModel
     * @param cm current clientmodel
     */
    public FacadeManager(ClientModel cm) {
        mClientModel = cm;
        turn = new Turn();
        chat = new Chat();
        // TODO:: add more facades here
    }


    /**
     * Updates all facades with a new model
     * @return
     */
    public void update(ClientModel cm){
        setmClientModel(cm);
        turn.setClientModel(cm);
        chat.setClientModel(cm);
        // TODO:: add more facades here
    }


    public ClientModel getmClientModel() {
        return mClientModel;
    }

    public void setmClientModel(ClientModel mClientModel) {
        this.mClientModel = mClientModel;
    }

    public Facade getTurn() {
        return turn;
    }

    public void setTurn(Facade turn) {
        this.turn = turn;
    }

    public Facade getChat() {
        return chat;
    }

    public void setChat(Facade chat) {
        this.chat = chat;
    }
}
