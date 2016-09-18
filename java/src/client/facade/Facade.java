package client.facade;

import shared.models.game.ClientModel;

/**
 * Created by audakel on 9/18/16.
 */
public class Facade implements IFacade{
    /** A reference to the game info in the client model, will update frequently */
    private ClientModel mClientModel;


    public ClientModel getClientModel() {
        return mClientModel;
    }

    public void setClientModel(ClientModel mClientModel) {
        this.mClientModel = mClientModel;
    }
}
