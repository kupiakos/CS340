package shared.facades;

import com.sun.istack.internal.NotNull;
import shared.locations.EdgeLocation;
import shared.locations.VertexLocation;
import shared.models.game.ClientModel;
import shared.models.game.Player;

/**
 * This facade creates an interface to communicate with the underlying building sub-model.
 */
public class BuildingFacade extends AbstractFacade {

    /**
     * Constructor. Requires a valid game model to work.
     *
     * @param model the model to use, not null
     * @throws NullPointerException if {@code model} is null
     * @pre {@code model} is a valid {@link ClientModel}.
     * @post This provides valid operations on {@code model}.
     */
    public BuildingFacade(@NotNull ClientModel model) {
        // I now realize the pun
        super(model);
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
     * <li>Current {@link Player} has one fewer unused roads.</li>
     * </ul>
     */
    public void buildRoad(@NotNull Player player, @NotNull EdgeLocation buildLocation, boolean isFree) {
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
    public boolean canBuildRoad(@NotNull Player player, @NotNull EdgeLocation buildLocation, boolean isFree) {
        return false;
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
        return false;
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
        return false;
    }

    /**
     * Get the number of roads a {@link Player} has built.
     *
     * @return The number of roads that the {@link Player} has built in the current game.
     * @pre The {@link Player} is currently in a game.
     * @post None.
     */
    public int getTotalRoadsBuilt(@NotNull Player player) {
        return 0;
    }

    /**
     * Checks to see if a road can be placed on the map at the specified {@link EdgeLocation}.
     *
     * @param buildLocation {@link EdgeLocation} being queried for road placement.
     * @return True if the edge is empty and adjacent to a Road/{City}/settlement belonging to current {@link Player}.
     * @pre The {@link Player} is in a game.
     * @post None
     */
    private boolean canPlaceRoad(@NotNull Player player, @NotNull EdgeLocation buildLocation) {
        return false;
    }

    /**
     * Checks to see if a settlement can be placed on the map at the specified {@link VertexLocation}.
     *
     * @param buildLocation {@link VertexLocation} being queried for settlement placement.
     * @return True if the {@link VertexLocation} is empty and adjacent to a road belonging to current {@link Player}.
     * @pre The {@link Player} is in a game.
     * @post None
     */
    private boolean canPlaceSettlement(@NotNull Player player, @NotNull VertexLocation buildLocation) {
        return false;
    }

    /**
     * Checks to see if a city can be placed on the map at the specified {@link VertexLocation}.
     *
     * @param buildLocation {@link VertexLocation} being queried for city placement.
     * @return True if the {@link VertexLocation} has a settlement belonging to the current {@link Player} and adjacent to a road belonging to current {@link Player}.
     * @pre The {@link Player} is in a game.
     * @post None
     */
    private boolean canPlaceCity(@NotNull Player player, @NotNull VertexLocation buildLocation) {
        return false;
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
        return 0;
    }

    /**
     * Queries how many unused settlements the given {@link Player} has.
     *
     * @return The number of unused settlements belonging to the current {@link Player}
     * @pre The {@link Player} is in a game.
     * @post None.
     */
    private int getUnusedSettlements(@NotNull Player player) {
        return 0;
    }

    /**
     * Queries how many unused cities the given {@link Player} has.
     *
     * @return The number of unused cities belonging to the current {@link Player}
     * @pre The {@link Player} is in a game.
     * @post None.
     */
    private int getUnusedCities(@NotNull Player player) {
        return 0;
    }

}
