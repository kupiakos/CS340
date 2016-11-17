package client.join;

import client.base.Controller;
import shared.definitions.AIType;
import shared.models.game.AddAIRequest;
import shared.models.games.GameInfo;
import shared.models.games.PlayerInfo;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;


/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends Controller implements IPlayerWaitingController {
    private final int SERVER_CONTACT_INTERVAL = 1000;
    private Timer mTimer;

    public PlayerWaitingController(IPlayerWaitingView view) {
        super(view);
    }

    @Override
    public IPlayerWaitingView getView() {

        return (IPlayerWaitingView) super.getView();
    }

    @Override
    public void start() {
        getView().showModal();
        setServer(getGameManager().getServer());
        ActionListener pollGames = e -> updatePlayers();
        mTimer = new Timer(SERVER_CONTACT_INTERVAL, pollGames);
        getAsync().runMethod(server::listAI)
                .onSuccess(AI -> SwingUtilities.invokeLater(() -> {
                    getView().setAIChoices(Arrays.stream(AI).map(AIType::toString).toArray(String[]::new));
                    updatePlayers();
                }))
                .onError(e -> displayError("Error Communicating with Server", "Cannot retrieve list of AI Types.\rError message: " + e.getMessage()))
                .start();
        mTimer.start();
    }

    private void updatePlayers() {
        getAsync().runMethod(server::listOfGames)
                .onSuccess(games -> SwingUtilities.invokeLater(() -> {
                    GameInfo game = Arrays.stream(games)
                            .filter(g -> g.getId() == JoinGameController.selectedGame.getId())
                            .findFirst().orElse(null);
                    if (game == null) {
                        return;
                    }
                    JoinGameController.selectedGame = game;
                    List<PlayerInfo> players = game.getPlayers();
                    getView().setPlayers(players.toArray(new PlayerInfo[players.size()]));
                    if (players.size() >= 4) {
                        //Let's start this thing!
                        mTimer.stop();
                        getView().closeModal();
                        getGameManager().startPoller();
                        return;
                    }
                    //TODO fix this whack redrawing process.
                    getView().closeModal();
                    getView().showModal();
                }))
                .onError(e -> displayError("Error Communicating with Server", "Cannot retrieve list of games.\rError message: " + e.getMessage()))
                .start();
    }

    @Override
    public void addAI() {
        AddAIRequest ai = new AddAIRequest(AIType.LARGEST_ARMY);
        for (AIType t : AIType.values()) {
            if (getView().getSelectedAI().equals(t.toString()))
                ai = new AddAIRequest(t);
        }
        getAsync().runMethod(server::addAI, ai)
                .onSuccess(() -> SwingUtilities.invokeLater(this::updatePlayers))
                .onError(e -> displayError("Error adding AI", e.getMessage()))
                .start();
    }

    private void displayError(String title, String message) {
        System.out.print(title + '\n' + message + '\n');
    }

}

