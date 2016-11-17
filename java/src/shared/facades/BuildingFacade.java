package shared.facades;

import org.jetbrains.annotations.NotNull;
import shared.definitions.PurchaseType;
import shared.locations.EdgeLocation;
import shared.locations.VertexLocation;
import shared.models.game.ClientModel;
import shared.models.game.Player;

import java.util.Set;

/**
 * This facade creates an interface to communicate with the underlying building sub-model.
 */
public class BuildingFacade extends AbstractFacade {

    private FacadeManager manager;
    private ResourcesFacade resource;
    private MapFacade map;

    /**
     * Constructor. Requires a valid game model to work.
     *
     * @param manager the manager to use, not null
     * @throws NullPointerException if {@code model} is null
     * @pre {@code model} is a valid {@link ClientModel}.
     * @post This provides valid operations on {@code model}.
     */
    public BuildingFacade(@NotNull FacadeManager manager) {
        super(manager);
        resource = manager.getResources();
        map = manager.getMap();
        this.manager = manager;
    }

    /**
     * Builds a road for the given player the specified location.
     *
     * @param player        The {@link Player} who is building the road.
     * @param buildLocation The {@link EdgeLocation} on the map where the road is to be built.
     * @param isFree        Whether the road is free to build.
     * @pre {@link #canBuildRoad} returns true for the given player and location.
     * @post <ul>
     * <li>Road is built on game map at specified location.</li>
     * <li>The player has one fewer unused roads.</li>
     * <li>Longest road may be given to the player if applicable.</li>
     * </ul>
     */
    public void buildRoad(@NotNull Player player, @NotNull EdgeLocation buildLocation, boolean isFree, boolean isSetup) throws IllegalArgumentException {
        if (!canBuildRoad(player, buildLocation, isFree, isSetup)) {
            throw new IllegalArgumentException();
        }
        this.getModel().getMap().addRoad(buildLocation, player.getPlayerIndex(), isSetup);
        if (!isFree) {
            resource.purchaseItem(player, PurchaseType.ROAD);
        }
        player.setRoads(player.getRoads() - 1);
    }

    /**
     * Builds a settlement for the given player at the specified location.
     *
     * @param buildLocation The {@link VertexLocation} on the map where the settlement is to be built.
     * @param isFree        Whether the current {@link Player} gets to build this for free.
     * @pre {@link #canBuildSettlement} returns true for the given player and location.
     * @post <ul>
     * <li>Settlement is built at given location.</li>
     * <li>Current {@link Player} has one fewer unused settlements.</li>
     * <li>Current {@link Player} gains one victory point.</li>
     * </ul>
     */
    public void buildSettlement(@NotNull Player player, @NotNull VertexLocation buildLocation, boolean isFree) {
        if (!canBuildSettlement(player, buildLocation, isFree))
            throw new IllegalArgumentException();
        this.getModel().getMap().addSettlement(buildLocation, player.getPlayerIndex(), manager.getTurn().isSetup());
        if (!isFree) {
            resource.purchaseItem(player, PurchaseType.SETTLEMENT);
        }
        player.setSettlements(player.getSettlements() - 1);
    }

    /**
     * Builds a city for the given player at the specified location.
     *
     * @param buildLocation the {@link VertexLocation} on the map where the city is to be built
     * @pre {@link #canBuildCity} returns true for the given player and location.
     * @post <ul>
     * <li>A new city is built at given {@link VertexLocation}.
     * <li>Current {@link Player} has one fewer unused cities and one
     * <li>more unused settlements.
     * <li>Current {@link Player} gains one victory point.
     * </ul>
     */
    public void buildCity(@NotNull Player player, @NotNull VertexLocation buildLocation) {
        if (!canBuildCity(player, buildLocation))
            throw new IllegalArgumentException();
        this.getModel().getMap().upgradeSettlement(buildLocation, player.getPlayerIndex());
        resource.purchaseItem(player, PurchaseType.CITY);
        player.setCities(player.getCities() - 1);
        player.setSettlements(player.getSettlements() + 1);
    }

