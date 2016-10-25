package client.roll;

import client.base.Controller;
import shared.definitions.PlayerIndex;
import shared.definitions.TurnStatus;
import shared.models.game.ClientModel;
import shared.models.game.Player;
import shared.models.moves.RollNumberAction;

import javax.swing.*;
import java.awt.event.ActionListener;


/**
 * Implementation for the roll controller
 */
public class RollController extends Controller implements IRollController {

    private IRollResultView resultView;
    private Timer rollTimer;
    private int countdown;

    /**
     * RollController constructor
     *
     * @param view       Roll view
     * @param resultView Roll result view
     */
    public RollController(IRollView view, IRollResultView resultView) {
        super(view);
        setResultView(resultView);
        observeClientModel();
    }

    @Override
    protected void updateFromModel(ClientModel model) {
        Player clientPlayer = getPlayer();
        PlayerIndex currentPlayerIndex = model.getTurnTracker().getCurrentTurn();
        Player currentPlayer = model.getPlayer(currentPlayerIndex);
        if (model.getTurnTracker().getStatus() == TurnStatus.ROLLING && clientPlayer.equals(currentPlayer)) {
            getRollView().showModal();
        } else {
            getRollView().closeModal();
        }
    }

    /**
     * Start timer
     */
    public void start() {
        countdown = 5;
        getRollView().showModal();
        ActionListener rollAction = e -> updateView();
        rollTimer = new Timer(1000, rollAction);
        rollTimer.start();
    }

    /**
     * update view for timer
     */
    public void updateView() {
        countdown--;
        getRollView().setMessage("Rolling automatically in..." + countdown + " seconds");
        if (countdown == 0) {
            countdown = 5;
            rollTimer.stop();
            rollDice();
        }
    }

    public IRollResultView getResultView() {
        return resultView;
    }

    public void setResultView(IRollResultView resultView) {
        this.resultView = resultView;
    }

    public IRollView getRollView() {
        return (IRollView) getView();
    }

    @Override
    public void rollDice() {
        rollTimer.stop();
        int random1 = 1 + (int) (Math.random() * ((6 - 1) + 1));
        int random2 = 1 + (int) (Math.random() * ((6 - 1) + 1));
        int rollValue = random1 + random2;

        RollNumberAction roll = new RollNumberAction(rollValue, getPlayer().getPlayerIndex());
        getAsync().runModelMethod(server::rollNumber, roll)
                .onError(Throwable::printStackTrace)
                .start();
        getResultView().setRollValue(rollValue);
        getResultView().showModal();
    }

}

