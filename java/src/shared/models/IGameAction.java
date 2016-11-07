package shared.models;

import shared.models.game.ClientModel;

/**
 * Created by elijahgk on 11/6/2016.
 * IGameAction is the interface for all game actions in Catan.  Think of a Game Action as a Command.
 */
public interface IGameAction {

    /**
     * execute will be called on the server's side.  It will make the necessary method calls to complete the action.
     * @param model
     */
    void execute(ClientModel model);
}
