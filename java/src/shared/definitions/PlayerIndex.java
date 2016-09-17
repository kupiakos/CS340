package shared.definitions;


public enum PlayerIndex {

    NOBODY(-1),
    FIRST(0),
    SECOND(1),
    THIRD(2),
    FOURTH(3);

    private int playerIndex;

    PlayerIndex(int index) {
        playerIndex = index;
    }

    int index() {
        return playerIndex;
    }

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
