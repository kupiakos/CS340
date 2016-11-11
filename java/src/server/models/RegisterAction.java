package server.models;

import shared.models.user.Credentials;

/**
 * Created by elija on 11/11/2016.
 */
public class RegisterAction extends ServerAction {
    private Credentials credentials;
    private int newUserId;

    public RegisterAction(Credentials credentials){
        this.credentials = credentials;
    }

    @Override
    public void execute(){
        newUserId = getServerModel().registerUser(credentials.getUsername(),credentials.getPassword());
    }

    public int getId(){
        return newUserId;
    }
}
