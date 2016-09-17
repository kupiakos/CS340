package shared.models.moves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javax.annotation.Generated;
import shared.definitions.DevCardType;
import shared.definitions.PlayerIndex;

@Generated("net.kupiakos")
public class MonumentAction {

    @SerializedName("type")
    @Expose(deserialize = false)
    private final DevCardType TYPE = DevCardType.MONUMENT;

    @SerializedName("playerIndex")
    @Expose
    private PlayerIndex playerIndex;


    // CUSTOM CODE
    // END CUSTOM CODE

    /**
     * No args constructor for use in serialization
     */
    public MonumentAction() {
    }

    /**
      * @param TYPE The type
      * @param playerIndex Who's playing this dev card
     */
    public MonumentAction(PlayerIndex playerIndex) {
            this.playerIndex = playerIndex;
    }

    /**
     * @return The type
     */
    public final DevCardType getType() { return TYPE; }

    /**
     * @return Who's playing this dev card
     */
    public PlayerIndex getPlayerIndex() { return playerIndex; }

    /**
     * @param playerIndex Who's playing this dev card
     */
    public void setPlayerIndex(PlayerIndex playerIndex) { this.playerIndex = playerIndex; }

    public MonumentAction withPlayerIndex(PlayerIndex playerIndex) {
        setPlayerIndex(playerIndex);
        return this;
    }

    @Override
    public String toString() {
        return "MonumentAction [" +
            "type=" + TYPE +
            ", playerIndex=" + playerIndex +
            "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof MonumentAction) {
            return equals((MonumentAction)other);
        }
        return false;
    }

    public boolean equals(MonumentAction other) {
        return (
            TYPE == other.TYPE &&
            playerIndex == other.playerIndex
        );
    }
}
