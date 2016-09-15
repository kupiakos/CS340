package client.server;

import shared.IServerProxy;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

/**
 * Created by elijahgk on 9/12/2016.
 * Implements the IServerProxy Interface.
 * Receives calls from client controllers.  Calls methods in clientCommunicator to communicate with actual server.
 */
public class ServerProxy implements IServerProxy {

    @Override
    boolean login(String username, String password) {
        return false;
    }

    @Override
    boolean register(String username, String password, boolean not_used) {
        return false;
    }

    //TODO Change type for listOfGames
    @Override
    void listOfGames() {

    }

    @Override
    boolean createGame(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts) {
        return false;
    }

    @Override
    boolean joinGames(boolean logged_in, boolean in_game, boolean space_available, boolean gameID, boolean available_color) {
        return false;
    }

    @Override
    boolean saveGame(boolean gameID, String file_name) {
        return false;
    }

    @Override
    boolean loadGame(boolean file_name) {
        return false;
    }

    @Override
    boolean gameState(boolean logged_in, int version) {
        return false;
    }

    @Override
    boolean resetGame(boolean logged_in) {
        return false;
    }

    @Override
    boolean getCommandsGame(boolean logged_in) {
        return false;
    }

    @Override
    boolean postCommandsGame(boolean logged_in) {
        return false;
    }

    @Override
    boolean listAI() {
        return false;
    }

    @Override
    boolean addAI(boolean logged_in, boolean space_available /*and a valid AI type*/) {
        return false;
    }

    @Override
    boolean changeLogLevel(/*enums or other valid logging level types*/) {
        return false;
    }

//Move APIs
    @Override
    String sendChat(String content) {
        return null;
    }

    @Override
    boolean acceptTrade(boolean willAccept) {
        return false;
    }

    @Override
    public boolean discardCards() {
        return false;
    }

    @Override
    public int rollNumber() {
        return 0;
    }

    @Override
    public boolean buildRoad(boolean free, EdgeLocation roadLocation) {
        return false;
    }

    @Override
    public boolean buildSettlement(boolean free, VertexLocation settlementLocation) {
        return false;
    }

    @Override
    public boolean buildCity(VertexLocation cityLocation) {
        return false;
    }

    @Override
    public boolean offerTrade() {
        return false;
    }

    @Override
    public boolean maritimeTrade(int ratio, ResourceType inputResource, ResourceType outputResource) {
        return false;
    }

    @Override
    public boolean robPlayer(HexLocation newLocation) {
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
    public boolean useSoldier(HexLocation newLocation) {
        return false;
    }

    @Override
    public boolean useYearOfPlenty(ResourceType Resource1, ResourceType Resource2) {
        return false;
    }

    @Override
    public boolean useRoadBuildinng(EdgeLocation edge1, EdgeLocation edge2) {
        return false;
    }

    @Override
    public boolean useMonopoly(ResourceType resourceType) {
        return false;
    }

    @Override
    public boolean useMonument() {
        return false;
    }
}
