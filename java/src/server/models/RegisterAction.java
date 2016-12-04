package server.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import shared.models.user.Credentials;

public class RegisterAction extends ServerAction {
    @SerializedName("type")
    @Expose(deserialize = false)
    private final String TYPE = "register";

    @Expose
    private Credentials credentials;

    public RegisterAction(Credentials credentials) {
        this.credentials = credentials;
    }

    @Override
    public void execute() {
        getServerModel().registerUser(credentials.getUsername(), credentials.getPassword());
    }
}
