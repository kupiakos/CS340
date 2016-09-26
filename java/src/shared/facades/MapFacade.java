package shared.facades;

import com.sun.istack.internal.NotNull;
import shared.definitions.HexType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.models.game.ClientModel;
import shared.models.game.Player;

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
     * Sees if a given {@link EdgeLocation} has a road built on it currently.
     *
     * @param edge The {@link EdgeLocation} that is being queried.
     * @return True if the {@link EdgeLocation}  has not been built upon; false otherwise.
     */
    public boolean isEdgeEmpty(@NotNull EdgeLocation edge) {
        return false;
    }

    /**
     * Sees if a given {@link VertexLocation} has a settlement or city built on it currently.
     *
     * @param vertex The {@link VertexLocation} that is being queried.
     * @return True if the{@link EdgeLocation} has not been built upon; false otherwise.
     */
    public boolean isVertexEmpty(@NotNull VertexLocation vertex) {
        return false;
    }

    /**
     * Sees if a given {@link EdgeLocation} has a road built on it that belongs to the given {@code player}.
     *
     * @param player The {@code player} whose road is being checked for.
     * @param edge   The {@link EdgeLocation} that is being queried.
     * @return True if the {@code player} has a road built on the specified {@link EdgeLocation}.
     */
    public boolean hasRoad(@NotNull Player player, @NotNull EdgeLocation edge) {
        return false;
    }

    /**
     * Sees if a given {@link VertexLocation} has a settlement built on it that belongs to the given {@code player}.
     *
     * @param player The {@code player} whose settlement is being checked for.
     * @param vertex The {@link VertexLocation} that is being queried.
     * @return True if the {@code player} has a settlement built on the specified {@link VertexLocation}.
     */
    public boolean hasSettlement(@NotNull Player player, @NotNull VertexLocation vertex) {
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
        return false;
    }

    /**
     * Sees if a given {@link HexLocation} is currently occupied by the robber.
     *
     * @param hex The {@link HexLocation} being queried.
     * @return True if the robber is currently on the specified {@link HexLocation}; false otherwise.
     */
    public boolean hasRobber(@NotNull HexLocation hex) {
        return false;
    }

    /**
     * Sees if a given {@link EdgeLocation} is adjacent a road and/or settlement/city owned by {@code player}.
     *
     * @param player The {@code player} for whom we are looking for owned roads/buildings adjacent to {@code edge}.
     * @param edge   The {@link EdgeLocation} that is being queried.
     * @return True if the {@code player} owns a road/building adjacent to the specified {@link EdgeLocation}.
     */
    public boolean isConnectedEdge(@NotNull Player player, @NotNull EdgeLocation edge) {
        return false;
    }

    /**
     * Sees if a given {@link VertexLocation} is adjacent a road owned by {@code player}.
     *
     * @param player The {@code player} for whom we are looking for owned roads adjacent to the {@link VertexLocation}.
     * @param vertex The {@link VertexLocation} that is being queried.
     * @return True if the {@code player} owns a road adjacent to the specified {@link VertexLocation}.
     */
    public boolean isConnectedVertex(@NotNull Player player, @NotNull VertexLocation vertex) {
        return false;
    }

    /**
     * Returns the {@link HexType} produced by the hex found at the given {@link HexLocation}.
     *
     * @param hex The {@link HexLocation} that is being queried.
     * @return The {@link HexType} produced at the specified {@link HexLocation}.
     */
    public HexType getHexType(@NotNull HexLocation hex) {
        return null;
    }

}
