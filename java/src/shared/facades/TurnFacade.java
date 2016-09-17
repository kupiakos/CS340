package shared.facades;

import shared.definitions.TurnStatus;
import shared.models.game.Player;

import java.util.List;

public class TurnFacade {

    /**
     * Will check to see if the current {@link Player} can end their turn.
     * @param player which {@link Player} to check
     * @return whether the turn could be ended
     */
    public boolean canEndTurn(Player player) {
        return false;
    }

    /**
     * Checks to see if a {@link Player} can end turn, then ends their turn.
     */
    public static void endTurn(Player player) {
//        if (canEndTurn(player)) {
//
//        }
    }

    /**
     * Asks the game if it is a specific {@link Player}'s turn.
     *
     * @param player the {@link Player} to check against
     * @return true if it is that {@link Player}'s turn
     */
    private boolean isPlayersTurn(Player player) {
        return false;
    }

    /**
     * Lets you check if you can trade, build, buy etc.
     *
     * @return the current {@link TurnStatus} of the turn
     */
    public TurnStatus getPhase() {
        return TurnStatus.FIRST_ROUND;
    }

    /**
     * Returns all {@link Player}s of the current game.
     * @return a list of {@link Player}s, from 2-4
     */
    public List<Player> getPlayers() {
        return null;
    }
}
