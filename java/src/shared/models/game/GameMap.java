package shared.models.game;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import shared.definitions.PlayerIndex;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.utils.MapUtils;

import javax.annotation.Generated;
import java.util.Map;
import java.util.Set;

@Generated("net.kupiakos")
public class GameMap {

    @SerializedName("roads")
    @Expose
    private Map<EdgeLocation, PlayerIndex> roads;

    @SerializedName("radius")
    @Expose
    private int radius;

    @SerializedName("robber")
    @Expose
    private HexLocation robber;

    @SerializedName("hexes")
    @Expose
    private Map<HexLocation, Hex> hexes;

    @SerializedName("ports")
    @Expose
    private Map<HexLocation, Port> ports;

    @SerializedName("settlements")
    @Expose
    private Map<VertexLocation, PlayerIndex> settlements;

    @SerializedName("cities")
    @Expose
    private Map<VertexLocation, PlayerIndex> cities;


    /**
     * No args constructor for use in serialization
     */
    public GameMap() {
    }

    /**
     * @param roads       The list of roads currently placed on the map
     * @param radius      The radius of the map (it includes the center hex, and the ocean hexes)
     * @param robber      The current location of the robber
     * @param hexes       A list of all the hexes on the grid - it's only land tiles
     * @param ports       The list of ports currently placed on the map
     * @param settlements The list of settlements currently placed on the map
     * @param cities      The list of cities currently placed on the map
     */
    public GameMap(Map<EdgeLocation, PlayerIndex> roads,
                   int radius,
                   HexLocation robber,
                   Map<HexLocation, Hex> hexes,
                   Map<HexLocation, Port> ports,
                   Map<VertexLocation, PlayerIndex> settlements,
                   Map<VertexLocation, PlayerIndex> cities) {
        this.roads = roads;
        this.radius = radius;
        this.robber = robber;
        this.hexes = hexes;
        this.ports = ports;
        this.settlements = settlements;
        this.cities = cities;
    }

    /**
     * Get the locations of the cities owned by the given player.
     *
     * @param player the player whose cities to retrieve, not null
     * @return the set containing the location of each city owned by the player
     */
    @NotNull
    public Set<VertexLocation> getPlayerCities(@NotNull PlayerIndex player) {
        return MapUtils.keysWithValue(cities, player);
    }

    /**
     * Get the locations of the settlements owned by the given player.
     *
     * @param player the player whose settlements to retrieve, not null
     * @return the set containing the location of each settlement owned by the player
     */
    @NotNull
    public Set<VertexLocation> getPlayerSettlements(@NotNull PlayerIndex player) {
        return MapUtils.keysWithValue(settlements, player);
    }

    /**
     * Get all roads owned by the given player.
     *
     * @param player the player whose roads to retrieve, not null
     * @return the set containing every road owned by the player
     */
    @NotNull
    public Set<EdgeLocation> getPlayerRoads(@NotNull PlayerIndex player) {
        return MapUtils.keysWithValue(roads, player);
    }

    /**
     * Get the hex associated with a given location.
     *
     * @param location the location to check
     * @return the hex at that location, or null if none
     */
    @Nullable
    public Hex getHex(@NotNull HexLocation location) {
        return hexes.get(location);
    }

    /**
     * Get all hexes with the number given.
     *
     * @param number the number to check
     * @return the hexes at that location, or empty set if none
     */
    @NotNull
    public Set<Hex> getHexesWithNumber(int number) {
        return MapUtils.valuesMatching(hexes, h -> h.getNumber() == number);
    }

    /**
     * Get the port associated with a given location.
     *
     * @param location the location to check
     * @return the port at that location, or null if none
     */
    @Nullable
    public Port getPort(@NotNull HexLocation location) {
        return ports.get(location);
    }

    /**
     * Get the road owner associated with a given location.
     *
     * @param location the location to check
     * @return the owner of the road at that location, or null if none
     */
    @Nullable
    public PlayerIndex getRoadOwner(@NotNull EdgeLocation location) {
        location = location.getNormalizedLocation();
        return roads.get(location);
    }

    /**
     * Get the owner of a settlement, or null if that location does not have a settlement.
     *
     * @param location the location to check
     * @return the owner of the settlement at that location, or null if no settlement
     */
    @Nullable
    public PlayerIndex getSettlementOwner(@NotNull VertexLocation location) {
        return settlements.get(location);
    }

    /**
     * Get the owner of a city, or null if that location does not have a city.
     *
     * @param location the location to check
     * @return the owner of the city at that location, or null if no city
     */
    @Nullable
    public PlayerIndex getCityOwner(@NotNull VertexLocation location) {
        return cities.get(location);
    }


    /**
     * Get whether the map could support adding a settlement owned by a player at the given location.
     * <p>
     * Note this does not check anything regarding resources or turn,
     * but strictly whether adding this to the map would not be an illegal configuration.
     * <p>
     * This tests distance and adjacency requirements.
     *
     * @param location the location to test, not null
     * @param player   the player to test, not null
     * @return whether the map could support adding a settlement owned by the player at that location
     */
    public boolean canAddSettlement(@NotNull VertexLocation location, @NotNull Player player) {
        return false;
    }

    /**
     * Add a settlement at the specific location belonging to a specific player.
     *
     * @param location the location to add the settlement, not null
     * @param player   the player to test, not null
     * @throws IllegalArgumentException if the precondition is violated
     * @pre {@link #canAddSettlement} returns true
     */
    public void addSettlement(@NotNull VertexLocation location, @NotNull Player player) {
    }

