package server.models;

import shared.models.user.Credentials;

public class RegisterAction extends ServerAction {
    private Credentials credentials;

    public RegisterAction(Credentials credentials) {
        this.credentials = credentials;
    }

    @Override
    public void execute() {
        getServerModel().registerUser(credentials.getUsername(), credentials.getPassword());
    }
}
