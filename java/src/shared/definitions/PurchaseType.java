package shared.definitions;

import shared.models.game.ResourceSet;

/**
 * Types of things that a player can purchase during the game.
 */
public enum PurchaseType {
    ROAD, SETTLEMENT, CITY, DEVCARD;

    /**
     * What this item would cost if purchased.
     * @return the list of resources required
     */
    public ResourceSet purchaseCost() {
        switch (this) {
            case ROAD:
                return new ResourceSet(0, 1, 0, 1, 0);
            case SETTLEMENT:
                return new ResourceSet(0, 1, 1, 1, 1);
            case CITY:
                return new ResourceSet(3, 0, 0, 0, 2);
            case DEVCARD:
                return new ResourceSet(1, 0, 1, 0, 1);
            default:
                assert false;
                return null;
        }
    }
}
