package client.server;

import com.google.gson.Gson;
import com.sun.istack.internal.NotNull;
import shared.IServer;
import shared.exceptions.JoinGameException;
import shared.models.game.AddAIRequest;
import shared.models.game.ClientModel;
import shared.models.games.*;
import shared.models.moves.*;
import shared.models.user.Credentials;
import shared.models.util.ChangeLogLevelRequest;
import shared.serialization.ModelSerializer;

import javax.naming.CommunicationException;
import javax.security.auth.login.CredentialNotFoundException;
import java.net.MalformedURLException;

/**
 * Created by elijahgk on 9/12/2016.
 * Implements the IServerProxy Interface.
 * Receives calls from client controllers.  Calls methods in clientCommunicator to communicate with actual server.
 */
public class ServerProxy implements IServer {

    public ServerProxy() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void login(@NotNull Credentials credentialsObject) throws CredentialNotFoundException, CommunicationException, IllegalArgumentException {
        String requestBody = ModelSerializer.getInstance().toJson(credentialsObject,Credentials.class);
        try {
            ClientCommunicator.getSingleton().sendHTTPRequest("/user/login",requestBody,"POST");
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void register(@NotNull Credentials credentialsObject) throws CredentialNotFoundException, CommunicationException, IllegalArgumentException {
        String requestBody = ModelSerializer.getInstance().toJson(credentialsObject, Credentials.class);
        try {
            ClientCommunicator.getSingleton().sendHTTPRequest("/user/register",requestBody,"POST");
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameInfo[] listOfGames() throws CommunicationException {
        try {
            String list = ClientCommunicator.getSingleton().sendHTTPRequest("/games/list","","GET");
            GameInfo[] games = ModelSerializer.getInstance().fromJson(list, GameInfo[].class);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createGame(@NotNull CreateGameRequest createGameObject) throws CommunicationException, IllegalArgumentException {
        String requestBody = ModelSerializer.getInstance().toJson(createGameObject, CreateGameRequest.class);
        try {
            ClientCommunicator.getSingleton().sendHTTPRequest("/games/create",requestBody,"POST");
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void joinGame(@NotNull JoinGameRequest joinGameObject) throws CommunicationException, JoinGameException {
        String requestBody = ModelSerializer.getInstance().toJson(joinGameObject, JoinGameRequest.class);
        try {
            ClientCommunicator.getSingleton().sendHTTPRequest("/games/join",requestBody,"POST");
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveGame(@NotNull SaveGameRequest saveGameObject) throws CommunicationException, IllegalArgumentException {
        String requestBody = ModelSerializer.getInstance().toJson(saveGameObject, SaveGameRequest.class);
        try {
            ClientCommunicator.getSingleton().sendHTTPRequest("/games/save",requestBody,"POST");
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadGame(@NotNull LoadGameRequest loadGameObject) throws CommunicationException, IllegalArgumentException {
        String requestBody = ModelSerializer.getInstance().toJson(loadGameObject, LoadGameRequest.class);
        try {
            ClientCommunicator.getSingleton().sendHTTPRequest("/games/load",requestBody,"POST");
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel gameState(int version) throws CommunicationException, IllegalArgumentException {
        String requestBody = ModelSerializer.getInstance().toJson(version, Integer.class);
        try {
            String modelJson = ClientCommunicator.getSingleton().sendHTTPRequest("/game/model",requestBody,"GET");
            return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel resetGame() throws CommunicationException {
        try {
            String modelJson = ClientCommunicator.getSingleton().sendHTTPRequest("/game/reset","","GET");
            return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getCommandsGame() throws CommunicationException {
        try {
            String commands = ClientCommunicator.getSingleton().sendHTTPRequest("/game/getCommandsGame","","GET");
            return ModelSerializer.getInstance().fromJson(commands, String[].class);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel postCommandsGame(@NotNull String[] gameCommands) throws CommunicationException, IllegalArgumentException {
        String requestBody = ModelSerializer.getInstance().toJson(gameCommands, String[].class);
        try {
            String modelJson = ClientCommunicator.getSingleton().sendHTTPRequest("/game/model",requestBody,"GET");
            return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] listAI() throws CommunicationException {
        try {
            String list = ClientCommunicator.getSingleton().sendHTTPRequest("/game/listAI","","GET");
            return ModelSerializer.getInstance().fromJson(list,String[].class);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAI(@NotNull AddAIRequest addAIObject) throws CommunicationException, IllegalArgumentException {
        String requestBody = ModelSerializer.getInstance().toJson(addAIObject, AddAIRequest.class);
        try {
            ClientCommunicator.getSingleton().sendHTTPRequest("/game/addAI",requestBody,"POST");
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeLogLevel(@NotNull ChangeLogLevelRequest changeLogLevelObject) throws CommunicationException, IllegalArgumentException {
        String requestBody = ModelSerializer.getInstance().toJson(changeLogLevelObject, ChangeLogLevelRequest.class);
        try {
            ClientCommunicator.getSingleton().sendHTTPRequest("/util/changeLogLevel",requestBody,"POST");
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel sendChat(@NotNull SendChatAction sendChatObject) throws CommunicationException, IllegalArgumentException {
        String requestBody = ModelSerializer.getInstance().toJson(sendChatObject, SendChatAction.class);
        try {
            String modelJson = ClientCommunicator.getSingleton().sendHTTPRequest("/moves/sendChat",requestBody,"POST");
            return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel acceptTrade(@NotNull AcceptTradeAction acceptTradeObject) throws CommunicationException, IllegalArgumentException {
        String requestBody = ModelSerializer.getInstance().toJson(acceptTradeObject, AcceptTradeAction.class);
        try {
            String modelJson = ClientCommunicator.getSingleton().sendHTTPRequest("/moves/acceptTrade",requestBody,"POST");
            return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel discardCards(@NotNull DiscardCardsAction discardCardsObject) throws CommunicationException, IllegalArgumentException {
        String requestBody = ModelSerializer.getInstance().toJson(discardCardsObject, DiscardCardsAction.class);
        try {
            String modelJson = ClientCommunicator.getSingleton().sendHTTPRequest("/moves/discardCards",requestBody,"POST");
            return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel rollNumber(@NotNull RollNumberAction rollNumberObject) throws CommunicationException, IllegalArgumentException {
        String requestBody = ModelSerializer.getInstance().toJson(rollNumberObject, RollNumberAction.class);
        try {
            String modelJson = ClientCommunicator.getSingleton().sendHTTPRequest("/moves/rollNumber",requestBody,"POST");
            return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel buildRoad(@NotNull BuildRoadAction buildRoadObject) throws CommunicationException, IllegalArgumentException {
        String requestBody = ModelSerializer.getInstance().toJson(buildRoadObject, BuildRoadAction.class);
        try {
            String modelJson = ClientCommunicator.getSingleton().sendHTTPRequest("/moves/buildRoad",requestBody,"POST");
            return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel buildSettlement(@NotNull BuildSettlementAction buildSettlementObject) throws CommunicationException, IllegalArgumentException {
        String requestBody = ModelSerializer.getInstance().toJson(buildSettlementObject, BuildSettlementAction.class);
        try {
            String modelJson = ClientCommunicator.getSingleton().sendHTTPRequest("/moves/buildSettlement",requestBody,"POST");
            return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel buildCity(@NotNull BuildCityAction buildCityObject) throws CommunicationException, IllegalArgumentException {
        String requestBody = ModelSerializer.getInstance().toJson(buildCityObject, BuildCityAction.class);
        try {
            String modelJson = ClientCommunicator.getSingleton().sendHTTPRequest("/moves/buildCity",requestBody,"POST");
            return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel offerTrade(@NotNull OfferTradeAction offerTradeObject) throws CommunicationException, IllegalArgumentException {
        String requestBody = ModelSerializer.getInstance().toJson(offerTradeObject, OfferTradeAction.class);
        try {
            String modelJson = ClientCommunicator.getSingleton().sendHTTPRequest("/moves/offerTrade",requestBody,"POST");
            return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel maritimeTrade(@NotNull MaritimeTradeAction maritimeTradeObject) throws CommunicationException, IllegalArgumentException {
        String requestBody = ModelSerializer.getInstance().toJson(maritimeTradeObject, MaritimeTradeAction.class);
        try {
            String modelJson = ClientCommunicator.getSingleton().sendHTTPRequest("/moves/maritimeTrade",requestBody,"POST");
            return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel robPlayer(@NotNull RobPlayerAction robPlayerObject) throws CommunicationException, IllegalArgumentException {
        String requestBody = ModelSerializer.getInstance().toJson(robPlayerObject, RollNumberAction.class);
        try {
            String modelJson = ClientCommunicator.getSingleton().sendHTTPRequest("/moves/robPlayer",requestBody,"POST");
            return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel finishTurn(@NotNull FinishMoveAction finishMoveObject) throws CommunicationException, IllegalArgumentException {
        String requestBody = ModelSerializer.getInstance().toJson(finishMoveObject, FinishMoveAction.class);
        try {
            String modelJson = ClientCommunicator.getSingleton().sendHTTPRequest("/moves/finishTurn",requestBody,"POST");
            return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel buyDevCard(@NotNull BuyDevCardAction buyDevCardObject) throws CommunicationException, IllegalArgumentException {
        String requestBody = ModelSerializer.getInstance().toJson(buyDevCardObject, BuyDevCardAction.class);
        try {
            String modelJson = ClientCommunicator.getSingleton().sendHTTPRequest("/moves/buyDevCard",requestBody,"POST");
            return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel useSoldier(@NotNull SoldierAction soldierObject) throws CommunicationException, IllegalArgumentException {
        String requestBody = ModelSerializer.getInstance().toJson(soldierObject, SoldierAction.class);
        try {
            String modelJson = ClientCommunicator.getSingleton().sendHTTPRequest("/moves/Soldier",requestBody,"POST");
            return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel useYearOfPlenty(@NotNull YearofPlentyAction yearOfPlentyObject) throws CommunicationException, IllegalArgumentException {
        String requestBody = ModelSerializer.getInstance().toJson(yearOfPlentyObject, YearofPlentyAction.class);
        try {
            String modelJson = ClientCommunicator.getSingleton().sendHTTPRequest("/moves/Year_Of_Plenty",requestBody,"POST");
            return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel useRoadBuilding(@NotNull RoadBuildingAction roadBuildingObject) throws CommunicationException, IllegalArgumentException {
        String requestBody = ModelSerializer.getInstance().toJson(roadBuildingObject, RoadBuildingAction.class);
        try {
            String modelJson = ClientCommunicator.getSingleton().sendHTTPRequest("/moves/Road_Building",requestBody,"POST");
            return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel useMonopoly(@NotNull MonopolyAction monopolyObject) throws CommunicationException, IllegalArgumentException {
        String requestBody = ModelSerializer.getInstance().toJson(monopolyObject, MonopolyAction.class);
        try {
            String modelJson = ClientCommunicator.getSingleton().sendHTTPRequest("/moves/Monopoly",requestBody,"POST");
            return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel useMonument(@NotNull MonumentAction monumentObject) throws CommunicationException, IllegalArgumentException {
        String requestBody = ModelSerializer.getInstance().toJson(monumentObject, MonumentAction.class);
        try {
            String modelJson = ClientCommunicator.getSingleton().sendHTTPRequest("/moves/Monument",requestBody,"POST");
            return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
