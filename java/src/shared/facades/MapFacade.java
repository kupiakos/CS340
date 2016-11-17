package shared.facades;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import shared.definitions.HexType;
import shared.definitions.PlayerIndex;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.models.game.ClientModel;
import shared.models.game.GameMap;
import shared.models.game.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This facade creates an interface to communicate with the underlying map sub-model.
 */
public class MapFacade extends AbstractFacade {

    /**
     * Constructor. Requires a valid game model to work.
     *
     * @param manager the manager to use, not null
     * @throws NullPointerException if {@code model} is null
     * @pre {@code model} is a valid {@link ClientModel}.
     * @post This provides valid operations on {@code model}.
     */
    public MapFacade(@NotNull FacadeManager manager) {
        super(manager);
    }

    /**
     * Checks to see if a road can be placed on the map at the specified {@link EdgeLocation}.
     *
     * @param location {@link EdgeLocation} being queried for road placement.
     * @return True if the edge is empty and adjacent to a Road/{City}/settlement belonging to current {@link Player}.
     * @pre The {@link Player} is in a game.
     * @post None
     */
    public boolean canPlaceRoad(Player player, EdgeLocation location, boolean isSetup) {
        location = location.getNormalizedLocation();
        return getMap().canAddRoad(location, player.getPlayerIndex(), isSetup);
    }

    /**
     * Checks to see if a settlement can be placed on the map at the specified {@link VertexLocation}.
     *
     * @param location {@link VertexLocation} being queried for settlement placement.
     * @return True if the {@link VertexLocation} is empty and adjacent to a road belonging to current {@link Player}.
     * @pre The {@link Player} is in a game.
     * @post None
     */
    public boolean canPlaceSettlement(Player player, VertexLocation location, boolean isFirstTurn) {
        location = location.getNormalizedLocation();
        return getMap().canAddSettlement(location, player.getPlayerIndex(), isFirstTurn);
    }

    /**
     * Checks to see if a city can be placed on the map at the specified {@link VertexLocation}.
     *
     * @param location {@link VertexLocation} being queried for city placement.
     * @return True if the {@link VertexLocation} has a settlement belonging to the current {@link Player} and adjacent to a road belonging to current {@link Player}.
     * @pre The {@link Player} is in a game.
     * @post None
     */
    public boolean canPlaceCity(Player player, VertexLocation location) {
        location = location.getNormalizedLocation();
        return getMap().canUpgradeSettlement(location, player.getPlayerIndex());
    }

    /**
     * Returns the player who currently has the longest road.
     *
     * @return
     */
    @Nullable
    PlayerIndex findLongestRoad() {
        GameMap map = getMap();
        Map<PlayerIndex, Integer> lengths = getModel().getPlayers().stream()
                .map(Player::getPlayerIndex)
                .collect(Collectors.toMap(Function.identity(), map::getPlayerLongestRoad));
        PlayerIndex longestRoadPlayer = getModel().getTurnTracker().getLongestRoad();
        for (PlayerIndex p : lengths.keySet()) {
            int roadSize = getMap().getPlayerLongestRoad(p);
            if (roadSize >= 5) {
                if (longestRoadPlayer == null) {
                    getModel().getTurnTracker().setLongestRoad(p);
                    longestRoadPlayer = p;
                    getModel().getPlayer(p).setVictoryPoints(getModel().getPlayer(p).getVictoryPoints() + 2);
                } else if (p != longestRoadPlayer) {
                    int currentWinnerSize = getMap().getPlayerLongestRoad(longestRoadPlayer);
                    if (roadSize > currentWinnerSize) {
                        getModel().getTurnTracker().setLongestRoad(p);
                        longestRoadPlayer = p;
                        getModel().getPlayer(p).setVictoryPoints(getModel().getPlayer(p).getVictoryPoints() + 2);
                        getModel().getPlayer(longestRoadPlayer).setVictoryPoints(getModel().getPlayer(longestRoadPlayer).getVictoryPoints() - 2);
                    }
                }
            }
        }
        return longestRoadPlayer;
    }



