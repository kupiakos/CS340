package shared.models.moves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javax.annotation.Generated;
import shared.definitions.PlayerIndex;

@Generated("net.kupiakos")
public class BuyDevCardAction {

    @SerializedName("type")
    @Expose(deserialize = false)
    private final String type = "BuyDevCard";

    @SerializedName("playerIndex")
    @Expose
    private PlayerIndex playerIndex;


    // CUSTOM CODE
    // END CUSTOM CODE

    /**
     * No args constructor for use in serialization
     */
    public BuyDevCardAction() {
    }

    /**
      * @param type The type
      * @param playerIndex Who's playing this dev card
     */
    public BuyDevCardAction(PlayerIndex playerIndex) {
            this.playerIndex = playerIndex;
    }

    /**
     * @return The type
     */
    public final String getType() { return type; }

    /**
     * @return Who's playing this dev card
     */
    public PlayerIndex getPlayerIndex() { return playerIndex; }

    /**
     * @param playerIndex Who's playing this dev card
     */
    public void setPlayerIndex(PlayerIndex playerIndex) { this.playerIndex = playerIndex; }

    public BuyDevCardAction withPlayerIndex(PlayerIndex playerIndex) {
        setPlayerIndex(playerIndex);
        return this;
    }

    @Override
    public String toString() {
        return "BuyDevCardAction [" +
            "type=" + type +
            ", playerIndex=" + playerIndex +
            "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof BuyDevCardAction) {
            return equals((BuyDevCardAction)other);
        }
        return false;
    }

    public boolean equals(BuyDevCardAction other) {
        return (
            type == other.type &&
            playerIndex == other.playerIndex
        );
    }
}
