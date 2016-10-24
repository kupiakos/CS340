package client.roll;

import client.base.Controller;
import shared.models.game.ClientModel;


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
        observeClientModel();
    }

    @Override
    protected void updateFromModel(ClientModel model) {
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
//        String beginTimerMessage = "Rolling automatically in... " + 5 + " seconds";
//        getRollView().setMessage(beginTimerMessage);
//        getRollView().showModal();
//
//        int timer = 5;
//        for(int i = 0; i < timer; i++) { //implement timer
//            String message = "Rolling automatically in... " + timer + " seconds";
//            getRollView().setMessage(message);
//        }
//
//        if(timer == 0) {
//            getRollView().closeModal();

        int rollValue = 0;
        int random1 = 1 + (int) (Math.random() * ((6 - 1) + 1));
        int random2 = 1 + (int) (Math.random() * ((6 - 1) + 1));
        rollValue = random1 + random2;
        getResultView().setRollValue(rollValue);
        getResultView().showModal();
//        }
    }

}

