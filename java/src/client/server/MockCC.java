package client.server;

import shared.models.game.AddAIRequest;
import shared.models.game.ClientModel;
import shared.models.game.MessageEntry;
import shared.models.games.CreateGameRequest;
import shared.models.games.LoadGameRequest;
import shared.models.games.SaveGameRequest;
import shared.models.moves.*;
import shared.models.user.Credentials;
import shared.models.util.ChangeLogLevelRequest;
import shared.serialization.ModelSerializer;

import javax.naming.CommunicationException;
import javax.security.auth.login.CredentialNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by elija on 9/29/2016.
 * A mock client communicator.
 * For testing purposes only.
 */
public class MockCC implements IClientCommunicator {

    private static MockCC mockCC;
    private ClientModel cm;

    public static MockCC initialize(ClientModel cm) {
        if (MockCC.mockCC == null) {
            mockCC = new MockCC();
            mockCC.cm = cm;
        }
        mockCC.cm = cm;
        return mockCC;
    }

    public String sendHTTPRequest(String URLSuffix, String requestBody, String requestMethod) throws
            IllegalArgumentException,
            CommunicationException,
            CredentialNotFoundException {
        switch (URLSuffix) {
            case "/user/login":
                return login(requestBody);
            case "/user/register":
                return register(requestBody);
            case "/games/list":
                return games();
            case "/games/create":
                return createGame(requestBody);
            case "/games/join":
                return joinGame(requestBody);
            case "/games/save":
                return saveGame(requestBody);
            case "/games/load":
                return loadGame(requestBody);
            case "/game/model":
                return gameState(requestBody);
            case "/game/reset":
                return reset();
            case "/game/commands":
                if (requestMethod == "POST")
                    return postCommands(requestBody);
                else
                    return getCommands();
            case "/game/addAI":
                return addAI(requestBody);
            case "/game/listAI":
                return listAI();
            case "/util/changeLogLevel":
                return changeLogLevel(requestBody);
            case "/moves/sendChat":
                return sendChat(requestBody);
            case "/moves/acceptTrade":
                return acceptTrade(requestBody);
            case "/moves/discardCards":
                return discardCards(requestBody);
            case "/moves/rollNumber":
                return rollNumber(requestBody);
            case "/moves/buildRoad":
                return buildRoad(requestBody);
            case "/moves/buildSettlement":
                return buildSettlement(requestBody);
            case "/moves/buildCity":
                return buildCity(requestBody);
            case "/moves/offerTrade":
                return offerTrade(requestBody);
            case "/moves/maritimeTrade":
                return maritimeTrade(requestBody);
            case "/moves/robPlayer":
                return robPlayer(requestBody);
            case "/moves/finishTurn":
                return finishTurn(requestBody);
            case "/moves/buyDevCard":
                return buyDevCard(requestBody);
            case "/moves/Soldier":
                return useSoldier(requestBody);
            case "/moves/Year_of_Plenty":
                return useYearOfPlenty(requestBody);
            case "/moves/Road_Building":
                return useRoadBuilding(requestBody);
            case "/moves/Monopoly":
                return useMonopoly(requestBody);
            case "/moves/Monument":
                return useMonument(requestBody);
            default:
                return "";
        }
    }

    private String login(String requestBody) throws CredentialNotFoundException {
        Credentials credentials = ModelSerializer.getInstance().fromJson(requestBody, Credentials.class);
        if (credentials.getUsername().equals("user") && credentials.getPassword().equals("password")) {
            return "";
        } else throw new CredentialNotFoundException("Cannot log in");
    }

    private String register(String requestBody) throws CredentialNotFoundException {
        Credentials credentials = ModelSerializer.getInstance().fromJson(requestBody, Credentials.class);
        if (!credentials.getUsername().equals("user")) {
            return "";
        } else throw new CredentialNotFoundException("Cannot register");
    }

    private String games() {
        return "";
    }

    private String createGame(String requestBody) {
        CreateGameRequest game = ModelSerializer.getInstance().fromJson(requestBody, CreateGameRequest.class);
        return "";
    }

    private String joinGame(String requestBody) {
        return "3";
    }

    private String saveGame(String requestBody) {
        SaveGameRequest save = ModelSerializer.getInstance().fromJson(requestBody, SaveGameRequest.class);
        return "";
    }

    private String loadGame(String requestBody) {
        LoadGameRequest load = ModelSerializer.getInstance().fromJson(requestBody, LoadGameRequest.class);
        return "";
    }

    private String gameState(String requestBody) {
        int version = ModelSerializer.getInstance().fromJson(requestBody, Integer.class);
        if (version == 1)
            return null;
        return ModelSerializer.getInstance().toJson(cm, ClientModel.class);
    }

    private String reset() {
        return ModelSerializer.getInstance().toJson(cm, ClientModel.class);
    }

    private String getCommands() {
        String[] commands = new String[cm.getLog().getLines().size()];
        for (int i = 0; i < commands.length; i++) {
            commands[i] = cm.getLog().getLines().get(i).getMessage();
        }
        return ModelSerializer.getInstance().toJson(commands, String[].class);
    }

