package shared.definitions;

import com.google.gson.annotations.SerializedName;

/**
 * The type of resources a hex can be.
 * Affects both the appearance and what resources are received.
 */
public enum HexType {
    @SerializedName("wood")
    WOOD(ResourceType.WOOD),

    @SerializedName("brick")
    BRICK(ResourceType.BRICK),

    @SerializedName("sheep")
    SHEEP(ResourceType.SHEEP),

    @SerializedName("wheat")
    WHEAT(ResourceType.WHEAT),

    @SerializedName("ore")
    ORE(ResourceType.ORE),

    @SerializedName("desert")
    DESERT(null),

    @SerializedName("water")
    WATER(null);

    private ResourceType resource;

    HexType(ResourceType resource) {
        this.resource = resource;
    }

    /**
     * Gets the type of resource that would be retrieved from this type of hex, may be null.
     * <p>
     * If a resource would not be returned from this type of hex,
     * like a {@link #DESERT desert} or {@link #WATER water} hex.
     *
     * @return the type of resource associated, or null if none
     */
    public ResourceType getResource() {
        return this.resource;
    }
}

