package shared.models.moves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javax.annotation.Generated;
import shared.locations.EdgeLocation;
import shared.definitions.PlayerIndex;

@Generated("net.kupiakos")
public class BuildRoadAction {

    @SerializedName("free")
    @Expose
    private Boolean free;

    @SerializedName("type")
    @Expose(deserialize = false)
    private final String type = "BuildRoad";

    @SerializedName("roadLocation")
    @Expose
    private EdgeLocation roadLocation;

    @SerializedName("playerIndex")
    @Expose
    private PlayerIndex playerIndex;


    // CUSTOM CODE
    // END CUSTOM CODE

    /**
     * No args constructor for use in serialization
     */
    public BuildRoadAction() {
    }

    /**
      * @param free Whether this is placed for free (setup)
      * @param type The type
      * @param roadLocation The roadLocation
      * @param playerIndex Who's placing the road
     */
    public BuildRoadAction(Boolean free, EdgeLocation roadLocation, PlayerIndex playerIndex) {
            this.free = free;
            this.roadLocation = roadLocation;
            this.playerIndex = playerIndex;
    }

    /**
     * @return Whether this is placed for free (setup)
     */
    public Boolean getFree() { return free; }

    /**
     * @param free Whether this is placed for free (setup)
     */
    public void setFree(Boolean free) { this.free = free; }

    public BuildRoadAction withFree(Boolean free) {
        setFree(free);
        return this;
    }
    /**
     * @return The type
     */
    public final String getType() { return type; }

    /**
     * @return The roadLocation
     */
    public EdgeLocation getRoadLocation() { return roadLocation; }

    /**
     * @param roadLocation The roadLocation
     */
    public void setRoadLocation(EdgeLocation roadLocation) { this.roadLocation = roadLocation; }

    public BuildRoadAction withRoadLocation(EdgeLocation roadLocation) {
        setRoadLocation(roadLocation);
        return this;
    }
    /**
     * @return Who's placing the road
     */
    public PlayerIndex getPlayerIndex() { return playerIndex; }

    /**
     * @param playerIndex Who's placing the road
     */
    public void setPlayerIndex(PlayerIndex playerIndex) { this.playerIndex = playerIndex; }

    public BuildRoadAction withPlayerIndex(PlayerIndex playerIndex) {
        setPlayerIndex(playerIndex);
        return this;
    }

    @Override
    public String toString() {
        return "BuildRoadAction [" +
            "free=" + free +
            ", type=" + type +
            ", roadLocation=" + roadLocation +
            ", playerIndex=" + playerIndex +
            "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof BuildRoadAction) {
            return equals((BuildRoadAction)other);
        }
        return false;
    }

    public boolean equals(BuildRoadAction other) {
        return (
            free == other.free &&
            type == other.type &&
            roadLocation == other.roadLocation &&
            playerIndex == other.playerIndex
        );
    }
}
