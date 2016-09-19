package shared.models.game;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javax.annotation.Generated;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.utils.MapUtils;

@Generated("net.kupiakos")
public class GameMap {

    @SerializedName("roads")
    @Expose
    private Map<EdgeLocation, Road> roads;

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
    private Map<VertexLocation, Player> settlements;

    @SerializedName("cities")
    @Expose
    private Map<VertexLocation, Player> cities;


    // CUSTOM CODE

    // TODO: Javadocs

    public Set<VertexLocation> getPlayerCities(@NotNull Player player) {
        return MapUtils.keysWithValue(cities, player);
    }

    public Set<VertexLocation> getPlayerSettlements(@NotNull Player player) {
        return MapUtils.keysWithValue(settlements, player);
    }

    @Nullable
    public Road getRoad(@NotNull EdgeLocation location) {
        location = location.getNormalizedLocation();
        return roads.get(location);
    }

    @Nullable
    public Hex getHex(@NotNull HexLocation location) {
        return hexes.get(location);
    }

    public Set<Hex> getHexesWithNumber(int number) {
        return MapUtils.valuesMatching(hexes, h -> h.getNumber() == number);
    }

    @Nullable
    public Port getPort(@NotNull HexLocation location) {
        return ports.get(location);
    }

    @Nullable
    public Player getSettlementOwner(@NotNull VertexLocation location) {
        return settlements.get(location);
    }

    @Nullable
    public Player getCityOwner(@NotNull VertexLocation location) {
        return cities.get(location);
    }

    public void addSettlement(@NotNull VertexLocation location, @NotNull Player player) {
    }

    public void addCity(@NotNull VertexLocation location, @NotNull Player player) {
    }

    public void addRoad(@NotNull Road road) {

    }

    // END CUSTOM CODE

    /**
     * No args constructor for use in serialization
     */
    public GameMap() {
    }

    /**
      * @param roads The list of roads currently placed on the map
      * @param radius The radius of the map (it includes the center hex, and the ocean hexes)
      * @param robber The current location of the robber
      * @param hexes A list of all the hexes on the grid - it's only land tiles
      * @param ports The list of ports currently placed on the map
      * @param settlements The list of settlements currently placed on the map
      * @param cities The list of cities currently placed on the map
     */
    public GameMap(Map<EdgeLocation, Road> roads,
                   int radius,
                   HexLocation robber,
                   Map<HexLocation, Hex> hexes,
                   Map<HexLocation, Port> ports,
                   Map<VertexLocation, Player> settlements,
                   Map<VertexLocation, Player> cities) {
            this.roads = roads;
            this.radius = radius;
            this.robber = robber;
            this.hexes = hexes;
            this.ports = ports;
            this.settlements = settlements;
            this.cities = cities;
    }

    /**
     * @return the list of roads currently placed on the map
     */
    public Map<EdgeLocation, Road> getRoads() { return roads; }

    /**
     * @param roads list of roads currently placed on the map
     */
    public void setRoads(@NotNull Map<EdgeLocation, Road> roads) { this.roads = roads; }

    public GameMap withRoads(@NotNull Map<EdgeLocation, Road> roads) {
        setRoads(roads);
        return this;
    }
    /**
     * @return The radius of the map (it includes the center hex, and the ocean hexes; pass this into the hexgrid constructor)
     */
    public int getRadius() { return radius; }

    /**
     * @param radius The radius of the map (it includes the center hex, and the ocean hexes; pass this into the hexgrid constructor)
     */
    public void setRadius(int radius) { this.radius = radius; }

    public GameMap withRadius(int radius) {
        setRadius(radius);
        return this;
    }
    /**
     * @return The current location of the robber
     */
    public HexLocation getRobber() { return robber; }

    /**
     * @param robber The current location of the robber
     */
    public void setRobber(@NotNull HexLocation robber) { this.robber = robber; }

    public GameMap withRobber(@NotNull HexLocation robber) {
        setRobber(robber);
        return this;
    }
    /**
     * @return A list of all the hexes on the grid - it's only land tiles
     */
    public Map<HexLocation, Hex> getHexes() { return hexes; }

    /**
     * @param hexes A list of all the hexes on the grid - it's only land tiles
     */
    public void setHexes(@NotNull Map<HexLocation, Hex> hexes) { this.hexes = hexes; }

    public GameMap withHexes(@NotNull Map<HexLocation, Hex> hexes) {
        setHexes(hexes);
        return this;
    }
    /**
     * @return the list of ports currently placed on the map
     */
    public Map<HexLocation, Port> getPorts() { return ports; }

    /**
     * @param ports list of ports currently placed on the map
     */
    public void setPorts(@NotNull Map<HexLocation, Port> ports) { this.ports = ports; }

    public GameMap withPorts(@NotNull Map<HexLocation, Port> ports) {
        setPorts(ports);
        return this;
    }
    /**
     * @return the list of settlements currently placed on the map
     */
    public Map<VertexLocation, Player> getSettlements() { return settlements; }

    /**
     * @param settlements list of settlements currently placed on the map
     */
    public void setSettlements(@NotNull Map<VertexLocation, Player> settlements) { this.settlements = settlements; }

    public GameMap withSettlements(@NotNull Map<VertexLocation, Player> settlements) {
        setSettlements(settlements);
        return this;
    }
    /**
     * @return the list of cities currently placed on the map
     */
    public Map<VertexLocation, Player> getCities() { return cities; }

    /**
     * @param cities list of cities currently placed on the map
     */
    public void setCities(@NotNull Map<VertexLocation, Player> cities) { this.cities = cities; }

    public GameMap withCities(@NotNull Map<VertexLocation, Player> cities) {
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
            return equals((GameMap)other);
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
