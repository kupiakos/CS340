package client.turntracker;

import client.base.Controller;
import shared.IServer;
import shared.definitions.CatanColor;
import shared.definitions.PlayerIndex;
import shared.facades.TurnFacade;
import shared.models.game.ClientModel;
import shared.models.game.Player;
import shared.models.game.TurnTracker;
import shared.models.moves.FinishMoveAction;

import javax.naming.CommunicationException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import static shared.definitions.Constants.END_GAME_MSG;
import static shared.definitions.TurnStatus.GAME_OVER;


/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements ITurnTrackerController {
    private static final Logger LOGGER = Logger.getLogger(TurnTracker.class.getSimpleName());
    boolean initialized = false;

    public TurnTrackerController(ITurnTrackerView view) {
        super(view);
        observeClientModel();
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
        TurnFacade tf = getFacade().getTurn();
        Player player = tf.getCurrentPlayer();
        IServer s = getServer();
        FinishMoveAction action = new FinishMoveAction(player.getPlayerIndex());

        if (tf.canEndTurn(player)) {
            tf.endTurn(player);
            try {
                s.finishTurn(action);
                LOGGER.info(player.getName() + "'s turn ended");
            } catch (CommunicationException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Gets the correct color of the players whos turn it is, also inits all the players
     */
    private void initFromModel(ClientModel model) {
        List<Player> players = model.getPlayers();
        CatanColor color = getPlayer().getColor();
        getView().setLocalPlayerColor(color);

        for (Player p : players) {
            if (p == null) continue;
            getView().initializePlayer(p.getPlayerIndex().index(), p.getName(), p.getColor());
        }

    }

    /**
     * Talks to the game view to update players current score cards, winner status.
     * Calls {@code getView()} for each player to access all the needed UI interfaces and
     * set the correct info for the player
     */
    @Override
    protected void updateFromModel(ClientModel model) {
        if (!initialized) {
            initialized = true;
            initFromModel(model);
        }

        TurnTracker tt = model.getTurnTracker();
        TurnFacade turns = getFacade().getTurn();

        PlayerIndex largestArmy = tt.getLargestArmy();
        PlayerIndex longestRoad = tt.getLongestRoad();
        PlayerIndex currentTurn = tt.getCurrentTurn();
        List<Player> players = model.getPlayers();
        PlayerIndex index;
        boolean gameOver = false;

        if (turns.isPlayersTurn(getPlayer())) {
            if (turns.canEndTurn(getPlayer())) {
                getView().updateGameState("End Turn", true);
            } else {
                getView().updateGameState("Complete other actions", false);
            }
        } else {
            getView().updateGameState("Waiting for other Players", false);
        }

        // update each player
        for (Player p : players) {
            // Player not set yet
            if (p == null || p.getPlayerIndex() == null) continue;
            LOGGER.fine("Updating score cards for " + p.getName());

            index = p.getPlayerIndex();
            getView().updatePlayer(
                    p.getPlayerIndex().index(),
                    p.getVictoryPoints(),
                    Objects.equals(index, currentTurn),     // booleans on card status
                    Objects.equals(index, largestArmy),
                    Objects.equals(index, longestRoad)
            );
        }

        Player winner = turns.getWinner();
        if (winner != null) {
            LOGGER.info("Game over");
            getFacade().getTurn().setPhase(GAME_OVER);
            getView().updateGameState(END_GAME_MSG, winner.getPlayerID() == getPlayer().getPlayerID());
        }
    }
}

