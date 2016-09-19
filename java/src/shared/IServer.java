package shared;

import com.sun.istack.internal.NotNull;
import shared.exceptions.JoinGameException;
import shared.models.game.AddAIRequest;
import shared.models.game.ClientModel;
import shared.models.games.*;
import shared.models.moves.*;
import shared.models.user.Credentials;
import shared.models.util.ChangeLogLevelRequest;

import javax.naming.CommunicationException;
import javax.security.auth.login.CredentialNotFoundException;
import java.util.List;

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
     * @throws CredentialNotFoundException if the credentials are invalid.
     * @throws CommunicationException      if there is an error contacting the server.
     * @throws IllegalArgumentException    if {@link Credentials} is not valid.
     * @pre The username and password are valid.
     * @post The login operation is successful and the user session is being tracked.
     */
    void login(@NotNull Credentials credentialsObject) throws CredentialNotFoundException, CommunicationException, IllegalArgumentException;

    /**
     * Method register a new user if the user name is not used, logs the caller in to the server, and sets their
     * catan.user HTTP cookie
     *
     * @param credentialsObject The information that needs to be added to the body of the HTTP request.  Contains the username and password of the player.
     * @throws CredentialNotFoundException if the credentials are an invalid format or have already been used.
     * @throws CommunicationException      if there is an error contacting the server.
     * @throws IllegalArgumentException    if {@link Credentials} is not valid.
     * @pre The crediantials are are valid and have not already been used.
     * @post A new user is registered on the server and the user session is being tracked.
     */
    void register(@NotNull Credentials credentialsObject) throws CredentialNotFoundException, CommunicationException, IllegalArgumentException;

    /**
     * Method returns info about all of the current games on the server
     *
     * @return The list of {@link GameInfo} objects that are running on the server.
     * @throws CommunicationException if there is an error contacting the server.
     * @pre None
     * @post None
     */
    List<GameInfo> listOfGames() throws CommunicationException;

    /**
     * Creates a new game on the server
     *
     * @param createGameObject The information that needs to be added to the body of the HTTP request.
     * @throws CommunicationException   If there is an error contacting the server.
     * @throws IllegalArgumentException if {@link CreateGameRequest} is not valid.
     * @pre name is not null and randomTiles, randomNumbers, and randomPorts contain valid boolean values
     * @post A new game is created on the server
     */
    void createGame(@NotNull CreateGameRequest createGameObject) throws CommunicationException, IllegalArgumentException;

    /**
     * Adds a player to a specific game and sets their catan.game cookie
     *
     * @param joinGameObject The information that needs to be added to the body of the HTTP request.
     * @throws CommunicationException if there is an error contacting the server.
     * @throws JoinGameException      if the {@link JoinGameRequest} does not allow the player to join.
     * @pre <ul>
     * <li>The user has previously logged in to the server</li>
     * <li>There is space in the game to add a new player.</li>
     * <li>The specified game ID is valid</li>
     * <li>The specified color is valid</li>
     * </ul>
     * @post The current Player has been added to the specified game with the specified color.
     */
    void joinGame(@NotNull JoinGameRequest joinGameObject) throws CommunicationException, JoinGameException;

    /**
     * For testing and debugging.  Save a game with a bug report for others to fix
     *
     * @param saveGameObject The information that needs to be added to the body of the HTTP request.
     * @throws CommunicationException   if there is an error contacting the server.
     * @throws IllegalArgumentException if {@link SaveGameRequest} does not contain a valid game ID or file name.
     * @pre {@link SaveGameRequest} contains a valid file name and game ID.
     * @post The current state of the specified game (including its ID) has been saved to the  specified file name in the server’s saves/ directory.
     */
    void saveGame(@NotNull SaveGameRequest saveGameObject) throws CommunicationException, IllegalArgumentException;

    /**
     * For testing and debugging.  Loads a game that was saved with a bug report for fixing purposes
     *
     * @param loadGameObject The information that needs to be added to the body of the HTTP request. This includes the file name
     * @throws CommunicationException   if there is an error contacting the server.
     * @throws IllegalArgumentException if {@link LoadGameRequest} does not contain a valid file name to load from.
     * @pre {@link LoadGameRequest} contains a valid file name.
     * @post The game in the specified file has been loaded into the server and its state restored  (including its ID).
     */
    void loadGame(@NotNull LoadGameRequest loadGameObject) throws CommunicationException, IllegalArgumentException;

    /**
     * Returns the current state of the game
     *
     * @param version If needed, a version number is needed in the URL; null is valid
     * @return The current game state, or null if one does not exist
     * @throws CommunicationException   if there is an error contacting the server.
     * @throws IllegalArgumentException if the version number is not valid.
     * @pre <ul>
     * <li>User is logged in and in a game.</li>
     * <li>version is a valid integer</li>
     * </ul>
     * @post None
     */
    ClientModel gameState(int version) throws CommunicationException, IllegalArgumentException;

    /**
     * Clears the command history of the current game (not the players)
     *
     * @return The ClientModel of the current game.
     * @throws CommunicationException if there is an error contacting the server.
     * @pre Caller is logged in and joined a game.
     * @post Command list is cleared
     */
    ClientModel resetGame() throws CommunicationException;

    /**
     * Returns a list of commands that have been executed in the current game.  Used for testing and debugging.
     *
     * @return List of all game commands that have been executed since the setup round of the game.
     * @throws CommunicationException if there is an error contacting the server.
     * @pre Caller is logged in and joined a game.
     * @post None.
     */
    List<String> getCommandsGame() throws CommunicationException;

    /**
     * Executes the specified command list in the current game.  Used for testing and debugging.
     *
     * @param gameCommands The game commands that have been executed in the current game.
     * @return The ClientModel of the current game.
     * @throws CommunicationException   if there is an error contacting the server.
     * @throws IllegalArgumentException if the list of commands is invalid.
     * @pre Caller is logged in and joined a game.
     * @post The passed-in command list has been applied to the game.
     */
    ClientModel postCommandsGame(@NotNull List<String> gameCommands) throws CommunicationException, IllegalArgumentException;

    /**
     * Returns a list of supported AI player types
     *
     * @return A list of the supported AI types.
     * @throws CommunicationException if there is an error communicating with the server.
     * @pre None
     * @post None.
     */
    List<String> listAI() throws CommunicationException;

    /**
     * Adds an AI player to the current game. You must login and join a game before calling this method
     *
     * @param addAIObject The information that needs to be added to the body of the HTTP request.  This contains an AI type
     * @throws CommunicationException   if there is an error communicating with the server.
     * @throws IllegalArgumentException if {@link AddAIRequest} does not contain valid information.
     * @pre Caller is logged in and joined a game, there is space for another player, {@link AddAIRequest} contains a valid AI type.
     * @post A new AI player of the specified type has been added to the current game.
     */
    void addAI(@NotNull AddAIRequest addAIObject) throws CommunicationException, IllegalArgumentException;

    /**
     * Sets the server's logging level
     *
     * @param changeLogLevelObject The information that needs to be added to the body of the HTTP request.  This includes a valid logging value
     * @throws CommunicationException   if there is an error communicating with the server.
     * @throws IllegalArgumentException if {@link ChangeLogLevelRequest} does not contain a valid log level.
     * @pre LogLevel is a valid logging level
     * @post The Server is using the specified logging level.
     */
    void changeLogLevel(@NotNull ChangeLogLevelRequest changeLogLevelObject) throws CommunicationException, IllegalArgumentException;