    private String postCommands(String requestBody) {
        String[] commands = ModelSerializer.getInstance().fromJson(requestBody, String[].class);
        List<MessageEntry> messages = new ArrayList<>();
        for (String command : commands) messages.add(new MessageEntry("", command));
        cm.getLog().setLines(messages);
        return ModelSerializer.getInstance().toJson(cm, ClientModel.class);
    }

    private String listAI() {
        return "";
    }

    private String addAI(String requestBody) {
        AddAIRequest ai = ModelSerializer.getInstance().fromJson(requestBody, AddAIRequest.class);
        return "";
    }

    private String changeLogLevel(String requestBody) {
        ChangeLogLevelRequest logLevel = ModelSerializer.getInstance().fromJson(requestBody, ChangeLogLevelRequest.class);
        return "";
    }

    private String sendChat(String requestBody) {
        SendChatAction chat = ModelSerializer.getInstance().fromJson(requestBody, SendChatAction.class);
        return ModelSerializer.getInstance().toJson(cm, ClientModel.class);
    }

    private String acceptTrade(String requestBody) {
        AcceptTradeAction trade = ModelSerializer.getInstance().fromJson(requestBody, AcceptTradeAction.class);
        return ModelSerializer.getInstance().toJson(cm, ClientModel.class);
    }

    private String discardCards(String requestBody) {
        DiscardCardsAction dicards = ModelSerializer.getInstance().fromJson(requestBody, DiscardCardsAction.class);
        return ModelSerializer.getInstance().toJson(cm, ClientModel.class);
    }

    private String rollNumber(String requestBody) {
        RollNumberAction roll = ModelSerializer.getInstance().fromJson(requestBody, RollNumberAction.class);
        return ModelSerializer.getInstance().toJson(cm, ClientModel.class);
    }

    private String buildRoad(String requestBody) {
        BuildRoadAction buildRoad = ModelSerializer.getInstance().fromJson(requestBody, BuildRoadAction.class);
        cm.getMap().getRoads().put(buildRoad.getRoadLocation(), buildRoad.getPlayerIndex());
        return ModelSerializer.getInstance().toJson(cm, ClientModel.class);
    }

    private String buildSettlement(String requestBody) {
        BuildSettlementAction buildSettlement = ModelSerializer.getInstance().fromJson(requestBody, BuildSettlementAction.class);
        cm.getMap().getSettlements().put(buildSettlement.getVertexLocation(), buildSettlement.getPlayerIndex());
        return ModelSerializer.getInstance().toJson(cm, ClientModel.class);
    }

    private String buildCity(String requestBody) {
        BuildCityAction buildCity = ModelSerializer.getInstance().fromJson(requestBody, BuildCityAction.class);
        try {
            cm.getMap().upgradeSettlement(buildCity.getVertexLocation(), buildCity.getPlayerIndex());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ModelSerializer.getInstance().toJson(cm, ClientModel.class);
    }

    private String offerTrade(String requestBody) {
        OfferTradeAction offer = ModelSerializer.getInstance().fromJson(requestBody, OfferTradeAction.class);
        return ModelSerializer.getInstance().toJson(cm, ClientModel.class);
    }

    private String maritimeTrade(String requestBody) {
        MaritimeTradeAction maritimeTrade = ModelSerializer.getInstance().fromJson(requestBody, MaritimeTradeAction.class);
        return ModelSerializer.getInstance().toJson(cm, ClientModel.class);
    }

    private String robPlayer(String requestBody) {
        RobPlayerAction robPlayer = ModelSerializer.getInstance().fromJson(requestBody, RobPlayerAction.class);
        return ModelSerializer.getInstance().toJson(cm, ClientModel.class);
    }

    private String finishTurn(String requestBody) {
        FinishMoveAction finishTurn = ModelSerializer.getInstance().fromJson(requestBody, FinishMoveAction.class);
        return ModelSerializer.getInstance().toJson(cm, ClientModel.class);
    }

    private String buyDevCard(String requestBody) {
        BuyDevCardAction buyCard = ModelSerializer.getInstance().fromJson(requestBody, BuyDevCardAction.class);
        return ModelSerializer.getInstance().toJson(cm, ClientModel.class);
    }

    private String useSoldier(String requestBody) {
        SoldierAction useSoldier = ModelSerializer.getInstance().fromJson(requestBody, SoldierAction.class);
        return ModelSerializer.getInstance().toJson(cm, ClientModel.class);
    }

    private String useYearOfPlenty(String requestBody) {
        YearofPlentyAction usePlenty = ModelSerializer.getInstance().fromJson(requestBody, YearofPlentyAction.class);
        return ModelSerializer.getInstance().toJson(cm, ClientModel.class);
    }

    private String useRoadBuilding(String requestBody) {
        RoadBuildingAction useBuilding = ModelSerializer.getInstance().fromJson(requestBody, RoadBuildingAction.class);
        return ModelSerializer.getInstance().toJson(cm, ClientModel.class);
    }

    private String useMonopoly(String requestBody) {
        MonopolyAction isMonopoly = ModelSerializer.getInstance().fromJson(requestBody, MonopolyAction.class);
        return ModelSerializer.getInstance().toJson(cm, ClientModel.class);
    }

    private String useMonument(String requestBody) {
        MonumentAction useMonument = ModelSerializer.getInstance().fromJson(requestBody, MonumentAction.class);
        return ModelSerializer.getInstance().toJson(cm, ClientModel.class);
    }
}
