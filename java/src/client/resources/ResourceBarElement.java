package client.resources;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import shared.definitions.ResourceType;

public enum ResourceBarElement {
    WOOD, BRICK, SHEEP, WHEAT, ORE, ROAD, SETTLEMENT, CITY, BUY_CARD, PLAY_CARD, SOLDIERS;

    @Contract(pure = true)
    @NotNull
    public static ResourceBarElement fromResource(@NotNull ResourceType type) {
        switch (type) {
            case WOOD:
                return WOOD;
            case BRICK:
                return BRICK;
            case SHEEP:
                return SHEEP;
            case WHEAT:
                return WHEAT;
            case ORE:
                return ORE;
        }
        assert false;
        return null;
    }
}

