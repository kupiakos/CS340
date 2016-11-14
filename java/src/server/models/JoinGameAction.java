package server.models;

import shared.definitions.CatanColor;
import shared.models.game.Player;
import shared.models.games.JoinGameRequest;
import shared.models.games.PlayerInfo;

/**
 * Created by elija on 11/11/2016.
 */
public class JoinGameAction extends ServerAction {
    private JoinGameRequest joinGameRequest;
    private int joinedGameId;
    private User user;

    public JoinGameAction(JoinGameRequest joinGameRequest, User user) {
        this.joinGameRequest = joinGameRequest;
        this.user = user;
    }

    @Override
    public void execute() {
        GameModel model = getServerModel().getGameModel(joinGameRequest.getId());
        model.getGameInfo().getPlayers().add(new PlayerInfo(CatanColor.valueOf(joinGameRequest.getColor()),user.getUsername(),user.getId()));
        joinedGameId = model.getId();
    }
}