    /**
     * Get whether the map could support adding a road owned by a player at the given location.
     * <p>
     * Note this does not check anything regarding resources or turn,
     * but strictly whether adding this to the map would not be an illegal configuration.
     * <p>
     * This tests distance and adjacency requirements.
     *
     * @param road the road containing the location and owner to add
     * @return whether the map could support adding a road owned by the player at that location
     */
    public boolean canAddRoad(@NotNull EdgeLocation location, @NotNull Player player) {
        return false;
    }

    /**
     * Add a road at the specific location belonging to a specific player.
     *
     * @param location the location to add the road, not null
     * @param player   the player to test, not null
     * @throws IllegalArgumentException if the precondition is violated
     * @pre {@link #canAddSettlement} returns true
     */
    public void addRoad(@NotNull EdgeLocation location, @NotNull Player player) {

    }

    /**
     * Get whether the map could support adding a settlement owned by a player at the given location.
     * <p>
     * Note this does not check anything regarding resources or turn,
     * but strictly whether adding this to the map would not be an illegal configuration.
     * <p>
     * A settlement owned by the player must exist at that location.
     *
     * @param location the location to test, not null
     * @param player   the player to test, not null
     * @return whether the map could support adding a settlement owned by the player at that location
     */
    public boolean canUpgradeSettlement(@NotNull VertexLocation location, @NotNull Player player) {
        return false;
    }

    /**
     * Upgrade a settlement at the specific location belonging to a specific player.
     *
     * @param location the location to add the settlement, not null
     * @param player   the player to test, not null
     * @throws IllegalArgumentException if the precondition is violated
     * @pre {@link #canAddSettlement} returns true
     */
    public boolean upgradeSettlement(@NotNull VertexLocation location, @NotNull Player player) {
        return false;
    }

    /**
     * @return the list of roads currently placed on the map
     */
    public Map<EdgeLocation, PlayerIndex> getRoads() {
        return roads;
    }

    /**
     * @param roads list of roads currently placed on the map
     */
    public void setRoads(@NotNull Map<EdgeLocation, PlayerIndex> roads) {
        this.roads = roads;
    }

    public GameMap withRoads(@NotNull Map<EdgeLocation, PlayerIndex> roads) {
        setRoads(roads);
        return this;
    }

    /**
     * @return The radius of the map (it includes the center hex, and the ocean hexes; pass this into the hexgrid constructor)
     */
    public int getRadius() {
        return radius;
    }

    /**
     * @param radius The radius of the map (it includes the center hex, and the ocean hexes; pass this into the hexgrid constructor)
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    public GameMap withRadius(int radius) {
        setRadius(radius);
        return this;
    }

    /**
     * @return The current location of the robber
     */
    public HexLocation getRobber() {
        return robber;
    }

    /**
     * @param robber The current location of the robber
     */
    public void setRobber(@NotNull HexLocation robber) {
        this.robber = robber;
    }

    public GameMap withRobber(@NotNull HexLocation robber) {
        setRobber(robber);
        return this;
    }

    /**
     * @return A list of all the hexes on the grid - it's only land tiles
     */
    public Map<HexLocation, Hex> getHexes() {
        return hexes;
    }

    /**
     * @param hexes A list of all the hexes on the grid - it's only land tiles
     */
    public void setHexes(@NotNull Map<HexLocation, Hex> hexes) {
        this.hexes = hexes;
    }

    public GameMap withHexes(@NotNull Map<HexLocation, Hex> hexes) {
        setHexes(hexes);
        return this;
    }

    /**
     * @return the list of ports currently placed on the map
     */
    public Map<HexLocation, Port> getPorts() {
        return ports;
    }

    /**
     * @param ports list of ports currently placed on the map
     */
    public void setPorts(@NotNull Map<HexLocation, Port> ports) {
        this.ports = ports;
    }

    public GameMap withPorts(@NotNull Map<HexLocation, Port> ports) {
        setPorts(ports);
        return this;
    }

    /**
     * @return the list of settlements currently placed on the map
     */
    public Map<VertexLocation, PlayerIndex> getSettlements() {
        return settlements;
    }

    /**
     * @param settlements list of settlements currently placed on the map
     */
    public void setSettlements(@NotNull Map<VertexLocation, PlayerIndex> settlements) {
        this.settlements = settlements;
    }

    public GameMap withSettlements(@NotNull Map<VertexLocation, PlayerIndex> settlements) {
        setSettlements(settlements);
        return this;
    }

    /**
     * @return the list of cities currently placed on the map
     */
    public Map<VertexLocation, PlayerIndex> getCities() {
        return cities;
    }

    /**
     * @param cities list of cities currently placed on the map
     */
    public void setCities(@NotNull Map<VertexLocation, PlayerIndex> cities) {
        this.cities = cities;
    }

    public GameMap withCities(@NotNull Map<VertexLocation, PlayerIndex> cities) {
        setCities(cities);
        return this;
    }

    @Override
    public String toString() {
        return "Map [" +
                "roads=" + roads +
                ", radius=" + radius +
                ", robber=" + robber +
                ", hexes=" + hexes +
                ", ports=" + ports +
                ", settlements=" + settlements +
                ", cities=" + cities +
                "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof GameMap) {
            return equals((GameMap) other);
        }
        return false;
    }

    public boolean equals(GameMap other) {
        return (
                roads == other.roads &&
                        radius == other.radius &&
                        robber == other.robber &&
                        hexes == other.hexes &&
                        ports == other.ports &&
                        settlements == other.settlements &&
                        cities == other.cities
        );
    }
}
