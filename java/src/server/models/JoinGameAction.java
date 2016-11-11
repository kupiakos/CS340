package server.models;

import shared.models.games.JoinGameRequest;

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
        GameModel model = serverModel.getGameModel(joinGameRequest.getId());
        joinedGameId = model.getId();
    }
}
