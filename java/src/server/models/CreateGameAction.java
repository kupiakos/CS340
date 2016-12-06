package server.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import shared.models.games.CreateGameRequest;

/**
 * Created by elija on 11/11/2016.
 */
public class CreateGameAction extends ServerAction {
    @SerializedName("type")
    @Expose(deserialize = false)
    private final String TYPE = "createGame";

    @Expose
    private CreateGameRequest request;

    private int createdGameId = -1;

    public CreateGameAction() {
    }

    public CreateGameAction(CreateGameRequest request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return "CreateGameAction{" +
                "request=" + request +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreateGameAction that = (CreateGameAction) o;

        return request != null ? request.equals(that.request) : that.request == null;
    }

    @Override
    public int hashCode() {
        return request != null ? request.hashCode() : 0;
    }

    @Override
    public void execute() {
        createdGameId = getServerModel().startNewGame(request.isRandomTiles(), request.isRandomPorts(), request.isRandomNumbers(), request.getName());
    }

    public int getCreatedGameId() {
        return createdGameId;
    }
}
