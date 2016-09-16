package shared.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Player {

    @SerializedName("cities")
    @Expose
    private String cities;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("discarded")
    @Expose
    private String discarded;
    @SerializedName("monuments")
    @Expose
    private String monuments;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("newDevCards")
    @Expose
    private NewDevCards newDevCards;
    @SerializedName("oldDevCards")
    @Expose
    private OldDevCards oldDevCards;
    @SerializedName("playerIndex")
    @Expose
    private String playerIndex;
    @SerializedName("playedDevCard")
    @Expose
    private String playedDevCard;
    @SerializedName("playerID")
    @Expose
    private String playerID;
    @SerializedName("resources")
    @Expose
    private Resources resources;
    @SerializedName("roads")
    @Expose
    private String roads;
    @SerializedName("settlements")
    @Expose
    private String settlements;
    @SerializedName("soldiers")
    @Expose
    private String soldiers;
    @SerializedName("victoryPoints")
    @Expose
    private String victoryPoints;

    /**
     * No args constructor for use in serialization
     */
    public Player() {
    }

    /**
     * @param playedDevCard
     * @param resources
     * @param settlements
     * @param oldDevCards
     * @param discarded
     * @param monuments
     * @param soldiers
     * @param playerID
     * @param cities
     * @param color
     * @param newDevCards
     * @param playerIndex
     * @param name
     * @param victoryPoints
     * @param roads
     */
    public Player(String cities, String color, String discarded, String monuments, String name, NewDevCards newDevCards, OldDevCards oldDevCards, String playerIndex, String playedDevCard, String playerID, Resources resources, String roads, String settlements, String soldiers, String victoryPoints) {
        this.cities = cities;
        this.color = color;
        this.discarded = discarded;
        this.monuments = monuments;
        this.name = name;
        this.newDevCards = newDevCards;
        this.oldDevCards = oldDevCards;
        this.playerIndex = playerIndex;
        this.playedDevCard = playedDevCard;
        this.playerID = playerID;
        this.resources = resources;
        this.roads = roads;
        this.settlements = settlements;
        this.soldiers = soldiers;
        this.victoryPoints = victoryPoints;
    }

    /**
     * @return The cities
     */
    public String getCities() {
        return cities;
    }

    /**
     * @param cities The cities
     */
    public void setCities(String cities) {
        this.cities = cities;
    }

    public Player withCities(String cities) {
        this.cities = cities;
        return this;
    }

    /**
     * @return The color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color The color
     */
    public void setColor(String color) {
        this.color = color;
    }

    public Player withColor(String color) {
        this.color = color;
        return this;
    }

    /**
     * @return The discarded
     */
    public String getDiscarded() {
        return discarded;
    }

    /**
     * @param discarded The discarded
     */
    public void setDiscarded(String discarded) {
        this.discarded = discarded;
    }

    public Player withDiscarded(String discarded) {
        this.discarded = discarded;
        return this;
    }

    /**
     * @return The monuments
     */
    public String getMonuments() {
        return monuments;
    }

    /**
     * @param monuments The monuments
     */
    public void setMonuments(String monuments) {
        this.monuments = monuments;
    }

    public Player withMonuments(String monuments) {
        this.monuments = monuments;
        return this;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    public Player withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @return The newDevCards
     */
    public NewDevCards getNewDevCards() {
        return newDevCards;
    }

    /**
     * @param newDevCards The newDevCards
     */
    public void setNewDevCards(NewDevCards newDevCards) {
        this.newDevCards = newDevCards;
    }

    public Player withNewDevCards(NewDevCards newDevCards) {
        this.newDevCards = newDevCards;
        return this;
    }

    /**
     * @return The oldDevCards
     */
    public OldDevCards getOldDevCards() {
        return oldDevCards;
    }

    /**
     * @param oldDevCards The oldDevCards
     */
    public void setOldDevCards(OldDevCards oldDevCards) {
        this.oldDevCards = oldDevCards;
    }

    public Player withOldDevCards(OldDevCards oldDevCards) {
        this.oldDevCards = oldDevCards;
        return this;
    }

    /**
     * @return The playerIndex
     */
    public String getPlayerIndex() {
        return playerIndex;
    }

    /**
     * @param playerIndex The playerIndex
     */
    public void setPlayerIndex(String playerIndex) {
        this.playerIndex = playerIndex;
    }

    public Player withPlayerIndex(String playerIndex) {
        this.playerIndex = playerIndex;
        return this;
    }

    /**
     * @return The playedDevCard
     */
    public String getPlayedDevCard() {
        return playedDevCard;
    }

    /**
     * @param playedDevCard The playedDevCard
     */
    public void setPlayedDevCard(String playedDevCard) {
        this.playedDevCard = playedDevCard;
    }

    public Player withPlayedDevCard(String playedDevCard) {
        this.playedDevCard = playedDevCard;
        return this;
    }

    /**
     * @return The playerID
     */
    public String getPlayerID() {
        return playerID;
    }

    /**
     * @param playerID The playerID
     */
    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public Player withPlayerID(String playerID) {
        this.playerID = playerID;
        return this;
    }

    /**
     * @return The resources
     */
    public Resources getResources() {
        return resources;
    }

    /**
     * @param resources The resources
     */
    public void setResources(Resources resources) {
        this.resources = resources;
    }

    public Player withResources(Resources resources) {
        this.resources = resources;
        return this;
    }

    /**
     * @return The roads
     */
    public String getRoads() {
        return roads;
    }

    /**
     * @param roads The roads
     */
    public void setRoads(String roads) {
        this.roads = roads;
    }

    public Player withRoads(String roads) {
        this.roads = roads;
        return this;
    }

    /**
     * @return The settlements
     */
    public String getSettlements() {
        return settlements;
    }

    /**
     * @param settlements The settlements
     */
    public void setSettlements(String settlements) {
        this.settlements = settlements;
    }

    public Player withSettlements(String settlements) {
        this.settlements = settlements;
        return this;
    }

    /**
     * @return The soldiers
     */
    public String getSoldiers() {
        return soldiers;
    }

    /**
     * @param soldiers The soldiers
     */
    public void setSoldiers(String soldiers) {
        this.soldiers = soldiers;
    }

    public Player withSoldiers(String soldiers) {
        this.soldiers = soldiers;
        return this;
    }

    /**
     * @return The victoryPoints
     */
    public String getVictoryPoints() {
        return victoryPoints;
    }

    /**
     * @param victoryPoints The victoryPoints
     */
    public void setVictoryPoints(String victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public Player withVictoryPoints(String victoryPoints) {
        this.victoryPoints = victoryPoints;
        return this;
    }

}
