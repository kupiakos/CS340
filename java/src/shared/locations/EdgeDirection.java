package shared.locations;

import com.google.gson.annotations.SerializedName;

public enum EdgeDirection {
    @SerializedName("NW")
    NorthWest,

    @SerializedName("N")
    North,

    @SerializedName("NE")
    NorthEast,

    @SerializedName("SE")
    SouthEast,

    @SerializedName("S")
    South,

    @SerializedName("SW")
    SouthWest;

    static {
        NorthWest.opposite = SouthEast;
        North.opposite = South;
        NorthEast.opposite = SouthWest;
        SouthEast.opposite = NorthWest;
        South.opposite = North;
        SouthWest.opposite = NorthEast;
    }

    private EdgeDirection opposite;

    public EdgeDirection getOppositeDirection() {
        return opposite;
    }
}

