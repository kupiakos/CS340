package shared.models.moves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javax.annotation.Generated;
import shared.definitions.PlayerIndex;

@Generated("net.kupiakos")
public class RollNumberAction {

    @SerializedName("number")
    @Expose
    private int number;

    @SerializedName("type")
    @Expose(deserialize = false)
    private final String type = "RollNumber";

    @SerializedName("playerIndex")
    @Expose
    private PlayerIndex playerIndex;


    // CUSTOM CODE
    // END CUSTOM CODE

    /**
     * No args constructor for use in serialization
     */
    public RollNumberAction() {
    }

    /**
      * @param number what number was rolled (2-12)
      * @param type The type
      * @param playerIndex Who's sending this command (0-3)
     */
    public RollNumberAction(int number, PlayerIndex playerIndex) {
            this.number = number;
            this.playerIndex = playerIndex;
    }

    /**
     * @return what number was rolled (2-12)
     */
    public int getNumber() { return number; }

    /**
     * @param number what number was rolled (2-12)
     */
    public void setNumber(int number) { this.number = number; }

    public RollNumberAction withNumber(int number) {
        setNumber(number);
        return this;
    }
    /**
     * @return The type
     */
    public final String getType() { return type; }

    /**
     * @return Who's sending this command (0-3)
     */
    public PlayerIndex getPlayerIndex() { return playerIndex; }

    /**
     * @param playerIndex Who's sending this command (0-3)
     */
    public void setPlayerIndex(PlayerIndex playerIndex) { this.playerIndex = playerIndex; }

    public RollNumberAction withPlayerIndex(PlayerIndex playerIndex) {
        setPlayerIndex(playerIndex);
        return this;
    }

    @Override
    public String toString() {
        return "RollNumberAction [" +
            "number=" + number +
            ", type=" + type +
            ", playerIndex=" + playerIndex +
            "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof RollNumberAction) {
            return equals((RollNumberAction)other);
        }
        return false;
    }

    public boolean equals(RollNumberAction other) {
        return (
            number == other.number &&
            type == other.type &&
            playerIndex == other.playerIndex
        );
    }
}
