package shared.models.game;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import shared.definitions.HexType;
import shared.definitions.PlayerIndex;
import shared.definitions.PortType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
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
    private int radius = 3;

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

    /**
     * Constructor that creates GameMap based on if the user wants random hex types, random port types,
     * and/or random numbers on hexes
     *
     * @param randomTiles   whether the HexTypes are random
     * @param randomPorts   whether the Port Types are random
     * @param randomNumbers whether the Numbers on the Hexes are random
     */
    public GameMap(boolean randomTiles, boolean randomPorts, boolean randomNumbers) {
        setNewGameHexes(randomTiles, randomNumbers);
        setNewGamePorts(randomPorts);
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
     * Sets the hexes of the new game based on if the user wants random hex types or random numbers on hexes
     *
     * @param randomTiles   whether the HexTypes are random
     * @param randomNumbers whether the Numbers on the Hexes are random
     */
    private void setNewGameHexes(boolean randomTiles, boolean randomNumbers) {
        List<Integer> hexNumbers = setHexNumbers(randomNumbers);
        List<HexType> hexTypes = setHexTypes(randomTiles);
        List<HexLocation> hexLocations = setHexLocations();

        HexType hexType = HexType.DESERT;
        int hexNumber = -1;
        boolean seenNegOne = false;
        boolean seenDesert = false;
        for (int i = 0; i < hexLocations.size(); i++) {
            if (hexNumbers.get(i) == 0 && hexTypes.get(i) != HexType.DESERT) {
                if (seenDesert) {
                    hexes.put(hexLocations.get(i), new Hex(hexNumber, hexLocations.get(i), hexTypes.get(i)));
                } else {
                    hexes.put(hexLocations.get(i), new Hex(0, hexLocations.get(i), HexType.DESERT));
                    hexType = hexTypes.get(i);
                }
                seenNegOne = true;
            } else if (hexTypes.get(i) == HexType.DESERT && hexNumbers.get(i) != 0) {
                if (seenNegOne) {
                    hexes.put(hexLocations.get(i), new Hex(hexNumbers.get(i), hexLocations.get(i), hexType));
                } else {
                    hexes.put(hexLocations.get(i), new Hex(0, hexLocations.get(i), HexType.DESERT));
                    hexNumber = hexNumbers.get(i);
                }
                seenDesert = true;
            } else {
                hexes.put(hexLocations.get(i), new Hex(hexNumbers.get(i), hexLocations.get(i), hexTypes.get(i)));
            }
        }
        Set<HexLocation> deserts = MapUtils.keysWithValueMatching(hexes, hex -> hex.getResource() == HexType.DESERT);
        assert deserts.size() == 1;
        HexLocation desert = deserts.iterator().next();
        setRobber(desert);
    }

    /**
     * Sets the hex locations of the new game based on the radius
     *
     * @return the List of Hex Locations for each hex on the map
     */
    private List<HexLocation> setHexLocations() {
        List<HexLocation> hexLocations = new ArrayList<>();

        for (int column = -radius + 1; column < radius; column++) {
            for (int diagonalRow = (column < 0) ? -radius - column + 1 : -radius + 1; diagonalRow < ((column > 0) ? radius - column : radius); diagonalRow++) {
                hexLocations.add(new HexLocation(column, diagonalRow));
            }
        }
        return hexLocations;
    }

    /**
     * Sets the Hex Types for each hex on the new game map
     *
     * @param randomTiles whether the HexTypes are random
     * @return List of Hex Types that contain a hex type for each hex on the new game map
     */
    private List<HexType> setHexTypes(boolean randomTiles) {
        List<HexType> hexTypes = Arrays.asList(HexType.ORE, HexType.WHEAT, HexType.WOOD, HexType.BRICK, HexType.SHEEP,
                HexType.SHEEP, HexType.ORE, HexType.DESERT, HexType.WOOD, HexType.WHEAT, HexType.WOOD, HexType.WHEAT,
                HexType.BRICK, HexType.ORE, HexType.BRICK, HexType.SHEEP, HexType.WOOD, HexType.SHEEP, HexType.WHEAT
        );

        if (randomTiles) {
            Collections.shuffle(hexTypes);
        }

        return hexTypes;
    }

    /**
     * Set the Hex Numbers for each hex on the new game map
     *
     * @param randomNumbers whether the Numbers on the Hexes are random
     * @return List of Integers that contain the numbers for each hex on the new game map
     */
    private List<Integer> setHexNumbers(boolean randomNumbers) {
        List<Integer> hexNumbers = Arrays.asList(5, 2, 6, 8, 10, 9, 3, 0, 3, 11, 4, 8, 4, 6, 5, 10, 11, 12, 9);

        if (randomNumbers) {
            Collections.shuffle(hexNumbers);
        }

        return hexNumbers;
    }

    /**
     * Sets the ports of the new game based on if the user wants random port types
     *
     * @param randomPorts whether the Port Types are random
     */
    private void setNewGamePorts(boolean randomPorts) {
        final List<EdgeLocation> portLocations = Arrays.asList(
                new EdgeLocation(new HexLocation(-3, 0), EdgeDirection.SouthEast),
                new EdgeLocation(new HexLocation(-1, -2), EdgeDirection.South),
                new EdgeLocation(new HexLocation(1, -3), EdgeDirection.South),
                new EdgeLocation(new HexLocation(3, -3), EdgeDirection.SouthWest),
                new EdgeLocation(new HexLocation(3, -1), EdgeDirection.NorthWest),
                new EdgeLocation(new HexLocation(2, 1), EdgeDirection.NorthWest),
                new EdgeLocation(new HexLocation(0, 3), EdgeDirection.North),
                new EdgeLocation(new HexLocation(-2, 3), EdgeDirection.NorthEast),
                new EdgeLocation(new HexLocation(-3, 2), EdgeDirection.NorthEast)
        );

        List<PortType> portTypes = Arrays.asList(
                PortType.THREE, PortType.WHEAT, PortType.ORE,
                PortType.THREE, PortType.SHEEP, PortType.THREE,
                PortType.THREE, PortType.BRICK, PortType.WOOD
        );

        if (randomPorts) {
            Collections.shuffle(portTypes);
        }

        for (int i = 0; i < portLocations.size(); i++) {
            HexLocation hexLoc = portLocations.get(i).getHexLoc();
            ports.put(hexLoc, new Port(hexLoc, portLocations.get(i).getDir(), (portTypes.get(i) == PortType.THREE) ? 3 : 2, portTypes.get(i)));
        }
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

    @NotNull
    public Set<HexLocation> getVertexHexes(@NotNull VertexLocation vertex) {
        Set<HexLocation> locs = vertex.getHexes();
        locs.removeIf(h -> !hexes.containsKey(h));
        return locs;
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
     * @param location        the location to test, not null
     * @param player          the player to test, not null
     * @param isFirstTwoTurns whether the game is in the first turn or not
     * @return whether the map could support adding a settlement owned by the player at that location
     */
    public boolean canAddSettlement(@NotNull VertexLocation location, @NotNull PlayerIndex player, boolean isFirstTwoTurns) {

        if (settlements.get(location) != null || cities.get(location) != null) {
            return false;
        }
        Set<EdgeLocation> adjacentEdges = getVertexEdges(location);
        if (adjacentEdges.size() == 0) {
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
     * @param location
     * @param player
     * @param isSetup
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
        if ((Math.abs(hexX) >= radius || Math.abs(hexY) >= radius || Math.abs(hexX + hexY) >= radius) && (Math.abs(neighborX) >= radius || Math.abs(neighborY) >= radius || Math.abs(neighborX + neighborY) >= radius)) {
            return false;
        }
        for (VertexLocation v : vertices) {
            HashSet<EdgeLocation> edges = (HashSet) getVertexEdges(v);
            if (hasBuilding(v)) {
                if (getBuildingOwner(v) == player)
                    hasAdjacentBuilding = true;
                else if (isSetup && settlementHasAdjacentRoads(v))
                    return false;
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
        if (!hasAdjacentRoad && !hasAdjacentBuilding)
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
            if ((Math.abs(hexX) >= radius || Math.abs(hexY) >= radius || Math.abs(hexX + hexY) >= radius) && (Math.abs(neighborX) >= radius || Math.abs(neighborY) >= radius || Math.abs(neighborX + neighborY) >= radius)) {
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
        int size = 0;
        int max = 0;
        Set<EdgeLocation> playerRoads = getPlayerRoads(player);

        while (!playerRoads.isEmpty()) {
            // Pick a road from the set
            EdgeLocation loc = playerRoads.iterator().next();
            Set<EdgeLocation> longest = getLongestRoad(new HashSet<EdgeLocation>(Arrays.asList(loc)), loc, player);
            max = Math.max(longest.size(), max);
            playerRoads.removeAll(longest);
        }
        return max;
    }

    private Set<EdgeLocation> connectedRoads(EdgeLocation loc) {
        PlayerIndex player = roads.get(loc);
        // Note: A continuous road is broken if another player builds a settlement on the vertex between two roads
        return loc.getConnectedVertices().stream()
                .filter(v -> {
                    PlayerIndex owner = getBuildingOwner(v);
                    return (owner == null || owner == player);
                })
                .flatMap(v -> getVertexEdges(v).stream())
                .distinct()
                .filter(e -> player == roads.get(e))
                .collect(Collectors.toSet());
    }

    /**
     * Find the longest road that extends the given road segment, starting from the given location.
     */
    private Set<EdgeLocation> getLongestRoad(Set<EdgeLocation> current, EdgeLocation loc, PlayerIndex player) {
        List<Set<EdgeLocation>> pathCandidates = new ArrayList<>();
        pathCandidates.add(current);
        for (EdgeLocation next : connectedRoads(loc)) {
            // No cycles allowed and no checking visited nodes
            // On an edge, this will always be caught
            if (pathCandidates.stream().anyMatch(candidate -> candidate.contains(next))) {
                continue;
            }
            Set<EdgeLocation> candidate = new HashSet<>(current);
            candidate.add(next);
            pathCandidates.add(getLongestRoad(candidate, next, player));
        }
        if (pathCandidates.size() == 1) {
            return current;
        }
        // The pair of sets whose union forms the largest set is the longest continuous road
        // To do this, all combinations size 2 of the candidates are iterated
        // There are (candidates.size choose 2) possible union candidates
        Set<EdgeLocation> largest = current;
        int n = pathCandidates.size();
        for (int i = 0; i < n - 1; ++i) {
            for (int j = i + 1; j < n; ++j) {
                Set<EdgeLocation> union = new HashSet<>(pathCandidates.get(i));
                union.addAll(pathCandidates.get(j));
                if (union.size() > largest.size()) {
                    largest = union;
                }
            }
        }
        return largest;
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
