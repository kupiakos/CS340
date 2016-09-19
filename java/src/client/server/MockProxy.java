package client.server;

import shared.IServer;
import shared.models.game.AddAIRequest;
import shared.models.game.ClientModel;
import shared.models.games.*;
import shared.models.moves.*;
import shared.models.user.Credentials;
import shared.models.util.ChangeLogLevelRequest;

import java.util.List;

/**
 * Created by elijahgk on 9/12/2016.
 * This class is used for testing purposes by hard coding results for Catan Server API requests.
 */
public class MockProxy implements IServer{

    public MockProxy(){}

    /**
     * Method checks if the login credentials are valid
     *
     * @param credentialsObject The information that needs to be added to the body of the HTTP request.
     * @return true if user is logged in; false otherwise
     * @pre username and password are not null
     * @post If the passed­in (username, password) pair is valid, 
     * @post 2. The HTTP response headers set the catan.user cookie to contain the identity of the  logged­in player.  
     * @post The cookie uses "Path=/", and its value contains a url­encoded JSON object of  the following form: { "name": STRING, "password": STRING, "playerID": INTEGER }.   
     */
    @Override
    public boolean login(Credentials credentialsObject) {
        return false;
    }

    /**
     * Method register a new user if the user name is not used, logs the caller in to the server, and sets their
     * catan.user HTTP cookie
     *
     * @param credentialsObject The information that needs to be added to the body of the HTTP request.
     * @return True if the username and password are registered; else false
     * @pre username is not null, password is not null, The specified username is not already in use.
     * @post If there is no existing user with the specified username,  A new user account has been created with the specified username and password. The HTTP response headers set the catan.user cookie to contain the identity of the  logged­in player.  The cookie uses "Path=/", and its value contains a url­encoded JSON object of  the following form: { "name": STRING, "password": STRING, "playerID": INTEGER }.
     */
    @Override
    public boolean register(Credentials credentialsObject) {
        return false;
    }

    /**
     * Method returns info about all of the current games on the server
     *
     * @pre None 
     * @post None 
     * @return JSON array containing a list of objects with the server's games if true; else false 
     */
    @Override
    public List<GameInfo> listOfGames() {
        return null;
    }

    /**
     * Creates a new game on the server
     *
     * @param createGameObject The information that needs to be added to the body of the HTTP request.
     * @return true if the game is created; else false
     * @pre name is not null and randomTiles, randomNumbers, and randomPorts contain valid boolean values
     * @post If the operation succeeds, A new game with the specified properties has been created 
     * @post The body contains a JSON object describing the newly created game 
     */
    @Override
    public boolean createGame(CreateGameRequest createGameObject) {
        return false;
    }

    /**
     * Adds a player to a specific game and sets their catan.game cookie
     *
     * @param joinGameObject The information that needs to be added to the body of the HTTP request.
     * @return true if player is added with chosen color and the server response includes the set cookie response
     * false if else
     * Note: The 1st, 4th, and 5th pre-conditions need to be true.  Only one of the 2nd and 3rd pre-conditions need to
     * be true
     * @pre 1. The user has previously logged in to the server (i.e., they have a valid catan.user HTTP  cookie).  
     * @pre 2. The player may join the game because   2.a They are already in the game, OR 
     * @pre 2.b There is space in the game to add a new player  3. The specified game ID is valid 
     * @pre 4. The specified color is valid (red, green, blue, yellow, puce, brown, white, purple, orange)
     * @post The player is in the game with the specified color. The server response includes the "Set­cookie" response header setting the catan.game HTTP cookie.
     */
    @Override
    public boolean joinGames(JoinGameRequest joinGameObject) {
        return false;
    }

    /**
     * For testing and debugging.  Save a game with a bug report for others to fix
     *
     * @param saveGameObject The information that needs to be added to the body of the HTTP request.
     * @return True if the game was saved to a file in the server's directory; false otherwise
     * @pre gameID and file_name are valid
     * @posr The current state of the specified game (including its ID) has been saved to the  specified file name in the server’s saves/ directory 
     */
    @Override
    public boolean saveGame(SaveGameRequest saveGameObject) {
        return false;
    }

    /**
     * For testing and debugging.  Loads a game that was saved with a bug report for fixing purposes
     *
     * @param loadGameObject The information that needs to be added to the body of the HTTP request.
     * @return True if game is loaded into the server; false otherwise
     * @pre a previously saved game file with the specified name exists in the server's saves/directory
     * @post The game in the specified file has been loaded into the server and its state restored  (including its ID).   
     */
    @Override
    public boolean loadGame(LoadGameRequest loadGameObject) {
        return false;
    }

    /**
     * Returns the current state of the game in JSON format
     *
     * @param version If needed, a version number is needed in the URL; null is valid
     * @return The new ClientModel.  Null if their is no new client model.
     * @pre caller is logged in and joined a game and, if specified, the version number is included as the "version" 
     * @pre query parameter in the request URL, and its value is a valid integer.
     * @post None
     */
    @Override
    public ClientModel gameState(int version) {
        return null;
    }

