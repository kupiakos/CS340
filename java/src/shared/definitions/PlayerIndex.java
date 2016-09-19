package shared.definitions;

import shared.models.game.ClientModel;

/**
 * A safer representation of a specific player's index in a specific game.
 * @see #fromInt
 */
public enum PlayerIndex {

    // TODO: Custom serializer/deserializer

    /**
     * Currently only used by {@link ClientModel#winner}, to represent nobody having won.
     */
    NOBODY(-1),

    /**
     * The first player
     */
    FIRST(0),

    /**
     * The second player
     */
    SECOND(1),

    /**
     * The third player
     */
    THIRD(2),

    /**
     * The fourth player
     */
    FOURTH(3);

    private int playerIndex;

    PlayerIndex(int index) {
        playerIndex = index;
    }

    /**
     * Get the numeric index this represents.
     * @return a number 1-4, or -1 if {@link #NOBODY}
     */
    public int index() {
        return playerIndex;
    }

    /**
     * Initializes an instance from a player's integer index.
     *
     * Index must be 0-3 for a valid player.
     * Any other index (-1 in the API) represents the
     * special index {@link #NOBODY}.
     * @param index the player index, 0-3 or -1
     * @return a {@link PlayerIndex} representing {@code index}
     */
    public static PlayerIndex fromInt(int index) {
        switch (index) {
            case 0:
                return FIRST;
            case 1:
                return SECOND;
            case 2:
                return THIRD;
            case 3:
                return FOURTH;
        }
        return NOBODY;
    }
}
