package server.client;

import org.jetbrains.annotations.NotNull;
import server.games.IServerManager;
import shared.IServer;
import shared.facades.FacadeManager;
import shared.models.game.AddAIRequest;
import shared.models.game.ClientModel;
import shared.models.games.*;
import shared.models.moves.*;
import shared.models.user.Credentials;
import shared.models.util.ChangeLogLevelRequest;

import javax.naming.CommunicationException;
import javax.security.auth.login.CredentialNotFoundException;

/**
 * The server-side implementation of the catan API.
 * Each instance is used for one game (or none)
 * <p>
 * Referred to as the "server facade" in the Phase 3 requirements.
 *
 * @see IServer
 */
public class GameServer implements IServer {
    private FacadeManager facades;
    private int gameId;
    private IServerManager serverManager;

    public GameServer(IServerManager serverManager, int gameId) {
        setServerManager(serverManager);
        setGameId(gameId);
    }

    @Override
    public void login(@NotNull Credentials credentials) throws CredentialNotFoundException, IllegalArgumentException, CommunicationException {

    }

    @Override
    public void register(@NotNull Credentials credentials) throws CredentialNotFoundException, IllegalArgumentException, CommunicationException {

    }

    @Override
    public GameInfo[] listOfGames() throws IllegalArgumentException, CommunicationException {
        return new GameInfo[0];
    }

    @Override
    public void createGame(@NotNull CreateGameRequest request) throws IllegalArgumentException, CommunicationException {

    }

    @Override
    public void joinGame(@NotNull JoinGameRequest request) throws IllegalArgumentException, CommunicationException {

    }

    @Override
    public void saveGame(@NotNull SaveGameRequest request) throws CommunicationException, IllegalArgumentException {

    }

    @Override
    public void loadGame(@NotNull LoadGameRequest request) throws IllegalArgumentException, CommunicationException {

    }

    @Override
    public ClientModel gameState(int version) throws IllegalArgumentException, CommunicationException {
        return null;
    }

    @Override
    public ClientModel resetGame() throws IllegalArgumentException, CommunicationException {
        return null;
    }

    @Override
    public String[] getCommandsGame() throws IllegalArgumentException, CommunicationException {
        return new String[0];
    }

    @Override
    public ClientModel postCommandsGame(@NotNull String[] gameCommands) throws IllegalArgumentException, CommunicationException {
        return null;
    }

    @Override
    public String[] listAI() throws IllegalArgumentException, CommunicationException {
        return new String[0];
    }

    @Override
    public void addAI(@NotNull AddAIRequest request) throws IllegalArgumentException, CommunicationException {

    }

    @Override
    public void changeLogLevel(@NotNull ChangeLogLevelRequest request) throws IllegalArgumentException, CommunicationException {

    }

    @Override
    public ClientModel sendChat(@NotNull SendChatAction action) throws IllegalArgumentException, CommunicationException {
        return null;
    }

    @Override
    public ClientModel acceptTrade(@NotNull AcceptTradeAction action) throws IllegalArgumentException, CommunicationException {
        return null;
    }

    @Override
    public ClientModel discardCards(@NotNull DiscardCardsAction action) throws IllegalArgumentException, CommunicationException {
        return null;
    }

    @Override
    public ClientModel rollNumber(@NotNull RollNumberAction action) throws IllegalArgumentException, CommunicationException {
        return null;
    }

    @Override
    public ClientModel buildRoad(@NotNull BuildRoadAction action) throws IllegalArgumentException, CommunicationException {
//        action.execute(getModel());
        // Add action to list of commands
        return getModel();
    }

    @Override
    public ClientModel buildSettlement(@NotNull BuildSettlementAction action) throws IllegalArgumentException, CommunicationException {
        return null;
    }

    @Override
    public ClientModel buildCity(@NotNull BuildCityAction action) throws IllegalArgumentException, CommunicationException {
        return null;
    }

    @Override
    public ClientModel offerTrade(@NotNull OfferTradeAction action) throws IllegalArgumentException, CommunicationException {
        return null;
    }

    @Override
    public ClientModel maritimeTrade(@NotNull MaritimeTradeAction action) throws IllegalArgumentException, CommunicationException {
        return null;
    }

    @Override
    public ClientModel robPlayer(@NotNull RobPlayerAction action) throws IllegalArgumentException, CommunicationException {
        return null;
    }

    @Override
    public ClientModel finishTurn(@NotNull FinishMoveAction action) throws IllegalArgumentException, CommunicationException {
        return null;
    }

    @Override
    public ClientModel buyDevCard(@NotNull BuyDevCardAction action) throws IllegalArgumentException, CommunicationException {
        return null;
    }

    @Override
    public ClientModel useSoldier(@NotNull SoldierAction action) throws IllegalArgumentException, CommunicationException {
        return null;
    }

    @Override
    public ClientModel useYearOfPlenty(@NotNull YearofPlentyAction action) throws IllegalArgumentException, CommunicationException {
        return null;
    }

    @Override
    public ClientModel useRoadBuilding(@NotNull RoadBuildingAction action) throws IllegalArgumentException, CommunicationException {
        return null;
    }

    @Override
    public ClientModel useMonopoly(@NotNull MonopolyAction action) throws IllegalArgumentException, CommunicationException {
        return null;
    }

    @Override
    public ClientModel useMonument(@NotNull MonumentAction action) throws IllegalArgumentException, CommunicationException {
        return null;
    }

    /**
     * Get the model for the current game
     *
     * @return the model this instance should use for the current game
     */
    @NotNull
    private ClientModel getModel() {
        if (getGameId() == -1) {
            throw new IllegalArgumentException("User must have joined a game");
        }
        // TODO: Fix
        return new ClientModel();
    }

    public FacadeManager getFacades() {
        return facades;
    }

    public void setFacades(FacadeManager facades) {
        this.facades = facades;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public IServerManager getServerManager() {
        return serverManager;
    }

    public void setServerManager(IServerManager serverManager) {
        this.serverManager = serverManager;
    }
}
