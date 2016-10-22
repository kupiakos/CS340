package client.turntracker;

import client.base.Controller;
import client.game.GameManager;
import shared.IServer;
import shared.definitions.PlayerIndex;
import shared.facades.TurnFacade;
import shared.models.game.ClientModel;
import shared.models.game.Player;
import shared.models.game.TurnTracker;
import shared.models.moves.FinishMoveAction;

import javax.naming.CommunicationException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static shared.definitions.Constants.END_GAME_MSG;
import static shared.definitions.Constants.WINNING_AMOUNT_VICTORY_POINTS;
import static shared.definitions.TurnStatus.GAME_OVER;


/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements ITurnTrackerController, Observer {

    public TurnTrackerController(ITurnTrackerView view) {
        super(view);
        initFromModel();
        GameManager.getGame().addObserver(this);

    }

    /**
     * Grabs the needed view from super
     *
     * @return ITurnTrackerView
     */
    @Override
    public ITurnTrackerView getView() {
        return (ITurnTrackerView) super.getView();
    }

    /**
     * Checks to see if the player can end turn, then calls the client facade and server
     */
    @Override
    public void endTurn() {
        // TODO:: Fix this shiz for da real playas
        FinishMoveAction action = new FinishMoveAction();
        TurnFacade tf = getFacade().getTurn();
        Player player = tf.getCurrentPlayer();
        IServer s = getServer();

        if (tf.canEndTurn(player)) {
            tf.endTurn(player);
            try {
                s.finishTurn(action);
            } catch (CommunicationException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Gets the correct color of the players whos turn it is, also inits all the players
     */
    private void initFromModel() {
        getView().setLocalPlayerColor(GameManager.getGame().getPlayerInfo().getColor());
        List<Player> players = GameManager.getGame().getClientModel().getPlayers();

        for (Player p : players) {
            if (p == null) continue;
            getView().initializePlayer(p.getPlayerID(), p.getName(), p.getColor());
        }
    }

    /**
     * Talks to the game view to update players current score cards, winner status.
     * Calls {@code getView()} for each player to access all the needed UI interfaces and
     * set the correct info for the player
     *
     * @param observable
     * @param o
     */
    @Override
    public void update(Observable observable, Object o) {
        ClientModel cm = GameManager.getGame().getClientModel();
        TurnTracker tt = GameManager.getGame().getClientModel().getTurnTracker();

        PlayerIndex largestArmy = tt.getLargestArmy();
        PlayerIndex longestRoad = tt.getLongestRoad();
        PlayerIndex currentTurn = tt.getCurrentTurn();
        List<Player> players = cm.getPlayers();
        PlayerIndex winnerIndex = null;
        PlayerIndex index;
        boolean gameOver = false;

        // update each player
        for (Player p : players) {
            // Player not set yet
            if (p == null || p.getPlayerIndex() == null) continue;

            if (p.getVictoryPoints() >= WINNING_AMOUNT_VICTORY_POINTS) {
                gameOver = true;
                winnerIndex = p.getPlayerIndex();
            }

            index = p.getPlayerIndex();
            getView().updatePlayer(
                    p.getPlayerIndex().index(),
                    p.getVictoryPoints(),
                    currentTurn.equals(index), // booleans on card status
                    largestArmy.equals(index),
                    longestRoad.equals(index)
            );
        }

        if (gameOver) {
            GameManager.getGame().getFacade().getTurn().setPhase(GAME_OVER);
            getView().updateGameState(END_GAME_MSG, false);
        }
    }
}

