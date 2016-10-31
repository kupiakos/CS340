package client.points;

import client.base.Controller;
import shared.models.game.ClientModel;
import shared.models.game.Player;

/**
 * Implementation for the points controller
 */
public class PointsController extends Controller implements IPointsController {

    private IGameFinishedView finishedView;

    /**
     * PointsController constructor
     *
     * @param view         Points view
     * @param finishedView Game finished view, which is displayed when the game is over
     */
    public PointsController(IPointsView view, IGameFinishedView finishedView) {
        super(view);
        setFinishedView(finishedView);
        observeClientModel();
    }

    @Override
    public void gameFinished() {
        System.out.println("Thank you for playing my game!");
        System.exit(0);
    }

    @Override
    protected void updateFromModel(ClientModel model) {
        getPointsView().setPoints(getPlayer().getVictoryPoints());

        Player winner = getFacade().getTurn().getWinner();
        if (winner != null) {
            boolean isLocalPlayer = winner.getPlayerIndex() == getPlayer().getPlayerIndex();
            getFinishedView().setWinner(winner.getName(), isLocalPlayer);
            getFinishedView().showOneModal();
        }
    }

    public IPointsView getPointsView() {
        return (IPointsView) super.getView();
    }

    public IGameFinishedView getFinishedView() {
        return finishedView;
    }

    public void setFinishedView(IGameFinishedView finishedView) {
        this.finishedView = finishedView;
    }
}

