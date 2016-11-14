package shared.models;

import shared.facades.FacadeManager;
import shared.models.game.ClientModel;

/**
 * Created by elijahgk on 11/6/2016.
 * IGameAction is the interface for all game actions in Catan.  Think of a Game Action as a Command.
 */
public abstract class GameAction implements ICommandAction {
    private FacadeManager facades;

    public FacadeManager getFacades() {
        return facades;
    }

    public void setFacades(FacadeManager facades) {
        this.facades = facades;
    }

    protected ClientModel getModel() {
        return getFacades().getClientModel();
    }
}
