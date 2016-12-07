package server.client;

import org.jetbrains.annotations.NotNull;
import server.games.IServerManager;
import server.models.*;
import shared.IServer;
import shared.definitions.AIType;
import shared.facades.FacadeManager;
import shared.models.GameAction;
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
 * The server-side implementation of the catan API.
 * Each instance is used for one game (or none)
 * <p>
 * Referred to as the "server facade" in the Phase 3 requirements.
 *
 * @see IServer
 */
public class GameServer implements IServer {
    private FacadeManager facades;
    private int gameId = -1;
    private IServerManager serverManager;
    private int userId;

    public GameServer(IServerManager serverManager, int gameId) {
        setGameId(gameId);
        setServerManager(serverManager);
        if (gameId != -1) {
            setFacades(new FacadeManager(getModel()));
        }
    }

    @Override
    public UserSession login(@NotNull Credentials credentials) throws CredentialNotFoundException, IllegalArgumentException, CommunicationException {
        if (!getServerModel().authenticateUser(credentials.getUsername(), credentials.getPassword())) {
            throw new CredentialNotFoundException("Failed to login - incorrect username or password");
        }
        return getServerModel().newSession(credentials.getUsername());
    }

    @Override
    public UserSession register(@NotNull Credentials credentials) throws CredentialNotFoundException, IllegalArgumentException, CommunicationException {
        RegisterAction action = new RegisterAction(credentials);
        action.setServerModel(getServerModel());
        action.execute();
        getServerManager().getPersistenceProvider().getUserDAO()
                .insert(getServerModel().getUser(credentials.getUsername()));
        return getServerModel().newSession(credentials.getUsername());
    }

    @Override
    public GameInfo[] listOfGames() throws IllegalArgumentException, CommunicationException {
        List<GameInfo> gameList = getServerModel().getGameInfo();
        return gameList.toArray(new GameInfo[gameList.size()]);
    }

    @Override
    public void createGame(@NotNull CreateGameRequest request) throws IllegalArgumentException, CommunicationException {
        CreateGameAction action = new CreateGameAction(request);
        action.setServerModel(getServerModel());
        action.execute();
        getServerManager().getPersistenceProvider().getGameDAO()
                .insert(getServerModel().getGameModel(action.getCreatedGameId()));
    }

    @Override
    public int joinGame(@NotNull JoinGameRequest request) throws IllegalArgumentException, CommunicationException {
        User user = getServerManager().getServerModel().getUser(getUserId());
        if (user == null) {
            throw new IllegalArgumentException("No user with ID " + getUserId() + " exists");
        }
        JoinGameAction action = new JoinGameAction(request, user);
        action.setServerModel(getServerModel());
        action.execute();
        getServerManager().storeCommand(action, getGameId());
        return action.getJoinedGameId();
    }

    @Override
    public void saveGame(@NotNull SaveGameRequest request) throws CommunicationException, IllegalArgumentException {

    }

    @Override
    public void loadGame(@NotNull LoadGameRequest request) throws IllegalArgumentException, CommunicationException {

    }

    @Override
    public ClientModel gameState(int version) throws IllegalArgumentException, CommunicationException {
        if (version != getModel().getVersion()) {
            return getModel();
        }
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
    public AIType[] listAI() throws IllegalArgumentException, CommunicationException {
        return AIType.values();
    }

    @Override
    public void addAI(@NotNull AddAIRequest request) throws IllegalArgumentException, CommunicationException {
        AddAIAction action = new AddAIAction(request, getGameId());
        action.setServerModel(getServerModel());
        action.execute();
        getServerManager().storeCommand(action, getGameId());
    }

    @Override
    public void changeLogLevel(@NotNull ChangeLogLevelRequest request) throws IllegalArgumentException, CommunicationException {

    }

    @NotNull
    private ClientModel executeGameAction(@NotNull GameAction action) {
        action.setFacades(getFacades());
        action.execute();
        getServerManager().storeCommand(action, getGameId());
        return getModel();
    }

    @Override
    public ClientModel sendChat(@NotNull SendChatAction action) throws IllegalArgumentException, CommunicationException {
        return executeGameAction(action);
    }

    @Override
    public ClientModel acceptTrade(@NotNull AcceptTradeAction action) throws IllegalArgumentException, CommunicationException {
        return executeGameAction(action);
    }

    @Override
    public ClientModel discardCards(@NotNull DiscardCardsAction action) throws IllegalArgumentException, CommunicationException {
        return executeGameAction(action);
    }

    @Override
    public ClientModel rollNumber(@NotNull RollNumberAction action) throws IllegalArgumentException, CommunicationException {
        return executeGameAction(action);
    }

    @Override
    public ClientModel buildRoad(@NotNull BuildRoadAction action) throws IllegalArgumentException, CommunicationException {
        return executeGameAction(action);
    }

    @Override
    public ClientModel buildSettlement(@NotNull BuildSettlementAction action) throws IllegalArgumentException, CommunicationException {
        return executeGameAction(action);
    }

    @Override
    public ClientModel buildCity(@NotNull BuildCityAction action) throws IllegalArgumentException, CommunicationException {
        return executeGameAction(action);
    }

    @Override
    public ClientModel offerTrade(@NotNull OfferTradeAction action) throws IllegalArgumentException, CommunicationException {
        return executeGameAction(action);
    }

    @Override
    public ClientModel maritimeTrade(@NotNull MaritimeTradeAction action) throws IllegalArgumentException, CommunicationException {
        return executeGameAction(action);
    }

    @Override
    public ClientModel robPlayer(@NotNull RobPlayerAction action) throws IllegalArgumentException, CommunicationException {
        return executeGameAction(action);
    }

    @Override
    public ClientModel finishTurn(@NotNull FinishMoveAction action) throws IllegalArgumentException, CommunicationException {
        return executeGameAction(action);
    }

    @Override
    public ClientModel buyDevCard(@NotNull BuyDevCardAction action) throws IllegalArgumentException, CommunicationException {
        return executeGameAction(action);
    }

    @Override
    public ClientModel useSoldier(@NotNull SoldierAction action) throws IllegalArgumentException, CommunicationException {
        return executeGameAction(action);
    }

    @Override
    public ClientModel useYearOfPlenty(@NotNull YearofPlentyAction action) throws IllegalArgumentException, CommunicationException {
        return executeGameAction(action);
    }

    @Override
    public ClientModel useRoadBuilding(@NotNull RoadBuildingAction action) throws IllegalArgumentException, CommunicationException {
        return executeGameAction(action);
    }

    @Override
    public ClientModel useMonopoly(@NotNull MonopolyAction action) throws IllegalArgumentException, CommunicationException {
        return executeGameAction(action);
    }

    @Override
    public ClientModel useMonument(@NotNull MonumentAction action) throws IllegalArgumentException, CommunicationException {
        return executeGameAction(action);
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public void setUserId(int id) {
        this.userId = id;
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
        return getServerModel().getGameModel(gameId).getClientModel();
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
        if (gameId != -1) {
            setFacades(new FacadeManager(getModel()));
        }
    }

    public ServerModel getServerModel() {
        return getServerManager().getServerModel();
    }
}
