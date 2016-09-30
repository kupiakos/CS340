package client.server;

import org.jetbrains.annotations.NotNull;
import shared.IServer;
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
 * This class is used for testing purposes by hard coding results for Catan Server API requests.
 */
public class MockProxy implements IServer {

    public MockProxy() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void login(@NotNull Credentials credentialsObject) throws CredentialNotFoundException, IllegalArgumentException, CommunicationException {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void register(@NotNull Credentials credentialsObject) throws CredentialNotFoundException, IllegalArgumentException, CommunicationException {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameInfo[] listOfGames() throws IllegalArgumentException, CommunicationException {
        return new GameInfo[0];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createGame(@NotNull CreateGameRequest createGameObject) throws IllegalArgumentException, CommunicationException {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void joinGame(@NotNull JoinGameRequest joinGameObject) throws IllegalArgumentException, CommunicationException {

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
    public void loadGame(@NotNull LoadGameRequest loadGameObject) throws IllegalArgumentException, CommunicationException {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel gameState(int version) throws IllegalArgumentException, CommunicationException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel resetGame() throws IllegalArgumentException, CommunicationException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getCommandsGame() throws IllegalArgumentException, CommunicationException {
        return new String[0];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel postCommandsGame(@NotNull String[] gameCommands) throws IllegalArgumentException, CommunicationException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] listAI() throws CommunicationException, IllegalArgumentException {
        return new String[0];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAI(@NotNull AddAIRequest addAIObject) throws IllegalArgumentException, CommunicationException {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeLogLevel(@NotNull ChangeLogLevelRequest changeLogLevelObject) throws IllegalArgumentException, CommunicationException {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel sendChat(@NotNull SendChatAction sendChatObject) throws IllegalArgumentException, CommunicationException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel acceptTrade(@NotNull AcceptTradeAction acceptTradeObject) throws IllegalArgumentException, CommunicationException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel discardCards(@NotNull DiscardCardsAction discardCardsObject) throws IllegalArgumentException, CommunicationException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel rollNumber(@NotNull RollNumberAction rollNumberObject) throws IllegalArgumentException, CommunicationException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel buildRoad(@NotNull BuildRoadAction buildRoadObject) throws IllegalArgumentException, CommunicationException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel buildSettlement(@NotNull BuildSettlementAction buildSettlementObject) throws IllegalArgumentException, CommunicationException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel buildCity(@NotNull BuildCityAction buildCityObject) throws IllegalArgumentException, CommunicationException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel offerTrade(@NotNull OfferTradeAction offerTradeObject) throws IllegalArgumentException, CommunicationException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel maritimeTrade(@NotNull MaritimeTradeAction maritimeTradeObject) throws IllegalArgumentException, CommunicationException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel robPlayer(@NotNull RobPlayerAction robPlayerObject) throws IllegalArgumentException, CommunicationException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel finishTurn(@NotNull FinishMoveAction finishMoveObject) throws IllegalArgumentException, CommunicationException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel buyDevCard(@NotNull BuyDevCardAction buyDevCardObject) throws IllegalArgumentException, CommunicationException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel useSoldier(@NotNull SoldierAction soldierObject) throws IllegalArgumentException, CommunicationException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel useYearOfPlenty(@NotNull YearofPlentyAction yearOfPlentyObject) throws IllegalArgumentException, CommunicationException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel useRoadBuilding(@NotNull RoadBuildingAction roadBuildingObject) throws IllegalArgumentException, CommunicationException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel useMonopoly(@NotNull MonopolyAction monopolyObject) throws IllegalArgumentException, CommunicationException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel useMonument(@NotNull MonumentAction monumentObject) throws IllegalArgumentException, CommunicationException {
        return null;
    }
}
