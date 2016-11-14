package shared.models.moves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;
import shared.models.GameAction;

import javax.annotation.Generated;
import java.util.Objects;

@Generated("net.kupiakos")
public class MaritimeTradeAction extends GameAction {

    @SerializedName("type")
    @Expose(deserialize = false)
    private final String TYPE = "maritimeTrade";
    @SerializedName("outputResource")
    @Expose
    private ResourceType outputResource;
    @SerializedName("ratio")
    @Expose
    private int ratio;

    @SerializedName("playerIndex")
    @Expose
    private PlayerIndex playerIndex;

    @SerializedName("inputResource")
    @Expose
    private ResourceType inputResource;


    // CUSTOM CODE
    // END CUSTOM CODE

    /**
     * No args constructor for use in serialization
     */
    public MaritimeTradeAction() {
    }

    /**
     * @param outputResource What TYPE of resource you're getting.
     * @param ratio          The ratio of the trade your doing as an integer (ie. put 3 for a 3:1 trade)
     * @param playerIndex    Who's doing the trading
     * @param inputResource  What TYPE of resource you're giving.
     */
    public MaritimeTradeAction(ResourceType outputResource, int ratio, PlayerIndex playerIndex, ResourceType inputResource) {
        this.outputResource = outputResource;
        this.ratio = ratio;
        this.playerIndex = playerIndex;
        this.inputResource = inputResource;
    }

    /**
     * @return What TYPE of resource you're getting.
     */
    @NotNull
    public ResourceType getOutputResource() {
        return outputResource;
    }

    /**
     * @param outputResource What TYPE of resource you're getting.
     */
    public void setOutputResource(@NotNull ResourceType outputResource) {
        this.outputResource = outputResource;
    }

    public MaritimeTradeAction withOutputResource(@NotNull ResourceType outputResource) {
        setOutputResource(outputResource);
        return this;
    }

    /**
     * @return The type
     */
    public final String getType() {
        return TYPE;
    }

    /**
     * @return The ratio of the trade your doing as an integer (ie. put 3 for a 3:1 trade)
     */
    public int getRatio() {
        return ratio;
    }

    /**
     * @param ratio The ratio of the trade your doing as an integer (ie. put 3 for a 3:1 trade)
     */
    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    public MaritimeTradeAction withRatio(int ratio) {
        setRatio(ratio);
        return this;
    }

    /**
     * @return Who's doing the trading
     */
    public PlayerIndex getPlayerIndex() {
        return playerIndex;
    }

    /**
     * @param playerIndex Who's doing the trading
     */
    public void setPlayerIndex(@NotNull PlayerIndex playerIndex) {
        this.playerIndex = playerIndex;
    }

    public MaritimeTradeAction withPlayerIndex(@NotNull PlayerIndex playerIndex) {
        setPlayerIndex(playerIndex);
        return this;
    }

    /**
     * @return What TYPE of resource you're giving.
     */
    public ResourceType getInputResource() {
        return inputResource;
    }

    /**
     * @param inputResource What TYPE of resource you're giving.
     */
    public void setInputResource(@NotNull ResourceType inputResource) {
        this.inputResource = inputResource;
    }

    public MaritimeTradeAction withInputResource(@NotNull ResourceType inputResource) {
        setInputResource(inputResource);
        return this;
    }

    @Override
    public String toString() {
        return "MaritimeTradeAction [" +
                "outputResource=" + outputResource +
                ", type=" + TYPE +
                ", ratio=" + ratio +
                ", playerIndex=" + playerIndex +
                ", inputResource=" + inputResource +
                "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof MaritimeTradeAction) {
            return equals((MaritimeTradeAction) other);
        }
        return false;
    }

    public boolean equals(MaritimeTradeAction other) {
        return (
                Objects.equals(outputResource, other.outputResource) &&
                        TYPE == other.TYPE &&
                        ratio == other.ratio &&
                        Objects.equals(playerIndex, other.playerIndex) &&
                        Objects.equals(inputResource, other.inputResource)
        );
    }

    /**
     * Run on the server.  Exchanges the input {@link ResourceType} for the output {@link ResourceType} at the given ratio.
     * Gives new resource to specified {@link PlayerIndex}.
     */
    @Override
    public void execute() {
        getFacades().getTrading().maritimeTrade(getModel().getPlayer(playerIndex), inputResource, outputResource);
    }
}
