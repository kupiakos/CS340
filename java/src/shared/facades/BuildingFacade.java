package shared.facades;

import shared.locations.EdgeLocation;
import shared.locations.VertexLocation;

/**
 * Created by elijahgk on 9/16/2016.
 * This facade creates an interface to communicate with the underlying building sub-model.
 */
public class BuildingFacade {

    /**
     * Builds a road for the current player at the specified location.
     * @pre canBuildRoad returns true for the current player and given location.
     * @post Road is built on game map at specified location.  Current player has one fewer unused roads.
     * @param buildLocation  The edge on the map where the road is to be built.
     * @param isFree  Whether the current player gets to build this for free.
     */
    public void buildRoad(EdgeLocation buildLocation, boolean isFree){}

    /**
     * Builds a settlement for the current player at the specified location.
     * @pre canBuildSettlement returns true for the current player and the given location.
     * @post Settlement is built at given location.  Current Player has one fewer unused settlements.  Current player gains one victory point.
     * @param buildLocation  The hex vertex on the map where the settlement is to be built.
     * @param isFree  Whether the current player gets to build this for free.
     */
    public void buildSettlement(VertexLocation buildLocation, boolean isFree){}

    /**
     * Builds a city for the current player at the specified location.
     * @pre canBuildCity returns true for the current player and the given location
     * @post City is built at given location.  Current player has one fewer unused cities and one more unused settlements.  Current player gains one victory point.
     * @param buildLocation The hex vertex on the map where the city is to be built.
     */
    public void buildCity(VertexLocation buildLocation){}

    /**
     * Check to see if the current player is able to build a road
     * @pre Player is currently in a game.
     * @post None.
     * @param buildLocation  The edge where the player wants to build a road.
     * @param isFree  Whether the current player gets to build this for free.
     * @return True if the player can build a road according to the rules of Catan; false otherwise.
     */
    public boolean canBuildRoad(EdgeLocation buildLocation, boolean isFree){
        return false;
    }

    /**
     * Check to see if the current player is able to build a settlement.
     * @pre Player is currently in a game.
     * @post None.
     * @param buildLocation The vertex where the player wants to build a settlement.
     * @param isFree  Whether the current player gets to build this for free.
     * @return True if the player can build a settlement according to the rules of Catan; false otherwise.
     */
    public boolean canBuildSettlement(VertexLocation buildLocation, boolean isFree){
        return false;
    }

    /**
     * Check to see if the current player is able to build a settlement.
     * @pre Player is currently in a game.
     * @post None.
     * @param buildLocation The vertex where the player wants to build a city.
     * @return True if the payer can build a city according to the rules of Catan; false otherwise.
     */
    public boolean canBuildCity(VertexLocation buildLocation){
        return false;
    }

    /**
     * Get the number of roads a player has built.
     * @pre The player is currently in a game.
     * @post None
     * @return The number of roads that the player has built in the current game.
     */
    public int getTotalRoadsBuilt(){
        return 0;
    }

    /**
     * Checks to see if a road can be placed on the map at the specified location.
     * @pre The player is in a game.
     * @post None
     * @param buildLocation  Location being queried for road placement.
     * @return  True if the edge is empty and adjacent to a road/city/settlement belonging to current player.
     */
    private boolean canPlaceRoad(EdgeLocation buildLocation){
        return false;
    }

    /**
     * Checks to see if a settlement can be placed on the map at the specified location.
     * @pre The player is in a game.
     * @post None
     * @param buildLocation  Location being queried for settlement placement.
     * @return  True if the vertex is empty and adjacent to a road belonging to current player.
     */
    private boolean canPlaceSettlement(VertexLocation buildLocation){
        return false;
    }

    /**
     * Checks to see if a city can be placed on the map at the specified location.
     * @pre The player is in a game.
     * @post None
     * @param buildLocation  Location being queried for city placement.
     * @return  True if the vertex has a settlement belonging to the current player and adjacent to a road belonging to current player.
     */
    private boolean canPlaceCity(VertexLocation buildLocation){
        return false;
    }

    /**
     * Check to see if current player has adequate resources to build a road
     * @pre Current player is in a game.
     * @post None.
     * @return  True if current player has at least 1 wood and 1 brick; false otherwise.
     */
    private boolean haveRoadResources(){
        return false;
    }

    /**
     * Check to see if current player has adequate resources to build a settlement.
     * @pre Current player is in a game.
     * @post None.
     * @return  True if current player has at least 1 brick, 1 grain, 1 wool and 1 wood; false otherwise.
     */
    private boolean haveSettlementResources(){
        return false;
    }

    /**
     * Check to see if current player has adequate resources to build a city.
     * @pre Current player is in a game.
     * @post None.
     * @return True if current player has at least 2 grain and three ore; false otherwise.
     */
    private boolean haveCityResources(){
        return false;
    }

    /**
     * Queries how many unused roads the current player has.
     * @pre The player is in a game.
     * @post None.
     * @return The number of unused roads belonging to the current player
     */
    private int getUnusedRoads(){
        return 0;
    }

    /**
     * Queries how many unused settlements the current player has.
     * @pre The player is in a game.
     * @post None.
     * @return The number of unused settlements belonging to the current player
     */
    private int getUnusedSettlements(){
        return 0;
    }

    /**
     * Queries how many unused cities the current player has.
     * @pre The player is in a game.
     * @post None.
     * @return The number of unused cities belonging to the current player
     */
    private int getUnusedCities(){
        return 0;
    }

}
