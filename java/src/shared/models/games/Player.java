package shared.models.games;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javax.annotation.Generated;
import shared.definitions.CatanColor;

/**
 * A stub for player representation
 */
@Generated("net.kupiakos")
public class Player {

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
    public Player() {
    }

    /**
      * @param color The color
      * @param name The name
      * @param id The id
     */
    public Player(CatanColor color, String name, int id) {
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
    public void setColor(CatanColor color) { this.color = color; }

    public Player withColor(CatanColor color) {
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
    public void setName(String name) { this.name = name; }

    public Player withName(String name) {
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

    public Player withId(int id) {
        setId(id);
        return this;
    }

    @Override
    public String toString() {
        return "Player [" +
            "color=" + color +
            ", name=" + name +
            ", id=" + id +
            "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Player) {
            return equals((Player)other);
        }
        return false;
    }

    public boolean equals(Player other) {
        return (
            color == other.color &&
            name == other.name &&
            id == other.id
        );
    }
}
