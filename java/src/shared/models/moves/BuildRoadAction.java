package shared.models.moves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import shared.definitions.PlayerIndex;
import shared.locations.EdgeLocation;
import shared.models.GameAction;
import shared.models.game.MessageEntry;

import javax.annotation.Generated;
import java.util.Objects;

@Generated("net.kupiakos")
public class BuildRoadAction extends GameAction {

    @SerializedName("type")
    @Expose(deserialize = false)
    private final String TYPE = "buildRoad";
    @SerializedName("free")
    @Expose
    private boolean free;
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
     * @param free         Whether this is placed for free (setup)
     * @param roadLocation The roadLocation
     * @param playerIndex  Who's placing the road
     */
    public BuildRoadAction(boolean free, EdgeLocation roadLocation, PlayerIndex playerIndex) {
        this.free = free;
        this.roadLocation = roadLocation;
        this.playerIndex = playerIndex;
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

    public BuildRoadAction withFree(boolean free) {
        setFree(free);
        return this;
    }

    /**
     * @return The type
     */
    public final String getType() {
        return TYPE;
    }

    /**
     * @return The roadLocation
     */
    @NotNull
    public EdgeLocation getRoadLocation() {
        return roadLocation;
    }

    /**
     * @param roadLocation The roadLocation
     */
    public void setRoadLocation(@NotNull EdgeLocation roadLocation) {
        this.roadLocation = roadLocation;
    }

    public BuildRoadAction withRoadLocation(@NotNull EdgeLocation roadLocation) {
        setRoadLocation(roadLocation);
        return this;
    }

    /**
     * @return Who's placing the road
     */
    public PlayerIndex getPlayerIndex() {
        return playerIndex;
    }

    /**
     * @param playerIndex Who's placing the road
     */
    public void setPlayerIndex(@NotNull PlayerIndex playerIndex) {
        this.playerIndex = playerIndex;
    }

    public BuildRoadAction withPlayerIndex(@NotNull PlayerIndex playerIndex) {
        setPlayerIndex(playerIndex);
        return this;
    }

    @Override
    public String toString() {
        return "BuildRoadAction [" +
                "free=" + free +
                ", type=" + TYPE +
                ", roadLocation=" + roadLocation +
                ", playerIndex=" + playerIndex +
                "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof BuildRoadAction) {
            return equals((BuildRoadAction) other);
        }
        return false;
    }

    public boolean equals(BuildRoadAction other) {
        return (
                free == other.free &&
                        TYPE == other.TYPE &&
                        Objects.equals(roadLocation, other.roadLocation) &&
                        Objects.equals(playerIndex, other.playerIndex)
        );
    }

    /**
     * Run on the server.  Builds a road in the game for the specified {@link PlayerIndex} at the specified {@link EdgeLocation}.
     */
    @Override
    public void execute() {
        getFacades().getBuilding().buildRoad(getFacades().getClientModel().getPlayer(playerIndex), roadLocation, free, getFacades().getTurn().isSetup());
        getFacades().getClientModel().getLog().addMessage(new MessageEntry(getModel().getPlayer(playerIndex).getName()," built a road"));
    }
}
