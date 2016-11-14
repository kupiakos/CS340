package shared.models.moves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import shared.definitions.PlayerIndex;
import shared.models.GameAction;

import javax.annotation.Generated;
import java.util.Objects;

@Generated("net.kupiakos")
public class BuyDevCardAction extends GameAction {

    @SerializedName("type")
    @Expose(deserialize = false)
    private final String TYPE = "buyDevCard";

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
     * @param playerIndex Who's playing this dev card
     */
    public BuyDevCardAction(PlayerIndex playerIndex) {
        this.playerIndex = playerIndex;
    }

    /**
     * @return The type
     */
    public final String getType() {
        return TYPE;
    }

    /**
     * @return Who's playing this dev card
     */
    @NotNull
    public PlayerIndex getPlayerIndex() {
        return playerIndex;
    }

    /**
     * @param playerIndex Who's playing this dev card
     */
    public void setPlayerIndex(@NotNull PlayerIndex playerIndex) {
        this.playerIndex = playerIndex;
    }

    public BuyDevCardAction withPlayerIndex(@NotNull PlayerIndex playerIndex) {
        setPlayerIndex(playerIndex);
        return this;
    }

    @Override
    public String toString() {
        return "BuyDevCardAction [" +
                "type=" + TYPE +
                ", playerIndex=" + playerIndex +
                "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof BuyDevCardAction) {
            return equals((BuyDevCardAction) other);
        }
        return false;
    }

    public boolean equals(BuyDevCardAction other) {
        return (
                TYPE == other.TYPE &&
                        Objects.equals(playerIndex, other.playerIndex)
        );
    }

    /**
     * Run on the server.  Buys a dev card for the specified {@link PlayerIndex}, which is added to their hand.
     * Removes the same card from the bank.
     */
    @Override
    public void execute() {
        getFacades().getDevCards().buyDevCard(getModel().getPlayer(playerIndex));
    }
}