    /**
     * Clears the command history of the current game (not the players)
     *
     * @return True if the command history is cleared; false otherwise
     * @pre caller is logged in and joined a game
     * @post If the operation succeeds,  1. The game’s command history has been cleared out 
     * @post 2. The game’s players have NOT been cleared out. The body contains the game’s updated client model JSON
     */
    @Override
    public boolean resetGame() {
        return false;
    }

    /**
     * Returns a list of commands that have been executed in the current game.  Used for testing and debugging
     *
     * @return true if the list of commands is returned; false otherwise
     * @pre caller is logged in and joined a game 
     * @post 2. The body contains a JSON array of  commands that have been executed in the game. 
     * @post This command array is suitable for passing back to the /game/command [POST] method to  restore the state of 
     * @post the game later (after calling /game/reset to revert the game to its initial state).    
     */
    @Override
    public boolean getCommandsGame() {
        return false;
    }

    /**
     * Executes the specified command list in the current game.  Used for testing and debugging
     *
     * @return True if the command is executed; false otherwise
     * @pre caller is logged in and joined a game
     * @post If the operation succeeds, The passed­in command list has been applied to the game. The body contains the game’s updated client model JSON   
     */
    @Override
    public boolean postCommandsGame() {
        return false;
    }

    /**
     * Returns a list of supported AI player types
     *
     * @pre None
     * @post The body contains a JSON string array enumerating the different types of AI players. 
     * @post These are the values that may be passed to the /game/addAI method.
     */
    @Override
    public void listAI() {

    }

    /**
     * Adds an AI player to the current game. You must login and join a game before calling this method
     *
     * @param addAIObject The information that needs to be added to the body of the HTTP request.
     * @return true if a new AI is added to the current game; false if not
     * @pre caller is logged in and joined a game, there is space for another player, and AIType is valid
     * @post A new AI player of the specified type has been added to the current game. Selects a name and color for the player.
     */
    @Override
    public boolean addAI(AddAIRequest addAIObject) {
        return false;
    }

    /**
     * Sets the server's logging level
     *
     * @param changeLogLevelObject The information that needs to be added to the body of the HTTP request.
     * @return True if logging level is changed; false if it is the same
     * @pre LogLevel is a valid logging level
     * @post 2. The Server is using the specified logging level 
     */
    @Override
    public boolean changeLogLevel(ChangeLogLevelRequest changeLogLevelObject) {
        return false;
    }

    /**
     * Sends a chat message to the other players anytime
     *
     * @param sendChatObject The information that needs to be added to the body of the HTTP request.
     * @return The message that the player wants to send
     * @pre None
     * @post Chat contains your message at the end
     */
    @Override
    public String sendChat(SendChatAction sendChatObject) {
        return null;
    }

    /**
     * Method considers if player has accepted the offer and then swaps specified resources if true
     *
     * @param acceptTradeObject The information that needs to be added to the body of the HTTP request.
     * @return True if resources is traded; false if not
     * @pre offered a domestic trade and, to accept the trade, you have the required resources
     * @post If you accepted, you and the player who offered swap the specified resources, If you declined no resources 
     * @post are exchanged, The trade offer is removed
     */
    @Override
    public boolean acceptTrade(AcceptTradeAction acceptTradeObject) {
        return false;
    }

    /**
     * Method that discards cards from a players hand.
     *
     * @param discardCardsObject The information that needs to be added to the body of the HTTP request.
     * @return True if cards were discarded; false otherwise.
     */
    @Override
    public boolean discardCards(DiscardCardsAction discardCardsObject) {
        return false;
    }

    /**
     * Rolls dice.
     *
     * @return The number that was rolled
     * @pre It is your turn and the client's model status is 'rolling'
     * @post The client model's status is now in 'Discarding', 'Robbing', or 'Playing'
     */
    @Override
    public int rollNumber() {
        return 0;
    }

    /**
     * Builds a road on the game map if player is able
     *
     * @param buildRoadObject The information that needs to be added to the body of the HTTP request.
     * @pre The location is open, is connected to a road owned by the player, and is not on the water.
     * @pre In addition, you must have the required resources if it is not free.  If in setup round, must be placed by a
     * @pre settlement owned by the player with no adjacent road
     * @post If !free, lose the required resources.  The road is now on the map in the correct location.  And longest
     * @post road has been applied, if applicable.
     * @return True if road was built; false otherwise.
     */
    @Override
    public boolean buildRoad(BuildRoadAction buildRoadObject) {
        return false;
    }

    /**
     * Builds a settlement on game map if player is able
     *
     * @param buildSettlementObject The information that needs to be added to the body of the HTTP request.
     * @pre Location is open.  Location is not on water.  Location is connected to player's road (unless in setup).
     * @pre Have resources (if !free). Not next to adjacent settlement.
     * @post Lose required resources (if !free).  The settlement has been placed on specified location.
     * @return True if settlement was built; false otherwise
     */
    @Override
    public boolean buildSettlement(BuildSettlementAction buildSettlementObject) {
        return false;
    }

