package server.models;

import com.google.gson.annotations.Expose;
import server.db.IDAOObject;
import shared.definitions.CatanColor;
import shared.definitions.PlayerIndex;
import shared.models.game.ClientModel;
import shared.models.game.DevCardSet;
import shared.models.game.Player;
import shared.models.game.ResourceSet;
import shared.models.games.GameInfo;
import shared.models.games.PlayerInfo;

import java.util.HashMap;
import java.util.Map;

public class GameModel implements IDAOObject {

    @Expose
    private int id;

    @Expose
    private GameInfo gameInfo;

    @Expose
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

    @Override
    public String toString() {
        return "GameModel{" +
                "id=" + id +
                ", gameInfo=" + gameInfo +
                ", clientModel=" + clientModel +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameModel gameModel = (GameModel) o;

        if (id != gameModel.id) return false;
        if (gameInfo != null ? !gameInfo.equals(gameModel.gameInfo) : gameModel.gameInfo != null) return false;
        return clientModel != null ? clientModel.equals(gameModel.clientModel) : gameModel.clientModel == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (gameInfo != null ? gameInfo.hashCode() : 0);
        result = 31 * result + (clientModel != null ? clientModel.hashCode() : 0);
        return result;
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
        PlayerInfo p = new PlayerInfo(color, user.getUsername(), user.getId());
        p.setPlayerIndex(PlayerIndex.fromInt(getClientModel().getPlayerCount()));
        getGameInfo().getPlayers().add(p);
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

    public void addAIPlayer() {
        final Map<Integer, String> names = new HashMap<>();
        names.put(-2, "Java");
        names.put(-3, "Python");
        names.put(-4, "Ruby");
        names.put(-5, "C++");
        PlayerInfo aiInfo = new PlayerInfo();
        int aiID = -2;
        for (PlayerInfo p : getGameInfo().getPlayers()) {
            if (p.getId() <= aiID) {
                aiID--;
            }
        }
        aiInfo.setId(aiID);
        aiInfo.setName(names.get(aiID));
        for (CatanColor c : CatanColor.values()) {
            boolean isTaken = false;
            for (PlayerInfo p : getGameInfo().getPlayers()) {
                if (p.getColor() == c) {
                    isTaken = true;
                    break;
                }
            }
            if (isTaken) {
                continue;
            }
            aiInfo.setColor(c);
            break;
        }
        aiInfo.setPlayerIndex(PlayerIndex.fromInt(getClientModel().getPlayerCount()));
        getGameInfo().getPlayers().add(aiInfo);
        getClientModel().getPlayers().add(new Player(
                4,  // starting cities left
                false,  // discarded
                new ResourceSet(),  // resources
                15,  // roads
                0,  // victory points
                new DevCardSet(),  // old dev cards
                0,  // soldiers
                aiInfo.getColor(),  // color
                new DevCardSet(),  // new dev cards
                PlayerIndex.fromInt(getClientModel().getPlayerCount()),  // player index
                0,  // monuments
                aiInfo.getName(),  // name
                5,  // starting settlements left
                aiInfo.getId(),  // player ID
                false  // have played dev card
        ));
    }
}
