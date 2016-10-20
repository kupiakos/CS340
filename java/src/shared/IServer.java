package shared;

import org.jetbrains.annotations.NotNull;
import shared.exceptions.JoinGameException;
import shared.models.game.AddAIRequest;
import shared.models.game.ClientModel;
import shared.models.games.*;
import shared.models.moves.*;
import shared.models.user.Credentials;
import shared.models.util.ChangeLogLevelRequest;

import javax.naming.CommunicationException;
import javax.security.auth.login.CredentialNotFoundException;

/**
 * Created by elijahgk on 9/12/2016.
 * Interface to be implemented in ServerProxy and MockProxy classes.
 * Implements the Catan Server API commands.
 */
public interface IServer {


//Non-Move APIs

    /**
     * Method checks if the login credentials are valid.  Throws exception if there are any problems or conflicts.
     *
     * @param credentialsObject The information that needs to be added to the body of the HTTP request. Contains the username and password of the player.
     * @pre The username and password are valid.
     * @post The login operation is successful and the user session is being tracked.
     */
    void login(@NotNull Credentials credentialsObject) throws CredentialNotFoundException, IllegalArgumentException, CommunicationException;

    /**
     * Method register a new user if the user name is not used, logs the caller in to the server, and sets their
     * catan.user HTTP cookie
     *
     * @param credentialsObject The information that needs to be added to the body of the HTTP request.  Contains the username and password of the player.
     * @pre The crediantials are are valid and have not already been used.
     * @post A new user is registered on the server and the user session is being tracked.
     */
    void register(@NotNull Credentials credentialsObject) throws CredentialNotFoundException, IllegalArgumentException, CommunicationException;

    /**
     * Method returns info about all of the current games on the server
     *
     * @return The list of {@link GameInfo} objects that are running on the server.
     * @pre None
     * @post None
     */
    GameInfo[] listOfGames() throws IllegalArgumentException, CommunicationException;

    /**
     * Creates a new game on the server
     *
     * @param createGameObject The information that needs to be added to the body of the HTTP request.
     * @pre name is not null and randomTiles, randomNumbers, and randomPorts contain valid boolean values
     * @post A new game is created on the server
     */
    void createGame(@NotNull CreateGameRequest createGameObject) throws IllegalArgumentException, CommunicationException;

    /**
     * Adds a player to a specific game and sets their catan.game cookie
     *
     * @param joinGameObject The information that needs to be added to the body of the HTTP request.
     * @throws JoinGameException if the {@link JoinGameRequest} does not allow the player to join.
     * @pre <ul>
     * <li>The user has previously logged in to the server</li>
     * <li>There is space in the game to add a new player.</li>
     * <li>The specified game ID is valid</li>
     * <li>The specified color is valid</li>
     * </ul>
     * @post The current Player has been added to the specified game with the specified color.
     */
    void joinGame(@NotNull JoinGameRequest joinGameObject) throws IllegalArgumentException, CommunicationException;

    /**
     * For testing and debugging.  Save a game with a bug report for others to fix
     *
     * @param saveGameObject The information that needs to be added to the body of the HTTP request.
     * @pre {@link SaveGameRequest} contains a valid file name and game ID.
     * @post The current state of the specified game (including its ID) has been saved to the  specified file name in the server’s saves/ directory.
     */
    void saveGame(@NotNull SaveGameRequest saveGameObject) throws CommunicationException, IllegalArgumentException;

    /**
     * For testing and debugging.  Loads a game that was saved with a bug report for fixing purposes
     *
     * @param loadGameObject The information that needs to be added to the body of the HTTP request. This includes the file name
     * @pre {@link LoadGameRequest} contains a valid file name.
     * @post The game in the specified file has been loaded into the server and its state restored  (including its ID).
     */
    void loadGame(@NotNull LoadGameRequest loadGameObject) throws IllegalArgumentException, CommunicationException;

    /**
     * Returns the current state of the game
     *
     * @param version If needed, a version number is needed in the URL; null is valid
     * @return The current game state, or null if one does not exist
     * @pre <ul>
     * <li>User is logged in and in a game.</li>
     * <li>version is a valid integer</li>
     * </ul>
     * @post None
     */
    ClientModel gameState(int version) throws IllegalArgumentException, CommunicationException;

    /**
     * Clears the command history of the current game (not the players)
     *
     * @return The ClientModel of the current game.
     * @pre Caller is logged in and joined a game.
     * @post Command list is cleared
     */
    ClientModel resetGame() throws IllegalArgumentException, CommunicationException;

    /**
     * Returns a list of commands that have been executed in the current game.  Used for testing and debugging.
     *
     * @return List of all game commands that have been executed since the setup round of the game.
     * @pre Caller is logged in and joined a game.
     * @post None.
     */
    String[] getCommandsGame() throws IllegalArgumentException, CommunicationException;

