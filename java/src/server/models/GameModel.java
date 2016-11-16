package server.models;

import shared.models.game.ClientModel;
import shared.models.games.GameInfo;

public class GameModel {

    private int id;
    private GameInfo gameInfo;
    private ClientModel clientModel;

    public GameModel() {
    }

    public GameModel(int id, GameInfo gameInfo, ClientModel clientModel) {
        this.id = id;
        this.gameInfo = gameInfo;
        this.clientModel = clientModel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GameInfo getGameInfo() {
        return gameInfo;
    }

    public void setGameInfo(GameInfo gameInfo) {
        this.gameInfo = gameInfo;
    }

    public ClientModel getClientModel() {
        return clientModel;
    }

    public void setClientModel(ClientModel clientModel) {
        this.clientModel = clientModel;
    }
}
