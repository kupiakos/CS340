package shared.models.moves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javax.annotation.Generated;
import shared.definitions.PlayerIndex;
import shared.locations.VertexLocation;

@Generated("net.kupiakos")
public class BuildCityAction {

    @SerializedName("type")
    @Expose(deserialize = false)
    private final String type = "BuildCity";

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
    public BuildCityAction() {
    }

    /**
      * @param type The type
      * @param vertexLocation The vertexLocation
      * @param playerIndex Who's placing the city
     */
    public BuildCityAction(VertexLocation vertexLocation, PlayerIndex playerIndex) {
            this.vertexLocation = vertexLocation;
            this.playerIndex = playerIndex;
    }

    /**
     * @return The type
     */
    public final String getType() { return type; }

    /**
     * @return The vertexLocation
     */
    public VertexLocation getVertexLocation() { return vertexLocation; }

    /**
     * @param vertexLocation The vertexLocation
     */
    public void setVertexLocation(VertexLocation vertexLocation) { this.vertexLocation = vertexLocation; }

    public BuildCityAction withVertexLocation(VertexLocation vertexLocation) {
        setVertexLocation(vertexLocation);
        return this;
    }
    /**
     * @return Who's placing the city
     */
    public PlayerIndex getPlayerIndex() { return playerIndex; }

    /**
     * @param playerIndex Who's placing the city
     */
    public void setPlayerIndex(PlayerIndex playerIndex) { this.playerIndex = playerIndex; }

    public BuildCityAction withPlayerIndex(PlayerIndex playerIndex) {
        setPlayerIndex(playerIndex);
        return this;
    }

    @Override
    public String toString() {
        return "BuildCityAction [" +
            "type=" + type +
            ", vertexLocation=" + vertexLocation +
            ", playerIndex=" + playerIndex +
            "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof BuildCityAction) {
            return equals((BuildCityAction)other);
        }
        return false;
    }

    public boolean equals(BuildCityAction other) {
        return (
            type == other.type &&
            vertexLocation == other.vertexLocation &&
            playerIndex == other.playerIndex
        );
    }
}
