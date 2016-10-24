package client.points;

import client.base.Controller;
import shared.definitions.PlayerIndex;
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
        initFromModel();
        observeClientModel();
    }

    private void initFromModel() {
        updateFromModel(getModel());
    }

    @Override
    protected void updateFromModel(ClientModel model) {
        Player player = getGameManager().getPlayer();
        int points = player.getVictoryPoints();
        getPointsView().setPoints(points);

        PlayerIndex winnerIndex = model.getWinner();
        if (winnerIndex.index() != -1) {
            Player winner = model.getPlayer(winnerIndex);
            boolean isLocalPlayer = false;
            if (player.equals(winner)) {
                isLocalPlayer = true;
            }
            getFinishedView().setWinner(winner.getName(), isLocalPlayer);
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

