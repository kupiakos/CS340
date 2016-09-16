package shared;

import jdk.nashorn.api.scripting.JSObject;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

/**
 * Created by elijahgk on 9/12/2016.
 * Interface to be implemented in ServerProxy and MockProxy classes.
 * Implements the Catan Server API commands.
 */
public interface IServerProxy {

    /**
     * Method that discards cards from a players hand.
     */
    boolean discardCards(/*Hand of discarded cards*/);

    /**
     * Rolls dice.
     * @return The number that was rolled
     */
    int rollNumber();

    /**
     * Builds a road on the game map if player is able
     * @param free  Whether or not the player needs to pay for the road
     * @param roadLocation  Where the road is to be placed
     * @return True if road was built; false otherwise.
     */
    boolean buildRoad(boolean free, EdgeLocation roadLocation);

    /**
     * Builds a settlement on game map if player is able
     * @param free  Whether or not the player needs to pay for the settlement
     * @param settlementLocation  Where the settlement is to be placed
     * @return True if settlement was built; false otherwise
     */
    boolean buildSettlement(boolean free, VertexLocation settlementLocation);

    /**
     * Builds a city on game map if player is able
     * @param cityLocation  Where the city is to be placed
     * @return True if city was built; false otherwise
     */
    boolean buildCity(VertexLocation cityLocation);

    /**
     * Offers cards to trade with other players.  If successful, offer is sent to other player
     * @return True is offer was sent; false otherwise
     */
    boolean offerTrade(/*Resource Hand, PlayerIndex*/);

    /**
     * Trades in your resources for resources offered by harbor.
     * @param ratio  The trade ratio of the harbor being used {2,3,or4}
     * @param inputResource  The resource you are trading in.
     * @param outputResource  The resource being traded to you.
     * @return True if trade was successful, false otherwise.
     */
    boolean maritimeTrade(int ratio, ResourceType inputResource, ResourceType outputResource);

    /**
     * Player gets to move robber to new location and target another player to rob
     * @param newLocation  New location to move robber to.  Cannot be kept on same spot.
     * @return True if robber was moved and player robbed; false otherwise.
     */
    boolean robPlayer(HexLocation newLocation /*PlayerIndex victim*/);

    /**
     * This method ends your turn and moves the game to the next player.
     * It also puts your new development card hand into you old hand.
     */
    void finishTurn();

    /**
     * Buys a development card from the deck if any are left and if you have enough resources.
     * @return  True if card was added to hand; false otherwise.
     */
    boolean buyDevCard();

    /**
     * Player gets to move robber to new location and target another player to rob
     * @param newLocation  New location to move robber to.  Cannot be kept on same spot.
     * @return True if knight card was used; false otherwise.
     */
    boolean useSoldier(HexLocation newLocation /*PlayerIndex victim*/);

    /**
     * Play the year of plenty card to gain two resources of your choice.
     * Resources must be avaiable in the resource bank
     * @param Resource1  First type of resource you want
     * @param Resource2 Second type of resource you want
     * @return True if resources were given to player; false otherwise
     */
    boolean useYearOfPlenty(ResourceType Resource1, ResourceType Resource2);

    /**
     * Play the road building card to build two new roads, if available
     * @param edge1  Location to build first road
     * @param edge2  Location to build second road
     * @return True if roads were built; false otherwise.
     */
    boolean useRoadBuildinng(EdgeLocation edge1, EdgeLocation edge2);

    /**
     * Play the monopoly card to take all of one type of resource from all other players
     * @param resourceType  The desired resource type.
     * @return  True if resource was given to you; false otherwise.
     */
    boolean useMonopoly(ResourceType resourceType);

    /**
     * Play your monument cards to gain victory point and win the game.
     * @return True if victory point was gained; false otherwise.
     */
    boolean useMonument();

    JSObject getModel(String version);
}
