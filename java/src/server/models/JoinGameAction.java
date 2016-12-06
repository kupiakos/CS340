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

    public JoinGameAction() {
    }

    public JoinGameAction(@NotNull JoinGameRequest joinGameRequest, @NotNull User user) {
        this.joinGameRequest = joinGameRequest;
        this.user = user;
    }

    @Override
    public String toString() {
        return "JoinGameAction{" +
                "joinGameRequest=" + joinGameRequest +
                ", joinedGameId=" + joinedGameId +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JoinGameAction that = (JoinGameAction) o;

        if (joinedGameId != that.joinedGameId) return false;
        if (joinGameRequest != null ? !joinGameRequest.equals(that.joinGameRequest) : that.joinGameRequest != null)
            return false;
        return user != null ? user.equals(that.user) : that.user == null;
    }

    @Override
    public int hashCode() {
        int result = joinGameRequest != null ? joinGameRequest.hashCode() : 0;
        result = 31 * result + joinedGameId;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
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