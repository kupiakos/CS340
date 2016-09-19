package shared.models.moves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sun.istack.internal.NotNull;
import shared.definitions.PlayerIndex;
import shared.locations.VertexLocation;

import javax.annotation.Generated;

@Generated("net.kupiakos")
public class BuildCityAction {

    @SerializedName("type")
    @Expose(deserialize = false)
    private final String TYPE = "BuildCity";

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
     * @param vertexLocation The vertexLocation
     * @param playerIndex    Who's placing the city
     */
    public BuildCityAction(VertexLocation vertexLocation, PlayerIndex playerIndex) {
        this.vertexLocation = vertexLocation;
        this.playerIndex = playerIndex;
    }

    /**
     * @return The type
     */
    public final String getType() {
        return TYPE;
    }

    /**
     * @return The vertexLocation
     */
    @NotNull
    public VertexLocation getVertexLocation() {
        return vertexLocation;
    }

    /**
     * @param vertexLocation The vertexLocation
     */
    public void setVertexLocation(@NotNull VertexLocation vertexLocation) {
        this.vertexLocation = vertexLocation;
    }

    public BuildCityAction withVertexLocation(@NotNull VertexLocation vertexLocation) {
        setVertexLocation(vertexLocation);
        return this;
    }

    /**
     * @return Who's placing the city
     */
    public PlayerIndex getPlayerIndex() {
        return playerIndex;
    }

    /**
     * @param playerIndex Who's placing the city
     */
    public void setPlayerIndex(@NotNull PlayerIndex playerIndex) {
        this.playerIndex = playerIndex;
    }

    public BuildCityAction withPlayerIndex(@NotNull PlayerIndex playerIndex) {
        setPlayerIndex(playerIndex);
        return this;
    }

    @Override
    public String toString() {
        return "BuildCityAction [" +
                "type=" + TYPE +
                ", vertexLocation=" + vertexLocation +
                ", playerIndex=" + playerIndex +
                "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof BuildCityAction) {
            return equals((BuildCityAction) other);
        }
        return false;
    }

    public boolean equals(BuildCityAction other) {
        return (
                TYPE == other.TYPE &&
                        vertexLocation == other.vertexLocation &&
                        playerIndex == other.playerIndex
        );
    }
}
