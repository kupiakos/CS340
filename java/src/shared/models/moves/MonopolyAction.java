package shared.models.moves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import shared.definitions.DevCardType;
import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;

import javax.annotation.Generated;

@Generated("net.kupiakos")
public class MonopolyAction {

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
    public PlayerIndex getPlayerIndex() {
        return playerIndex;
    }

    /**
     * @param playerIndex Who's playing this dev card
     */
    public void setPlayerIndex(PlayerIndex playerIndex) {
        this.playerIndex = playerIndex;
    }

    public MonopolyAction withPlayerIndex(PlayerIndex playerIndex) {
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
    public void setResource(ResourceType resource) {
        this.resource = resource;
    }

    public MonopolyAction withResource(ResourceType resource) {
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
                        playerIndex == other.playerIndex &&
                        resource == other.resource
        );
    }
}
