package server.models;

import shared.models.ICommandAction;

/**
 * Created by elija on 11/11/2016.
 */
public class ServerAction implements ICommandAction {
    ServerModel serverModel;

    @Override
    public void execute() {

    }

    public void setServerModel(ServerModel s){
        serverModel = s;
    }

    public ServerModel getServerModel(){
        return serverModel;
    }
}
