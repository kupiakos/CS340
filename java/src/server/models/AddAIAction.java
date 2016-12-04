package server.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import shared.models.game.AddAIRequest;

/**
 * Created by elija on 11/16/2016.
 */
public class AddAIAction extends ServerAction {
    @SerializedName("type")
    @Expose(deserialize = false)
    private final String TYPE = "addAI";

    @Expose
    private AddAIRequest request;
    @Expose
    private int gameID;

    public AddAIAction() {
    }

    public AddAIAction(AddAIRequest request, int ID) {
        this.request = request;
        gameID = ID;
    }

    @Override
    public void execute() throws IllegalArgumentException {
        GameModel model = getServerModel().getGameModel(gameID);
        if (model == null) {
            throw new IllegalArgumentException("No game with ID " + gameID + " exists");
        }
        model.addAIPlayer();
    }
}
