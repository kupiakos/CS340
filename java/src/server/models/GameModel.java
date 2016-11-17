package server.models;

import shared.definitions.CatanColor;
import shared.definitions.PlayerIndex;
import shared.models.game.ClientModel;
import shared.models.game.DevCardSet;
import shared.models.game.Player;
import shared.models.game.ResourceSet;
import shared.models.games.GameInfo;
import shared.models.games.PlayerInfo;

public class GameModel {

    private int id;
    private GameInfo gameInfo;
    private ClientModel clientModel;

    public GameModel() {
        gameInfo = new GameInfo();
        clientModel = new ClientModel();
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

    public void addPlayer(User user, CatanColor color) {
        PlayerIndex index;
        for (PlayerInfo p : getGameInfo().getPlayers()) {
            if (p.getId() == user.getId()) {
                p.setColor(color);
                index = p.getPlayerIndex();
                getClientModel().getPlayers().get(index.index()).setColor(color);
                return;
            }
        }
        getGameInfo().getPlayers().add(new PlayerInfo(color, user.getUsername(), user.getId()));
        getClientModel().getPlayers().add(new Player(
                4,  // starting cities left
                false,  // discarded
                new ResourceSet(),  // resources
                15,  // roads
                0,  // victory points
                new DevCardSet(),  // old dev cards
                0,  // soldiers
                color,  // color
                new DevCardSet(),  // new dev cards
                PlayerIndex.fromInt(getClientModel().getPlayerCount()),  // player index
                0,  // monuments
                user.getUsername(),  // name
                5,  // starting settlements left
                user.getId(),  // player ID
                false  // have played dev card
        ));
    }
}
