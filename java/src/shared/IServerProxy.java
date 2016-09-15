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
public interface IServerProxy {

    /**
     * Method that discards cards from a players hand.
     * @param discardCardsObject The information that needs to be added to the body of the HTTP request.
     * @return True if cards were discarded; false otherwise.
     */
    boolean discardCards(DiscardCards discardCardsObject);

    /**
     * Rolls dice.
     * @return The number that was rolled
     */
    int rollNumber();

    /**
     * Builds a road on the game map if player is able
     * @param buildRoadObject The information that needs to be added to the body of the HTTP request.
     * @return True if road was built; false otherwise.
     */
    boolean buildRoad(BuildRoad buildRoadObject);

    /**
     * Builds a settlement on game map if player is able
     * @param buildSettlementObject The information that needs to be added to the body of the HTTP request.
     * @return True if settlement was built; false otherwise
     */
    boolean buildSettlement(BuildSettlement buildSettlementObject);

    /**
     * Builds a city on game map if player is able
     * @param buildCityObject The information that needs to be added to the body of the HTTP request.
     * @return True if city was built; false otherwise
     */
    boolean buildCity(BuildCity buildCityObject);

    /**
     * Offers cards to trade with other players.  If successful, offer is sent to other player
     * @param offerTradeObject The information that needs to be added to the body of the HTTP request.
     * @return True is offer was sent; false otherwise
     */
    boolean offerTrade(OfferTrade offerTradeObject);

    /**
     * Trades in your resources for resources offered by harbor.
     * @param maritimeTradeObject The information that needs to be added to the body of the HTTP request.
     * @return True if trade was successful, false otherwise.
     */
    boolean maritimeTrade(MaritimeTrade maritimeTradeObject);

    /**
     * Player gets to move robber to new location and target another player to rob
     * @param robPlayerObject The information that needs to be added to the body of the HTTP request.
     * @return True if robber was moved and player robbed; false otherwise.
     */
    boolean robPlayer(RobPlayer robPlayerObject);

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
     * @param soldierObject The information that needs to be added to the body of the HTTP request.
     * @return True if knight card was used; false otherwise.
     */
    boolean useSoldier(Soldier soldierObject);

    /**
     * Play the year of plenty card to gain two resources of your choice.
     * @param yearOfPlentyObject The information that needs to be added to the body of the HTTP request.
     * @return True if resources were given to player; false otherwise
     */
    boolean useYearOfPlenty(YearOfPlenty yearOfPlentyObject);

    /**
     * Play the road building card to build two new roads, if available
     * @param roadBuildingObject The information that needs to be added to the body of the HTTP request.
     * @return True if roads were built; false otherwise.
     */
    boolean useRoadBuildinng(RoadBuilding roadBuildingObject);

    /**
     * Play the monopoly card to take all of one type of resource from all other players
     * @param monopolyObject The information that needs to be added to the body of the HTTP request.
     * @return  True if resource was given to you; false otherwise.
     */
    boolean useMonopoly(Monopoly monopolyObject);

    /**
     * Play your monument cards to gain victory point and win the game.
     * @return True if victory point was gained; false otherwise.
     */
    boolean useMonument();

}
