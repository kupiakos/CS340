package shared.models.moves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import shared.definitions.DevCardType;
import shared.definitions.PlayerIndex;
import shared.locations.EdgeLocation;
import shared.models.GameAction;

import javax.annotation.Generated;
import java.util.Objects;

@Generated("net.kupiakos")
public class RoadBuildingAction extends GameAction {

    @SerializedName("type")
    @Expose(deserialize = false)
    private final DevCardType TYPE = DevCardType.ROAD_BUILD;

    @SerializedName("spot2")
    @Expose
    private EdgeLocation spot2;

    @SerializedName("spot1")
    @Expose
    private EdgeLocation spot1;

    @SerializedName("playerIndex")
    @Expose
    private PlayerIndex playerIndex;


    // CUSTOM CODE
    // END CUSTOM CODE

    /**
     * No args constructor for use in serialization
     */
    public RoadBuildingAction() {
    }

    /**
     * @param spot2       The spot2
     * @param spot1       The spot1
     * @param playerIndex Who's placing the roads
     */
    public RoadBuildingAction(EdgeLocation spot2, EdgeLocation spot1, PlayerIndex playerIndex) {
        this.spot2 = spot2;
        this.spot1 = spot1;
        this.playerIndex = playerIndex;
    }

    /**
     * @return The type
     */
    public final DevCardType getType() {
        return TYPE;
    }

    /**
     * @return The spot2
     */
    public EdgeLocation getSpot2() {
        return spot2;
    }

    /**
     * @param spot2 The spot2
     */
    public void setSpot2(@Nullable EdgeLocation spot2) {
        this.spot2 = spot2;
    }

    public RoadBuildingAction withSpot2(@Nullable EdgeLocation spot2) {
        setSpot2(spot2);
        return this;
    }

    /**
     * @return The spot1
     */
    public EdgeLocation getSpot1() {
        return spot1;
    }

    /**
     * @param spot1 The spot1
     */
    public void setSpot1(@NotNull EdgeLocation spot1) {
        this.spot1 = spot1;
    }

    public RoadBuildingAction withSpot1(@NotNull EdgeLocation spot1) {
        setSpot1(spot1);
        return this;
    }

    /**
     * @return Who's placing the roads
     */
    public PlayerIndex getPlayerIndex() {
        return playerIndex;
    }

    /**
     * @param playerIndex Who's placing the roads
     */
    public void setPlayerIndex(@NotNull PlayerIndex playerIndex) {
        this.playerIndex = playerIndex;
    }

    public RoadBuildingAction withPlayerIndex(@NotNull PlayerIndex playerIndex) {
        setPlayerIndex(playerIndex);
        return this;
    }

    @Override
    public String toString() {
        return "RoadBuildingAction [" +
                "type=" + TYPE +
                ", spot2=" + spot2 +
                ", spot1=" + spot1 +
                ", playerIndex=" + playerIndex +
                "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof RoadBuildingAction) {
            return equals((RoadBuildingAction) other);
        }
        return false;
    }

    public boolean equals(RoadBuildingAction other) {
        return (
                TYPE == other.TYPE &&
                        Objects.equals(spot2, other.spot2) &&
                        Objects.equals(spot1, other.spot1) &&
                        Objects.equals(playerIndex, other.playerIndex)
        );
    }

    /**
     * Run on the server.  Executes a road building card on the server side. Builds two roads at the specified {@link shared.locations.VertexLocation}s
     * for the specified {@link PlayerIndex}.
     */
    @Override
    public void execute() {

    }
}
