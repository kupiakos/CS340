package shared;

import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.models.*;

/**
 * Created by elijahgk on 9/12/2016.
 * Interface to be implemented in ServerProxy and MockProxy classes.
 * Implements the Catan Server API commands.
 */
public interface IServerProxy{

    /**
     * Method that discards cards from a players hand.
     * @param discardCardsObject The information that needs to be added to the body of the HTTP request.
     * @return True if cards were discarded; false otherwise.
     */
    boolean discardCards(DiscardCards discardCardsObject);

    /**
     * Rolls dice.
     * @pre  It is your turn and the client's model status is 'rolling'
     * @post  The client model's status is now in 'Discarding', 'Robbing', or 'Playing'
     * @return The number that was rolled
     */
    int rollNumber();

    /**
     * Builds a road on the game map if player is able
     * @pre The location is open, is connected to a road owned by the player, and is not on the water.
     * @pre In addition, you must have the required resources if it is not free.  If in setup round, must be placed by a settlement owned by the player with no adjacent road
     * @post  If !free, lose the required resources.  The road is now on the map in the correct location.  And longest road has been applied, if applicable.
     * @param buildRoadObject The information that needs to be added to the body of the HTTP request.
     * @return True if road was built; false otherwise.
     */
    boolean buildRoad(BuildRoad buildRoadObject);

    /**
     * Builds a settlement on game map if player is able
     * @pre Location is open.  Location is not on water.  Location is connected to player's road (unless in setup).  Have resources (if !free). Not next to adjacent settlement.
     * @post  Lose required resources (if !free).  The settlement has been placed on specified location.
     * @param buildSettlementObject The information that needs to be added to the body of the HTTP request.
     * @return True if settlement was built; false otherwise
     */
    boolean buildSettlement(BuildSettlement buildSettlementObject);

    /**
     * Builds a city on game map if player is able
     * @pre There is currently a settlement belonging to the player where the city is to be built.  Player has required resources.
     * @post  Lose required resources.  City is placed on specified location.  Player receives 1 settlement back.
     * @param buildCityObject The information that needs to be added to the body of the HTTP request.
     * @return True if city was built; false otherwise
     */
    boolean buildCity(BuildCity buildCityObject);

    /**
     * Offers cards to trade with other players.  If successful, offer is sent to other player
     * @pre Player has the resources they are offering.
     * @post  The trade is offered to the other player (stored in server model)
     * @param offerTradeObject The information that needs to be added to the body of the HTTP request.
     * @return True is offer was sent; false otherwise
     */
    boolean offerTrade(OfferTrade offerTradeObject);

    /**
     * Trades in your resources for resources offered by harbor.
     * @pre Player has resources they are trading in.  For ratios less than 4, you have the correct port for the trade.
     * @post The trade has been executed (resources offered by player are now in bank,  requiested resources are received by player).
     * @param maritimeTradeObject The information that needs to be added to the body of the HTTP request.
     * @return True if trade was successful, false otherwise.
     */
    boolean maritimeTrade(MaritimeTrade maritimeTradeObject);

    /**
     * Player gets to move robber to new location and target another player to rob
     * @pre The robber is not being kept in the same location.  If a player is being robbed, then that player has resources.
     * @post The robber is in the new specified location.  The play being robbed (if any) has given robbing player 1 random resource.
     * @param robPlayerObject The information that needs to be added to the body of the HTTP request.
     * @return True if robber was moved and player robbed; false otherwise.
     */
    boolean robPlayer(RobPlayer robPlayerObject);

    /**
     * This method ends your turn and moves the game to the next player.
     * It also puts your new development card hand into you old hand.
     * @pre None
     * @post The cards in the newDevHand have been transferred to the oldDevHand
     */
    void finishTurn();

    /**
     * Buys a development card from the deck if any are left and if you have enough resources.
     * @pre Player has required resources.  There are dev cards left in the bank.
     * @post Player has a new dev card in 1) oldDevHand if monument; in newDevHand otherwise.
     * @return  True if card was added to hand; false otherwise.
     */
    boolean buyDevCard();

    /**
     * Player gets to move robber to new location and target another player to rob
     * @pre The robber is not being kept in same location.  The player being robbed (if any) has resource cards.
     * @post Robber is moved.  Player being robbed (if any) has given player a resource card at random. Largest army is transferred (if applicable).  Cannot play other dev cards this turn.
     * @param soldierObject The information that needs to be added to the body of the HTTP request.
     * @return True if knight card was used; false otherwise.
     */
    boolean useSoldier(Soldier soldierObject);

    /**
     * Play the year of plenty card to gain two resources of your choice.
     * @pre The two specified resources are in the bank.
     * @post Player has gained two specified resources.
     * @param yearOfPlentyObject The information that needs to be added to the body of the HTTP request.
     * @return True if resources were given to player; false otherwise
     */
    boolean useYearOfPlenty(YearOfPlenty yearOfPlentyObject);

    /**
     * Play the road building card to build two new roads, if available
     * @pre First road location is connected to one of player's other roads.  Second location is connected as well (can be connected to first road).  Neither road is on water.  Player has two unused roads.
     * @post Play has two fewer unused roads.  Roads are placed at specified location.  Longest road is transferred (if applicable).
     * @param roadBuildingObject The information that needs to be added to the body of the HTTP request.
     * @return True if roads were built; false otherwise.
     */
    boolean useRoadBuildinng(RoadBuilding roadBuildingObject);

    /**
     * Play the monopoly card to take all of one type of resource from all other players
     * @pre None
     * @post All other players have given you all of their resource cards of one specified type.
     * @param monopolyObject The information that needs to be added to the body of the HTTP request.
     * @return  True if resource was given to you; false otherwise.
     */
    boolean useMonopoly(Monopoly monopolyObject);

    /**
     * Play your monument cards to gain victory point and win the game.
     * @pre Player will win after having played all of their monument cards.
     * @post You gain victory point(s).
     * @return True if victory point was gained; false otherwise.
     */
    boolean useMonument();

}
