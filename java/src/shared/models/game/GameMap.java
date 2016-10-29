package shared.models.game;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import shared.definitions.HexType;
import shared.definitions.PlayerIndex;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import shared.utils.MapUtils;

import javax.annotation.Generated;
import java.util.*;
import java.util.stream.Collectors;

@Generated("net.kupiakos")
public class GameMap {

    @SerializedName("roads")
    @Expose
    private Map<EdgeLocation, PlayerIndex> roads = new HashMap<>();

    @SerializedName("radius")
    @Expose
    private int radius;

    @SerializedName("robber")
    @Expose
    private HexLocation robber;

    @SerializedName("hexes")
    @Expose
    private Map<HexLocation, Hex> hexes = new HashMap<>();

    @SerializedName("ports")
    @Expose
    private Map<HexLocation, Port> ports = new HashMap<>();

    @SerializedName("settlements")
    @Expose
    private Map<VertexLocation, PlayerIndex> settlements = new HashMap<>();

    @SerializedName("cities")
    @Expose
    private Map<VertexLocation, PlayerIndex> cities = new HashMap<>();


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

    private static <V> Map<VertexLocation, V> normalizeVertexMap(Map<VertexLocation, V> vMap) {
        return vMap.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey().getNormalizedLocation(), e -> e.getValue()));
    }

    private static <V> Map<EdgeLocation, V> normalizeEdgeMap(Map<EdgeLocation, V> eMap) {
        return eMap.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey().getNormalizedLocation(), e -> e.getValue()));
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
     * Get the locations of the buildings (settlements and cities) owned by the given player.
     *
     * @param player the player whose buildings to retrieve, not null
     * @return the set containing the location of each settlement or city owned by the player
     */
    @NotNull
    public Set<VertexLocation> getPlayerBuildings(@NotNull PlayerIndex player) {
        Set<VertexLocation> buildings = getPlayerSettlements(player);
        buildings.addAll(getPlayerCities(player));
        return buildings;
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
     * Get the ports that a player has a building connected to.
     *
     * @param player the player whose connected ports to retrieve, not null
     * @return the set containing every port connected to by the player
     */
    @NotNull
    public Set<Port> getPlayerPorts(@NotNull PlayerIndex player) {
        Set<VertexLocation> buildings = getPlayerBuildings(player);
        return MapUtils.valuesWithKeyMatching(ports,
                loc -> loc.getVerticesStream().anyMatch(buildings::contains));
    }

    /**
     * Get the location of buildings attached to this hex.
     *
     * @param location the location of the hex
     * @return a set of normalized vertex locations of the buildings
     */
    public Set<VertexLocation> getHexBuildings(@NotNull HexLocation location) {
        return location.getVerticesStream()
                .filter(v -> getBuildingOwner(v) != null)
                .collect(Collectors.toSet());
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
        location = location.getNormalizedLocation();
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
        location = location.getNormalizedLocation();
        return cities.get(location);
    }

    /**
     * Get the owner of a building (settlement or city), or null if that location has neither
     *
     * @param location the location to check
     * @return the owner of the building (settlement or city), or null if that location has neither
     */
    @Nullable
    public PlayerIndex getBuildingOwner(@NotNull VertexLocation location) {
        location = location.getNormalizedLocation();
        if (getSettlementOwner(location) != null)
            return getSettlementOwner(location);
        else
            return getCityOwner(location);
    }

    /**
     * Get whether the map could support adding a settlement owned by a player at the given location.
     * <p>
     * Note this does not check anything regarding resources or turn,
     * but strictly whether adding this to the map would not be an illegal configuration.
     * <p>
     * This tests distance and adjacency requirements.
     * If it is the first turn, don't check whether the player is connected to one of their own roads.
     *
     * @param location    the location to test, not null
     * @param player      the player to test, not null
     * @param isFirstTurn whether the game is in the first turn or not
     * @return whether the map could support adding a settlement owned by the player at that location
     */
    public boolean canAddSettlement(@NotNull VertexLocation location, @NotNull PlayerIndex player, boolean isFirstTwoTurns) {

        if (settlements.get(location) != null || cities.get(location) != null) {
            return false;
        }
        Set<EdgeLocation> adjacentEdges = getVertexEdges(location);
        if(adjacentEdges.size()==0){
            return false;
        }
        boolean ownsAdjacentRoad = false;
        boolean adjacentToAnotherPlayersRoad = false;
        for (EdgeLocation edge : adjacentEdges) {
            if (getRoadOwner(edge) == player) {
                ownsAdjacentRoad = true;
            } else if (getRoadOwner(edge) != null) {
                adjacentToAnotherPlayersRoad = true;
            }
        }
        if (!ownsAdjacentRoad && !isFirstTwoTurns) {
            return false;
        }
        if (adjacentToAnotherPlayersRoad) {
            return false;
        }

        Set<VertexLocation> vertices = getAdjacentVertices(location);

        for (VertexLocation vertex : vertices) {
            if (settlements.get(vertex) != null || cities.get(vertex) != null) {
                return false;
            }
        }

        return true;
    }

    /**
     * Add a settlement at the specific location belonging to a specific player.
     *
     * @param location the location to add the settlement, not null
     * @param player   the player to test, not null
     * @throws IllegalArgumentException if the precondition is violated
     * @pre {@link #canAddSettlement} returns true
     */
    public void addSettlement(@NotNull VertexLocation location, @NotNull PlayerIndex player, boolean isFirstTwoTurns) {
        if (!canAddSettlement(location, player, isFirstTwoTurns)) {
            throw new IllegalArgumentException("Can't add Settlement");
        }
        location = location.getNormalizedLocation();
        settlements.put(location, player);
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
    public boolean canAddRoad(@NotNull EdgeLocation location, @NotNull PlayerIndex player, @NotNull boolean isSetup) {
        if (roads.containsKey(location))
            return false;
        boolean hasAdjacentBuilding = false;
        boolean hasAdjacentRoad = false;
        Set<VertexLocation> vertices = location.getConnectedVertices();
        int hexX = location.getHexLoc().getX();
        int hexY = location.getHexLoc().getY();
        int neighborX = location.getHexLoc().getNeighborLoc(location.getDir()).getX();
        int neighborY = location.getHexLoc().getNeighborLoc(location.getDir()).getY();
        if((Math.abs(hexX) >= radius || Math.abs(hexY) >= radius || Math.abs(hexX + hexY) >= radius)&&(Math.abs(neighborX) >= radius || Math.abs(neighborY) >= radius || Math.abs(neighborX + neighborY)>=radius)){
            return false;
        }
        for (VertexLocation v : vertices) {
            HashSet<EdgeLocation> edges = (HashSet) getVertexEdges(v);
            if (hasBuilding(v)) {
                if (getBuildingOwner(v) != player)
                    return false;
                else if (isSetup && settlementHasAdjacentRoads(v))
                    return false;
                else
                    hasAdjacentBuilding = true;
            }
            for (EdgeLocation e : edges) {
                if (e.equals(location))
                    continue;
                else if (roads.containsKey(e)) {
                    if (getRoadOwner(e) == player)
                        hasAdjacentRoad = true;
                }
            }
        }
        if(!hasAdjacentRoad && !hasAdjacentBuilding)
            return false;
        else if (isSetup && !hasAdjacentBuilding)
            return false;
        else
            return true;
    }

    /**
     * Add a road at the specific location belonging to a specific player.
     *
     * @param location the location to add the road, not null
     * @param player   the player to test, not null
     * @throws IllegalArgumentException if the precondition is violated
     * @pre {@link #canAddSettlement} returns true
     */

    public void addRoad(@NotNull EdgeLocation location, @NotNull PlayerIndex player, @NotNull boolean isSetup) {
        if (!canAddRoad(location, player, isSetup)) {
            throw new IllegalArgumentException("Can't add road");
        }
        location = location.getNormalizedLocation();
        roads.put(location, player);
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
    public boolean canUpgradeSettlement(@NotNull VertexLocation location, @NotNull PlayerIndex player) {
        location = location.getNormalizedLocation();
        if (settlements.get(location) != player) {
            return false;
        }
        return true;
    }

    /**
     * Upgrade a settlement at the specific location belonging to a specific player.
     *
     * @param location the location to add the settlement, not null
     * @param player   the player to test, not null
     * @throws IllegalArgumentException if the precondition is violated
     * @pre {@link #canAddSettlement} returns true
     */
    public void upgradeSettlement(@NotNull VertexLocation location, @NotNull PlayerIndex player) {
        if (!canUpgradeSettlement(location, player)) {
            throw new IllegalArgumentException("Can't upgrade Settlement");
        }
        location = location.getNormalizedLocation();
        settlements.remove(location);
        cities.put(location, player);
    }

    /**
     * gets the adjacent vertices to the passed in vertex location
     *
     * @param location the passed in vertex location to get the adjacent vertices from
     * @return the adjacent vertices to the passed in vertex location
     */
    public Set<VertexLocation> getAdjacentVertices(VertexLocation location) {

        Set<VertexLocation> verticesResult = new HashSet<>();

        Set<EdgeLocation> adjacentEdges = getVertexEdges(location);
        for (EdgeLocation adjacentEdge : adjacentEdges) {

            Set<VertexLocation> adjacentVertices = adjacentEdge.getConnectedVertices();
            for (VertexLocation adjacentVertex : adjacentVertices) {
                verticesResult.add(adjacentVertex);
            }

        }

        return verticesResult;
    }

    /**
     * Checks if the vertex location has a building (city or settlement), or neither
     *
     * @param vertex the vertex to be checked
     * @return true if there is a building and false if there is no building
     */
    boolean hasBuilding(VertexLocation vertex) {
        if (this.settlements.containsKey(vertex))
            return true;
        else if (this.cities.containsKey(vertex))
            return true;
        return false;
    }

    /**
     * Gets the Edges connecting to the passed in vertex location
     *
     * @param vertex the vertex to get the connecting edges from
     * @return the edge locations connected to the passed in vertex
     */
    Set<EdgeLocation> getVertexEdges(VertexLocation vertex) {
        Set<EdgeLocation> edges = vertex.getEdges();
        for (Iterator<EdgeLocation> itr = edges.iterator(); itr.hasNext(); ) {
            EdgeLocation e = itr.next();
            int hexX = e.getHexLoc().getX();
            int hexY = e.getHexLoc().getY();
            int neighborX = e.getHexLoc().getNeighborLoc(e.getDir()).getX();
            int neighborY = e.getHexLoc().getNeighborLoc(e.getDir()).getY();
            if((Math.abs(hexX) >= radius || Math.abs(hexY) >= radius || Math.abs(hexX + hexY) >= radius)&&(Math.abs(neighborX) >= radius || Math.abs(neighborY) >= radius || Math.abs(neighborX + neighborY)>=radius)){
                itr.remove();
            }
        }
        return edges;
    }

    /**
     * Method which is used only in the first two rounds of setup.  Makes sure player is only placing
     * a road next to the settlement he just placed.
     *
     * @param v the location of a settlment
     * @return true if settlement has no adjacent roads.
     */
    boolean settlementHasAdjacentRoads(VertexLocation v) {
        HashSet<EdgeLocation> edges = (HashSet) getVertexEdges(v);
        for (EdgeLocation e : edges) {
            if (roads.containsKey(e))
                return true;
        }
        return false;
    }

    /**
     * Find the length of the longest connected road for the given player denoted by their {@link PlayerIndex}.
     *
     * @param player
     * @return Greatest number of connected roads.
     */
    public int getPlayerLongestRoad(PlayerIndex player) {
        int max = 0;
        HashSet<EdgeLocation> edges = (HashSet) getPlayerRoads(player);
        for (EdgeLocation e : edges) {
            int roadSize = getRoadSize(1, e, player);
            if (roadSize > max)
                max = roadSize;
        }
        return max;
    }

    /**
     * Recursive method that finds the greatest number of connected roads for the given player with a starting location.
     *
     * @param currentSize
     * @param edge
     * @param player
     * @return
     */
    private int getRoadSize(int currentSize, EdgeLocation edge, PlayerIndex player) {
        Set<EdgeLocation> edges;
        switch (edge.getDir()) {
            case North:
                edges = getVertexEdges(new VertexLocation(edge.getHexLoc(), VertexDirection.NorthEast));
                break;
            case NorthEast:
                edges = getVertexEdges(new VertexLocation(edge.getHexLoc(), VertexDirection.East));
                break;
            case SouthEast:
                edges = getVertexEdges(new VertexLocation(edge.getHexLoc(), VertexDirection.SouthEast));
                break;
            case South:
                edges = getVertexEdges(new VertexLocation(edge.getHexLoc(), VertexDirection.SouthWest));
                break;
            case SouthWest:
                edges = getVertexEdges(new VertexLocation(edge.getHexLoc(), VertexDirection.West));
                break;
            case NorthWest:
                edges = getVertexEdges(new VertexLocation(edge.getHexLoc(), VertexDirection.NorthWest));
                break;
            default:
                edges = new HashSet<>();
                break;
        }
        int max = currentSize;
        for (EdgeLocation e : edges) {
            if (e.equals(edge.getNormalizedLocation()))
                continue;
            else if (roads.get(e) == player) {
                int newSize = getRoadSize(currentSize + 1, e, player);
                if (newSize > max) {
                    max = newSize;
                }
            }
        }
        return max;
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
        this.roads = normalizeEdgeMap(roads);
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
        this.settlements = normalizeVertexMap(settlements);
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
        this.cities = normalizeVertexMap(cities);
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
                Objects.equals(roads, other.roads) &&
                        radius == other.radius &&
                        Objects.equals(robber, other.robber) &&
                        Objects.equals(hexes, other.hexes) &&
                        Objects.equals(ports, other.ports) &&
                        Objects.equals(settlements, other.settlements) &&
                        Objects.equals(cities, other.cities)
        );
    }
}
