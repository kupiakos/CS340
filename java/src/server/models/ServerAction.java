package server.models;

import shared.models.ICommandAction;

public abstract class ServerAction implements ICommandAction {
    ServerModel serverModel;

    public ServerModel getServerModel() {
        return serverModel;
    }

    public void setServerModel(ServerModel s) {
        serverModel = s;
    }
}
