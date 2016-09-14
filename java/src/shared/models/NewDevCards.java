package shared.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class NewDevCards {

    @SerializedName("monopoly")
    @Expose
    private String monopoly;
    @SerializedName("monument")
    @Expose
    private String monument;
    @SerializedName("roadBuilding")
    @Expose
    private String roadBuilding;
    @SerializedName("soldier")
    @Expose
    private String soldier;
    @SerializedName("yearOfPlenty")
    @Expose
    private String yearOfPlenty;

    /**
     * No args constructor for use in serialization
     */
    public NewDevCards() {
    }

    /**
     * @param soldier
     * @param monopoly
     * @param yearOfPlenty
     * @param monument
     * @param roadBuilding
     */
    public NewDevCards(String monopoly, String monument, String roadBuilding, String soldier, String yearOfPlenty) {
        this.monopoly = monopoly;
        this.monument = monument;
        this.roadBuilding = roadBuilding;
        this.soldier = soldier;
        this.yearOfPlenty = yearOfPlenty;
    }

    /**
     * @return The monopoly
     */
    public String getMonopoly() {
        return monopoly;
    }

    /**
     * @param monopoly The monopoly
     */
    public void setMonopoly(String monopoly) {
        this.monopoly = monopoly;
    }

    public NewDevCards withMonopoly(String monopoly) {
        this.monopoly = monopoly;
        return this;
    }

    /**
     * @return The monument
     */
    public String getMonument() {
        return monument;
    }

    /**
     * @param monument The monument
     */
    public void setMonument(String monument) {
        this.monument = monument;
    }

    public NewDevCards withMonument(String monument) {
        this.monument = monument;
        return this;
    }

    /**
     * @return The roadBuilding
     */
    public String getRoadBuilding() {
        return roadBuilding;
    }

    /**
     * @param roadBuilding The roadBuilding
     */
    public void setRoadBuilding(String roadBuilding) {
        this.roadBuilding = roadBuilding;
    }

    public NewDevCards withRoadBuilding(String roadBuilding) {
        this.roadBuilding = roadBuilding;
        return this;
    }

    /**
     * @return The soldier
     */
    public String getSoldier() {
        return soldier;
    }

    /**
     * @param soldier The soldier
     */
    public void setSoldier(String soldier) {
        this.soldier = soldier;
    }

    public NewDevCards withSoldier(String soldier) {
        this.soldier = soldier;
        return this;
    }

    /**
     * @return The yearOfPlenty
     */
    public String getYearOfPlenty() {
        return yearOfPlenty;
    }

    /**
     * @param yearOfPlenty The yearOfPlenty
     */
    public void setYearOfPlenty(String yearOfPlenty) {
        this.yearOfPlenty = yearOfPlenty;
    }

    public NewDevCards withYearOfPlenty(String yearOfPlenty) {
        this.yearOfPlenty = yearOfPlenty;
        return this;
    }

}