    /**
     * Sees if a given {@link EdgeLocation} has a road built on it currently.
     *
     * @param edge The {@link EdgeLocation} that is being queried.
     * @return True if the {@link EdgeLocation}  has not been built upon; false otherwise.
     */
    public boolean isEdgeEmpty(@NotNull EdgeLocation edge) {
        return !getMap().getRoads().containsKey(edge);
    }

    /**
     * Sees if a given {@link VertexLocation} has a settlement or city built on it currently.
     *
     * @param vertex The {@link VertexLocation} that is being queried.
     * @return True if the{@link EdgeLocation} has not been built upon; false otherwise.
     */
    public boolean isVertexEmpty(@NotNull VertexLocation vertex) {
        return (!getMap().getSettlements().containsKey(vertex) && !getMap().getCities().containsKey(vertex));
    }

    /**
     * Sees if a given {@link VertexLocation} has a settlement built on it that belongs to the given {@code player}.
     *
     * @param player The {@code player} whose settlement is being checked for.
     * @param vertex The {@link VertexLocation} that is being queried.
     * @return True if the {@code player} has a settlement built on the specified {@link VertexLocation}.
     */
    public boolean hasSettlement(@NotNull Player player, @NotNull VertexLocation vertex) {
        if (getMap().getSettlements().containsKey(vertex)) {
            if (getMap().getRoads().get(vertex) == player.getPlayerIndex())
                return true;
        }
        return false;
    }

    /**
     * Sees if a given {@link VertexLocation} has a city built on it that belongs to the given {@code player}.
     *
     * @param player The {@code player} whose city is being checked for.
     * @param vertex The {@link VertexLocation} that is being queried.
     * @return True if the {@code player} has a city built on the specified {@link VertexLocation}.
     */
    public boolean hasCity(@NotNull Player player, @NotNull VertexLocation vertex) {
        if (getMap().getCities().containsKey(vertex)) {
            if (getMap().getCities().get(vertex) == player.getPlayerIndex())
                return true;
        }
        return false;
    }

    /**
     * Sees if a given {@link HexLocation} is currently occupied by the robber.
     *
     * @param hex The {@link HexLocation} being queried.
     * @return True if the robber is currently on the specified {@link HexLocation}; false otherwise.
     */
    public boolean hasRobber(@NotNull HexLocation hex) {
        return getMap().getRobber() == hex;
    }

    /**
     * Sees if a given {@link EdgeLocation} is adjacent a road and/or settlement/city owned by {@code player}.
     *
     * @param player The {@code player} for whom we are looking for owned roads/buildings adjacent to {@code edge}.
     * @param edge   The {@link EdgeLocation} that is being queried.
     * @return True if the {@code player} owns a road/building adjacent to the specified {@link EdgeLocation}.
     */


    /**
     * Returns the {@link HexType} produced by the hex found at the given {@link HexLocation}.
     *
     * @param hex The {@link HexLocation} that is being queried.
     * @return The {@link HexType} produced at the specified {@link HexLocation}.
     */
    public HexType getHexType(@NotNull HexLocation hex) {
        return getMap().getHex(hex).getResource();
    }

    public GameMap getMap() {
        return getModel().getMap();
    }

    public List<HexLocation> getOceanBorder(int radius) {
        List<HexLocation> locs = new ArrayList<>();
        for (int i = 0; i < radius + 1; ++i) {
            // Western border
            locs.add(new HexLocation(-radius, i));
            // Eastern border
            locs.add(new HexLocation(radius, -i));
        }
        for (int i = 0; i < radius; ++i) {
            // Southwestern border
            locs.add(new HexLocation(-i, radius));
            // Northeastern border
            locs.add(new HexLocation(i, -radius));
        }
        for (int i = 1; i < radius; ++i) {
            // Southeastern border
            locs.add(new HexLocation(i, radius - i));
            // Northwestern border
            locs.add(new HexLocation(-i, i - radius));
        }
        return locs;
    }
}
