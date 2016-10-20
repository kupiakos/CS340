package client.server;

import org.jetbrains.annotations.NotNull;
import shared.IServer;
import shared.models.game.AddAIRequest;
import shared.models.game.ClientModel;
import shared.models.games.*;
import shared.models.moves.*;
import shared.models.user.Credentials;
import shared.models.util.ChangeLogLevelRequest;
import shared.serialization.ModelSerializer;

import javax.naming.CommunicationException;
import javax.security.auth.login.CredentialNotFoundException;

/**
 * Created by elijahgk on 9/12/2016.
 * Implements the IServerProxy Interface.
 * Receives calls from client controllers.  Calls methods in clientCommunicator to communicate with actual server.
 */
public class ServerProxy implements IServer {

    private IClientCommunicator cc;

    public ServerProxy() {
        cc = ClientCommunicator.getSingleton();
    }

    public void setMockCC(ClientModel cm) {
        cc = MockCC.initialize(cm);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void login(@NotNull Credentials credentialsObject) throws CredentialNotFoundException, IllegalArgumentException, CommunicationException {
        String requestBody = ModelSerializer.getInstance().toJson(credentialsObject, Credentials.class);
        try {
            cc.sendHTTPRequest("/user/login", requestBody, "POST");
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("400")) {
                throw new CredentialNotFoundException("Username or password were not recognized.");
            } else {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void register(@NotNull Credentials credentialsObject) throws CredentialNotFoundException, IllegalArgumentException, CommunicationException {
        String requestBody = ModelSerializer.getInstance().toJson(credentialsObject, Credentials.class);
        try {
            cc.sendHTTPRequest("/user/register", requestBody, "POST");
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("400")) {
                throw new CredentialNotFoundException("Invalid username or password.");
            } else throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameInfo[] listOfGames() throws IllegalArgumentException, CommunicationException {
        GameInfo[] games = null;
        String list = cc.sendHTTPRequest("/games/list", "", "GET");
        games = ModelSerializer.getInstance().fromJson(list, GameInfo[].class);
        return games;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createGame(@NotNull CreateGameRequest createGameObject) throws IllegalArgumentException, CommunicationException {
        String requestBody = ModelSerializer.getInstance().toJson(createGameObject, CreateGameRequest.class);
        cc.sendHTTPRequest("/games/create", requestBody, "POST");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void joinGame(@NotNull JoinGameRequest joinGameObject) throws IllegalArgumentException, CommunicationException {
        String requestBody = ModelSerializer.getInstance().toJson(joinGameObject, JoinGameRequest.class);
        cc.sendHTTPRequest("/games/join", requestBody, "POST");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveGame(@NotNull SaveGameRequest saveGameObject) throws IllegalArgumentException, CommunicationException {
        String requestBody = ModelSerializer.getInstance().toJson(saveGameObject, SaveGameRequest.class);
        cc.sendHTTPRequest("/games/save", requestBody, "POST");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadGame(@NotNull LoadGameRequest loadGameObject) throws IllegalArgumentException, CommunicationException {
        String requestBody = ModelSerializer.getInstance().toJson(loadGameObject, LoadGameRequest.class);
        cc.sendHTTPRequest("/games/load", requestBody, "POST");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel gameState(int version) throws IllegalArgumentException, CommunicationException {
        String requestBody = ModelSerializer.getInstance().toJson(version, Integer.class);
        String modelJson = "";
        modelJson = cc.sendHTTPRequest("/game/model", requestBody, "GET");
        return ModelSerializer.getInstance().fromJson(modelJson, ClientModel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel resetGame() throws IllegalArgumentException, CommunicationException {
        String modelJson = "";
        modelJson = cc.sendHTTPRequest("/game/reset", "", "GET");
        return ModelSerializer.getInstance().fromJson(modelJson, ClientModel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getCommandsGame() throws IllegalArgumentException, CommunicationException {
        String commands = "";
        commands = cc.sendHTTPRequest("/game/commands", "", "GET");
        return ModelSerializer.getInstance().fromJson(commands, String[].class);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel postCommandsGame(@NotNull String[] gameCommands) throws IllegalArgumentException, CommunicationException {
        String requestBody = ModelSerializer.getInstance().toJson(gameCommands, String[].class);
        String modelJson = "";
        modelJson = cc.sendHTTPRequest("/game/commands", requestBody, "POST");
        return ModelSerializer.getInstance().fromJson(modelJson, ClientModel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] listAI() throws IllegalArgumentException, CommunicationException {
        String list = "";
        list = cc.sendHTTPRequest("/game/listAI", "", "GET");
        return ModelSerializer.getInstance().fromJson(list, String[].class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAI(@NotNull AddAIRequest addAIObject) throws IllegalArgumentException, CommunicationException {
        String requestBody = ModelSerializer.getInstance().toJson(addAIObject, AddAIRequest.class);
        cc.sendHTTPRequest("/game/addAI", requestBody, "POST");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeLogLevel(@NotNull ChangeLogLevelRequest changeLogLevelObject) throws IllegalArgumentException, CommunicationException {
        String requestBody = ModelSerializer.getInstance().toJson(changeLogLevelObject, ChangeLogLevelRequest.class);
        cc.sendHTTPRequest("/util/changeLogLevel", requestBody, "POST");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel sendChat(@NotNull SendChatAction sendChatObject) throws IllegalArgumentException, CommunicationException {
        String requestBody = ModelSerializer.getInstance().toJson(sendChatObject, SendChatAction.class);
        String modelJson = "";
        modelJson = cc.sendHTTPRequest("/moves/sendChat", requestBody, "POST");
        return ModelSerializer.getInstance().fromJson(modelJson, ClientModel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel acceptTrade(@NotNull AcceptTradeAction acceptTradeObject) throws IllegalArgumentException, CommunicationException {
        String requestBody = ModelSerializer.getInstance().toJson(acceptTradeObject, AcceptTradeAction.class);
        String modelJson = "";
        modelJson = cc.sendHTTPRequest("/moves/acceptTrade", requestBody, "POST");
        return ModelSerializer.getInstance().fromJson(modelJson, ClientModel.class);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel discardCards(@NotNull DiscardCardsAction discardCardsObject) throws IllegalArgumentException, CommunicationException {
        String requestBody = ModelSerializer.getInstance().toJson(discardCardsObject, DiscardCardsAction.class);
        String modelJson = "";
        modelJson = cc.sendHTTPRequest("/moves/discardCards", requestBody, "POST");
        return ModelSerializer.getInstance().fromJson(modelJson, ClientModel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel rollNumber(@NotNull RollNumberAction rollNumberObject) throws IllegalArgumentException, CommunicationException {
        String requestBody = ModelSerializer.getInstance().toJson(rollNumberObject, RollNumberAction.class);
        String modelJson = null;
        modelJson = cc.sendHTTPRequest("/moves/rollNumber", requestBody, "POST");
        return ModelSerializer.getInstance().fromJson(modelJson, ClientModel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel buildRoad(@NotNull BuildRoadAction buildRoadObject) throws IllegalArgumentException, CommunicationException {
        String requestBody = ModelSerializer.getInstance().toJson(buildRoadObject, BuildRoadAction.class);
        String modelJson = "";
        modelJson = cc.sendHTTPRequest("/moves/buildRoad", requestBody, "POST");
        return ModelSerializer.getInstance().fromJson(modelJson, ClientModel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel buildSettlement(@NotNull BuildSettlementAction buildSettlementObject) throws IllegalArgumentException, CommunicationException {
        String requestBody = ModelSerializer.getInstance().toJson(buildSettlementObject, BuildSettlementAction.class);
        String modelJson = "";
        modelJson = cc.sendHTTPRequest("/moves/buildSettlement", requestBody, "POST");
        return ModelSerializer.getInstance().fromJson(modelJson, ClientModel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel buildCity(@NotNull BuildCityAction buildCityObject) throws IllegalArgumentException, CommunicationException {
        String requestBody = ModelSerializer.getInstance().toJson(buildCityObject, BuildCityAction.class);
        String modelJson = "";
        modelJson = cc.sendHTTPRequest("/moves/buildCity", requestBody, "POST");
        System.out.println(modelJson);
        return ModelSerializer.getInstance().fromJson(modelJson, ClientModel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel offerTrade(@NotNull OfferTradeAction offerTradeObject) throws IllegalArgumentException, CommunicationException {
        String requestBody = ModelSerializer.getInstance().toJson(offerTradeObject, OfferTradeAction.class);
        String modelJson = "";
        modelJson = cc.sendHTTPRequest("/moves/offerTrade", requestBody, "POST");
        return ModelSerializer.getInstance().fromJson(modelJson, ClientModel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel maritimeTrade(@NotNull MaritimeTradeAction maritimeTradeObject) throws IllegalArgumentException, CommunicationException {
        String requestBody = ModelSerializer.getInstance().toJson(maritimeTradeObject, MaritimeTradeAction.class);
        String modelJson = "";
        modelJson = cc.sendHTTPRequest("/moves/maritimeTrade", requestBody, "POST");
        return ModelSerializer.getInstance().fromJson(modelJson, ClientModel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel robPlayer(@NotNull RobPlayerAction robPlayerObject) throws IllegalArgumentException, CommunicationException {
        String requestBody = ModelSerializer.getInstance().toJson(robPlayerObject, RobPlayerAction.class);
        String modelJson = "";
        modelJson = cc.sendHTTPRequest("/moves/robPlayer", requestBody, "POST");
        return ModelSerializer.getInstance().fromJson(modelJson, ClientModel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel finishTurn(@NotNull FinishMoveAction finishMoveObject) throws IllegalArgumentException, CommunicationException {
        String requestBody = ModelSerializer.getInstance().toJson(finishMoveObject, FinishMoveAction.class);
        String modelJson = "";
        modelJson = cc.sendHTTPRequest("/moves/finishTurn", requestBody, "POST");
        return ModelSerializer.getInstance().fromJson(modelJson, ClientModel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel buyDevCard(@NotNull BuyDevCardAction buyDevCardObject) throws IllegalArgumentException, CommunicationException {
        String requestBody = ModelSerializer.getInstance().toJson(buyDevCardObject, BuyDevCardAction.class);
        String modelJson = "";
        modelJson = cc.sendHTTPRequest("/moves/buyDevCard", requestBody, "POST");
        return ModelSerializer.getInstance().fromJson(modelJson, ClientModel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel useSoldier(@NotNull SoldierAction soldierObject) throws IllegalArgumentException, CommunicationException {
        String requestBody = ModelSerializer.getInstance().toJson(soldierObject, SoldierAction.class);
        String modelJson = "";
        modelJson = cc.sendHTTPRequest("/moves/Soldier", requestBody, "POST");
        return ModelSerializer.getInstance().fromJson(modelJson, ClientModel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel useYearOfPlenty(@NotNull YearofPlentyAction yearOfPlentyObject) throws IllegalArgumentException, CommunicationException {
        String requestBody = ModelSerializer.getInstance().toJson(yearOfPlentyObject, YearofPlentyAction.class);
        String modelJson = "";
        modelJson = cc.sendHTTPRequest("/moves/Year_Of_Plenty", requestBody, "POST");
        return ModelSerializer.getInstance().fromJson(modelJson, ClientModel.class);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel useRoadBuilding(@NotNull RoadBuildingAction roadBuildingObject) throws IllegalArgumentException, CommunicationException {
        String requestBody = ModelSerializer.getInstance().toJson(roadBuildingObject, RoadBuildingAction.class);
        String modelJson = "";
        modelJson = cc.sendHTTPRequest("/moves/Road_Building", requestBody, "POST");
        return ModelSerializer.getInstance().fromJson(modelJson, ClientModel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel useMonopoly(@NotNull MonopolyAction monopolyObject) throws IllegalArgumentException, CommunicationException {
        String requestBody = ModelSerializer.getInstance().toJson(monopolyObject, MonopolyAction.class);
        String modelJson = "";
        modelJson = cc.sendHTTPRequest("/moves/Monopoly", requestBody, "POST");
        return ModelSerializer.getInstance().fromJson(modelJson, ClientModel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel useMonument(@NotNull MonumentAction monumentObject) throws IllegalArgumentException, CommunicationException {
        String requestBody = ModelSerializer.getInstance().toJson(monumentObject, MonumentAction.class);
        String modelJson = "";
        modelJson = cc.sendHTTPRequest("/moves/Monument", requestBody, "POST");
        return ModelSerializer.getInstance().fromJson(modelJson, ClientModel.class);
    }
}