    /**
     * Executes the specified command list in the current game.  Used for testing and debugging.
     *
     * @param gameCommands The game commands that have been executed in the current game.
     * @return The ClientModel of the current game.
     * @pre Caller is logged in and joined a game.
     * @post The passed-in command list has been applied to the game.
     */
    ClientModel postCommandsGame(@NotNull String[] gameCommands) throws IllegalArgumentException, CommunicationException;

    /**
     * Returns a list of supported AI player types
     *
     * @return A list of the supported AI types.
     * @pre None
     * @post None.
     */
    String[] listAI() throws IllegalArgumentException, CommunicationException;

    /**
     * Adds an AI player to the current game. You must login and join a game before calling this method
     *
     * @param addAIObject The information that needs to be added to the body of the HTTP request.  This contains an AI type
     * @pre Caller is logged in and joined a game, there is space for another player, {@link AddAIRequest} contains a valid AI type.
     * @post A new AI player of the specified type has been added to the current game.
     */
    void addAI(@NotNull AddAIRequest addAIObject) throws IllegalArgumentException, CommunicationException;

    /**
     * Sets the server's logging level
     *
     * @param changeLogLevelObject The information that needs to be added to the body of the HTTP request.  This includes a valid logging value
     * @pre LogLevel is a valid logging level
     * @post The Server is using the specified logging level.
     */
    void changeLogLevel(@NotNull ChangeLogLevelRequest changeLogLevelObject) throws IllegalArgumentException, CommunicationException;

//Move APIs (Assumed pre-condition for all Move APIs are caller is logged in and joined a game)

    /**
     * Sends a chat message to the other players anytime
     *
     * @param sendChatObject The information that needs to be added to the body of the HTTP request.  This contains a type, the player index, and the content of the chat
     * @return The ClientModel of the current game.
     * @pre None
     * @post Chat contains your message at the end
     */
    ClientModel sendChat(@NotNull SendChatAction sendChatObject) throws IllegalArgumentException, CommunicationException;

    /**
     * Method considers if player has accepted the offer and then swaps specified resources if true
     *
     * @param acceptTradeObject The information that needs to be added to the body of the HTTP request.  This includes the player index and whether they are accepting or not
     * @return The ClientModel of the current game.
     * @pre player is offered a domestic trade.
     * @post If you accepted, you and the player who offered swap the specified resources, If you declined no resources are exchanged, The trade offer is removed
     */
    ClientModel acceptTrade(@NotNull AcceptTradeAction acceptTradeObject) throws IllegalArgumentException, CommunicationException;

    /**
     * Method that discards cards from a players hand.
     *
     * @param discardCardsObject The information that needs to be added to the body of the HTTP request.  This includes the player index and the cards they are discarding.
     * @return The ClientModel of the current game.
     * @pre Caller has the cards that he is trying to discard.
     * @post Caller no longer has the cards defined in {@link DiscardCardsAction}.
     */
    ClientModel discardCards(@NotNull DiscardCardsAction discardCardsObject) throws IllegalArgumentException, CommunicationException;

    /**
     * Tells the server what number you have rolled
     *
     * @return The ClientModel of the current game.
     * @pre It is your turn and the client's model status is 'rolling'
     * @post The client model's status is now in 'Discarding', 'Robbing', or 'Playing'
     */
    ClientModel rollNumber(@NotNull RollNumberAction rollNumberObject) throws IllegalArgumentException, CommunicationException;

    /**
     * Builds a road on the game map if player is able
     *
     * @param buildRoadObject The information that needs to be added to the body of the HTTP request.  This includes a valid location, player index, and whether it is free
     * @return The ClientModel of the current game.
     * @pre caller canBuildRoad. {@link BuildRoadAction} is valid.
     * @post If !free, lose the required resources.  The road is now on the map in the correct location.  Player loses 1 road. Longest road has been applied, if applicable.
     */
    ClientModel buildRoad(@NotNull BuildRoadAction buildRoadObject) throws IllegalArgumentException, CommunicationException;

    /**
     * Builds a settlement on game map if player is able
     *
     * @param buildSettlementObject The information that needs to be added to the body of the HTTP request.  This includes a valid location, player index, and whether the settlement is free
     * @return The ClientModel of the current game.
     * @pre Caller canBuildSettlement.  {@link BuildSettlementAction} is valid
     * @post Lose required resources (if !free).  The settlement has been placed on specified location.  Player loses 1 settlement.
     */
    ClientModel buildSettlement(@NotNull BuildSettlementAction buildSettlementObject) throws IllegalArgumentException, CommunicationException;

    /**
     * Builds a city on game map if player is able
     *
     * @param buildCityObject The information that needs to be added to the body of the HTTP request.  This includes a valid location and player index.
     * @return The ClientModel of the current game.
     * @pre Caller canBuildCity.  {@link BuildCityAction} is valid.
     * @post Lose required resources.  City is placed on specified location.  Player receives 1 settlement back and loses 1 city.
     */
    ClientModel buildCity(@NotNull BuildCityAction buildCityObject) throws IllegalArgumentException, CommunicationException;

