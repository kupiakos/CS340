package shared.models.moves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javax.annotation.Generated;
import shared.definitions.PlayerIndex;

@Generated("net.kupiakos")
public class DiscardCardsAction {

    @SerializedName("type")
    @Expose(deserialize = false)
    private final String type = "DiscardCards";

    @SerializedName("discardedCards")
    @Expose
    private ResourceList discardedCards;

    @SerializedName("playerIndex")
    @Expose
    private PlayerIndex playerIndex;


    // CUSTOM CODE
    // END CUSTOM CODE

    /**
     * No args constructor for use in serialization
     */
    public DiscardCardsAction() {
    }

    /**
      * @param type The type
      * @param discardedCards The discardedCards
      * @param playerIndex Who's discarding
     */
    public DiscardCardsAction(ResourceList discardedCards, PlayerIndex playerIndex) {
            this.discardedCards = discardedCards;
            this.playerIndex = playerIndex;
    }

    /**
     * @return The type
     */
    public final String getType() { return type; }

    /**
     * @return The discardedCards
     */
    public ResourceList getDiscardedCards() { return discardedCards; }

    /**
     * @param discardedCards The discardedCards
     */
    public void setDiscardedCards(ResourceList discardedCards) { this.discardedCards = discardedCards; }

    public DiscardCardsAction withDiscardedCards(ResourceList discardedCards) {
        setDiscardedCards(discardedCards);
        return this;
    }
    /**
     * @return Who's discarding
     */
    public PlayerIndex getPlayerIndex() { return playerIndex; }

    /**
     * @param playerIndex Who's discarding
     */
    public void setPlayerIndex(PlayerIndex playerIndex) { this.playerIndex = playerIndex; }

    public DiscardCardsAction withPlayerIndex(PlayerIndex playerIndex) {
        setPlayerIndex(playerIndex);
        return this;
    }

    @Override
    public String toString() {
        return "DiscardCardsAction [" +
            "type=" + type +
            ", discardedCards=" + discardedCards +
            ", playerIndex=" + playerIndex +
            "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof DiscardCardsAction) {
            return equals((DiscardCardsAction)other);
        }
        return false;
    }

    public boolean equals(DiscardCardsAction other) {
        return (
            type == other.type &&
            discardedCards == other.discardedCards &&
            playerIndex == other.playerIndex
        );
    }
}
