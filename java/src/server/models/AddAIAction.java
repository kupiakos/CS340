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
    public String toString() {
        return "AddAIAction{" +
                "request=" + request +
                ", gameID=" + gameID +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddAIAction that = (AddAIAction) o;

        if (gameID != that.gameID) return false;
        return request != null ? request.equals(that.request) : that.request == null;
    }

    @Override
    public int hashCode() {
        int result = request != null ? request.hashCode() : 0;
        result = 31 * result + gameID;
        return result;
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
