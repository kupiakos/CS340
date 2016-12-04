package server.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import shared.models.games.JoinGameRequest;
import shared.models.games.PlayerInfo;

/**
 * Created by elija on 11/11/2016.
 */
public class JoinGameAction extends ServerAction {
    @SerializedName("type")
    @Expose(deserialize = false)
    private final String TYPE = "joinGame";

    @Expose
    private JoinGameRequest joinGameRequest;

    @Expose
    private int joinedGameId;

    @Expose
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
        for (PlayerInfo p : model.getGameInfo().getPlayers()) {
            if (p.getColor() == joinGameRequest.getColor() && p.getId() != user.getId()) {
                throw new IllegalArgumentException("Sorry. That color has already been selected.");
            }
        }
        model.addPlayer(user, joinGameRequest.getColor());
        joinedGameId = model.getId();
    }

    public int getJoinedGameId() {
        return joinedGameId;
    }
}