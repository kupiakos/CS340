package shared.models.moves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javax.annotation.Generated;
import shared.definitions.DevCardType;
import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;

@Generated("net.kupiakos")
public class MonopolyAction {

    @SerializedName("type")
    @Expose(deserialize = false)
    private final DevCardType type = DevCardType.MONOPOLY;

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
      * @param type The type
      * @param playerIndex Who's playing this dev card
      * @param resource The resource
     */
    public MonopolyAction(PlayerIndex playerIndex, ResourceType resource) {
            this.playerIndex = playerIndex;
            this.resource = resource;
    }

    /**
     * @return The type
     */
    public final DevCardType getType() { return type; }

    /**
     * @return Who's playing this dev card
     */
    public PlayerIndex getPlayerIndex() { return playerIndex; }

    /**
     * @param playerIndex Who's playing this dev card
     */
    public void setPlayerIndex(PlayerIndex playerIndex) { this.playerIndex = playerIndex; }

    public MonopolyAction withPlayerIndex(PlayerIndex playerIndex) {
        setPlayerIndex(playerIndex);
        return this;
    }
    /**
     * @return The resource
     */
    public ResourceType getResource() { return resource; }

    /**
     * @param resource The resource
     */
    public void setResource(ResourceType resource) { this.resource = resource; }

    public MonopolyAction withResource(ResourceType resource) {
        setResource(resource);
        return this;
    }

    @Override
    public String toString() {
        return "MonopolyAction [" +
            "type=" + type +
            ", playerIndex=" + playerIndex +
            ", resource=" + resource +
            "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof MonopolyAction) {
            return equals((MonopolyAction)other);
        }
        return false;
    }

    public boolean equals(MonopolyAction other) {
        return (
            type == other.type &&
            playerIndex == other.playerIndex &&
            resource == other.resource
        );
    }
}
