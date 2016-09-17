package shared.facades;

import client.data.PlayerInfo;
import shared.definitions.PlayerIndex;
import shared.models.game.Player;

import java.util.List;

public class TurnFacade {

    /**
     * Will check to see if the current {@link Player} can end their turn.
     * @param player Which player to check
     * @return Whether the turn could be ended
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
     * Asks the game if it is the playerID's turn
     *
     * @param player The ID of the player asking
     * @return True if it is that @
     */
    private boolean isPlayersTurn(PlayerIndex player) {
        return false;
    }

    /**
     * Lets you check if you can trade, build, buy etc.
     *
     * @return The current phase of the turn
     */
    public String getPhase() {
        return null;
    }

    /**
     * Lets you see all the players including yourself
     * @return list of playerInfs
     */
    public List<PlayerInfo> getPlayers() {
        return null;
    }
}
