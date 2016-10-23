package client.base;

import client.game.GameManager;
import client.game.IGameManager;
import client.utils.ServerAsyncHelper;
import shared.IServer;
import shared.facades.FacadeManager;
import shared.models.game.ClientModel;
import shared.models.game.Player;

import java.util.Observable;
import java.util.Observer;

/**
 * Base class for controllers
 */
public abstract class Controller implements IController, Observer {
    // Kept as a protected field to keep the usage terse
    protected IServer server;

    private IView view;
    private IGameManager game;

    protected Controller(IView view) {
        setView(view);
        setGameManager(GameManager.getGame());
    }

    @Override
    public IView getView() {
        return this.view;
    }

    private void setView(IView view) {
        this.view = view;
    }

    public IServer getServer() {
        return server;
    }

    public void setServer(IServer server) {
        this.server = server;
    }

    protected ClientModel getModel() {
        return getGameManager().getClientModel();
    }

    protected Player getPlayer() {
        return getGameManager().getPlayer();
    }

    protected FacadeManager getFacade() {
        return getGameManager().getFacade();
    }

    public IGameManager getGameManager() {
        return game;
    }

    public void setGameManager(IGameManager game) {
        this.game = game;
    }

    public void observeClientModel() {
        getGameManager().addObserver(this);
    }


    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof IGameManager && arg instanceof ClientModel) {
            setServer(((IGameManager) o).getServer());
            updateFromModel((ClientModel) arg);
        }
    }

    protected void updateFromModel(ClientModel model) {
        // Do nothing by default, overridden by classes that need it
    }

    protected ServerAsyncHelper getAsync() {
        return getGameManager().getAsync();
    }

}

