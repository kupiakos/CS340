package shared.models.moves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javax.annotation.Generated;
import shared.definitions.PlayerIndex;
import shared.models.game.ResourceList;

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
    private ResourceList offer;

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
      * @param TYPE The type
      * @param receiver Who you're offering the trade to (0-3)
      * @param offer What you get (+) and what you give (-)
      * @param playerIndex Who's sending the offer
     */
    public OfferTradeAction(PlayerIndex receiver, ResourceList offer, PlayerIndex playerIndex) {
            this.receiver = receiver;
            this.offer = offer;
            this.playerIndex = playerIndex;
    }

    /**
     * @return The type
     */
    public final String getType() { return TYPE; }

    /**
     * @return Who you're offering the trade to (0-3)
     */
    public PlayerIndex getReceiver() { return receiver; }

    /**
     * @param receiver Who you're offering the trade to (0-3)
     */
    public void setReceiver(PlayerIndex receiver) { this.receiver = receiver; }

    public OfferTradeAction withReceiver(PlayerIndex receiver) {
        setReceiver(receiver);
        return this;
    }
    /**
     * @return What you get (+) and what you give (-)
     */
    public ResourceList getOffer() { return offer; }

    /**
     * @param offer What you get (+) and what you give (-)
     */
    public void setOffer(ResourceList offer) { this.offer = offer; }

    public OfferTradeAction withOffer(ResourceList offer) {
        setOffer(offer);
        return this;
    }
    /**
     * @return Who's sending the offer
     */
    public PlayerIndex getPlayerIndex() { return playerIndex; }

    /**
     * @param playerIndex Who's sending the offer
     */
    public void setPlayerIndex(PlayerIndex playerIndex) { this.playerIndex = playerIndex; }

    public OfferTradeAction withPlayerIndex(PlayerIndex playerIndex) {
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
            return equals((OfferTradeAction)other);
        }
        return false;
    }

    public boolean equals(OfferTradeAction other) {
        return (
            TYPE == other.TYPE &&
            receiver == other.receiver &&
            offer == other.offer &&
            playerIndex == other.playerIndex
        );
    }
}
