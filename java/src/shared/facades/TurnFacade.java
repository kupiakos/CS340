package shared.facades;

import com.sun.istack.internal.NotNull;
import shared.definitions.PlayerIndex;
import shared.definitions.TurnStatus;
import shared.models.game.ClientModel;
import shared.models.game.MessageEntry;
import shared.models.game.Player;
import shared.models.game.TurnTracker;

import java.util.List;

import static client.game.GameManager.getGame;
import static shared.definitions.Constants.LOG_FINISH_TURN_MSG;
import static shared.definitions.TurnStatus.*;

/**
 * Provides common operations on the turn of a game.
 */
public class TurnFacade extends AbstractFacade {

    /**
     * Constructor. Requires a valid game model to work.
     *
     * @param manager the manager to use, not null
     * @throws NullPointerException if {@code model} is null
     * @pre {@code model} is a valid {@link ClientModel}.
     * @post This provides valid operations on {@code model}.
     */
    public TurnFacade(@NotNull FacadeManager manager) {
        super(manager);
    }

    /**
     * Ends a player's turn.
     *
     * @param player the {@link Player} to end the turn of
     * @pre {@code player} belongs to the current {@link ClientModel}, and it's their turn.
     * @post {@code player}'s turn is ended
     */
    public void endTurn(@NotNull PlayerIndex player) {
        if (canEndTurn(player)) {
            //TODO:: Figure out if we need to consolidate cards or anything or reset them to a start state
            advanceTurnStatus();
            MessageEntry m = new MessageEntry(player.name(), LOG_FINISH_TURN_MSG);
            getModel().writeLog(m);
        }
    }


    /**
     * Will check to see if the current {@link Player} can end their turn.
     *
     * @param p which {@link Player} to check
     * @return whether the turn could be ended
     * @pre {@code player} belongs to the current {@link ClientModel}.
     * @post None.
     */
    public boolean canEndTurn(@NotNull PlayerIndex pi) {
        TurnStatus ts = getModel().getTurnTracker().getStatus();
        Player p = getGame().getClientModel().getPlayer(pi);

        if (!isPlayersTurn(p)) return false;

        switch (ts) {
            case PLAYING:
                return true;
            case FIRST_ROUND:
                return (p.getRoads() == 1) && (p.getSettlements() == 1);
            case SECOND_ROUND:
                return (p.getRoads() == 2) && (p.getSettlements() == 2);
            case DISCARDING:
            case ROLLING:
            case ROBBING:
                //TODO:: Read rules on what is supposed to happen if you try and end your turn
            default:
                return false;
        }
    }


    /**
     * Returns whether it is a specific {@link Player}'s turn.
     *
     * @param player the {@link Player} to check against
     * @return true if it is that {@link Player}'s turn
     * @pre {@code player} belongs to the current {@link ClientModel}.
     * @post None.
     */
    public boolean isPlayersTurn(@NotNull Player player) {
        return (player.getPlayerIndex() == getModel().getTurnTracker().getCurrentTurn());
    }

    /**
     * Lets you check if you can trade, build, buy etc.
     *
     * @return the current {@link TurnStatus} of the game
     * @pre A model is currently set, and it is a valid game.
     * @post None.
     */
    public TurnStatus getPhase() {
        return getModel().getTurnTracker().getStatus();
    }

    public void setPhase(TurnStatus ts) {
        getModel().getTurnTracker().setStatus(ts);
    }

    /**
     * Returns all {@link Player}s of the current game.
     *
     * @return a list of {@link Player}s, from 2-4
     * @pre A model is currently set.
     * @post None.
     */
    public List<Player> getPlayers() {
        return getModel().getPlayers();
    }


    /**
     * i am lazy and hate typing
     *
     * @return the turnTracker
     */
    public TurnTracker tt() {
        return getModel().getTurnTracker();
    }

