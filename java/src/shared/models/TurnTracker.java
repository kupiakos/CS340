package shared.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class TurnTracker {

    @SerializedName("currentTurn")
    @Expose
    private String currentTurn;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("longestRoad")
    @Expose
    private String longestRoad;
    @SerializedName("largestArmy")
    @Expose
    private String largestArmy;

    /**
     * No args constructor for use in serialization
     */
    public TurnTracker() {
    }

    /**
     * @param longestRoad
     * @param status
     * @param currentTurn
     * @param largestArmy
     */
    public TurnTracker(String currentTurn, String status, String longestRoad, String largestArmy) {
        this.currentTurn = currentTurn;
        this.status = status;
        this.longestRoad = longestRoad;
        this.largestArmy = largestArmy;
    }

    /**
     * @return The currentTurn
     */
    public String getCurrentTurn() {
        return currentTurn;
    }

    /**
     * @param currentTurn The currentTurn
     */
    public void setCurrentTurn(String currentTurn) {
        this.currentTurn = currentTurn;
    }

    public TurnTracker withCurrentTurn(String currentTurn) {
        this.currentTurn = currentTurn;
        return this;
    }

    /**
     * @return The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public TurnTracker withStatus(String status) {
        this.status = status;
        return this;
    }

    /**
     * @return The longestRoad
     */
    public String getLongestRoad() {
        return longestRoad;
    }

    /**
     * @param longestRoad The longestRoad
     */
    public void setLongestRoad(String longestRoad) {
        this.longestRoad = longestRoad;
    }

    public TurnTracker withLongestRoad(String longestRoad) {
        this.longestRoad = longestRoad;
        return this;
    }

    /**
     * @return The largestArmy
     */
    public String getLargestArmy() {
        return largestArmy;
    }

    /**
     * @param largestArmy The largestArmy
     */
    public void setLargestArmy(String largestArmy) {
        this.largestArmy = largestArmy;
    }

    public TurnTracker withLargestArmy(String largestArmy) {
        this.largestArmy = largestArmy;
        return this;
    }

}
