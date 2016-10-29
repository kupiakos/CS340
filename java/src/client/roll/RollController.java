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
        if (rollTimer == null &&
                model.getTurnTracker().getStatus() == TurnStatus.ROLLING &&
                getFacade().getTurn().isPlayersTurn(getPlayer())) {
            start();
        } else {
            if (getRollView().isModalShowing()) {
                //getRollView().closeModal();
            }
        }
    }

    /**
     * Start timer
     */
    public void start() {
        if (rollTimer != null) {
            return;
        }
        countdown = 5;
        getRollView().setMessage("Rolling automatically in..." + countdown + " seconds");
        ActionListener rollAction = e -> updateView();
        rollTimer = new Timer(1000, rollAction);
        rollTimer.start();
        if (!getRollView().isModalShowing()) {
            getRollView().showModal();
        }
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
        // rollTimer.stop();
        int random1 = 1 + (int) (Math.random() * ((6 - 1) + 1));
        int random2 = 1 + (int) (Math.random() * ((6 - 1) + 1));
        int rollValue = random1 + random2;

        getRollView().closeModal();
        RollNumberAction roll = new RollNumberAction(rollValue, getPlayer().getPlayerIndex());
        getAsync().runModelMethod(server::rollNumber, roll)
                .onSuccess(() -> SwingUtilities.invokeLater(() -> {
                    rollTimer = null;
                    getResultView().setRollValue(rollValue);
                    getResultView().showModal();
                }))
                .onError(Throwable::printStackTrace)
                .start();
    }

}

