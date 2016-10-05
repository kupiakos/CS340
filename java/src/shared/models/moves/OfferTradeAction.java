package shared.models.moves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import shared.definitions.PlayerIndex;
import shared.models.game.ResourceSet;

import javax.annotation.Generated;
import java.util.Objects;

@Generated("net.kupiakos")
public class OfferTradeAction {

    @SerializedName("type")
    @Expose(deserialize = false)
    private final String TYPE = "OfferTrade";

    @SerializedName("receiver")
    @Expose
    private PlayerIndex receiver;

    @SerializedName("offer")
    @Expose
    private ResourceSet offer;

    @SerializedName("playerIndex")
    @Expose
    private PlayerIndex playerIndex;


    // CUSTOM CODE
    // END CUSTOM CODE

    /**
     * No args constructor for use in serialization
     */
    public OfferTradeAction() {
    }

    /**
     * @param receiver    Who you're offering the trade to (0-3)
     * @param offer       What you get (+) and what you give (-)
     * @param playerIndex Who's sending the offer
     */
    public OfferTradeAction(PlayerIndex receiver, ResourceSet offer, PlayerIndex playerIndex) {
        this.receiver = receiver;
        this.offer = offer;
        this.playerIndex = playerIndex;
    }

    /**
     * @return The type
     */
    public final String getType() {
        return TYPE;
    }

    /**
     * @return Who you're offering the trade to (0-3)
     */
    public PlayerIndex getReceiver() {
        return receiver;
    }

    /**
     * @param receiver Who you're offering the trade to (0-3)
     */
    public void setReceiver(@NotNull PlayerIndex receiver) {
        this.receiver = receiver;
    }

    public OfferTradeAction withReceiver(@NotNull PlayerIndex receiver) {
        setReceiver(receiver);
        return this;
    }

    /**
     * @return What you get (+) and what you give (-)
     */
    public ResourceSet getOffer() {
        return offer;
    }

    /**
     * @param offer What you get (+) and what you give (-)
     */
    public void setOffer(@NotNull ResourceSet offer) {
        this.offer = offer;
    }

    public OfferTradeAction withOffer(@NotNull ResourceSet offer) {
        setOffer(offer);
        return this;
    }

    /**
     * @return Who's sending the offer
     */
    public PlayerIndex getPlayerIndex() {
        return playerIndex;
    }

    /**
     * @param playerIndex Who's sending the offer
     */
    public void setPlayerIndex(@NotNull PlayerIndex playerIndex) {
        this.playerIndex = playerIndex;
    }

    public OfferTradeAction withPlayerIndex(@NotNull PlayerIndex playerIndex) {
        setPlayerIndex(playerIndex);
        return this;
    }

    @Override
    public String toString() {
        return "OfferTradeAction [" +
                "type=" + TYPE +
                ", receiver=" + receiver +
                ", offer=" + offer +
                ", playerIndex=" + playerIndex +
                "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof OfferTradeAction) {
            return equals((OfferTradeAction) other);
        }
        return false;
    }

    public boolean equals(OfferTradeAction other) {
        return (
                TYPE == other.TYPE &&
                        Objects.equals(receiver, other.receiver) &&
                        Objects.equals(offer, other.offer) &&
                        Objects.equals(playerIndex, other.playerIndex)
        );
    }
}
