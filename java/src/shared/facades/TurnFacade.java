package shared.facades;

import com.google.gson.annotations.SerializedName;
import com.sun.istack.internal.NotNull;
import shared.definitions.TurnStatus;
import shared.models.game.ClientModel;
import shared.models.game.MessageEntry;
import shared.models.game.Player;

import java.util.List;

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
    public void endTurn(@NotNull Player player) {
        if (canEndTurn(player)) {
            //TODO:: Figure out if we need to consolidate cards or anything or reset them to a start state
            advanceTurnStatus();
            MessageEntry m = new MessageEntry(player.getName(), LOG_FINISH_TURN_MSG);
            getModel().writeLog(m);
        }
    }

    /**
     * Looks at the current status, what players have gone, and then sets the correct game phase for the correct player
     */
    private void advanceTurnStatus() {
        //TODO:: fig this out
    }

    /**
     * Will check to see if the current {@link Player} can end their turn.
     *
     * @param p which {@link Player} to check
     * @return whether the turn could be ended
     * @pre {@code player} belongs to the current {@link ClientModel}.
     * @post None.
     */
    public boolean canEndTurn(@NotNull Player p) {
        TurnStatus ts = getModel().getTurnTracker().getStatus();

        if (!isPlayersTurn(p)) return false;

        switch(ts) {
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
    private boolean isPlayersTurn(@NotNull Player player) {
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
}
