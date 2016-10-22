package shared.definitions;

import shared.models.game.ResourceSet;

/**
 * Types of things that a player can purchase during the game.
 */
public enum PurchaseType {
    ROAD(new ResourceSet(0, 1, 0, 1, 0)),

    SETTLEMENT(new ResourceSet(0, 1, 1, 1, 1)),

    CITY(new ResourceSet(3, 0, 0, 0, 2)),

    DEVCARD(new ResourceSet(1, 0, 1, 0, 1));

    private ResourceSet resourcesRequired;


    PurchaseType(ResourceSet resourcesRequired) {
        this.resourcesRequired = resourcesRequired;
    }

    /**
     * What this item would cost if purchased.
     *
     * @return the list of resources required
     */
    public ResourceSet purchaseCost() {
        return new ResourceSet(resourcesRequired);
    }
}
