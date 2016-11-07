package shared.models.moves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import shared.definitions.PlayerIndex;
import shared.locations.VertexLocation;
import shared.models.IGameAction;
import shared.models.game.ClientModel;

import javax.annotation.Generated;
import java.util.Objects;

@Generated("net.kupiakos")
public class BuildSettlementAction implements IGameAction {

    @SerializedName("type")
    @Expose(deserialize = false)
    private final String TYPE = "buildSettlement";

    @SerializedName("free")
    @Expose
    private boolean free;

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
     * @param free           Whether this is placed for free (setup)
     * @param vertexLocation The vertexLocation
     * @param playerIndex    Who's placing the settlement
     */
    public BuildSettlementAction(boolean free, VertexLocation vertexLocation, PlayerIndex playerIndex) {
        this.free = free;
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
     * @return Whether this is placed for free (setup)
     */
    public boolean getFree() {
        return free;
    }

    /**
     * @param free Whether this is placed for free (setup)
     */
    public void setFree(boolean free) {
        this.free = free;
    }

    public BuildSettlementAction withFree(boolean free) {
        setFree(free);
        return this;
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

    public BuildSettlementAction withVertexLocation(@NotNull VertexLocation vertexLocation) {
        setVertexLocation(vertexLocation);
        return this;
    }

    /**
     * @return Who's placing the settlement
     */
    public PlayerIndex getPlayerIndex() {
        return playerIndex;
    }

    /**
     * @param playerIndex Who's placing the settlement
     */
    public void setPlayerIndex(@NotNull PlayerIndex playerIndex) {
        this.playerIndex = playerIndex;
    }

    public BuildSettlementAction withPlayerIndex(@NotNull PlayerIndex playerIndex) {
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
            return equals((BuildSettlementAction) other);
        }
        return false;
    }

    public boolean equals(BuildSettlementAction other) {
        return (
                (TYPE == other.TYPE) &&
                        (free == other.free) &&
                        Objects.equals(vertexLocation, other.vertexLocation) &&
                        Objects.equals(playerIndex, other.playerIndex)
        );
    }

    /**
     * Run on the server.  Builds a settlement in the game for the {@link PlayerIndex} at the specified {@link VertexLocation}.
     * @param model
     */
    @Override
    public void execute(ClientModel model) {

    }
}