    /**
     * Check to see if the given player is able to build a road.
     *
     * @param buildLocation the {@link EdgeLocation} where the {@link Player} wants to build a road
     * @param isFree        whether the current {@link Player} gets to build this for free
     * @return true if the player can build a road according to the rules of Catan; false otherwise
     * @pre {@code Player} is currently in a game
     * @post None.
     */
    public boolean canBuildRoad(@NotNull Player player, @NotNull EdgeLocation buildLocation, boolean isFree, boolean isSetup) {
        resource = manager.getResources();
        if (getUnusedRoads(player) <= 0)
            return false;
        if (!map.canPlaceRoad(player, buildLocation, isSetup))
            return false;
        if (!isFree) {
            if (!resource.canPurchaseItem(player, PurchaseType.ROAD))
                return false;
        }
        return true;
    }

    /**
     * Check to see if the given player is able to build a settlement.
     *
     * @param buildLocation The {@link VertexLocation} where the {@link Player} wants to build a settlement.
     * @param isFree        Whether the current {@link Player} gets to build this for free.
     * @return True if the {@link Player} can build a settlement according to the rules of Catan; false otherwise.
     * @pre {@link Player} is currently in a game.
     * @post None.
     */
    public boolean canBuildSettlement(@NotNull Player player, @NotNull VertexLocation buildLocation, boolean isFree) {
        resource = manager.getResources();
        if (getUnusedSettlements(player) <= 0)
            return false;
        else if (!map.canPlaceSettlement(player, buildLocation, manager.getTurn().isSetup()))
            return false;
        else if (!isFree) {
            if (!resource.canPurchaseItem(player, PurchaseType.SETTLEMENT))
                return false;
        }
        return true;
    }

    /**
     * Check to see if the given player is able to build a settlement.
     *
     * @param buildLocation The {@link VertexLocation} where the {@link Player} wants to build a city.
     * @return True if the {@link Player} can build a city according to the rules of Catan; false otherwise.
     * @pre {@link Player} is currently in a game.
     * @post None.
     */
    public boolean canBuildCity(@NotNull Player player, @NotNull VertexLocation buildLocation) {
        resource = manager.getResources();
        if (getUnusedCities(player) < 1)
            return false;
        else if (!map.canPlaceCity(player, buildLocation))
            return false;
        else if (!resource.canPurchaseItem(player, PurchaseType.CITY))
            return false;
        return true;
    }

    /**
     * Get the number of roads a {@link Player} has built.
     *
     * @return The number of roads that the {@link Player} has built in the current game.
     * @pre The {@link Player} is currently in a game.
     * @post None.
     */
    public int getTotalRoadsBuilt(@NotNull Player player) {
        Set<EdgeLocation> roads = getModel().getMap().getPlayerRoads(player.getPlayerIndex());
        return roads.size();
    }

    /**
     * Queries how many unused roads the given {@link Player} has.
     *
     * @param player the player, not null
     * @return The number of unused roads belonging to the current {@link Player}
     * @pre The {@link Player} is in a game.
     * @post None.
     */
    private int getUnusedRoads(@NotNull Player player) {
        return player.getRoads();
    }

    /**
     * Queries how many unused settlements the given {@link Player} has.
     *
     * @return The number of unused settlements belonging to the current {@link Player}
     * @pre The {@link Player} is in a game.
     * @post None.
     */
    private int getUnusedSettlements(@NotNull Player player) {
        return player.getSettlements();
    }

    /**
     * Queries how many unused cities the given {@link Player} has.
     *
     * @return The number of unused cities belonging to the current {@link Player}
     * @pre The {@link Player} is in a game.
     * @post None.
     */
    private int getUnusedCities(@NotNull Player player) {
        return player.getCities();
    }


}
