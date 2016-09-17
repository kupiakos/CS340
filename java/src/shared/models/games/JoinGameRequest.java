package shared.models.games;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javax.annotation.Generated;

@Generated("net.kupiakos")
public class JoinGameRequest {

    @SerializedName("color")
    @Expose
    private String color;

    @SerializedName("id")
    @Expose
    private int id;


    // CUSTOM CODE
    // END CUSTOM CODE

    /**
     * No args constructor for use in serialization
     */
    public JoinGameRequest() {
    }

    /**
      * @param color What color you want to join (or rejoin) as.
      * @param id The ID of the game to join
     */
    public JoinGameRequest(String color, int id) {
            this.color = color;
            this.id = id;
    }

    /**
     * @return What color you want to join (or rejoin) as.
     */
    public String getColor() { return color; }

    /**
     * @param color What color you want to join (or rejoin) as.
     */
    public void setColor(String color) { this.color = color; }

    public JoinGameRequest withColor(String color) {
        setColor(color);
        return this;
    }
    /**
     * @return The ID of the game to join
     */
    public int getId() { return id; }

    /**
     * @param id The ID of the game to join
     */
    public void setId(int id) { this.id = id; }

    public JoinGameRequest withId(int id) {
        setId(id);
        return this;
    }

    @Override
    public String toString() {
        return "JoinGameRequest [" +
            "color=" + color +
            ", id=" + id +
            "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof JoinGameRequest) {
            return equals((JoinGameRequest)other);
        }
        return false;
    }

    public boolean equals(JoinGameRequest other) {
        return (
            color == other.color &&
            id == other.id
        );
    }
}
