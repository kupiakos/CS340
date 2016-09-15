package shared;

import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by elijahgk on 9/12/2016.
 * Interface to be implemented in ServerProxy and MockProxy classes.
 * Implements the Catan Server API commands.
 */
public interface IServerProxy {

//Non-Move APIs
    /**
     * Method checks if the login credentials are valid
     * @param username String User Id
     * @param password String that corresponds with user Id
     * @pre username and password are not null
     * @post If the passed­in (username, password) pair is valid,  1. The server returns an HTTP 200 success response with “Success” in the body.  2. The HTTP response headers set the catan.user cookie to contain the identity of the  logged­in player.  The cookie uses ”Path=/”, and its value contains a url­encoded JSON object of  the following form: { “name”: STRING, “password”: STRING, “playerID”: INTEGER }.  For  example, { “name”: “Rick”, “password”: “secret”, “playerID”: 14 }.    If the passed­in (username, password) pair is not valid, or the operation fails for any other  reason,  1. The server returns an HTTP 400 error response, and the body contains an error  message.
     * @return true if user is logged in; false otherwise
     */
    boolean login(String username, String password);

    /**
     * Method register a new user if the user name is not used, logs the caller in to the server, and sets their
     *  catan.user HTTP cookie
     * @param username User ID
     * @param password A password that corresponds with the user ID
     * @param not_used A true/false statement saying if the username is already used
     * @pre username is not null, password is not null, The specified username is not already in use.
     * @post If there is no existing user with the specified username,  1. A new user account has been created with the specified username and password.  2. The server returns an HTTP 200 success response with “Success” in the body.  3. The HTTP response headers set the catan.user cookie to contain the identity of the  logged­in player.  The cookie uses ”Path=/”, and its value contains a url­encoded JSON object of  the following form: { “name”: STRING, “password”: STRING, “playerID”: INTEGER }.  For  example, { “name”: “Rick”, “password”: “secret”, “playerID”: 14 }.  If there is already an existing user with the specified name, or the operation fails for any other  reason,  1. The server returns an HTTP 400 error response, and the body contains an error message.
     * @return True if the username and password are registered; else false
     */
    boolean register(String username, String password, boolean not_used);

    /**
     * Method returns info about all of the current games on the server
     * @pre None
     * @post If the operation succeeds,  1. The server returns an HTTP 200 success response.  2. The body contains a JSON array containing a list of objects that contain information about the server’s games    If the operation fails,  1. The server returns an HTTP 400 error response, and the body contains an error message.
     * @return JSON array containing a list of objects with the server's games if true; else false
     */
    void listOfGames();
    //TODO Change type for listOfGames

    /**
     * Creates a new game on the server
     * @param name String name of the game
     * @param randomTiles boolean
     * @param randomNumbers boolean
     * @param randomPorts boolean
     * @pre name is not null and randomTiles, randomNumbers, and randomPorts contain valid boolean values
     * @post If the operation succeeds,  1. A new game with the specified properties has been created  2. The server returns an HTTP 200 success response.  3. The body contains a JSON object describing the newly created game    If the operation fails,  1. The server returns an HTTP 400 error response, and the body contains an error message.
     * @return true if the game is created; else false
     */
    boolean createGame(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts);

    /**
     * Adds a player to a specific game and sets their catan.game cookie
     * @param logged_in If the player is logged in
     * @param in_game If the player are already included the game
     * @param space_available If there is an open space for a new player
     * @param gameID If the game id is valid
     * @param available_color If the chosen color is valid
     * @pre 1. The user has previously logged in to the server (i.e., they have a valid catan.user HTTP  cookie).   2. The player may join the game because   2.a They are already in the game, OR  2.b There is space in the game to add a new player  3. The specified game ID is valid  4. The specified color is valid (red, green, blue, yellow, puce, brown, white, purple, orange)
     * @post If the operation succeeds,  1. The server returns an HTTP 200 success response with “Success” in the body.  2. The player is in the game with the specified color (i.e. calls to /games/list method will  show the player in the game with the chosen color). 3. The server response includes the “Set­cookie” response header setting the catan.game  HTTP cookie    If the operation fails, 
    1. The server returns an HTTP 400 error response, and the body contains an error  message.
     * @return true if player is added with chosen color and the server response includes the set cookie response
    false if else
     * Note: The 1st, 4th, and 5th pre-conditions need to be true.  Only one of the 2nd and 3rd pre-conditions need to
    be true
     */
    boolean joinGames(boolean logged_in, boolean in_game, boolean space_available, boolean gameID, boolean available_color);

    /**
     * For testing and debugging.  Save a game with a bug report for others to fix
     * @param gameID The game ID; true if valid, false otherwise
     * @param file_name The file to save the game; cannot be null or empty
     * @pre gameID and file_name are valid
     * @post If the operation succeeds,  1. The server returns an HTTP 200 success response with “Success” in the body.  2. The current state of the specified game (including its ID) has been saved to the  specified file name in the server’s saves/ directory    If the operation fails,  1. The server returns an HTTP 400 error response, and the body contains an error message.
     * @return True if the game was saved to a file in the server's directory; false otherwise
     */
    boolean saveGame(boolean gameID, String file_name);

