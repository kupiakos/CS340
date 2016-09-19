package shared.models.game;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javax.annotation.Generated;

import com.sun.istack.internal.NotNull;
import shared.definitions.PlayerIndex;
import shared.definitions.CatanColor;

@Generated("net.kupiakos")
public class Player {

    @SerializedName("cities")
    @Expose
    private int cities;

    @SerializedName("discarded")
    @Expose
    private boolean discarded;

    @SerializedName("resources")
    @Expose
    private ResourceSet resources;

    @SerializedName("roads")
    @Expose
    private int roads;

    @SerializedName("victoryPoints")
    @Expose
    private int victoryPoints;

    @SerializedName("oldDevCards")
    @Expose
    private DevCardList oldDevCards;

    @SerializedName("soldiers")
    @Expose
    private int soldiers;

    @SerializedName("color")
    @Expose
    private CatanColor color;

    @SerializedName("newDevCards")
    @Expose
    private DevCardList newDevCards;

    @SerializedName("playerIndex")
    @Expose
    private PlayerIndex playerIndex;

    @SerializedName("monuments")
    @Expose
    private int monuments;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("settlements")
    @Expose
    private int settlements;

    @SerializedName("playerID")
    @Expose
    private int playerID;

    @SerializedName("playedDevCard")
    @Expose
    private boolean playedDevCard;


    // CUSTOM CODE
    // END CUSTOM CODE

    /**
     * No args constructor for use in serialization
     */
    public Player() {
    }

    /**
      * @param cities How many cities this player has left to play
      * @param discarded Whether this player has discarded or not already this discard phase.
      * @param resources The resource cards this player has.
      * @param roads The roads
      * @param victoryPoints The victoryPoints
      * @param oldDevCards The dev cards the player had when the turn started.
      * @param soldiers The soldiers
      * @param color The color of this player.
      * @param newDevCards The dev cards the player bought this turn.
      * @param playerIndex What place in the array is this player? 0-3. It determines their turn order. This is used often everywhere.
      * @param monuments How many monuments this player has played.
      * @param name The name
      * @param settlements The settlements
      * @param playerID The unique playerID. This is used to pick the client player apart from the others. This is only used here and in your cookie.
      * @param playedDevCard Whether the player has played a dev card this turn.
     */
    public Player(int cities, boolean discarded, ResourceSet resources, int roads, int victoryPoints, DevCardList oldDevCards, int soldiers, CatanColor color, DevCardList newDevCards, PlayerIndex playerIndex, int monuments, String name, int settlements, int playerID, boolean playedDevCard) {
            this.cities = cities;
            this.discarded = discarded;
            this.resources = resources;
            this.roads = roads;
            this.victoryPoints = victoryPoints;
            this.oldDevCards = oldDevCards;
            this.soldiers = soldiers;
            this.color = color;
            this.newDevCards = newDevCards;
            this.playerIndex = playerIndex;
            this.monuments = monuments;
            this.name = name;
            this.settlements = settlements;
            this.playerID = playerID;
            this.playedDevCard = playedDevCard;
    }

    /**
     * @return How many cities this player has left to play
     */
    public int getCities() { return cities; }

    /**
     * @param cities How many cities this player has left to play
     */
    public void setCities(int cities) { this.cities = cities; }

    public Player withCities(int cities) {
        setCities(cities);
        return this;
    }
    /**
     * @return Whether this player has discarded or not already this discard phase.
     */
    public boolean isDiscarded() { return discarded; }

    /**
     * @param discarded Whether this player has discarded or not already this discard phase.
     */
    public void setDiscarded(boolean discarded) { this.discarded = discarded; }

    public Player withDiscarded(boolean discarded) {
        setDiscarded(discarded);
        return this;
    }
    /**
     * @return The resource cards this player has.
     */
    public ResourceSet getResources() { return resources; }

    /**
     * @param resources The resource cards this player has.
     */
    public void setResources(@NotNull ResourceSet resources) { this.resources = resources; }

    public Player withResources(@NotNull ResourceSet resources) {
        setResources(resources);
        return this;
    }
    /**
     * @return The roads
     */
    public int getRoads() { return roads; }

    /**
     * @param roads The roads
     */
    public void setRoads(int roads) { this.roads = roads; }

    public Player withRoads(int roads) {
        setRoads(roads);
        return this;
    }
    /**
     * @return The victoryPoints
     */
    public int getVictoryPoints() { return victoryPoints; }

    /**
     * @param victoryPoints The victoryPoints
     */
    public void setVictoryPoints(int victoryPoints) { this.victoryPoints = victoryPoints; }

    public Player withVictoryPoints(int victoryPoints) {
        setVictoryPoints(victoryPoints);
        return this;
    }
    /**
     * @return The dev cards the player had when the turn started.
     */
    public DevCardList getOldDevCards() { return oldDevCards; }

