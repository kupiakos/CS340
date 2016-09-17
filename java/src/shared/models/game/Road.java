package shared.models.game;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javax.annotation.Generated;
import shared.locations.EdgeLocation;
import shared.definitions.PlayerIndex;

@Generated("net.kupiakos")
public class Road {

    @SerializedName("owner")
    @Expose
    private PlayerIndex owner;

    @SerializedName("location")
    @Expose
    private EdgeLocation location;


    // CUSTOM CODE
    // END CUSTOM CODE

    /**
     * No args constructor for use in serialization
     */
    public Road() {
    }

    /**
      * @param owner The index (not id) of the player who owns this piece (0-3)
      * @param location The location of this road.
     */
    public Road(PlayerIndex owner, EdgeLocation location) {
            this.owner = owner;
            this.location = location;
    }

    /**
     * @return The index (not id) of the player who owns this piece (0-3)
     */
    public PlayerIndex getOwner() { return owner; }

    /**
     * @param owner The index (not id) of the player who owns this piece (0-3)
     */
    public void setOwner(PlayerIndex owner) { this.owner = owner; }

    public Road withOwner(PlayerIndex owner) {
        setOwner(owner);
        return this;
    }
    /**
     * @return The location of this road.
     */
    public EdgeLocation getLocation() { return location; }

    /**
     * @param location The location of this road.
     */
    public void setLocation(EdgeLocation location) { this.location = location; }

    public Road withLocation(EdgeLocation location) {
        setLocation(location);
        return this;
    }

    @Override
    public String toString() {
        return "Road [" +
            "owner=" + owner +
            ", location=" + location +
            "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Road) {
            return equals((Road)other);
        }
        return false;
    }

    public boolean equals(Road other) {
        return (
            owner == other.owner &&
            location == other.location
        );
    }
}