    /**
     * Builds a city on game map if player is able
     *
     * @param buildCityObject The information that needs to be added to the body of the HTTP request.
     * @pre There is currently a settlement belonging to the player where the city is to be built.  Player has required resources.
     * @post Lose required resources.  City is placed on specified location.  Player receives 1 settlement back.
     * @return True if city was built; false otherwise
     */
    @Override
    public boolean buildCity(BuildCityAction buildCityObject) {
        return false;
    }

    /**
     * Offers cards to trade with other players.  If successful, offer is sent to other player
     *
     * @param offerTradeObject The information that needs to be added to the body of the HTTP request.
     * @pre Player has the resources they are offering.
     * @post The trade is offered to the other player (stored in server model)
     * @return True is offer was sent; false otherwise
     */
    @Override
    public boolean offerTrade(OfferTradeAction offerTradeObject) {
        return false;
    }

    /**
     * Trades in your resources for resources offered by harbor.
     *
     * @param maritimeTradeObject The information that needs to be added to the body of the HTTP request.
     * @return True if trade was successful, false otherwise.
     * @pre Player has resources they are trading in.  For ratios less than 4, you have the correct port for the trade.
     * @post The trade has been executed (resources offered by player are now in bank,  requiested resources are received by player).
     */
    @Override
    public boolean maritimeTrade(MaritimeTradeAction maritimeTradeObject) {
        return false;
    }

    /**
     * Player gets to move robber to new location and target another player to rob
     *
     * @param robPlayerObject The information that needs to be added to the body of the HTTP request.
     * @return True if robber was moved and player robbed; false otherwise.
     * @pre The robber is not being kept in the same location.  If a player is being robbed, then that player has resources.
     * @post The robber is in the new specified location.  The play being robbed (if any) has given robbing player 1 random resource.
     */
    @Override
    public boolean robPlayer(RobPlayerAction robPlayerObject) {
        return false;
    }

    /**
     * This method ends your turn and moves the game to the next player.
     * It also puts your new development card hand into you old hand.
     *
     * @param finishMoveObject
     * @pre None
     * @post The cards in the newDevHand have been transferred to the oldDevHand
     */
    @Override
    public void finishTurn(FinishMoveAction finishMoveObject) {

    }

    /**
     * Buys a development card from the deck if any are left and if you have enough resources.
     *
     * @param buyDevCardObject
     * @return True if card was added to hand; false otherwise.
     * @pre Player has required resources.  There are dev cards left in the bank.
     * @post Player has a new dev card in 1) oldDevHand if monument; in newDevHand otherwise.
     */
    @Override
    public boolean buyDevCard(BuyDevCardAction buyDevCardObject) {
        return false;
    }

    /**
     * Player gets to move robber to new location and target another player to rob
     *
     * @param soldierObject The information that needs to be added to the body of the HTTP request.
     * @return True if knight card was used; false otherwise.
     * @pre The robber is not being kept in same location.  The player being robbed (if any) has resource cards.
     * @post Robber is moved.  Player being robbed (if any) has given player a resource card at random. Largest army is
     * @post transferred (if applicable).  Cannot play other dev cards this turn.
     */
    @Override
    public boolean useSoldier(SoldierAction soldierObject) {
        return false;
    }

    /**
     * Play the year of plenty card to gain two resources of your choice.
     *
     * @param yearOfPlentyObject The information that needs to be added to the body of the HTTP request.
     * @return True if resources were given to player; false otherwise
     * @pre The two specified resources are in the bank.
     * @post Player has gained two specified resources.
     */
    @Override
    public boolean useYearOfPlenty(YearofPlentyAction yearOfPlentyObject) {
        return false;
    }

    /**
     * Play the road building card to build two new roads, if available
     *
     * @param roadBuildingObject The information that needs to be added to the body of the HTTP request.
     * @return True if roads were built; false otherwise.
     * @pre First road location is connected to one of player's other roads.  Second location is connected as well
     * @pre (can be connected to first road).  Neither road is on water.  Player has two unused roads.
     * @post Play has two fewer unused roads.  Roads are placed at specified location.  Longest road is transferred (if applicable).
     */
    @Override
    public boolean useRoadBuilding(RoadBuildingAction roadBuildingObject) {
        return false;
    }

    /**
     * Play the monopoly card to take all of one type of resource from all other players
     *
     * @param monopolyObject The information that needs to be added to the body of the HTTP request.
     * @return True if resource was given to you; false otherwise.
     * @pre None
     * @post All other players have given you all of their resource cards of one specified type.
     */
    @Override
    public boolean useMonopoly(MonopolyAction monopolyObject) {
        return false;
    }

    /**
     * Play your monument cards to gain victory point and win the game.
     *
     * @param monumentObject
     * @return True if victory point was gained; false otherwise.
     * @pre Player will win after having played all of their monument cards.
     * @post You gain victory point(s).
     */
    @Override
    public boolean useMonument(MonumentAction monumentObject) {
        return false;
    }
}
