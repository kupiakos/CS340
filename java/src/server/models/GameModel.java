package server.models;

import shared.models.game.ClientModel;
import shared.models.games.GameInfo;

public class GameModel {

    private GameInfo gameInfo;
    private ClientModel clientModel;

    public GameModel() {
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