//Move APIs (Assumed pre-condition for all Move APIs are caller is logged in and joined a game)

    /**
     * Sends a chat message to the other players anytime
     *
     * @param sendChatObject The information that needs to be added to the body of the HTTP request.  This contains a type, the player index, and the content of the chat
     * @return The message that the player wants to send
     * @throws CommunicationException   if there is an error communicating with the server.
     * @throws IllegalArgumentException if {@link SendChatAction} is not valid.
     * @pre None
     * @post Chat contains your message at the end
     */
    String sendChat(@NotNull SendChatAction sendChatObject) throws CommunicationException, IllegalArgumentException;

    /**
     * Method considers if player has accepted the offer and then swaps specified resources if true
     *
     * @param acceptTradeObject The information that needs to be added to the body of the HTTP request.  This includes the player index and whether they are accepting or not
     * @throws CommunicationException   if there is an error contacting the server.
     * @throws IllegalArgumentException if {@link AcceptTradeAction} does not contain valid data.
     * @pre player is offered a domestic trade.
     * @post If you accepted, you and the player who offered swap the specified resources, If you declined no resources are exchanged, The trade offer is removed
     */
    void acceptTrade(@NotNull AcceptTradeAction acceptTradeObject) throws CommunicationException, IllegalArgumentException;

    /**
     * Method that discards cards from a players hand.
     *
     * @param discardCardsObject The information that needs to be added to the body of the HTTP request.  This includes the player index and the cards they are discarding.
     * @throws CommunicationException   if there is an error contacting the server.
     * @throws IllegalArgumentException if the {@link DiscardCardsAction} does not contain valid data.
     * @pre Caller has the cards that he is trying to discard.
     * @post Caller no longer has the cards defined in {@link DiscardCardsAction}.
     */
    void discardCards(@NotNull DiscardCardsAction discardCardsObject) throws CommunicationException, IllegalArgumentException;

    /**
     * Tells the server what number you have rolled
     *
     * @return The ClientModel of the current game.
     * @throws CommunicationException   if there is an error contacting the server.
     * @throws IllegalArgumentException if {@link RollNumberAction} is not valid.
     * @pre It is your turn and the client's model status is 'rolling'
     * @post The client model's status is now in 'Discarding', 'Robbing', or 'Playing'
     */
    ClientModel rollNumber(@NotNull RollNumberAction rollNumberObject) throws CommunicationException, IllegalArgumentException;

    /**
     * Builds a road on the game map if player is able
     *
     * @param buildRoadObject The information that needs to be added to the body of the HTTP request.  This includes a valid location, player index, and whether it is free
     * @return The ClientModel of the current game.
     * @throws CommunicationException   if there is an error contacting the server.
     * @throws IllegalArgumentException if {@link BuildRoadAction} is not valid.
     * @pre caller canBuildRoad. {@link BuildRoadAction} is valid.
     * @post If !free, lose the required resources.  The road is now on the map in the correct location.  Player loses 1 road. Longest road has been applied, if applicable.
     */
    ClientModel buildRoad(@NotNull BuildRoadAction buildRoadObject) throws CommunicationException, IllegalArgumentException;

    /**
     * Builds a settlement on game map if player is able
     *
     * @param buildSettlementObject The information that needs to be added to the body of the HTTP request.  This includes a valid location, player index, and whether the settlement is free
     * @return The ClientModel of the current game.
     * @throws CommunicationException   if there as an error contacting the server.
     * @throws IllegalArgumentException if {@link BuildSettlementAction} is not valid.
     * @pre Caller canBuildSettlement.  {@link BuildSettlementAction} is valid
     * @post Lose required resources (if !free).  The settlement has been placed on specified location.  Player loses 1 settlement.
     */
    ClientModel buildSettlement(@NotNull BuildSettlementAction buildSettlementObject) throws CommunicationException, IllegalArgumentException;

    /**
     * Builds a city on game map if player is able
     *
     * @param buildCityObject The information that needs to be added to the body of the HTTP request.  This includes a valid location and player index.
     * @return The ClientModel of the current game.
     * @throws CommunicationException   if there is an error contacting the server.
     * @throws IllegalArgumentException if {@link BuildCityAction} is not valid.
     * @pre Caller canBuildCity.  {@link BuildCityAction} is valid.
     * @post Lose required resources.  City is placed on specified location.  Player receives 1 settlement back and loses 1 city.
     */
    ClientModel buildCity(@NotNull BuildCityAction buildCityObject) throws CommunicationException, IllegalArgumentException;

    /**
     * Offers cards to trade with other players.  If successful, offer is sent to other player
     *
     * @param offerTradeObject The information that needs to be added to the body of the HTTP request.  This includes the player index, the resources being offered, and the index of the receiving player
     * @return The ClientModel of the current game.
     * @throws CommunicationException   if there is an error contacting the server.
     * @throws IllegalArgumentException if {@link OfferTradeAction} is not valid.
     * @pre Player has the resources they are offering. {@link OfferTradeAction} is valid.
     * @post The trade is offered to the other player (stored in server model).
     */
    ClientModel offerTrade(@NotNull OfferTradeAction offerTradeObject) throws CommunicationException, IllegalArgumentException;

    /**
     * Trades in your resources for resources offered by harbor.
     *
     * @param maritimeTradeObject The information that needs to be added to the body of the HTTP request.  This includes the player index, and , optionally, the ratio, input resource and output resource.
     * @return The ClientModel of the current game.
     * @throws CommunicationException   if there is an error contacting the server.
     * @throws IllegalArgumentException if {@link MaritimeTradeAction} is not valid.
     * @pre Player has resources they are trading in.  For ratios less than 4, you have the correct port for the trade.
     * @post The trade has been executed (resources offered by player are now in bank,  requiested resources are received by player).
     */
    ClientModel maritimeTrade(@NotNull MaritimeTradeAction maritimeTradeObject) throws CommunicationException, IllegalArgumentException;

    /**
     * Player gets to move robber to new location and target another player to rob
     *
     * @param robPlayerObject The information that needs to be added to the body of the HTTP request.  This includes the player index, the vitim index and the new location of the robber.
     * @return The ClientModel of the current game.
     * @throws CommunicationException   if there is an error contacting the server.
     * @throws IllegalArgumentException if {@link RobPlayerAction} is not valid.
     * @pre The robber is not being kept in the same location.  If a player is being robbed, then that player has resources.
     * @post The robber is in the new specified location.  The play being robbed (if any) has given robbing player 1 random resource.
     */
    ClientModel robPlayer(@NotNull RobPlayerAction robPlayerObject) throws CommunicationException, IllegalArgumentException;

    /**
     * This method ends your turn and moves the game to the next player.
     * It also puts your new development card hand into you old hand.
     *
     * @return The ClientModel of the current game.
     * @throws CommunicationException   if there is an error contacting the server.
     * @throws IllegalArgumentException if {@link FinishMoveAction} is not valid.
     * @pre None
     * @post The cards in the newDevHand have been transferred to the oldDevHand
     */
    ClientModel finishTurn(@NotNull FinishMoveAction finishMoveObject) throws CommunicationException, IllegalArgumentException;

    /**
     * Buys a development card from the deck if any are left and if you have enough resources.
     *
     * @return The ClientModel of the current game.
     * @throws CommunicationException   if there was an error contacting the server.
     * @throws IllegalArgumentException if {@link BuyDevCardAction} is not valid.
     * @pre Player has required resources.  There are dev cards left in the bank.
     * @post Player has a new dev card in 1) oldDevHand if monument; in newDevHand otherwise.
     */
    ClientModel buyDevCard(@NotNull BuyDevCardAction buyDevCardObject) throws CommunicationException, IllegalArgumentException;

    /**
     * Player gets to move robber to new location and target another player to rob
     *
     * @param soldierObject The information that needs to be added to the body of the HTTP request.  This includes the player index, the vitim index and the new location of the robber.
     * @return The ClientModel of the current game.
     * @throws CommunicationException   if there is an error contacting the server.
     * @throws IllegalArgumentException if {@link SoldierAction} is not valid.
     * @pre The robber is not being kept in same location.  The player being robbed (if any) has resource cards.
     * @post Robber is moved.  Player being robbed (if any) has given player a resource card at random. Largest army is transferred (if applicable).  Cannot play other dev cards this turn.
     */
    ClientModel useSoldier(@NotNull SoldierAction soldierObject) throws CommunicationException, IllegalArgumentException;

    /**
     * Play the year of plenty card to gain two resources of your choice.
     *
     * @param yearOfPlentyObject The information that needs to be added to the body of the HTTP request.  This includes the player index and the two specified resources.
     * @return The ClientModel of the current game.
     * @throws CommunicationException   if there is an error contacting the server.
     * @throws IllegalArgumentException if {@link YearofPlentyAction} is not valid.
     * @pre The two specified resources are in the bank.
     * @post Player has gained two specified resources.
     */
    ClientModel useYearOfPlenty(@NotNull YearofPlentyAction yearOfPlentyObject) throws CommunicationException, IllegalArgumentException;

    /**
     * Play the road building card to build two new roads, if available
     *
     * @param roadBuildingObject The information that needs to be added to the body of the HTTP request.  This includes the player index and the two edge locations to place the road.
     * @return The ClientModel of the current game.
     * @throws CommunicationException   if there is an error contacting the server.
     * @throws IllegalArgumentException if {@link RoadBuildingAction} is not valid.
     * @pre First road location is connected to one of player's other roads.  Second location is connected as well (can be connected to first road).  Neither road is on water.  Player has two unused roads.
     * @post Play has two fewer unused roads.  Roads are placed at specified location.  Longest road is transferred (if applicable).
     */
    ClientModel useRoadBuilding(@NotNull RoadBuildingAction roadBuildingObject) throws CommunicationException, IllegalArgumentException;

    /**
     * Play the monopoly card to take all of one type of resource from all other players
     *
     * @param monopolyObject The information that needs to be added to the body of the HTTP request.  This includes the player index and the resource type.
     * @return The ClientModel of the current game.
     * @throws CommunicationException   if there is an error contacting the server.
     * @throws IllegalArgumentException if {@link MonopolyAction} is not valid.
     * @pre None
     * @post All other players have given you all of their resource cards of one specified type.
     */
    ClientModel useMonopoly(@NotNull MonopolyAction monopolyObject) throws CommunicationException, IllegalArgumentException;

    /**
     * Play your monument cards to gain victory point and win the game.
     *
     * @param monumentObject The information to be included in the HTTP request.  This includes the player index.
     * @return The ClientModel of the current game.
     * @throws CommunicationException   if there is an error contacting the server.
     * @throws IllegalArgumentException if {@link MonumentAction} is not valid.
     * @pre Player will win after having played all of their monument cards.
     * @post You gain victory point(s).
     */
    ClientModel useMonument(@NotNull MonumentAction monumentObject) throws CommunicationException, IllegalArgumentException;

}
