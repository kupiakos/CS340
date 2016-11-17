package server.models;

import org.jetbrains.annotations.NotNull;
import shared.models.games.JoinGameRequest;

/**
 * Created by elija on 11/11/2016.
 */
public class JoinGameAction extends ServerAction {
    private JoinGameRequest joinGameRequest;
    private int joinedGameId;
    private User user;

    public JoinGameAction(@NotNull JoinGameRequest joinGameRequest, @NotNull User user) {
        this.joinGameRequest = joinGameRequest;
        this.user = user;
    }

    @Override
    public void execute() {
        GameModel model = getServerModel().getGameModel(joinGameRequest.getId());
        if (model == null) {
            throw new IllegalArgumentException("No game with ID " + joinGameRequest.getId() + " exists");
        }
        model.addPlayer(user, joinGameRequest.getColor());
        joinedGameId = model.getId();
    }

    public int getJoinedGameId() {
        return joinedGameId;
    }
}