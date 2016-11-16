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
public class YearofPlentyAction extends GameAction {

    @SerializedName("type")
    @Expose(deserialize = false)
    private final DevCardType TYPE = DevCardType.YEAR_OF_PLENTY;

    @SerializedName("resource1")
    @Expose
    private ResourceType resource1;

    @SerializedName("playerIndex")
    @Expose
    private PlayerIndex playerIndex;

    @SerializedName("resource2")
    @Expose
    private ResourceType resource2;


    // CUSTOM CODE
    // END CUSTOM CODE

    /**
     * No args constructor for use in serialization
     */
    public YearofPlentyAction() {
    }

    /**
     * @param resource1   The resource1
     * @param playerIndex Who's playing this dev card
     * @param resource2   The resource2
     */
    public YearofPlentyAction(ResourceType resource1, PlayerIndex playerIndex, ResourceType resource2) {
        this.resource1 = resource1;
        this.playerIndex = playerIndex;
        this.resource2 = resource2;
    }

    /**
     * @return The type
     */
    public final DevCardType getType() {
        return TYPE;
    }

    /**
     * @return The resource1
     */
    public ResourceType getResource1() {
        return resource1;
    }

    /**
     * @param resource1 The resource1
     */
    public void setResource1(@NotNull ResourceType resource1) {
        this.resource1 = resource1;
    }

    public YearofPlentyAction withResource1(@NotNull ResourceType resource1) {
        setResource1(resource1);
        return this;
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

    public YearofPlentyAction withPlayerIndex(@NotNull PlayerIndex playerIndex) {
        setPlayerIndex(playerIndex);
        return this;
    }

    /**
     * @return The resource2
     */
    public ResourceType getResource2() {
        return resource2;
    }

    /**
     * @param resource2 The resource2
     */
    public void setResource2(@NotNull ResourceType resource2) {
        this.resource2 = resource2;
    }

    public YearofPlentyAction withResource2(@NotNull ResourceType resource2) {
        setResource2(resource2);
        return this;
    }

    @Override
    public String toString() {
        return "YearofPlentyAction [" +
                "type=" + TYPE +
                ", resource1=" + resource1 +
                ", playerIndex=" + playerIndex +
                ", resource2=" + resource2 +
                "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof YearofPlentyAction) {
            return equals((YearofPlentyAction) other);
        }
        return false;
    }

    public boolean equals(YearofPlentyAction other) {
        return (
                TYPE == other.TYPE &&
                        Objects.equals(resource1, other.resource1) &&
                        Objects.equals(playerIndex, other.playerIndex) &&
                        Objects.equals(resource2, other.resource2)
        );
    }

    /**
     * Run on the server.  Executes a year of plenty card.  Gives specified {@link PlayerIndex} one of each specified
     * {@link ResourceType}.
     */
    @Override
    public void execute() {
        getFacades().getDevCards().useYearOfPlentyCard(getModel().getPlayer(playerIndex), resource1, resource2);
        getFacades().getClientModel().getLog().addMessage(new MessageEntry(getModel().getPlayer(playerIndex).getName(), " played Year of Plenty"));
    }
}
