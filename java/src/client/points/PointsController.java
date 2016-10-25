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
        observeClientModel();
    }

    @Override
    protected void updateFromModel(ClientModel model) {
        Player player = getPlayer();

        Player longestRoadPlayer = null;
        PlayerIndex longestRoad = model.getTurnTracker().getLongestRoad();
        if (longestRoad != null) {
            longestRoadPlayer = model.getPlayer(longestRoad);
        }

        int points = player.getVictoryPoints();
        if (player.equals(longestRoadPlayer)) {
            points += 2;
        }
        getPointsView().setPoints(points);

        PlayerIndex winnerIndex = model.getWinner();
        if (winnerIndex != null) {
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