    /**
     * Sets the game round and player once all have joined
     */
    public void startGame() {
        tt().setStatus(FIRST_ROUND);
        tt().setCurrentTurn(PlayerIndex.FIRST);
    }

    /**
     * Makes the current turn go up, true if it was able to, false if not
     */
    private boolean incrementTurn() {
        int numPlayers = getModel().getPlayerCount();
        int curPlayerTurn = tt().getCurrentTurn().index();

        if (curPlayerTurn + 1 <= numPlayers) {
            PlayerIndex pi = PlayerIndex.fromInt(curPlayerTurn + 1);
            tt().setCurrentTurn(pi);
            return true;
        }
        return false;
    }

    /**
     * Makes the current turn go up, true if it was able to, false if not
     */
    private boolean decrementTurn() {
        int curPlayerTurn = tt().getCurrentTurn().index();

        if (curPlayerTurn > 0) {
            PlayerIndex pi = PlayerIndex.fromInt(curPlayerTurn - 1);
            tt().setCurrentTurn(pi);
            return true;
        }
        return false;
    }


    /**
     * Sets the game state to the next player's turn (roll phase)
     *
     * @return successful or not
     */
    public boolean advanceTurnStatus() {
        if (tt().getStatus() == ROLLING || tt().getStatus() == ROBBING) return false;


        if (tt().getStatus() == SECOND_ROUND) decrementTurn();
        else incrementTurn();


        // If we go to the last player
        if (tt().getCurrentTurn().index() > 3) {
            tt().setCurrentTurn(PlayerIndex.FIRST);

            //if it's the first round go to the second round
            if (tt().getStatus() == FIRST_ROUND) {
                tt().setStatus(SECOND_ROUND);
                tt().setCurrentTurn(PlayerIndex.FOURTH);
            }
        }

        if (tt().getCurrentTurn().index() == 0 && tt().getStatus() == SECOND_ROUND) {
            tt().setStatus(ROLLING);
            tt().setCurrentTurn(PlayerIndex.FIRST);
        }

        if (tt().getStatus() == PLAYING) {
            tt().setStatus(ROLLING);
        }

        return true;
    }

    /**
     * Sets the game state to the same player's turn (build phase)
     *
     * @return successful or not (almost always is true)
     */
    public boolean startBuildPhase() {
        if (tt().getStatus() != ROLLING) return false;

        tt().setStatus(PLAYING);

        return true;
    }

    /**
     * Start robbing
     *
     * @return
     */
    public boolean startRobbing(boolean requireDiscard) {
        if (tt().getStatus() != ROLLING && tt().getStatus() != PLAYING)
            return false;

        if (requireDiscard)
            tt().setStatus(DISCARDING);
        else
            tt().setStatus(ROBBING);

        return true;
    }

    /**
     * Stop the robbing
     *
     * @return
     */
    public boolean stopRobbing() {
        if (tt().getStatus() != ROBBING) return false;

        tt().setStatus(PLAYING);
        return true;
    }


    /**
     * checks to see if you can stop rolling, if you can it will change to playing
     * @return true on state change from rolling -> playing
     */
    public boolean stopRolling() {
        if (tt().getStatus() != ROLLING) return false;

        tt().setStatus(PLAYING);
        return true;
    }

    /**
     * checks if the game state is finished, check if someone has 10 points or not
     *
     * @return successful or not
     */
    public boolean isEndGame() {
        return tt().getStatus() == GAME_OVER;
    }

    /**
     * Whether the game is in setup or not
     *
     * @return true if firstround or second round
     */
    public boolean isSetup() {
        return tt().getStatus() == FIRST_ROUND || tt().getStatus() == SECOND_ROUND;
    }

    /**
     * Change the game state to game over
     */
    public void endGame() {
        for (Player p : getGame().getClientModel().getPlayers()) {
            if (p.getVictoryPoints() > 10){
                tt().setStatus(GAME_OVER);
            }
        }
    }
}
