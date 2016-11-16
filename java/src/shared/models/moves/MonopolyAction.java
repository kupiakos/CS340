package shared.models.moves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import shared.definitions.DevCardType;
import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;
import shared.models.GameAction;
import shared.models.game.MessageEntry;

import javax.annotation.Generated;
import java.util.Objects;

@Generated("net.kupiakos")
public class MonopolyAction extends GameAction {

    @SerializedName("type")
    @Expose(deserialize = false)
    private final DevCardType TYPE = DevCardType.MONOPOLY;

    @SerializedName("playerIndex")
    @Expose
    private PlayerIndex playerIndex;

    @SerializedName("resource")
    @Expose
    private ResourceType resource;


    // CUSTOM CODE
    // END CUSTOM CODE

    /**
     * No args constructor for use in serialization
     */
    public MonopolyAction() {
    }

    /**
     * @param playerIndex Who's playing this dev card
     * @param resource    The resource
     */
    public MonopolyAction(PlayerIndex playerIndex, ResourceType resource) {
        this.playerIndex = playerIndex;
        this.resource = resource;
    }

    /**
     * @return The type
     */
    public final DevCardType getType() {
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

    public MonopolyAction withPlayerIndex(@NotNull PlayerIndex playerIndex) {
        setPlayerIndex(playerIndex);
        return this;
    }

    /**
     * @return The resource
     */
    public ResourceType getResource() {
        return resource;
    }

    /**
     * @param resource The resource
     */
    public void setResource(@NotNull ResourceType resource) {
        this.resource = resource;
    }

    public MonopolyAction withResource(@NotNull ResourceType resource) {
        setResource(resource);
        return this;
    }

    @Override
    public String toString() {
        return "MonopolyAction [" +
                "type=" + TYPE +
                ", playerIndex=" + playerIndex +
                ", resource=" + resource +
                "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof MonopolyAction) {
            return equals((MonopolyAction) other);
        }
        return false;
    }

    public boolean equals(MonopolyAction other) {
        return (
                TYPE == other.TYPE &&
                        Objects.equals(playerIndex, other.playerIndex) &&
                        Objects.equals(resource, other.resource)
        );
    }

    /**
     * Run on the server.  Executes monopoly card on server based on given {@link PlayerIndex} and {@link ResourceType}.
     */
    @Override
    public void execute() {
        getFacades().getDevCards().useMonopolyCard(getModel().getPlayer(playerIndex), resource);
        getFacades().getClientModel().getLog().addMessage(new MessageEntry(getModel().getPlayer(playerIndex).getName(), " played a Monopoly card"));
    }
}
