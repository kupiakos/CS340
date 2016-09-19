package client.server;

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
 * This class is used for testing purposes by hard coding results for Catan Server API requests.
 */
public class MockProxy implements IServer{

    public MockProxy(){}


    @Override
    public void login(@NotNull Credentials credentialsObject) throws CredentialNotFoundException, CommunicationException, IllegalArgumentException {

    }

    @Override
    public void register(@NotNull Credentials credentialsObject) throws CredentialNotFoundException, CommunicationException, IllegalArgumentException {

    }

    @Override
    public List<GameInfo> listOfGames() throws CommunicationException {
        return null;
    }

    @Override
    public void createGame(@NotNull CreateGameRequest createGameObject) throws CommunicationException, IllegalArgumentException {

    }

    @Override
    public void joinGame(@NotNull JoinGameRequest joinGameObject) throws CommunicationException, JoinGameException {

    }

    @Override
    public void saveGame(@NotNull SaveGameRequest saveGameObject) throws CommunicationException, IllegalArgumentException {

    }

    @Override
    public void loadGame(@NotNull LoadGameRequest loadGameObject) throws CommunicationException, IllegalArgumentException {

    }

    @Override
    public ClientModel gameState(int version) throws CommunicationException, IllegalArgumentException {
        return null;
    }

    @Override
    public ClientModel resetGame() throws CommunicationException {
        return null;
    }

    @Override
    public List<String> getCommandsGame() throws CommunicationException {
        return null;
    }

    @Override
    public ClientModel postCommandsGame(@NotNull List<String> gameCommands) throws CommunicationException, IllegalArgumentException {
        return null;
    }

    @Override
    public List<String> listAI() throws CommunicationException {
        return null;
    }

    @Override
    public void addAI(@NotNull AddAIRequest addAIObject) throws CommunicationException, IllegalArgumentException {

    }

    @Override
    public void changeLogLevel(@NotNull ChangeLogLevelRequest changeLogLevelObject) throws CommunicationException, IllegalArgumentException {

    }

    @Override
    public String sendChat(@NotNull SendChatAction sendChatObject) throws CommunicationException, IllegalArgumentException {
        return null;
    }

    @Override
    public void acceptTrade(@NotNull AcceptTradeAction acceptTradeObject) throws CommunicationException, IllegalArgumentException {

    }

    @Override
    public void discardCards(@NotNull DiscardCardsAction discardCardsObject) throws CommunicationException, IllegalArgumentException {

    }

    @Override
    public ClientModel rollNumber(@NotNull RollNumberAction rollNumberObject) throws CommunicationException, IllegalArgumentException {
        return null;
    }

    @Override
    public ClientModel buildRoad(@NotNull BuildRoadAction buildRoadObject) throws CommunicationException, IllegalArgumentException {
        return null;
    }

    @Override
    public ClientModel buildSettlement(@NotNull BuildSettlementAction buildSettlementObject) throws CommunicationException, IllegalArgumentException {
        return null;
    }

    @Override
    public ClientModel buildCity(@NotNull BuildCityAction buildCityObject) throws CommunicationException, IllegalArgumentException {
        return null;
    }

    @Override
    public ClientModel offerTrade(@NotNull OfferTradeAction offerTradeObject) throws CommunicationException, IllegalArgumentException {
        return null;
    }

    @Override
    public ClientModel maritimeTrade(@NotNull MaritimeTradeAction maritimeTradeObject) throws CommunicationException, IllegalArgumentException {
        return null;
    }

    @Override
    public ClientModel robPlayer(@NotNull RobPlayerAction robPlayerObject) throws CommunicationException, IllegalArgumentException {
        return null;
    }

    @Override
    public ClientModel finishTurn(@NotNull FinishMoveAction finishMoveObject) throws CommunicationException, IllegalArgumentException {
        return null;
    }

    @Override
    public ClientModel buyDevCard(@NotNull BuyDevCardAction buyDevCardObject) throws CommunicationException, IllegalArgumentException {
        return null;
    }

    @Override
    public ClientModel useSoldier(@NotNull SoldierAction soldierObject) throws CommunicationException, IllegalArgumentException {
        return null;
    }

    @Override
    public ClientModel useYearOfPlenty(@NotNull YearofPlentyAction yearOfPlentyObject) throws CommunicationException, IllegalArgumentException {
        return null;
    }

    @Override
    public ClientModel useRoadBuilding(@NotNull RoadBuildingAction roadBuildingObject) throws CommunicationException, IllegalArgumentException {
        return null;
    }

    @Override
    public ClientModel useMonopoly(@NotNull MonopolyAction monopolyObject) throws CommunicationException, IllegalArgumentException {
        return null;
    }

    @Override
    public ClientModel useMonument(@NotNull MonumentAction monumentObject) throws CommunicationException, IllegalArgumentException {
        return null;
    }
}
