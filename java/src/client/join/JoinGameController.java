package client.join;

import client.base.Controller;
import client.base.IAction;
import client.misc.IMessageView;
import org.jetbrains.annotations.Nullable;
import shared.definitions.CatanColor;
import shared.definitions.PlayerIndex;
import shared.models.games.CreateGameRequest;
import shared.models.games.GameInfo;
import shared.models.games.JoinGameRequest;

import javax.naming.CommunicationException;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Arrays;


/**
 * Implementation for the join game controller
 */
public class JoinGameController extends Controller implements IJoinGameController {

    public static GameInfo selectedGame;
    private final int SERVER_CONTACT_INTERVAL = 1000;
    private INewGameView newGameView;
    private ISelectColorView selectColorView;
    private IMessageView messageView;
    private IAction joinAction;
    private javax.swing.Timer mTimer;

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
        getJoinGameView().showModal();
        setServer(getGameManager().getServer());
        ActionListener pollGames = e -> reloadGamesList();
        mTimer = new javax.swing.Timer(SERVER_CONTACT_INTERVAL, pollGames);
        reloadGamesList();
        mTimer.start();
    }

    public void reloadGamesList() {
        getAsync().runMethod(server::listOfGames)
                .onSuccess(games -> SwingUtilities.invokeLater(() -> {
                    getJoinGameView().setGames(games, getGameManager().getPlayerInfo());
                    if (!getNewGameView().isModalShowing() && !getSelectColorView().isModalShowing()) {
                        //TODO fix this whack redrawing stuff
                        getJoinGameView().closeModal();
                        getJoinGameView().showModal();
                    }
                }))
                .onError(e -> displayError("Error Communicating with Server", "Cannot retrieve list of games.\nError message: " + e.getMessage()))
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
        if (getNewGameView().getTitle().isEmpty()) {
            displayError("Cannot create game", "Cannot create a game with an empty title!");
            return;
        }
        CreateGameRequest newGame = new CreateGameRequest(
                getNewGameView().getRandomlyPlaceHexes(),
                getNewGameView().getUseRandomPorts(),
                getNewGameView().getRandomlyPlaceNumbers(),
                getNewGameView().getTitle()
        );
        getAsync().runMethod(server::createGame, newGame)
                .onSuccess(() -> SwingUtilities.invokeLater(this::reloadGamesList))
                .onError(e -> displayError("Error Communicating with Server", "Cannot create a new game.\nError Message: " + e.getMessage()))
                .start();
        getNewGameView().closeModal();
    }

    @Override
    public void startJoinGame(GameInfo game) {
        selectedGame = game;
        for (CatanColor c : CatanColor.values()) {
            getSelectColorView().setColorEnabled(c, true);
        }
        selectedGame.getPlayers().stream()
                .filter(p -> p.getId() != getGameManager().getPlayerInfo().getId())
                .forEach(p -> getSelectColorView().setColorEnabled(p.getColor(), false));
        getSelectColorView().showModal();
    }

    @Override
    public void cancelJoinGame() {
        getJoinGameView().closeModal();
    }

    @Nullable
    private GameInfo getGameWithId(int id) {
        try {
            return Arrays.stream(server.listOfGames())
                    .filter(gi -> gi.getId() == id)
                    .findFirst().orElse(null);
        } catch (CommunicationException e) {
            return null;
        }
    }

    @Override
    public void joinGame(CatanColor color) {
        JoinGameRequest joinGameRequest = new JoinGameRequest(color.toString().toLowerCase(), selectedGame.getId());
        getAsync().runMethod(server::joinGame, joinGameRequest)
                .onSuccess(() -> SwingUtilities.invokeLater(() -> {
                    selectedGame = getGameWithId(selectedGame.getId());
                    getGameManager().getPlayerInfo().setColor(color);
                    if (selectedGame.getPlayers().size() != 0) {
                        for (int i = 0; i < selectedGame.getPlayers().size(); i++) {
                            if (selectedGame.getPlayers().get(i).getId() == getGameManager().getPlayerInfo().getId()) {
                                getGameManager().getPlayerInfo().setPlayerIndex(PlayerIndex.fromInt(i));
                                break;
                            } else if (i == selectedGame.getPlayers().size() - 1) {
                                getGameManager().getPlayerInfo().setPlayerIndex(PlayerIndex.fromInt(i + 1));
                            }
                        }
                    } else {
                        getGameManager().getPlayerInfo().setPlayerIndex(PlayerIndex.FIRST);
                    }
                    mTimer.stop();
                    if (getSelectColorView().isModalShowing()) {
                        getSelectColorView().closeModal();
                    }
                    getJoinGameView().closeModal();
                    joinAction.execute();
                }))
                .onError(e -> {
                    displayError("Error", "Cannot join game.\nError Message: " + e.getMessage());
                    e.printStackTrace();
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

