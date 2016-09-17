package shared.definitions;

import com.google.gson.annotations.SerializedName;

public enum ResourceType
{
    @SerializedName("Wood")
	WOOD,

    @SerializedName("Brick")
	BRICK,

    @SerializedName("Sheep")
    SHEEP,

    @SerializedName("Wheat")
    WHEAT,

    @SerializedName("Ore")
    ORE
}

