package shared.models.games;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javax.annotation.Generated;

@Generated("net.kupiakos")
public class LoadGameRequest {

    @SerializedName("name")
    @Expose
    private String name;


    // CUSTOM CODE
    // END CUSTOM CODE

    /**
     * No args constructor for use in serialization
     */
    public LoadGameRequest() {
    }

    /**
      * @param name The name of the saved game file that you want to load. (The game's ID is restored as well.)
     */
    public LoadGameRequest(String name) {
            this.name = name;
    }

    /**
     * @return The name of the saved game file that you want to load. (The game's ID is restored as well.)
     */
    public String getName() { return name; }

    /**
     * @param name The name of the saved game file that you want to load. (The game's ID is restored as well.)
     */
    public void setName(String name) { this.name = name; }

    public LoadGameRequest withName(String name) {
        setName(name);
        return this;
    }

    @Override
    public String toString() {
        return "LoadGameRequest [" +
            "name=" + name +
            "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof LoadGameRequest) {
            return equals((LoadGameRequest)other);
        }
        return false;
    }

    public boolean equals(LoadGameRequest other) {
        return (
            name == other.name
        );
    }
}
