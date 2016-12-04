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

    public CreateGameAction(CreateGameRequest request) {
        this.request = request;
    }

    @Override
    public void execute() {
        getServerModel().startNewGame(request.isRandomTiles(), request.isRandomPorts(), request.isRandomNumbers(), request.getName());
    }
}
