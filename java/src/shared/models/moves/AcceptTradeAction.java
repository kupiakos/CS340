package shared.models.moves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javax.annotation.Generated;
import shared.definitions.PlayerIndex;

@Generated("net.kupiakos")
public class AcceptTradeAction {

    @SerializedName("type")
    @Expose(deserialize = false)
    private final String type = "AcceptTrade";

    @SerializedName("willAccept")
    @Expose
    private boolean willAccept;

    @SerializedName("playerIndex")
    @Expose
    private PlayerIndex playerIndex;


    // CUSTOM CODE
    // END CUSTOM CODE

    /**
     * No args constructor for use in serialization
     */
    public AcceptTradeAction() {
    }

    /**
      * @param type The type
      * @param willAccept Whether you accept the trade or not
      * @param playerIndex Who's accepting / rejecting this trade
     */
    public AcceptTradeAction(boolean willAccept, PlayerIndex playerIndex) {
            this.willAccept = willAccept;
            this.playerIndex = playerIndex;
    }

    /**
     * @return The type
     */
    public final String getType() { return type; }

    /**
     * @return Whether you accept the trade or not
     */
    public boolean getWillAccept() { return willAccept; }

    /**
     * @param willAccept Whether you accept the trade or not
     */
    public void setWillAccept(boolean willAccept) { this.willAccept = willAccept; }

    public AcceptTradeAction withWillAccept(boolean willAccept) {
        setWillAccept(willAccept);
        return this;
    }
    /**
     * @return Who's accepting / rejecting this trade
     */
    public PlayerIndex getPlayerIndex() { return playerIndex; }

    /**
     * @param playerIndex Who's accepting / rejecting this trade
     */
    public void setPlayerIndex(PlayerIndex playerIndex) { this.playerIndex = playerIndex; }

    public AcceptTradeAction withPlayerIndex(PlayerIndex playerIndex) {
        setPlayerIndex(playerIndex);
        return this;
    }

    @Override
    public String toString() {
        return "AcceptTradeAction [" +
            "type=" + type +
            ", willAccept=" + willAccept +
            ", playerIndex=" + playerIndex +
            "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof AcceptTradeAction) {
            return equals((AcceptTradeAction)other);
        }
        return false;
    }

    public boolean equals(AcceptTradeAction other) {
        return (
            type == other.type &&
            willAccept == other.willAccept &&
            playerIndex == other.playerIndex
        );
    }
}
