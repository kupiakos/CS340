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

    public RegisterAction() {
    }

    public RegisterAction(Credentials credentials) {
        this.credentials = credentials;
    }

    @Override
    public String toString() {
        return "RegisterAction{" +
                "credentials=" + credentials +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegisterAction that = (RegisterAction) o;

        return credentials != null ? credentials.equals(that.credentials) : that.credentials == null;
    }

    @Override
    public int hashCode() {
        return credentials != null ? credentials.hashCode() : 0;
    }

    @Override
    public void execute() {
        getServerModel().registerUser(credentials.getUsername(), credentials.getPassword());
    }
}
