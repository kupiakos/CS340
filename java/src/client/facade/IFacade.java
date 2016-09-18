package client.facade;

import shared.models.game.ClientModel;

/**
 * Created by audakel on 9/18/16.
 */
public interface IFacade {
    /** Make sure each facade can access the client mdoel */
    ClientModel getClientModel();

    /** Make sure each facade can set a new client model*/
    void setClientModel(ClientModel mClientModel);
}
