package server.models;

import shared.models.games.CreateGameRequest;

/**
 * Created by elija on 11/11/2016.
 */
public class CreateGameAction extends ServerAction{
    private CreateGameRequest request;

    public CreateGameAction(CreateGameRequest request){
        this.request = request;
    }

    @Override
    public void execute(){
        getServerModel().startNewGame(request.isRandomTiles(),request.isRandomPorts(),request.isRandomNumbers(),request.getName());
    }
}