    /**
     * @param oldDevCards The dev cards the player had when the turn started.
     */
    public void setOldDevCards(@NotNull DevCardList oldDevCards) { this.oldDevCards = oldDevCards; }

    public Player withOldDevCards(@NotNull DevCardList oldDevCards) {
        setOldDevCards(oldDevCards);
        return this;
    }
    /**
     * @return The soldiers
     */
    public int getSoldiers() { return soldiers; }

    /**
     * @param soldiers The soldiers
     */
    public void setSoldiers(int soldiers) { this.soldiers = soldiers; }

    public Player withSoldiers(int soldiers) {
        setSoldiers(soldiers);
        return this;
    }
    /**
     * @return The color of this player.
     */
    public CatanColor getColor() { return color; }

    /**
     * @param color The color of this player.
     */
    public void setColor(@NotNull CatanColor color) { this.color = color; }

    public Player withColor(@NotNull CatanColor color) {
        setColor(color);
        return this;
    }
    /**
     * @return The dev cards the player bought this turn.
     */
    public DevCardList getNewDevCards() { return newDevCards; }

    /**
     * @param newDevCards The dev cards the player bought this turn.
     */
    public void setNewDevCards(@NotNull DevCardList newDevCards) { this.newDevCards = newDevCards; }

    public Player withNewDevCards(@NotNull DevCardList newDevCards) {
        setNewDevCards(newDevCards);
        return this;
    }
    /**
     * @return What place in the array is this player? 0-3. It determines their turn order. This is used often everywhere.
     */
    public PlayerIndex getPlayerIndex() { return playerIndex; }

    /**
     * @param playerIndex What place in the array is this player? 0-3. It determines their turn order. This is used often everywhere.
     */
    public void setPlayerIndex(@NotNull PlayerIndex playerIndex) { this.playerIndex = playerIndex; }

    public Player withPlayerIndex(@NotNull PlayerIndex playerIndex) {
        setPlayerIndex(playerIndex);
        return this;
    }
    /**
     * @return How many monuments this player has played.
     */
    public int getMonuments() { return monuments; }

    /**
     * @param monuments How many monuments this player has played.
     */
    public void setMonuments(int monuments) { this.monuments = monuments; }

    public Player withMonuments(int monuments) {
        setMonuments(monuments);
        return this;
    }
    /**
     * @return The name
     */
    public String getName() { return name; }

    /**
     * @param name The name
     */
    public void setName(@NotNull String name) { this.name = name; }

    public Player withName(@NotNull String name) {
        setName(name);
        return this;
    }
    /**
     * @return The settlements
     */
    public int getSettlements() { return settlements; }

    /**
     * @param settlements The settlements
     */
    public void setSettlements(int settlements) { this.settlements = settlements; }

    public Player withSettlements(int settlements) {
        setSettlements(settlements);
        return this;
    }
    /**
     * @return The unique playerID. This is used to pick the client player apart from the others. This is only used here and in your cookie.
     */
    public int getPlayerID() { return playerID; }

    /**
     * @param playerID The unique playerID. This is used to pick the client player apart from the others. This is only used here and in your cookie.
     */
    public void setPlayerID(int playerID) { this.playerID = playerID; }

    public Player withPlayerID(int playerID) {
        setPlayerID(playerID);
        return this;
    }
    /**
     * @return Whether the player has played a dev card this turn.
     */
    public boolean isPlayedDevCard() { return playedDevCard; }

    /**
     * @param playedDevCard Whether the player has played a dev card this turn.
     */
    public void setPlayedDevCard(boolean playedDevCard) { this.playedDevCard = playedDevCard; }

    public Player withPlayedDevCard(boolean playedDevCard) {
        setPlayedDevCard(playedDevCard);
        return this;
    }

    @Override
    public String toString() {
        return "Player [" +
            "cities=" + cities +
            ", discarded=" + discarded +
            ", resources=" + resources +
            ", roads=" + roads +
            ", victoryPoints=" + victoryPoints +
            ", oldDevCards=" + oldDevCards +
            ", soldiers=" + soldiers +
            ", color=" + color +
            ", newDevCards=" + newDevCards +
            ", playerIndex=" + playerIndex +
            ", monuments=" + monuments +
            ", name=" + name +
            ", settlements=" + settlements +
            ", playerID=" + playerID +
            ", playedDevCard=" + playedDevCard +
            "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Player) {
            return equals((Player)other);
        }
        return false;
    }

    public boolean equals(Player other) {
        return (
            cities == other.cities &&
            discarded == other.discarded &&
            resources == other.resources &&
            roads == other.roads &&
            victoryPoints == other.victoryPoints &&
            oldDevCards == other.oldDevCards &&
            soldiers == other.soldiers &&
            color == other.color &&
            newDevCards == other.newDevCards &&
            playerIndex == other.playerIndex &&
            monuments == other.monuments &&
            name == other.name &&
            settlements == other.settlements &&
            playerID == other.playerID &&
            playedDevCard == other.playedDevCard
        );
    }
}
