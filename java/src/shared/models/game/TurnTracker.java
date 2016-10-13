package shared.models.game;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import shared.definitions.PlayerIndex;
import shared.definitions.TurnStatus;

import javax.annotation.Generated;
import java.util.Objects;

@Generated("net.kupiakos")
public class TurnTracker {

    @SerializedName("longestRoad")
    @Expose
    private PlayerIndex longestRoad;

    @SerializedName("currentTurn")
    @Expose
    private PlayerIndex currentTurn;

    @SerializedName("status")
    @Expose
    private TurnStatus status;

    @SerializedName("largestArmy")
    @Expose
    private PlayerIndex largestArmy;


    // CUSTOM CODE
    // END CUSTOM CODE

    /**
     * No args constructor for use in serialization
     */
    public TurnTracker() {
    }

    /**
     * @param longestRoad The index of who has the longest road
     * @param currentTurn Who's turn it is (0-3)
     * @param status      What's happening now
     * @param largestArmy The index of who has the biggest army (3 or more)
     */
    public TurnTracker(PlayerIndex longestRoad, PlayerIndex currentTurn, TurnStatus status, PlayerIndex largestArmy) {
        this.longestRoad = longestRoad;
        this.currentTurn = currentTurn;
        this.status = status;
        this.largestArmy = largestArmy;
    }

    /**
     * @return The index of who has the longest road
     */
    public PlayerIndex getLongestRoad() {
        return longestRoad;
    }

    /**
     * @param longestRoad The index of who has the longest road
     */
    public void setLongestRoad(@NotNull PlayerIndex longestRoad) {
        this.longestRoad = longestRoad;
    }

    public TurnTracker withLongestRoad(@NotNull PlayerIndex longestRoad) {
        setLongestRoad(longestRoad);
        return this;
    }

    /**
     * @return Who's turn it is (0-3)
     */
    public PlayerIndex getCurrentTurn() {
        return currentTurn;
    }

    /**
     * @param currentTurn Who's turn it is (0-3)
     */
    public void setCurrentTurn(@NotNull PlayerIndex currentTurn) {
        this.currentTurn = currentTurn;
    }

    public TurnTracker withCurrentTurn(@NotNull PlayerIndex currentTurn) {
        setCurrentTurn(currentTurn);
        return this;
    }

    /**
     * @return What's happening now
     */
    public TurnStatus getStatus() {
        return status;
    }

    /**
     * @param status What's happening now
     */
    public void setStatus(@NotNull TurnStatus status) {
        this.status = status;
    }

    public TurnTracker withStatus(@NotNull TurnStatus status) {
        setStatus(status);
        return this;
    }

    /**
     * @return The index of who has the biggest army (3 or more)
     */
    public PlayerIndex getLargestArmy() {
        return largestArmy;
    }

    /**
     * @param largestArmy The index of who has the biggest army (3 or more)
     */
    public void setLargestArmy(@NotNull PlayerIndex largestArmy) {
        this.largestArmy = largestArmy;
    }

    public TurnTracker withLargestArmy(@NotNull PlayerIndex largestArmy) {
        setLargestArmy(largestArmy);
        return this;
    }

    @Override
    public String toString() {
        return "TurnTracker [" +
                "longestRoad=" + longestRoad +
                ", currentTurn=" + currentTurn +
                ", status=" + status +
                ", largestArmy=" + largestArmy +
                "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof TurnTracker) {
            return equals((TurnTracker) other);
        }
        return false;
    }

    public boolean equals(TurnTracker other) {
        return (
                Objects.equals(longestRoad, other.longestRoad) &&
                        Objects.equals(currentTurn, other.currentTurn) &&
                        Objects.equals(status, other.status) &&
                        Objects.equals(largestArmy, other.largestArmy)
        );
    }


}
