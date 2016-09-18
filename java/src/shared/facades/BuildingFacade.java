package shared.facades;

import shared.locations.EdgeLocation;
import shared.locations.VertexLocation;
import shared.models.game.Player;

/**
 * Created by elijahgk on 9/16/2016.
 * This facade creates an interface to communicate with the underlying building sub-model.
 */
public class BuildingFacade {

    public BuildingFacade(){}

    /**
     * Builds a road for the current {@link Player} at the specified location.
     *
     * @pre canBuildRoad returns true for the current {@link Player} and given location.
     * @post Road is built on game map at specified location.  Current {@link Player} has one fewer unused roads.
     * @param buildLocation  The {@link EdgeLocation} on the map where the road is to be built.
     * @param player The {@link Player} who is building the road.
     * @param isFree  Whether the road is free to build.
     */
    public void buildRoad(EdgeLocation buildLocation, boolean isFree, Player player){}

    /**
     * Builds a settlement for the current {@link Player} at the specified {@link VertexLocation}.
     * @pre canBuildSettlement returns true for the current {@link Player} and the given location.
     * @post Settlement is built at given location.  Current {@link Player} has one fewer unused settlements.
     * @post Current {@link Player} gains one victory point.
     * @param buildLocation  The {@link VertexLocation} on the map where the settlement is to be built.
     * @param isFree  Whether the current {@link Player} gets to build this for free.
     */
    public void buildSettlement(VertexLocation buildLocation, boolean isFree, Player player){}

    /**
     * Builds a city for the current {@link Player} at the specified {@link VertexLocation}.
     * @pre canBuildCity returns true for the current {@link Player} and the given location
     * @post City is built at given {@link VertexLocation}.  Current {@link Player} has one fewer unused cities and one
     * @post more unused settlements.
     * @post Current {@link Player} gains one victory point.
     * @param buildLocation The {@link VertexLocation} on the map where the city is to be built.
     */
    public void buildCity(VertexLocation buildLocation, Player player){}

    /**
     * Check to see if the current {@link Player} is able to build a road.
     * @pre {@link Player} is currently in a game.
     * @post None.
     * @param buildLocation  The {@link EdgeLocation} where the {@link Player} wants to build a road.
     * @param isFree  Whether the current {@link Player} gets to build this for free.
     * @return True if the player can build a road according to the rules of Catan; false otherwise.
     */
    public boolean canBuildRoad(EdgeLocation buildLocation, boolean isFree, Player player){
        return false;
    }

    /**
     * Check to see if the current {@link Player} is able to build a settlement.
     * @pre {@link Player} is currently in a game.
     * @post None.
     * @param buildLocation The {@link VertexLocation} where the {@link Player} wants to build a settlement.
     * @param isFree  Whether the current {@link Player} gets to build this for free.
     * @return True if the {@link Player} can build a settlement according to the rules of Catan; false otherwise.
     */
    public boolean canBuildSettlement(VertexLocation buildLocation, boolean isFree, Player player){
        return false;
    }

    /**
     * Check to see if the current {@link Player} is able to build a settlement.
     * @pre {@link Player} is currently in a game.
     * @post None.
     * @param buildLocation The {@link VertexLocation} where the {@link Player} wants to build a city.
     * @return True if the {@link Player} can build a city according to the rules of Catan; false otherwise.
     */
    public boolean canBuildCity(VertexLocation buildLocation, Player player){
        return false;
    }

    /**
     * Get the number of roads a {@link Player} has built.
     * @pre The {@link Player} is currently in a game.
     * @post None.
     * @return The number of roads that the {@link Player} has built in the current game.
     */
    public int getTotalRoadsBuilt(Player player){
        return 0;
    }

    /**
     * Checks to see if a road can be placed on the map at the specified {@link EdgeLocation}.
     * @pre The {@link Player} is in a game.
     * @post None
     * @param buildLocation  {@link EdgeLocation} being queried for road placement.
     * @return  True if the edge is empty and adjacent to a Road/{City}/settlement belonging to current {@link Player}.
     */
    private boolean canPlaceRoad(EdgeLocation buildLocation, Player player){
        return false;
    }

    /**
     * Checks to see if a settlement can be placed on the map at the specified {@link VertexLocation}.
     * @pre The {@link Player} is in a game.
     * @post None
     * @param buildLocation  {@link VertexLocation} being queried for settlement placement.
     * @return  True if the {@link VertexLocation} is empty and adjacent to a road belonging to current {@link Player}.
     */
    private boolean canPlaceSettlement(VertexLocation buildLocation, Player player){
        return false;
    }

    /**
     * Checks to see if a city can be placed on the map at the specified {@link VertexLocation}.
     * @pre The {@link Player} is in a game.
     * @post None
     * @param buildLocation  {@link VertexLocation} being queried for city placement.
     * @return  True if the {@link VertexLocation} has a settlement belonging to the current {@link Player} and adjacent to a road belonging to current {@link Player}.
     */
    private boolean canPlaceCity(VertexLocation buildLocation, Player player){
        return false;
    }

    /**
     * Check to see if current {@link Player} has adequate resources to build a road
     * @pre Current {@link Player} is in a game.
     * @post None.
     * @return  True if current {@link Player} has at least 1 wood and 1 brick; false otherwise.
     */
    private boolean haveRoadResources(Player player){
        return false;
    }

    /**
     * Check to see if current player has adequate resources to build a settlement.
     * @pre Current player is in a game.
     * @post None.
     * @return  True if current {@link Player} has at least 1 brick, 1 grain, 1 wool and 1 wood; false otherwise.
     */
    private boolean haveSettlementResources(Player player){
        return false;
    }

    /**
     * Check to see if current {@link Player} has adequate resources to build a city.
     * @pre Current {@link Player} is in a game.
     * @post None.
     * @return True if current {@link Player} has at least 2 grain and 3 ore; false otherwise.
     */
    private boolean haveCityResources(Player player){
        return false;
    }

    /**
     * Queries how many unused roads the current {@link Player} has.
     * @pre The {@link Player} is in a game.
     * @post None.
     * @return The number of unused roads belonging to the current {@link Player}
     */
    private int getUnusedRoads(Player player){
        return 0;
    }

    /**
     * Queries how many unused settlements the current {@link Player} has.
     * @pre The {@link Player} is in a game.
     * @post None.
     * @return The number of unused settlements belonging to the current {@link Player}
     */
    private int getUnusedSettlements(Player player){
        return 0;
    }

    /**
     * Queries how many unused cities the current {@link Player} has.
     * @pre The {@link Player} is in a game.
     * @post None.
     * @return The number of unused cities belonging to the current {@link Player}
     */
    private int getUnusedCities(Player player){
        return 0;
    }

}
