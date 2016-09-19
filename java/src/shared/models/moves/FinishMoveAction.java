package shared.models.moves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sun.istack.internal.NotNull;
import shared.definitions.PlayerIndex;

import javax.annotation.Generated;

@Generated("net.kupiakos")
public class FinishMoveAction {

    @SerializedName("type")
    @Expose(deserialize = false)
    private final String TYPE = "FinishTurn";

    @SerializedName("playerIndex")
    @Expose
    private PlayerIndex playerIndex;


    // CUSTOM CODE
    // END CUSTOM CODE

    /**
     * No args constructor for use in serialization
     */
    public FinishMoveAction() {
    }

    /**
     * @param playerIndex Who's sending this command (0-3)
     */
    public FinishMoveAction(PlayerIndex playerIndex) {
        this.playerIndex = playerIndex;
    }

    /**
     * @return The type
     */
    public final String getType() {
        return TYPE;
    }

    /**
     * @return Who's sending this command (0-3)
     */
    @NotNull
    public PlayerIndex getPlayerIndex() {
        return playerIndex;
    }

    /**
     * @param playerIndex Who's sending this command (0-3)
     */
    public void setPlayerIndex(@NotNull PlayerIndex playerIndex) {
        this.playerIndex = playerIndex;
    }

    public FinishMoveAction withPlayerIndex(@NotNull PlayerIndex playerIndex) {
        setPlayerIndex(playerIndex);
        return this;
    }

    @Override
    public String toString() {
        return "FinishMoveAction [" +
                "type=" + TYPE +
                ", playerIndex=" + playerIndex +
                "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof FinishMoveAction) {
            return equals((FinishMoveAction) other);
        }
        return false;
    }

    public boolean equals(FinishMoveAction other) {
        return (
                TYPE == other.TYPE &&
                        playerIndex == other.playerIndex
        );
    }
}
