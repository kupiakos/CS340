package server.models;

import shared.models.game.AddAIRequest;

/**
 * Created by elija on 11/16/2016.
 */
public class AddAIAction extends ServerAction {
    private AddAIRequest request;
    private int gameID;

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
