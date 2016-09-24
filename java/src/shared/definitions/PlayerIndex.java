package shared.definitions;

/**
 * A safer representation of a specific player's index in a specific game.
 * @see #fromInt
 */
public enum PlayerIndex {

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

    public static final int MAX_PLAYERS = 4;

    /**
     * Get the numeric index this represents.
     * @return a number 1-4
     */
    public int index() {
        return playerIndex;
    }

    /**
     * Initializes an instance from a player's integer index.
     *
     * Index must be 0-3 for a valid player.
     * @param index the player index, in range [0, 3]
     * @return a {@link PlayerIndex} representing {@code index}
     * @throws IllegalArgumentException if index is not in range [0, 3]
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
            default:
                throw new IllegalArgumentException("PlayerIndex must be 0-3");
        }
    }
}
