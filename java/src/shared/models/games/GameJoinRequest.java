package shared.models.games;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javax.annotation.Generated;
import java.util.List;
import java.util.ArrayList;

/**
 * A request to join a game
 */
@Generated("net.kupiakos")
public class GameJoinRequest {

    @SerializedName("players")
    @Expose
    private List<Player> players = new ArrayList<Player>();

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("id")
    @Expose
    private int id;


    // CUSTOM CODE
    // END CUSTOM CODE

    /**
     * No args constructor for use in serialization
     */
    public GameJoinRequest() {
    }

    /**
      * @param players The players
      * @param title The title
      * @param id The id
     */
    public GameJoinRequest(List<Player> players, String title, int id) {
            this.players = players;
            this.title = title;
            this.id = id;
    }

    /**
     * @return The players
     */
    public List<Player> getPlayers() { return players; }

    /**
     * @param players The players
     */
    public void setPlayers(List<Player> players) { this.players = players; }

    public GameJoinRequest withPlayers(List<Player> players) {
        setPlayers(players);
        return this;
    }
    /**
     * @return The title
     */
    public String getTitle() { return title; }

    /**
     * @param title The title
     */
    public void setTitle(String title) { this.title = title; }

    public GameJoinRequest withTitle(String title) {
        setTitle(title);
        return this;
    }
    /**
     * @return The id
     */
    public int getId() { return id; }

    /**
     * @param id The id
     */
    public void setId(int id) { this.id = id; }

    public GameJoinRequest withId(int id) {
        setId(id);
        return this;
    }

    @Override
    public String toString() {
        return "GameJoinRequest [" +
            "players=" + players +
            ", title=" + title +
            ", id=" + id +
            "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof GameJoinRequest) {
            return equals((GameJoinRequest)other);
        }
        return false;
    }

    public boolean equals(GameJoinRequest other) {
        return (
            players == other.players &&
            title == other.title &&
            id == other.id
        );
    }
}
