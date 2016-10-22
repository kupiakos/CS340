package shared.locations;

import com.google.gson.annotations.SerializedName;

public enum VertexDirection {
    @SerializedName("W")
    West,

    @SerializedName("NW")
    NorthWest,

    @SerializedName("NE")
    NorthEast,

    @SerializedName("E")
    East,

    @SerializedName("SE")
    SouthEast,

    @SerializedName("SW")
    SouthWest;

    static {
        West.opposite = East;
        NorthWest.opposite = SouthEast;
        NorthEast.opposite = SouthWest;
        East.opposite = West;
        SouthEast.opposite = NorthWest;
        SouthWest.opposite = NorthEast;
    }

    private VertexDirection opposite;

    public VertexDirection getOppositeDirection() {
        return opposite;
    }
}

