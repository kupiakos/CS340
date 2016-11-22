package shared.models.moves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import shared.definitions.PlayerIndex;
import shared.models.GameAction;
import shared.models.game.ResourceSet;

import javax.annotation.Generated;
import java.util.Objects;

@Generated("net.kupiakos")
public class DiscardCardsAction extends GameAction {

    @SerializedName("type")
    @Expose(deserialize = false)
    private final String TYPE = "discardCards";

    @SerializedName("discardedCards")
    @Expose
    private ResourceSet discardedCards;

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
     * @param discardedCards The discardedCards
     * @param playerIndex    Who's discarding
     */
    public DiscardCardsAction(ResourceSet discardedCards, PlayerIndex playerIndex) {
        this.discardedCards = discardedCards;
        this.playerIndex = playerIndex;
    }

    /**
     * @return The type
     */
    public final String getType() {
        return TYPE;
    }

    /**
     * @return The discardedCards
     */
    @NotNull
    public ResourceSet getDiscardedCards() {
        return discardedCards;
    }

    /**
     * @param discardedCards The discardedCards
     */
    public void setDiscardedCards(@NotNull ResourceSet discardedCards) {
        this.discardedCards = discardedCards;
    }

    public DiscardCardsAction withDiscardedCards(@NotNull ResourceSet discardedCards) {
        setDiscardedCards(discardedCards);
        return this;
    }

    /**
     * @return Who's discarding
     */
    public PlayerIndex getPlayerIndex() {
        return playerIndex;
    }

    /**
     * @param playerIndex Who's discarding
     */
    public void setPlayerIndex(@NotNull PlayerIndex playerIndex) {
        this.playerIndex = playerIndex;
    }

    public DiscardCardsAction withPlayerIndex(@NotNull PlayerIndex playerIndex) {
        setPlayerIndex(playerIndex);
        return this;
    }

    @Override
    public String toString() {
        return "DiscardCardsAction [" +
                "type=" + TYPE +
                ", discardedCards=" + discardedCards +
                ", playerIndex=" + playerIndex +
                "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof DiscardCardsAction) {
            return equals((DiscardCardsAction) other);
        }
        return false;
    }

    public boolean equals(DiscardCardsAction other) {
        return (
                TYPE == other.TYPE &&
                        Objects.equals(discardedCards, other.discardedCards) &&
                        Objects.equals(playerIndex, other.playerIndex)
        );
    }

    /**
     * Run on the server.  Remove the specified {@link ResourceSet} from the specified {@link PlayerIndex}.
     * Returns those resources to the bank.
     */
    @Override
    public void execute() {
        getFacades().getRobber().discard(discardedCards, getModel().getPlayer(playerIndex));
        getModel().incrementVersion();
    }
}
