package client.points;

import client.base.Controller;
import shared.definitions.PlayerIndex;
import shared.models.game.ClientModel;
import shared.models.game.Player;

import java.util.Objects;

/**
 * Implementation for the points controller
 */
public class PointsController extends Controller implements IPointsController {

    private IGameFinishedView finishedView;
    private boolean isLongestRoadPlayer;
    private boolean isLargestArmyPlayer;

    /**
     * PointsController constructor
     *
     * @param view         Points view
     * @param finishedView Game finished view, which is displayed when the game is over
     */
    public PointsController(IPointsView view, IGameFinishedView finishedView) {
        super(view);
        setFinishedView(finishedView);
        isLongestRoadPlayer = false;
        isLargestArmyPlayer = false;
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

        if (Objects.equals(player, longestRoadPlayer) && !isLongestRoadPlayer) {
            points += 2;
            isLongestRoadPlayer = true;
        } else {
            if (isLongestRoadPlayer) {
                points -= 2;
            }
            isLongestRoadPlayer = false;
        }

        Player largestArmyPlayer = null;
        PlayerIndex largestArmy = model.getTurnTracker().getLargestArmy();
        if (largestArmy != null) {
            largestArmyPlayer = model.getPlayer(largestArmy);
        }

        if (Objects.equals(player, largestArmyPlayer) && !isLargestArmyPlayer) {
            points += 2;
            isLargestArmyPlayer = true;
        } else {
            if (isLargestArmyPlayer) {
                points -= 2;
            }
            isLargestArmyPlayer = false;
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

