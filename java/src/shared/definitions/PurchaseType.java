package shared.definitions;

import shared.models.game.ResourceList;

/**
 * Types of things that a player can purchase during the game.
 */
public enum PurchaseType {
    ROAD, SETTLEMENT, CITY, DEVCARD;

    /**
     * What this item would cost if purchased.
     * @return the list of resources required
     */
    public ResourceList purchaseCost() {
        switch (this) {
            case ROAD:
                return new ResourceList(0, 1, 0, 1, 0);
            case SETTLEMENT:
                return new ResourceList(0, 1, 1, 1, 1);
            case CITY:
                return new ResourceList(3, 0, 0, 0, 2);
            case DEVCARD:
                return new ResourceList(1, 0, 1, 0, 1);
            default:
                assert false;
                return null;
        }
    }
}
