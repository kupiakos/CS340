package client.server;

import client.game.GameManager;
import org.junit.Test;
import shared.definitions.AIType;
import shared.definitions.ResourceType;
import shared.locations.*;
import shared.models.game.AddAIRequest;
import shared.models.game.ClientModel;
import shared.models.game.Player;
import shared.models.game.ResourceSet;
import shared.models.games.CreateGameRequest;
import shared.models.games.JoinGameRequest;
import shared.models.games.LoadGameRequest;
import shared.models.games.SaveGameRequest;
import shared.models.moves.*;
import shared.models.user.Credentials;
import shared.models.util.ChangeLogLevelRequest;
import shared.serialization.ModelExample;

import javax.naming.CommunicationException;
import javax.security.auth.login.CredentialNotFoundException;
import java.util.HashMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by elija on 9/29/2016.
 * Tests the functionality of the server proxy using a mock client communicator
 */
public class ServerProxyTest {
    private ServerProxy server;

    public ServerProxyTest() {
        server = new ServerProxy("localhost", "8881");
    }

    @org.junit.Before
    public void setup() {
        ClientModel cm = ModelExample.fullJsonModel();
        GameManager.getGame().setClientModel(cm);
        this.server.setMockCC(cm);
    }

    @Test(expected = CredentialNotFoundException.class)
    public void testBadLogin() throws CredentialNotFoundException, IllegalArgumentException, CommunicationException {
        Credentials credentials = new Credentials("joe", "user");
        server.login(credentials);
    }

    @Test(expected = CredentialNotFoundException.class)
    public void testBadRegister() throws CredentialNotFoundException, IllegalArgumentException, CommunicationException {
        Credentials credentials = new Credentials("password", "user");
        server.register(credentials);
    }

    @Test
    public void testUserCommands() throws CredentialNotFoundException, IllegalArgumentException, CommunicationException {
        server.login(new Credentials("password", "user"));
        server.register(new Credentials("123456", "harold74"));
    }

    @Test
    public void testGamesCommands() throws IllegalArgumentException, CommunicationException {
        server.listOfGames();
        server.createGame(new CreateGameRequest(true, true, true, "Game01"));
        server.joinGame(new JoinGameRequest("RED", 0));
        server.saveGame(new SaveGameRequest("Game01", 1));
        server.loadGame(new LoadGameRequest("Game01"));
    }

    @Test
    public void testGameCommands() throws IllegalArgumentException, CommunicationException {
        assertTrue(server.gameState(1) == null);
        assertFalse(server.gameState(2) == null);
        assertFalse(server.resetGame() == null);
        String[] commands = new String[2];
        commands[0] = "I lost some memory in my computer, but I didn't mind one bit.";
        commands[1] = "That's very punny of you.";
        server.postCommandsGame(commands);
        assertTrue(server.getCommandsGame().length == 2);
        server.listAI();
        server.addAI(new AddAIRequest(AIType.LARGEST_ARMY));
    }

    @Test
    public void testUtils() throws IllegalArgumentException, CommunicationException {
        server.changeLogLevel(new ChangeLogLevelRequest("Log"));
    }

    @Test
    public void testMoves() throws IllegalArgumentException, CommunicationException {
        GameManager.getGame().getClientModel().getMap().setRoads(new HashMap<>());
        GameManager.getGame().getClientModel().getMap().setSettlements(new HashMap<>());
        GameManager.getGame().getClientModel().getMap().setCities(new HashMap<>());
        Player player = GameManager.getGame().getClientModel().getPlayers().get(0);
        assertTrue(server.sendChat(new SendChatAction("Wat up?", player.getPlayerIndex())) != null);
        assertTrue(server.acceptTrade(new AcceptTradeAction(false, player.getPlayerIndex())) != null);
        assertTrue(server.discardCards(new DiscardCardsAction(new ResourceSet(0, 0, 0, 0, 0), player.getPlayerIndex())) != null);
        assertTrue(server.rollNumber(new RollNumberAction(2, player.getPlayerIndex())) != null);
        assertTrue(GameManager.getGame().getClientModel().getMap().getPlayerRoads(player.getPlayerIndex()).size() == 0);
        GameManager.getGame().setClientModel(server.buildRoad(new BuildRoadAction(true, new EdgeLocation(new HexLocation(0, 0), EdgeDirection.North), player.getPlayerIndex())));
        assertTrue(GameManager.getGame().getClientModel().getMap().getPlayerRoads(player.getPlayerIndex()).size() == 1);
        assertTrue(GameManager.getGame().getClientModel().getMap().getPlayerSettlements(player.getPlayerIndex()).size() == 0);
        GameManager.getGame().setClientModel(server.buildSettlement(new BuildSettlementAction(true, new VertexLocation(new HexLocation(0, 0), VertexDirection.NorthEast), player.getPlayerIndex())));
        assertTrue(GameManager.getGame().getClientModel().getMap().getPlayerSettlements(player.getPlayerIndex()).size() == 1);
        assertTrue(GameManager.getGame().getClientModel().getMap().getPlayerCities(player.getPlayerIndex()).size() == 0);
        GameManager.getGame().setClientModel(server.buildCity(new BuildCityAction(new VertexLocation(new HexLocation(0, 0), VertexDirection.NorthEast), player.getPlayerIndex())));
        assertTrue(GameManager.getGame().getClientModel().getMap().getPlayerCities(player.getPlayerIndex()).size() == 1);
        assertTrue(GameManager.getGame().getClientModel().getMap().getPlayerSettlements(player.getPlayerIndex()).size()==0);
        assertTrue(server.offerTrade(new OfferTradeAction(GameManager.getGame().getClientModel().getPlayers().get(1).getPlayerIndex(), new ResourceSet(0, 0, 0, 1, 1), player.getPlayerIndex())) != null);
        assertTrue(server.maritimeTrade(new MaritimeTradeAction(ResourceType.ORE, 2, player.getPlayerIndex(), ResourceType.WOOD)) != null);
        assertTrue(server.robPlayer(new RobPlayerAction(new HexLocation(0, 0), player.getPlayerIndex(), 1)) != null);
        assertTrue(server.finishTurn(new FinishMoveAction(player.getPlayerIndex())) != null);
        assertTrue(server.buyDevCard(new BuyDevCardAction(player.getPlayerIndex())) != null);
        assertTrue(server.useSoldier(new SoldierAction(new HexLocation(0, 0), player.getPlayerIndex(), 1)) != null);
        assertTrue(server.useYearOfPlenty(new YearofPlentyAction(ResourceType.WHEAT, player.getPlayerIndex(), ResourceType.WOOD)) != null);
        assertTrue(server.useMonopoly(new MonopolyAction(player.getPlayerIndex(), ResourceType.ORE)) != null);
        assertTrue(server.useMonument(new MonumentAction(player.getPlayerIndex())) != null);
    }
}
