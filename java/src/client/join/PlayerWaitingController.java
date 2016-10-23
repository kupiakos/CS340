package client.join;

import client.base.Controller;
import client.server.ServerProxy;
import shared.definitions.AIType;
import shared.models.game.AddAIRequest;
import shared.models.game.ClientModel;
import shared.models.games.GameInfo;
import shared.models.games.PlayerInfo;

import javax.swing.*;
import java.util.Arrays;


/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends Controller implements IPlayerWaitingController {

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
        updatePlayers();
        getAsync().runMethod(server::listAI)
                .onSuccess(AI -> SwingUtilities.invokeLater(() -> {
                    getView().setAIChoices(AI);
                    getView().showModal();
                }))
                .onError(e -> displayError("Error Communicating with Server", "Cannot retrieve list of AI Types.\rError message: " + e.getMessage()))
                .start();
    }

    private void updatePlayers(){
        getAsync().runMethod(server::listOfGames)
                .onSuccess(games -> SwingUtilities.invokeLater(() -> {
                    for(GameInfo g : games){
                        for(PlayerInfo p : g.getPlayers()){
                            if(p.getId() == getGameManager().getPlayerInfo().getId()){
                                getView().setPlayers(Arrays.copyOf(g.getPlayers().toArray(),g.getPlayers().size(),PlayerInfo[].class));
                                break;
                            }
                        }
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
            if(getView().getSelectedAI() == t.toString())
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

    public void displayError(String title, String message) {
        System.out.print(title+'\n'+message+'\n');
    }

}

