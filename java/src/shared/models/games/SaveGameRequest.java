package shared.models.games;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javax.annotation.Generated;

@Generated("net.kupiakos")
public class SaveGameRequest {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("id")
    @Expose
    private int id;


    // CUSTOM CODE
    // END CUSTOM CODE

    /**
     * No args constructor for use in serialization
     */
    public SaveGameRequest() {
    }

    /**
      * @param name The file name you want to save it under
      * @param id The ID of the game to save
     */
    public SaveGameRequest(String name, int id) {
            this.name = name;
            this.id = id;
    }

    /**
     * @return The file name you want to save it under
     */
    public String getName() { return name; }

    /**
     * @param name The file name you want to save it under
     */
    public void setName(String name) { this.name = name; }

    public SaveGameRequest withName(String name) {
        setName(name);
        return this;
    }
    /**
     * @return The ID of the game to save
     */
    public int getId() { return id; }

    /**
     * @param id The ID of the game to save
     */
    public void setId(int id) { this.id = id; }

    public SaveGameRequest withId(int id) {
        setId(id);
        return this;
    }

    @Override
    public String toString() {
        return "SaveGameRequest [" +
            "name=" + name +
            ", id=" + id +
            "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof SaveGameRequest) {
            return equals((SaveGameRequest)other);
        }
        return false;
    }

    public boolean equals(SaveGameRequest other) {
        return (
            name == other.name &&
            id == other.id
        );
    }
}
