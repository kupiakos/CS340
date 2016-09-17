package shared.models.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javax.annotation.Generated;

@Generated("net.kupiakos")
public class Credentials {

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("username")
    @Expose
    private String username;


    // CUSTOM CODE
    // END CUSTOM CODE

    /**
     * No args constructor for use in serialization
     */
    public Credentials() {
    }

    /**
      * @param password The password
      * @param username The username
     */
    public Credentials(String password, String username) {
            this.password = password;
            this.username = username;
    }

    /**
     * @return The password
     */
    public String getPassword() { return password; }

    /**
     * @param password The password
     */
    public void setPassword(String password) { this.password = password; }

    public Credentials withPassword(String password) {
        setPassword(password);
        return this;
    }
    /**
     * @return The username
     */
    public String getUsername() { return username; }

    /**
     * @param username The username
     */
    public void setUsername(String username) { this.username = username; }

    public Credentials withUsername(String username) {
        setUsername(username);
        return this;
    }

    @Override
    public String toString() {
        return "Credentials [" +
            "password=" + password +
            ", username=" + username +
            "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Credentials) {
            return equals((Credentials)other);
        }
        return false;
    }

    public boolean equals(Credentials other) {
        return (
            password == other.password &&
            username == other.username
        );
    }
}
