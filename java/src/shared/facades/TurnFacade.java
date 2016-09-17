package shared.facades;

import client.data.PlayerInfo;

import java.util.List;

public class TurnFacade {

    /**
     * Will check to see if the current player can end their turn
     * @return
     */
    public static boolean canEndTurn() {
        return false;
    }

    /**
     * checks to see if they can end turn, then will end their turn
     */
    public static void finishTurn() {
        if (canEndTurn()) {

        }
    }

    /**
     * Asks the game if it is the playerID's turn
     *
     * @param playerID The ID of the player asking
     */
    private boolean myTurn(int playerID) {
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
