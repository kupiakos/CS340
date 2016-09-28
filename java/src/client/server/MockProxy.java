package client.server;

import client.game.GameManager;
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
public class MockProxy implements IServer {

    public MockProxy() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void login(@NotNull Credentials credentialsObject) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void register(@NotNull Credentials credentialsObject) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameInfo[] listOfGames() {
        return new GameInfo[0];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createGame(@NotNull CreateGameRequest createGameObject) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void joinGame(@NotNull JoinGameRequest joinGameObject) throws JoinGameException {

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
    public void loadGame(@NotNull LoadGameRequest loadGameObject) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel gameState(int version) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel resetGame() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getCommandsGame() {
        return new String[0];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel postCommandsGame(@NotNull String[] gameCommands) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] listAI() throws CommunicationException {
        return new String[0];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAI(@NotNull AddAIRequest addAIObject) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeLogLevel(@NotNull ChangeLogLevelRequest changeLogLevelObject) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel sendChat(@NotNull SendChatAction sendChatObject) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel acceptTrade(@NotNull AcceptTradeAction acceptTradeObject) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel discardCards(@NotNull DiscardCardsAction discardCardsObject) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel rollNumber(@NotNull RollNumberAction rollNumberObject) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel buildRoad(@NotNull BuildRoadAction buildRoadObject) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel buildSettlement(@NotNull BuildSettlementAction buildSettlementObject) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel buildCity(@NotNull BuildCityAction buildCityObject) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel offerTrade(@NotNull OfferTradeAction offerTradeObject) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel maritimeTrade(@NotNull MaritimeTradeAction maritimeTradeObject) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel robPlayer(@NotNull RobPlayerAction robPlayerObject) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel finishTurn(@NotNull FinishMoveAction finishMoveObject) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel buyDevCard(@NotNull BuyDevCardAction buyDevCardObject) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel useSoldier(@NotNull SoldierAction soldierObject) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel useYearOfPlenty(@NotNull YearofPlentyAction yearOfPlentyObject) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel useRoadBuilding(@NotNull RoadBuildingAction roadBuildingObject) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel useMonopoly(@NotNull MonopolyAction monopolyObject) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel useMonument(@NotNull MonumentAction monumentObject) {
        return null;
    }
}
