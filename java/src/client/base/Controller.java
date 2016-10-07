package client.base;

import client.game.GameManager;
import client.utils.ServerAsyncHelper;
import shared.IServer;
import shared.models.game.ClientModel;

import java.util.Observable;
import java.util.Observer;

/**
 * Base class for controllers
 */
public abstract class Controller implements IController, Observer {
    // Kept as a protected field to keep the usage terse
    protected IServer server;

    private IView view;
    private GameManager game;

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

    public GameManager getGameManager() {
        return game;
    }

    public void setGameManager(GameManager game) {
        this.game = game;
    }

    public void observeClientModel() {
        getGameManager().addObserver(this);
    }


    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof GameManager && arg instanceof ClientModel) {
            setServer(((GameManager) o).getServer());
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

