package shared.models.moves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import shared.definitions.DevCardType;
import shared.definitions.PlayerIndex;
import shared.locations.HexLocation;
import shared.models.GameAction;

import javax.annotation.Generated;
import java.util.Objects;

@Generated("net.kupiakos")
public class SoldierAction extends GameAction {

    @SerializedName("type")
    @Expose(deserialize = false)
    private final DevCardType TYPE = DevCardType.SOLDIER;

    @SerializedName("location")
    @Expose
    private HexLocation location;

    @SerializedName("playerIndex")
    @Expose
    private PlayerIndex playerIndex;

    @SerializedName("victimIndex")
    @Expose
    private int victimIndex;


    // CUSTOM CODE
    // END CUSTOM CODE

    /**
     * No args constructor for use in serialization
     */
    public SoldierAction() {
    }

    /**
     * @param location    the new location of the robber
     * @param playerIndex Who's playing this dev card
     * @param victimIndex The index of the player to rob
     */
    public SoldierAction(HexLocation location, PlayerIndex playerIndex, int victimIndex) {
        this.location = location;
        this.playerIndex = playerIndex;
        this.victimIndex = victimIndex;
    }

    /**
     * @return The type
     */
    public final DevCardType getType() {
        return TYPE;
    }

    /**
     * @return the new location of the robber
     */
    public HexLocation getLocation() {
        return location;
    }

    /**
     * @param location the new location of the robber
     */
    public void setLocation(@NotNull HexLocation location) {
        this.location = location;
    }

    public SoldierAction withLocation(@NotNull HexLocation location) {
        setLocation(location);
        return this;
    }

    /**
     * @return Who's playing this dev card
     */
    public PlayerIndex getPlayerIndex() {
        return playerIndex;
    }

    /**
     * @param playerIndex Who's playing this dev card
     */
    public void setPlayerIndex(@NotNull PlayerIndex playerIndex) {
        this.playerIndex = playerIndex;
    }

    public SoldierAction withPlayerIndex(@NotNull PlayerIndex playerIndex) {
        setPlayerIndex(playerIndex);
        return this;
    }

    /**
     * @return The index of the player to rob
     */
    public int getVictimIndex() {
        return victimIndex;
    }

    /**
     * @param victimIndex The index of the player to rob
     */
    public void setVictimIndex(int victimIndex) {
        this.victimIndex = victimIndex;
    }

    public SoldierAction withVictimIndex(int victimIndex) {
        setVictimIndex(victimIndex);
        return this;
    }

    @Override
    public String toString() {
        return "SoldierAction [" +
                "type=" + TYPE +
                ", location=" + location +
                ", playerIndex=" + playerIndex +
                ", victimIndex=" + victimIndex +
                "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof SoldierAction) {
            return equals((SoldierAction) other);
        }
        return false;
    }

    public boolean equals(SoldierAction other) {
        return (
                TYPE == other.TYPE &&
                        Objects.equals(location, other.location) &&
                        Objects.equals(playerIndex, other.playerIndex) &&
                        victimIndex == other.victimIndex
        );
    }

    /**
     * Run on the server.  Executes a soldier card on the server.  Allows {@link PlayerIndex} to move the robber to specified
     * {@link HexLocation} and rob a player at the location.
     */
    @Override
    public void execute() {
        getFacades().getDevCards().useSoldierCard(getModel().getPlayer(playerIndex));
        getFacades().getClientModel().getLog().prefixMessage(getModel().getPlayer(playerIndex), " played a Soldier card");
        getModel().incrementVersion();
    }
}
