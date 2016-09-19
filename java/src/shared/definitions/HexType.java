package shared.definitions;

import com.google.gson.annotations.SerializedName;

/**
 * The type of resources a hex can be.
 * Affects both the appearance and what resources are received.
 */
public enum HexType
{
    @SerializedName("Wood")
    WOOD(ResourceType.WOOD),

    @SerializedName("Brick")
    BRICK(ResourceType.BRICK),

    @SerializedName("Sheep")
    SHEEP(ResourceType.SHEEP),

    @SerializedName("Wheat")
    WHEAT(ResourceType.WHEAT),

    @SerializedName("Ore")
    ORE(ResourceType.ORE),

    @SerializedName("Desert")
    DESERT(null),

    @SerializedName("Water")
    WATER(null);

    private ResourceType resource;

    HexType(ResourceType resource) {
        this.resource = resource;
    }

    /**
     * Gets the type of resource that would be retrieved from this type of hex, may be null.
     *
     * If a resource would not be returned from this type of hex,
     * like a {@link #DESERT desert} or {@link #WATER water} hex.
     * @return the type of resource associated, or null if none
     */
    public ResourceType getResource() {
        return this.resource;
    }
}

