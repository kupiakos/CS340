package shared.models.game;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import shared.definitions.PlayerIndex;

import javax.annotation.Generated;
import java.util.Objects;

@Generated("net.kupiakos")
public class TradeOffer {

    @SerializedName("receiver")
    @Expose
    private PlayerIndex receiver;

    @SerializedName("offer")
    @Expose
    private ResourceSet offer;

    @SerializedName("sender")
    @Expose
    private PlayerIndex sender;


    // CUSTOM CODE
    // END CUSTOM CODE

    /**
     * No args constructor for use in serialization
     */
    public TradeOffer() {
    }

    /**
     * @param receiver The index of the person the trade was offered to.
     * @param offer    Positive numbers are resources being offered. Negative are resources being asked for.
     * @param sender   The index of the person offering the trade
     */
    public TradeOffer(PlayerIndex receiver, ResourceSet offer, PlayerIndex sender) {
        this.receiver = receiver;
        this.offer = offer;
        this.sender = sender;
    }

    /**
     * @return The index of the person the trade was offered to.
     */
    public PlayerIndex getReceiver() {
        return receiver;
    }

    /**
     * @param receiver The index of the person the trade was offered to.
     */
    public void setReceiver(@NotNull PlayerIndex receiver) {
        this.receiver = receiver;
    }

    public TradeOffer withReceiver(@NotNull PlayerIndex receiver) {
        setReceiver(receiver);
        return this;
    }

    /**
     * @return Positive numbers are resources being offered. Negative are resources being asked for.
     */
    public ResourceSet getOffer() {
        return offer;
    }

    /**
     * @param offer Positive numbers are resources being offered. Negative are resources being asked for.
     */
    public void setOffer(@NotNull ResourceSet offer) {
        this.offer = offer;
    }

    public TradeOffer withOffer(@NotNull ResourceSet offer) {
        setOffer(offer);
        return this;
    }

    /**
     * @return The index of the person offering the trade
     */
    public PlayerIndex getSender() {
        return sender;
    }

    /**
     * @param sender The index of the person offering the trade
     */
    public void setSender(@NotNull PlayerIndex sender) {
        this.sender = sender;
    }

    public TradeOffer withSender(@NotNull PlayerIndex sender) {
        setSender(sender);
        return this;
    }

    @Override
    public String toString() {
        return "TradeOffer [" +
                "receiver=" + receiver +
                ", offer=" + offer +
                ", sender=" + sender +
                "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof TradeOffer) {
            return equals((TradeOffer) other);
        }
        return false;
    }

    public boolean equals(TradeOffer other) {
        return (
                Objects.equals(receiver, other.receiver) &&
                        Objects.equals(offer, other.offer) &&
                        Objects.equals(sender, other.sender)
        );
    }
}
