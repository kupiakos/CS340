package client.join;

import client.base.Controller;
import client.server.ServerProxy;
import shared.definitions.AIType;
import shared.models.game.AddAIRequest;
import shared.models.game.ClientModel;
import shared.models.games.GameInfo;
import shared.models.games.PlayerInfo;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Arrays;


/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends Controller implements IPlayerWaitingController {
    private final int SERVER_CONTACT_INTERVAL = 1000;
    private Timer mTimer;

    public PlayerWaitingController(IPlayerWaitingView view) {
        super(view);
        setServer(new ServerProxy());
        observeClientModel();
    }

    @Override
    public IPlayerWaitingView getView() {

        return (IPlayerWaitingView) super.getView();
    }

    @Override
    public void start() {
        ActionListener pollGames = e->updatePlayers();
        mTimer = new Timer(SERVER_CONTACT_INTERVAL, pollGames);
        updatePlayers();
        getAsync().runMethod(server::listAI)
                .onSuccess(AI -> SwingUtilities.invokeLater(() -> {
                    getView().setAIChoices(AI);
                    getView().showModal();
                }))
                .onError(e -> displayError("Error Communicating with Server", "Cannot retrieve list of AI Types.\rError message: " + e.getMessage()))
                .start();
        mTimer.start();
    }

    private void updatePlayers(){
        getAsync().runMethod(server::listOfGames)
                .onSuccess(games -> SwingUtilities.invokeLater(() -> {
                    GameInfo game = games[JoinGameController.selectedGame.getId()];
                    PlayerInfo[] playerArray = Arrays.copyOf(game.getPlayers().toArray(),game.getPlayers().size(),PlayerInfo[].class);
                    getView().setPlayers(playerArray);
                    if(playerArray.length>=4){
                        //Let's start this thing!
                        mTimer.stop();
                        getView().closeModal();
                        return;
                    }
                    getView().showModal();
                }))
                .onError(e -> displayError("Error Communicating with Server", "Cannot retrieve list of games.\rError message: " + e.getMessage()))
                .start();
    }

    @Override
    public void addAI() {
        AddAIRequest ai = new AddAIRequest(AIType.LARGEST_ARMY);
        for(AIType t : AIType.values()){
            if(getView().getSelectedAI().equals(t.toString()))
                ai = new AddAIRequest(t);
        }
        getAsync().runMethod(server::addAI, ai)
                .onSuccess(()->SwingUtilities.invokeLater(()->{
                    updatePlayers();
                    getView().showModal();
                }))
                .onError(e->displayError("Error adding AI",e.getMessage()))
                .start();
    }

    @Override
    protected void updateFromModel(ClientModel cm){
        updatePlayers();
    }

    private void displayError(String title, String message) {
        System.out.print(title+'\n'+message+'\n');
    }

}

