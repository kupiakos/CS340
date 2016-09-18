package shared.facades;

import shared.definitions.HexType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.models.game.Player;

/**
 * Created by elijahgk on 9/17/2016.
 * This facade creates an interface to communicate with the underlying map sub-model.
 */
public class MapFacade {

    public MapFacade(){}

    /**
     * Sees if a given {@link EdgeLocation} has a road built on it currently.
     * @param edge  The {@link EdgeLocation} that is being queried.
     * @return  True if the {@link EdgeLocation}  has not been built upon; false otherwise.
     */
    public boolean isEdgeEmpty(EdgeLocation edge){
        return false;
    }

    /**
     * Sees if a given {@link VertexLocation} has a settlement or city built on it currently.
     * @param vertex  The {@link VertexLocation} that is being queried.
     * @return True if the{@link EdgeLocation} has not been built upon; false otherwise.
     */
    public boolean isVertexEmpty(VertexLocation vertex){
        return false;
    }

    /**
     * Sees if a given {@link EdgeLocation} has a road built on it that belongs to the given {@link Player}.
     * @param edge The {@link EdgeLocation} that is being queried.
     * @param player  The {@link Player} whose road is being checked for.
     * @return  True if the {@link Player} has a road built on the specified {@link EdgeLocation}.
     */
    public boolean hasRoad(EdgeLocation edge, Player player){
        return false;
    }

    /**
     * Sees if a given {@link VertexLocation} has a settlement built on it that belongs to the given {@link Player}.
     * @param vertex The {@link VertexLocation} that is being queried.
     * @param player  The {@link Player} whose settlement is being checked for.
     * @return  True if the {@link Player} has a settlement built on the specified {@link VertexLocation}.
     */
    public boolean hasSettlement(VertexLocation vertex, Player player){
        return false;
    }

    /**
     * Sees if a given {@link VertexLocation} has a city built on it that belongs to the given {@link Player}.
     * @param vertex The {@link VertexLocation} that is being queried.
     * @param player  The {@link Player} whose city is being checked for.
     * @return  True if the {@link Player} has a city built on the specified {@link VertexLocation}.
     */
    public boolean hasCity(VertexLocation vertex, Player player){
        return false;
    }

    /**
     * Sees if a given {@link HexLocation} is currently occupied by the robber.
     * @param hex  The {@link HexLocation} being queried.
     * @return True if the robber is currently on the specified {@link HexLocation}; false otherwise.
     */
    public boolean hasRobber(HexLocation hex){
        return false;
    }

    /**
     * Sees if a given {@link EdgeLocation} is adjacent a road and/or settlement/city owned by {@link Player}.
     * @param edge  The {@link EdgeLocation} that is being queried.
     * @param player The {@link Player} for whom we are looking for owned roads/buildings adjacent to the {@link EdgeLocation}.
     * @return True if the {@link Player} owns a road/building adjacent to the specified {@link EdgeLocation}.
     */
    public boolean isConnectedEdge(EdgeLocation edge, Player player){
        return false;
    }

    /**
     * Sees if a given {@link VertexLocation} is adjacent a road owned by {@link Player}.
     * @param vertex  The {@link VertexLocation} that is being queried.
     * @param player The {@link Player} for whom we are looking for owned roads adjacent to the {@link VertexLocation}.
     * @return True if the {@link Player} owns a road adjacent to the specified {@link VertexLocation}.
     */
    public boolean isConnectedVertex(VertexLocation vertex, Player player){
        return false;
    }

    /**
     * Returns the {@link HexType} produced by the hex found at the given {@link HexLocation}.
     * @param hex The {@link HexLocation} that is being queried.
     * @return  The {@link HexType} produced at the specified {@link HexLocation}.
     */
    public HexType getHexType(HexLocation hex){
        return null;
    }

}
