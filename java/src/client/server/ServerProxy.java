package client.server;

import com.google.gson.Gson;
import shared.IServerProxy;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.models.*;

/**
 * Created by elijahgk on 9/12/2016.
 * Implements the IServerProxy Interface.
 * Receives calls from client controllers.  Calls methods in clientCommunicator to communicate with actual server.
 */
public class ServerProxy implements IServerProxy {

    public ServerProxy(){}

    @Override
    public boolean login(Credentials credentialsObject) {
        return false;
    }

    @Override
    public boolean register(Credentials credentialsObject) {
        return false;
    }

    @Override
    public Gson listOfGames() {
        return null;
    }

    @Override
    public boolean createGame(CreateGameRequest createGameObject) {
        return false;
    }

    @Override
    public boolean joinGames(JoinGameRequest joinGameObject) {
        return false;
    }

    @Override
    public boolean saveGame(SaveGameRequest saveGameObject) {
        return false;
    }

    @Override
    public boolean loadGame(LoadGameRequest loadGameObject) {
        return false;
    }

    @Override
    public Gson gameState(int version) {
        return null;
    }

    @Override
    public boolean resetGame() {
        return false;
    }

    @Override
    public boolean getCommandsGame() {
        return false;
    }

    @Override
    public boolean postCommandsGame() {
        return false;
    }

    @Override
    public void listAI() {

    }

    @Override
    public boolean addAI(AddAIRequest addAIObject) {
        return false;
    }

    @Override
    public boolean changeLogLevel(ChangeLogLevelRequest changeLogLevelObject) {
        return false;
    }

    @Override
    public String sendChat(SendChat sendChatObject) {
        return null;
    }

    @Override
    public boolean acceptTrade(AcceptTrade acceptTradeObject) {
        return false;
    }

    @Override
    public boolean discardCards(DiscardCards discardCardsObject) {
        return false;
    }

    @Override
    public int rollNumber() {
        return 0;
    }

    @Override
    public boolean buildRoad(BuildRoad buildRoadObject) {
        return false;
    }

    @Override
    public boolean buildSettlement(BuildSettlement buildSettlementObject) {
        return false;
    }

    @Override
    public boolean buildCity(BuildCity buildCityObject) {
        return false;
    }

    @Override
    public boolean offerTrade(OfferTrade offerTradeObject) {
        return false;
    }

    @Override
    public boolean maritimeTrade(MaritimeTrade maritimeTradeObject) {
        return false;
    }

    @Override
    public boolean robPlayer(RobPlayer robPlayerObject) {
        return false;
    }

    @Override
    public void finishTurn() {

    }

    @Override
    public boolean buyDevCard() {
        return false;
    }

    @Override
    public boolean useSoldier(Soldier soldierObject) {
        return false;
    }

    @Override
    public boolean useYearOfPlenty(YearOfPlenty yearOfPlentyObject) {
        return false;
    }

    @Override
    public boolean useRoadBuilding(RoadBuilding roadBuildingObject) {
        return false;
    }

    @Override
    public boolean useMonopoly(Monopoly monopolyObject) {
        return false;
    }

    @Override
    public boolean useMonument() {
        return false;
    }
}
