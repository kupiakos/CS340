package shared.definitions;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public enum PortType {
    @SerializedName("wood")
    WOOD,

    @SerializedName("brick")
    BRICK,

    @SerializedName("sheep")
    SHEEP,

    @SerializedName("wheat")
    WHEAT,

    @SerializedName("ore")
    ORE,

    @SerializedName("three")
    THREE;

    /**
     * Get the resource that matches this port type, or null if it's a three-resource port
     */
    @Contract(pure = true)
    @Nullable
    public ResourceType getResource() {
        switch (this) {
            case WOOD:
                return ResourceType.WOOD;
            case BRICK:
                return ResourceType.BRICK;
            case SHEEP:
                return ResourceType.SHEEP;
            case WHEAT:
                return ResourceType.WHEAT;
            case ORE:
                return ResourceType.ORE;
            case THREE:
                return null;
        }
        assert false;
        return null;
    }
}

