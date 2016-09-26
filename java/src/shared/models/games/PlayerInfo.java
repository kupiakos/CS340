package shared.models.games;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sun.istack.internal.NotNull;
import shared.definitions.CatanColor;

import javax.annotation.Generated;
import java.util.Objects;

/**
 * A stub for player representation
 */
@Generated("net.kupiakos")
public class PlayerInfo {

    @SerializedName("color")
    @Expose
    private CatanColor color;

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
    public PlayerInfo() {
    }

    /**
      * @param color The color
      * @param name The name
      * @param id The id
     */
    public PlayerInfo(CatanColor color, String name, int id) {
            this.color = color;
            this.name = name;
            this.id = id;
    }

    /**
     * @return The color
     */
    public CatanColor getColor() { return color; }

    /**
     * @param color The color
     */
    public void setColor(@NotNull CatanColor color) { this.color = color; }

    public PlayerInfo withColor(@NotNull CatanColor color) {
        setColor(color);
        return this;
    }
    /**
     * @return The name
     */
    public String getName() { return name; }

    /**
     * @param name The name
     */
    public void setName(@NotNull String name) { this.name = name; }

    public PlayerInfo withName(@NotNull String name) {
        setName(name);
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

    public PlayerInfo withId(int id) {
        setId(id);
        return this;
    }

    @Override
    public String toString() {
        return "PlayerInfo [" +
            "color=" + color +
            ", name=" + name +
            ", id=" + id +
            "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof PlayerInfo) {
            return equals((PlayerInfo)other);
        }
        return false;
    }

    public boolean equals(PlayerInfo other) {
        return (
                Objects.equals(color, other.color) &&
                        Objects.equals(name, other.name) &&
            id == other.id
        );
    }
}
