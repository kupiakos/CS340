package shared.models.moves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javax.annotation.Generated;
import shared.definitions.PlayerIndex;
import shared.locations.VertexLocation;

@Generated("net.kupiakos")
public class BuildSettlementAction {

    @SerializedName("type")
    @Expose(deserialize = false)
    private final String TYPE = "BuildSettlement";

    @SerializedName("free")
    @Expose
    private Boolean free;

    @SerializedName("vertexLocation")
    @Expose
    private VertexLocation vertexLocation;

    @SerializedName("playerIndex")
    @Expose
    private PlayerIndex playerIndex;


    // CUSTOM CODE
    // END CUSTOM CODE

    /**
     * No args constructor for use in serialization
     */
    public BuildSettlementAction() {
    }

    /**
      * @param TYPE The type
      * @param free Whether this is placed for free (setup)
      * @param vertexLocation The vertexLocation
      * @param playerIndex Who's placing the settlement
     */
    public BuildSettlementAction(Boolean free, VertexLocation vertexLocation, PlayerIndex playerIndex) {
            this.free = free;
            this.vertexLocation = vertexLocation;
            this.playerIndex = playerIndex;
    }

    /**
     * @return The type
     */
    public final String getType() { return TYPE; }

    /**
     * @return Whether this is placed for free (setup)
     */
    public Boolean getFree() { return free; }

    /**
     * @param free Whether this is placed for free (setup)
     */
    public void setFree(Boolean free) { this.free = free; }

    public BuildSettlementAction withFree(Boolean free) {
        setFree(free);
        return this;
    }
    /**
     * @return The vertexLocation
     */
    public VertexLocation getVertexLocation() { return vertexLocation; }

    /**
     * @param vertexLocation The vertexLocation
     */
    public void setVertexLocation(VertexLocation vertexLocation) { this.vertexLocation = vertexLocation; }

    public BuildSettlementAction withVertexLocation(VertexLocation vertexLocation) {
        setVertexLocation(vertexLocation);
        return this;
    }
    /**
     * @return Who's placing the settlement
     */
    public PlayerIndex getPlayerIndex() { return playerIndex; }

    /**
     * @param playerIndex Who's placing the settlement
     */
    public void setPlayerIndex(PlayerIndex playerIndex) { this.playerIndex = playerIndex; }

    public BuildSettlementAction withPlayerIndex(PlayerIndex playerIndex) {
        setPlayerIndex(playerIndex);
        return this;
    }

    @Override
    public String toString() {
        return "BuildSettlementAction [" +
            "type=" + TYPE +
            ", free=" + free +
            ", vertexLocation=" + vertexLocation +
            ", playerIndex=" + playerIndex +
            "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof BuildSettlementAction) {
            return equals((BuildSettlementAction)other);
        }
        return false;
    }

    public boolean equals(BuildSettlementAction other) {
        return (
            TYPE == other.TYPE &&
            free == other.free &&
            vertexLocation == other.vertexLocation &&
            playerIndex == other.playerIndex
        );
    }
}
