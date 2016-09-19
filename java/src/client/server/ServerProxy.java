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

import javax.naming.CommunicationException;
import javax.security.auth.login.CredentialNotFoundException;
import java.util.List;

/**
 * Created by elijahgk on 9/12/2016.
 * Implements the IServerProxy Interface.
 * Receives calls from client controllers.  Calls methods in clientCommunicator to communicate with actual server.
 */
public class ServerProxy implements IServer {

    public ServerProxy() {
    }


    /**
     * Converts the model returned from the server in Json to the model of class ClientModel.
     *
     * @param gameModel The Json model returned by the server.
     * @return The game model as a ClientModel.
     */
    private ClientModel jsonToModelConversion(@NotNull Gson gameModel) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void login(@NotNull Credentials credentialsObject) throws CredentialNotFoundException, CommunicationException, IllegalArgumentException {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void register(@NotNull Credentials credentialsObject) throws CredentialNotFoundException, CommunicationException, IllegalArgumentException {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GameInfo> listOfGames() throws CommunicationException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createGame(@NotNull CreateGameRequest createGameObject) throws CommunicationException, IllegalArgumentException {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void joinGame(@NotNull JoinGameRequest joinGameObject) throws CommunicationException, JoinGameException {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveGame(@NotNull SaveGameRequest saveGameObject) throws CommunicationException, IllegalArgumentException {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadGame(@NotNull LoadGameRequest loadGameObject) throws CommunicationException, IllegalArgumentException {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel gameState(int version) throws CommunicationException, IllegalArgumentException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel resetGame() throws CommunicationException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getCommandsGame() throws CommunicationException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel postCommandsGame(@NotNull List<String> gameCommands) throws CommunicationException, IllegalArgumentException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> listAI() throws CommunicationException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAI(@NotNull AddAIRequest addAIObject) throws CommunicationException, IllegalArgumentException {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeLogLevel(@NotNull ChangeLogLevelRequest changeLogLevelObject) throws CommunicationException, IllegalArgumentException {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String sendChat(@NotNull SendChatAction sendChatObject) throws CommunicationException, IllegalArgumentException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void acceptTrade(@NotNull AcceptTradeAction acceptTradeObject) throws CommunicationException, IllegalArgumentException {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void discardCards(@NotNull DiscardCardsAction discardCardsObject) throws CommunicationException, IllegalArgumentException {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel rollNumber(@NotNull RollNumberAction rollNumberObject) throws CommunicationException, IllegalArgumentException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel buildRoad(@NotNull BuildRoadAction buildRoadObject) throws CommunicationException, IllegalArgumentException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel buildSettlement(@NotNull BuildSettlementAction buildSettlementObject) throws CommunicationException, IllegalArgumentException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel buildCity(@NotNull BuildCityAction buildCityObject) throws CommunicationException, IllegalArgumentException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel offerTrade(@NotNull OfferTradeAction offerTradeObject) throws CommunicationException, IllegalArgumentException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel maritimeTrade(@NotNull MaritimeTradeAction maritimeTradeObject) throws CommunicationException, IllegalArgumentException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel robPlayer(@NotNull RobPlayerAction robPlayerObject) throws CommunicationException, IllegalArgumentException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel finishTurn(@NotNull FinishMoveAction finishMoveObject) throws CommunicationException, IllegalArgumentException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel buyDevCard(@NotNull BuyDevCardAction buyDevCardObject) throws CommunicationException, IllegalArgumentException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel useSoldier(@NotNull SoldierAction soldierObject) throws CommunicationException, IllegalArgumentException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel useYearOfPlenty(@NotNull YearofPlentyAction yearOfPlentyObject) throws CommunicationException, IllegalArgumentException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel useRoadBuilding(@NotNull RoadBuildingAction roadBuildingObject) throws CommunicationException, IllegalArgumentException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel useMonopoly(@NotNull MonopolyAction monopolyObject) throws CommunicationException, IllegalArgumentException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel useMonument(@NotNull MonumentAction monumentObject) throws CommunicationException, IllegalArgumentException {
        return null;
    }
}