    /**
     * For testing and debugging.  Loads a game that was saved with a bug report for fixing purposes
     * @param file_name The file name exists in the server's directory
     * @pre a previously saved game file with the specified name exists in the server's saves/directory
     * @post If the operation succeeds, 1. The server returns an HTTP 200 success response with “Success” in the body.  2. The game in the specified file has been loaded into the server and its state restored  (including its ID).    If the operation fails,  1. The server returns an HTTP 400 error response, and the body contains an error message.
     * @return True if game is loaded into the server; false otherwise
     */
    boolean loadGame(boolean file_name);

    /**
     * Returns the current state of the game in JSON format
     * @param logged_in User has logged in and joined a game
     * @param version If needed, a version number is needed in the URL; null is valid
     * @pre caller is logged in and joined a game and, if specified, the version number is included as the “version” query parameter in the request URL, and its value is a valid integer.
     * @post If the operation succeeds,  1. The server returns an HTTP 200 success response.  2. The response body contains JSON data  a. The full client model JSON is returned if the caller does not provide a version number, or the provide version number does not match the version on the server  b. “true” (true in double quotes) is returned if the caller provided a version number, and the version number matched the version number on the server    If the operation fails,  1. The server returns an HTTP 400 error response, and the body contains an error message.
     * @return true if file has been loaded; false if not
     */
    boolean gameState(boolean logged_in, int version);

    /**
     * Clears the command history of the current game (not the players)
     * @param logged_in User has logged in and joined a game
     * @pre caller is logged in and joined a game
     * @post If the operation succeeds,  1. The game’s command history has been cleared out  2. The game’s players have NOT been cleared out  3. The server returns an HTTP 200 success response.  4. The body contains the game’s updated client model JSON    If the operation fails,  1. The server returns an HTTP 400 error response, and the body contains an error message.
     * @return True if the command history is cleared; false otherwise
     */
    boolean resetGame(boolean logged_in);

    /**
     * Returns a list of commands that have been executed in the current game.  Used for testing and debugging
     * @param logged_in User has logged in and joined a game
     * @pre caller is logged in and joined a game
     * @post If the operation succeeds,  1. The server returns an HTTP 200 success response.  2. The body contains a JSON array of  commands that have been executed in the game.  This command array is suitable for passing back to the /game/command [POST] method to  restore the state of the game later (after calling /game/reset to revert the game to its initial state).     If the operation fails,  1. The server returns an HTTP 400 error response, and the body contains an error message.
     * @return true if the list of commands is returned; false otherwise
     */
    boolean getCommandsGame(boolean logged_in);

    /**
     * Executes the specified command list in the current game.  Used for testing and debugging
     * @param logged_in User has logged in and joined a game
     * @pre caller is logged in and joined a game
     * @post If the operation succeeds,  1. The passed­in command list has been applied to the game.  2. The server returns an HTTP 200 success response.  3. The body contains the game’s updated client model JSON    If the operation fails,  1. The server returns an HTTP 400 error response, and the body contains an error message.
     * @return True if the command is executed; false otherwise
     */
    boolean postCommandsGame(boolean logged_in);

    /**
     * Returns a list of supported AI player types
     * @pre None
     * @post If the operation succeeds,  1. The server returns an HTTP 200 success response.  2. The body contains a JSON string array enumerating the different types of AI players.  These are the values that may be passed to the /game/addAI method.
     */
    void listAI();

    /**
     * Adds an AI player to the current game. You must login and join a game before calling this method
     * @param logged_in User has logged in and joined a game
     * @param space_available There is a spot available for a player or AI to join the game
     * @param AIType a valid AI type
     * @pre caller is logged in and joined a game, there is space for another player, and AIType is valid
     * @post If the operation succeeds,  1. The server returns an HTTP 200 success response with “Success” in the body.  2. A new AI player of the specified type has been added to the current game.  The server  selected a name and color for the player.    If the operation fails,  1. The server returns an HTTP 400 error response, and the body contains an error message.
     * @return true if a new AI is added to the current game; false if not
     */
    boolean addAI(boolean logged_in, boolean space_available /*and a valid AI type*/);

    /**
     * Sets the server's logging level
     * @param LogLevel A valid logging level
     * @pre LogLevel is a valid logging level
     * @post If the operation succeeds,  1. The server returns an HTTP 200 success response with “Success” in the body.  2. The Server is using the specified logging level    If the operation fails,  1. The server returns an HTTP 400 error response, and the body contains an error message.
     * @return True if logging level is changed; false if it is the same
     */
    boolean changeLogLevel(/*enums or other valid logging level types*/);

//Move APIs (Assumed pre-condition for all Move APIs are caller is logged in and joined a game)
    /**
     * Sends a chat message to the other players anytime
     * @param content The message the player wants to send
     * @pre None
     * @post Chat contains your message at the end
     * @return The message that the player wants to send
     */
    String sendChat(String content);

    /**
     * Method considers if player has accepted the offer and then swaps specified resources if true
     * @param willAccept If the player wants to accept the offered trade
     * @pre offered a domestic trade and, to accept the trade, you have the required resources
     * @post  If you accepted, you and the player who offered swap the specified resources, If you declined no resources are exchanged, The trade offer is removed
     * @return True if resources is traded; false if not
     */
    boolean acceptTrade(boolean willAccept);

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

}
