package client.join;

import client.base.Controller;
import client.base.IAction;
import client.data.GameInfo;
import client.game.GameManager;
import client.misc.IMessageView;
import client.server.ServerProxy;
import shared.definitions.CatanColor;
import shared.models.games.CreateGameRequest;
import shared.models.games.JoinGameRequest;
import shared.models.user.Credentials;

import javax.swing.*;


/**
 * Implementation for the join game controller
 */
public class JoinGameController extends Controller implements IJoinGameController {

    private INewGameView newGameView;
    private ISelectColorView selectColorView;
    private IMessageView messageView;
    private IAction joinAction;
    private GameInfo selectedGame;

    /**
     * JoinGameController constructor
     *
     * @param view            Join game view
     * @param newGameView     New game view
     * @param selectColorView Select color view
     * @param messageView     Message view (used to display error messages that occur while the user is joining a game)
     */
    public JoinGameController(IJoinGameView view, INewGameView newGameView,
                              ISelectColorView selectColorView, IMessageView messageView) {

        super(view);
        setNewGameView(newGameView);
        setSelectColorView(selectColorView);
        setMessageView(messageView);
        setServer(new ServerProxy());
        selectedGame = null;
    }

    public IJoinGameView getJoinGameView() {

        return (IJoinGameView) super.getView();
    }

    /**
     * Returns the action to be executed when the user joins a game
     *
     * @return The action to be executed when the user joins a game
     */
    public IAction getJoinAction() {

        return joinAction;
    }

    /**
     * Sets the action to be executed when the user joins a game
     *
     * @param value The action to be executed when the user joins a game
     */
    public void setJoinAction(IAction value) {

        joinAction = value;
    }

    public INewGameView getNewGameView() {

        return newGameView;
    }

    public void setNewGameView(INewGameView newGameView) {

        this.newGameView = newGameView;
    }

    public ISelectColorView getSelectColorView() {

        return selectColorView;
    }

    public void setSelectColorView(ISelectColorView selectColorView) {

        this.selectColorView = selectColorView;
    }

    public IMessageView getMessageView() {

        return messageView;
    }

    public void setMessageView(IMessageView messageView) {

        this.messageView = messageView;
    }

    @Override
    public void start() {
        getAsync().runMethod(server::listOfGames)
                .onSuccess(games -> SwingUtilities.invokeLater(() -> {
                    getJoinGameView().setGames(games, getGameManager().getPlayerInfo());
                    getJoinGameView().showModal();
                }))
                .onError(e -> displayError("Error Communicating with Server", "Cannot retrieve list of games.\rError message: " + e.getMessage()))
                .start();

    }

    public void reloadGamesList() {
        getAsync().runMethod(server::listOfGames)
                .onSuccess(games -> SwingUtilities.invokeLater(() -> {
                    getJoinGameView().setGames(games, getGameManager().getPlayerInfo());
                    getJoinGameView().showModal();
                }))
                .onError(e -> displayError("Error Communicating with Server", "Cannot retrieve list of games.\rError message: " + e.getMessage()))
                .start();
    }

    @Override
    public void startCreateNewGame() {

        getNewGameView().showModal();
    }

    @Override
    public void cancelCreateNewGame() {
        getNewGameView().setTitle("");
        getNewGameView().setRandomlyPlaceHexes(false);
        getNewGameView().setRandomlyPlaceNumbers(false);
        getNewGameView().setUseRandomPorts(false);
        getNewGameView().closeModal();
    }

    @Override
    public void createNewGame() {

        CreateGameRequest newGame = new CreateGameRequest(getNewGameView().getRandomlyPlaceHexes(), getNewGameView().getUseRandomPorts(), getNewGameView().getRandomlyPlaceNumbers(), getNewGameView().getTitle());
        getAsync().runMethod(server::createGame, newGame)
                .onSuccess(() -> {
                    reloadGamesList();
                })
                .onError(e -> displayError("Error Communicating with Server", "Cannot create a new game.\rError Message: " + e.getMessage()))
                .start();
        getNewGameView().closeModal();
    }

    @Override
    public void startJoinGame(GameInfo game) {
        selectedGame = game;
        getSelectColorView().showModal();
    }

    @Override
    public void cancelJoinGame() {

        getJoinGameView().closeModal();
    }

    @Override
    public void joinGame(CatanColor color) {
        JoinGameRequest joinGameRequest = new JoinGameRequest(color.toString().toLowerCase(), selectedGame.getId());
        getAsync().runMethod(server::joinGame, joinGameRequest)
                .onSuccess(() -> {
                    getGameManager().getPlayerInfo().setPlayerIndex(selectedGame.getPlayers().size());
                    getGameManager().getPlayerInfo().setColor(color);
                    getJoinGameView().closeModal();
                    joinAction.execute();
                })
                .onError(e -> {
                    displayError("Error", "Cannot join game.\rError Message: " + e.getMessage());
                    selectedGame = null;
                })
                .start();
        getSelectColorView().closeModal();
    }

    public void displayError(String title, String message) {
        messageView.setTitle(title);
        messageView.setMessage(message);
        messageView.showModal();
    }
}

