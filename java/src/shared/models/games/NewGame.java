package shared.models.games;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sun.istack.internal.NotNull;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Information about a new game
 */
@Generated("net.kupiakos")
public class NewGame {

    @SerializedName("players")
    @Expose
    private List<EmptyPlayer> players = new ArrayList<EmptyPlayer>();

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
    public NewGame() {
    }

    /**
      * @param players The players
      * @param title The title
      * @param id The id
     */
    public NewGame(List<EmptyPlayer> players, String title, int id) {
            this.players = players;
            this.title = title;
            this.id = id;
    }

    /**
     * @return The players
     */
    public List<EmptyPlayer> getPlayers() { return players; }

    /**
     * @param players The players
     */
    public void setPlayers(@NotNull List<EmptyPlayer> players) { this.players = players; }

    public NewGame withPlayers(@NotNull List<EmptyPlayer> players) {
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
    public void setTitle(@NotNull String title) { this.title = title; }

    public NewGame withTitle(@NotNull String title) {
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

    public NewGame withId(int id) {
        setId(id);
        return this;
    }

    @Override
    public String toString() {
        return "NewGame [" +
            "players=" + players +
            ", title=" + title +
            ", id=" + id +
            "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof NewGame) {
            return equals((NewGame)other);
        }
        return false;
    }

    public boolean equals(NewGame other) {
        return (
                Objects.equals(players, other.players) &&
                        Objects.equals(title, other.title) &&
            id == other.id
        );
    }
}
