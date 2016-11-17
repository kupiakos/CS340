package shared.models.moves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import shared.definitions.PlayerIndex;
import shared.locations.VertexLocation;
import shared.models.GameAction;

import javax.annotation.Generated;
import java.util.Objects;

@Generated("net.kupiakos")
public class BuildCityAction extends GameAction {

    @SerializedName("type")
    @Expose(deserialize = false)
    private final String TYPE = "buildCity";

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
                        Objects.equals(vertexLocation, other.vertexLocation) &&
                        Objects.equals(playerIndex, other.playerIndex)
        );
    }

    /**
     * Run on the server.  Will build a city at the specified {@link VertexLocation} for the specified {@link PlayerIndex}.
     */
    @Override
    public void execute() {
        getFacades().getBuilding().buildCity(getModel().getPlayer(playerIndex), vertexLocation);
        getFacades().getClientModel().getLog().prefixMessage(getModel().getPlayer(playerIndex), " built a city");
        getModel().incrementVersion();
    }
}
