package shared.models.moves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import shared.definitions.PlayerIndex;

import javax.annotation.Generated;
import java.util.Objects;

@Generated("net.kupiakos")
public class AcceptTradeAction {

    @SerializedName("type")
    @Expose(deserialize = false)
    private final String TYPE = "AcceptTrade";

    @SerializedName("willAccept")
    @Expose
    private boolean willAccept;

    @SerializedName("playerIndex")
    @Expose
    private PlayerIndex receiver;


    // CUSTOM CODE
    // END CUSTOM CODE

    /**
     * No args constructor for use in serialization
     */
    public AcceptTradeAction() {
    }

    /**
     * @param willAccept Whether you accept the trade or not
     * @param receiver   Who's accepting / rejecting this trade
     */
    public AcceptTradeAction(boolean willAccept, PlayerIndex receiver) {
        this.willAccept = willAccept;
        this.receiver = receiver;
    }

    /**
     * @return The type
     */
    public final String getType() {
        return TYPE;
    }

    /**
     * @return Whether you accept the trade or not
     */
    public boolean getWillAccept() {
        return willAccept;
    }

    /**
     * @param willAccept Whether you accept the trade or not
     */
    public void setWillAccept(boolean willAccept) {
        this.willAccept = willAccept;
    }

    public AcceptTradeAction withWillAccept(boolean willAccept) {
        setWillAccept(willAccept);
        return this;
    }

    /**
     * @return Who's accepting / rejecting this trade
     */
    @NotNull
    public PlayerIndex getReceiver() {
        return receiver;
    }

    /**
     * @param receiver Who's accepting / rejecting this trade
     */
    public void setReceiver(@NotNull PlayerIndex receiver) {
        this.receiver = receiver;
    }

    public AcceptTradeAction withPlayerIndex(@NotNull PlayerIndex playerIndex) {
        setReceiver(playerIndex);
        return this;
    }

    @Override
    public String toString() {
        return "AcceptTradeAction [" +
                "type=" + TYPE +
                ", willAccept=" + willAccept +
                ", receiver=" + receiver +
                "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof AcceptTradeAction) {
            return equals((AcceptTradeAction) other);
        }
        return false;
    }

    public boolean equals(AcceptTradeAction other) {
        return (
                TYPE == other.TYPE &&
                        willAccept == other.willAccept &&
                        Objects.equals(receiver, other.receiver)
        );
    }
}
