package shared.models.games;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javax.annotation.Generated;
import java.util.List;
import java.util.ArrayList;

/**
 * Information about a game
 */
@Generated("net.kupiakos")
public class Game {

    @SerializedName("players")
    @Expose
    private List<PlayerInfo> players = new ArrayList<PlayerInfo>();

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
    public Game() {
    }

    /**
      * @param players The players
      * @param title The title
      * @param id The id
     */
    public Game(List<PlayerInfo> players, String title, int id) {
            this.players = players;
            this.title = title;
            this.id = id;
    }

    /**
     * @return The players
     */
    public List<PlayerInfo> getPlayers() { return players; }

    /**
     * @param players The players
     */
    public void setPlayers(List<PlayerInfo> players) { this.players = players; }

    public Game withPlayers(List<PlayerInfo> players) {
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

    public Game withTitle(String title) {
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

    public Game withId(int id) {
        setId(id);
        return this;
    }

    @Override
    public String toString() {
        return "Game [" +
            "players=" + players +
            ", title=" + title +
            ", id=" + id +
            "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Game) {
            return equals((Game)other);
        }
        return false;
    }

    public boolean equals(Game other) {
        return (
            players == other.players &&
            title == other.title &&
            id == other.id
        );
    }
}