    /**
     * Offers cards to trade with other players.  If successful, offer is sent to other player
     *
     * @param offerTradeObject The information that needs to be added to the body of the HTTP request.  This includes the player index, the resources being offered, and the index of the receiving player
     * @return The ClientModel of the current game.
     * @pre Player has the resources they are offering. {@link OfferTradeAction} is valid.
     * @post The trade is offered to the other player (stored in server model).
     */
    ClientModel offerTrade(@NotNull OfferTradeAction offerTradeObject) throws IllegalArgumentException, CommunicationException;

    /**
     * Trades in your resources for resources offered by harbor.
     *
     * @param maritimeTradeObject The information that needs to be added to the body of the HTTP request.  This includes the player index, and , optionally, the ratio, input resource and output resource.
     * @return The ClientModel of the current game.
     * @pre Player has resources they are trading in.  For ratios less than 4, you have the correct port for the trade.
     * @post The trade has been executed (resources offered by player are now in bank,  requiested resources are received by player).
     */
    ClientModel maritimeTrade(@NotNull MaritimeTradeAction maritimeTradeObject) throws IllegalArgumentException, CommunicationException;

    /**
     * Player gets to move robber to new location and target another player to rob
     *
     * @param robPlayerObject The information that needs to be added to the body of the HTTP request.  This includes the player index, the vitim index and the new location of the robber.
     * @return The ClientModel of the current game.
     * @pre The robber is not being kept in the same location.  If a player is being robbed, then that player has resources.
     * @post The robber is in the new specified location.  The play being robbed (if any) has given robbing player 1 random resource.
     */
    ClientModel robPlayer(@NotNull RobPlayerAction robPlayerObject) throws IllegalArgumentException, CommunicationException;

    /**
     * This method ends your turn and moves the game to the next player.
     * It also puts your new development card hand into you old hand.
     *
     * @return The ClientModel of the current game.
     * @pre None
     * @post The cards in the newDevHand have been transferred to the oldDevHand
     */
    ClientModel finishTurn(@NotNull FinishMoveAction finishMoveObject) throws IllegalArgumentException, CommunicationException;

    /**
     * Buys a development card from the deck if any are left and if you have enough resources.
     *
     * @return The ClientModel of the current game.
     * @pre Player has required resources.  There are dev cards left in the bank.
     * @post Player has a new dev card in 1) oldDevHand if monument; in newDevHand otherwise.
     */
    ClientModel buyDevCard(@NotNull BuyDevCardAction buyDevCardObject) throws IllegalArgumentException, CommunicationException;

    /**
     * Player gets to move robber to new location and target another player to rob
     *
     * @param soldierObject The information that needs to be added to the body of the HTTP request.  This includes the player index, the vitim index and the new location of the robber.
     * @return The ClientModel of the current game.
     * @pre The robber is not being kept in same location.  The player being robbed (if any) has resource cards.
     * @post Robber is moved.  Player being robbed (if any) has given player a resource card at random. Largest army is transferred (if applicable).  Cannot play other dev cards this turn.
     */
    ClientModel useSoldier(@NotNull SoldierAction soldierObject) throws IllegalArgumentException, CommunicationException;

    /**
     * Play the year of plenty card to gain two resources of your choice.
     *
     * @param yearOfPlentyObject The information that needs to be added to the body of the HTTP request.  This includes the player index and the two specified resources.
     * @return The ClientModel of the current game.
     * @pre The two specified resources are in the bank.
     * @post Player has gained two specified resources.
     */
    ClientModel useYearOfPlenty(@NotNull YearofPlentyAction yearOfPlentyObject) throws IllegalArgumentException, CommunicationException;

    /**
     * Play the road building card to build two new roads, if available
     *
     * @param roadBuildingObject The information that needs to be added to the body of the HTTP request.  This includes the player index and the two edge locations to place the road.
     * @return The ClientModel of the current game.
     * @pre First road location is connected to one of player's other roads.  Second location is connected as well (can be connected to first road).  Neither road is on water.  Player has two unused roads.
     * @post Play has two fewer unused roads.  Roads are placed at specified location.  Longest road is transferred (if applicable).
     */
    ClientModel useRoadBuilding(@NotNull RoadBuildingAction roadBuildingObject) throws IllegalArgumentException, CommunicationException;

    /**
     * Play the monopoly card to take all of one type of resource from all other players
     *
     * @param monopolyObject The information that needs to be added to the body of the HTTP request.  This includes the player index and the resource type.
     * @return The ClientModel of the current game.
     * @pre None
     * @post All other players have given you all of their resource cards of one specified type.
     */
    ClientModel useMonopoly(@NotNull MonopolyAction monopolyObject) throws IllegalArgumentException, CommunicationException;

    /**
     * Play your monument cards to gain victory point and win the game.
     *
     * @param monumentObject The information to be included in the HTTP request.  This includes the player index.
     * @return The ClientModel of the current game.
     * @pre Player will win after having played all of their monument cards.
     * @post You gain victory point(s).
     */
    ClientModel useMonument(@NotNull MonumentAction monumentObject) throws IllegalArgumentException, CommunicationException;

}
