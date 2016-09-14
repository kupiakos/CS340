package shared.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class TradeOffer {

    @SerializedName("sender")
    @Expose
    private String sender;
    @SerializedName("receiver")
    @Expose
    private String receiver;
    @SerializedName("offer")
    @Expose
    private Offer offer;

    /**
     * No args constructor for use in serialization
     */
    public TradeOffer() {
    }

    /**
     * @param sender
     * @param receiver
     * @param offer
     */
    public TradeOffer(String sender, String receiver, Offer offer) {
        this.sender = sender;
        this.receiver = receiver;
        this.offer = offer;
    }

    /**
     * @return The sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * @param sender The sender
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    public TradeOffer withSender(String sender) {
        this.sender = sender;
        return this;
    }

    /**
     * @return The receiver
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * @param receiver The receiver
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public TradeOffer withReceiver(String receiver) {
        this.receiver = receiver;
        return this;
    }

    /**
     * @return The offer
     */
    public Offer getOffer() {
        return offer;
    }

    /**
     * @param offer The offer
     */
    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public TradeOffer withOffer(Offer offer) {
        this.offer = offer;
        return this;
    }

}
