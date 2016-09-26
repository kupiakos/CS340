package shared.models.games;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sun.istack.internal.NotNull;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Information about a game that a Player may or may not be able to join.
 */
@Generated("net.kupiakos")
public class GameInfo {

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
    public GameInfo() {
    }

    /**
      * @param players The players
      * @param title The title
      * @param id The id
     */
    public GameInfo(List<PlayerInfo> players, String title, int id) {
            this.players = players;
            this.title = title;
            this.id = id;
    }

    /**
     * Returns whether a player can join this game.
     *
     * If there are already 4 players, or this player is already part of the game, false.
     * Otherwise, true.
     * @param player the info of the player to check for
     * @return whether this player could join this game
     */
    public boolean canJoinGame(@NotNull PlayerInfo player) {
        return false;
    }

    /**
     * @return The players
     */
    public List<PlayerInfo> getPlayers() { return players; }

    /**
     * @param players The players
     */
    public void setPlayers(@NotNull List<PlayerInfo> players) { this.players = players; }

    public GameInfo withPlayers(@NotNull List<PlayerInfo> players) {
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

    public GameInfo withTitle(@NotNull String title) {
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

    public GameInfo withId(int id) {
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
        if (other instanceof GameInfo) {
            return equals((GameInfo)other);
        }
        return false;
    }

    public boolean equals(GameInfo other) {
        return (
                Objects.equals(players, other.players) &&
                        Objects.equals(title, other.title) &&
            id == other.id
        );
    }
}
