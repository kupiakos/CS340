package client.roll;

import client.base.Controller;
import shared.models.game.ClientModel;
import shared.models.moves.RollNumberAction;


/**
 * Implementation for the roll controller
 */
public class RollController extends Controller implements IRollController {

    private IRollResultView resultView;

    /**
     * RollController constructor
     *
     * @param view       Roll view
     * @param resultView Roll result view
     */
    public RollController(IRollView view, IRollResultView resultView) {
        super(view);
        setResultView(resultView);
        initFromModel();
        observeClientModel();
    }

    private void initFromModel() {
        updateFromModel(getModel());
    }

    @Override
    protected void updateFromModel(ClientModel model) {
        //String beginTimerMessage = "Rolling automatically in... " + 5 + " seconds";
//        getRollView().setMessage(beginTimerMessage);
//        getRollView().showModal();
//
//        int timer = 5;
//        for(int i = 0; i < timer; i++) { //implement timer
//            String message = "Rolling automatically in... " + timer + " seconds";
//            getRollView().setMessage(message);
//        }
//
//            getRollView().closeModal();
//        rollDice();
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

