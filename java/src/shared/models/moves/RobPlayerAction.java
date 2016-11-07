package shared.models.moves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import shared.definitions.PlayerIndex;
import shared.locations.HexLocation;
import shared.models.IGameAction;

import javax.annotation.Generated;
import java.util.Objects;

@Generated("net.kupiakos")
public class RobPlayerAction implements IGameAction {

    @SerializedName("type")
    @Expose(deserialize = false)
    private final String TYPE = "robPlayer";

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
    public RobPlayerAction() {
    }

    /**
     * @param location    the new location of the robber
     * @param playerIndex Who's doing the robbing
     * @param victimIndex The order index of the player to rob
     */
    public RobPlayerAction(HexLocation location, PlayerIndex playerIndex, int victimIndex) {
        this.location = location;
        this.playerIndex = playerIndex;
        this.victimIndex = victimIndex;
    }

    /**
     * @return The type
     */
    public final String getType() {
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

    public RobPlayerAction withLocation(@NotNull HexLocation location) {
        setLocation(location);
        return this;
    }

    /**
     * @return Who's doing the robbing
     */
    public PlayerIndex getPlayerIndex() {
        return playerIndex;
    }

    /**
     * @param playerIndex Who's doing the robbing
     */
    public void setPlayerIndex(@NotNull PlayerIndex playerIndex) {
        this.playerIndex = playerIndex;
    }

    public RobPlayerAction withPlayerIndex(@NotNull PlayerIndex playerIndex) {
        setPlayerIndex(playerIndex);
        return this;
    }

    /**
     * @return The order index of the player to rob
     */
    public int getVictimIndex() {
        return victimIndex;
    }

    /**
     * @param victimIndex The order index of the player to rob
     */
    public void setVictimIndex(int victimIndex) {
        this.victimIndex = victimIndex;
    }

    public RobPlayerAction withVictimIndex(int victimIndex) {
        setVictimIndex(victimIndex);
        return this;
    }

    @Override
    public String toString() {
        return "RobPlayerAction [" +
                "type=" + TYPE +
                ", location=" + location +
                ", playerIndex=" + playerIndex +
                ", victimIndex=" + victimIndex +
                "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof RobPlayerAction) {
            return equals((RobPlayerAction) other);
        }
        return false;
    }

    public boolean equals(RobPlayerAction other) {
        return (
                TYPE == other.TYPE &&
                        Objects.equals(location, other.location) &&
                        Objects.equals(playerIndex, other.playerIndex) &&
                        victimIndex == other.victimIndex
        );
    }

    /**
     * Run on the server.  Lets a player move the robber to a new {@link HexLocation} and rob a player on that hex.
     */
    @Override
    public void execute() {

    }
}
