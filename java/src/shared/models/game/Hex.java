package shared.models.game;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import shared.definitions.HexType;
import shared.locations.HexLocation;

import javax.annotation.Generated;
import java.util.Objects;

@Generated("net.kupiakos")
public class Hex {

    @SerializedName("number")
    @Expose
    private int number = 0;

    @SerializedName("location")
    @Expose
    private HexLocation location;

    // Unfortunately it's quite difficult to tell GSON to
    // deserialize a default value for a missing property.
    // So, Java defaults are used.
    @SerializedName("resource")
    @Expose
    private HexType resource = HexType.DESERT;


    // CUSTOM CODE
    // END CUSTOM CODE

    /**
     * No args constructor for use in serialization
     */
    public Hex() {
    }

    /**
     * @param number   What number is on this tile. It's omitted if this is a desert hex.
     * @param location The location
     * @param resource What resource this tile gives - it's only here if the tile is not desert.
     */
    public Hex(int number, HexLocation location, HexType resource) {
        this.number = number;
        this.location = location;
        this.resource = resource;
    }

    /**
     * @return What number is on this tile. It's omitted if this is a desert hex.
     */
    public int getNumber() {
        return number;
    }

    /**
     * @param number What number is on this tile. It's omitted if this is a desert hex.
     */
    public void setNumber(int number) {
        this.number = number;
    }

    public Hex withNumber(int number) {
        setNumber(number);
        return this;
    }

    /**
     * @return The location
     */
    public HexLocation getLocation() {
        return location;
    }

    /**
     * @param location The location
     */
    public void setLocation(@NotNull HexLocation location) {
        this.location = location;
    }

    public Hex withLocation(@NotNull HexLocation location) {
        setLocation(location);
        return this;
    }

    /**
     * @return What resource this tile gives - it's only here if the tile is not desert.
     */
    public HexType getResource() {
        return resource;
    }

    /**
     * @param resource What resource this tile gives - it's only here if the tile is not desert.
     */
    public void setResource(@NotNull HexType resource) {
        this.resource = resource;
    }

    public Hex withResource(@NotNull HexType resource) {
        setResource(resource);
        return this;
    }

    @Override
    public String toString() {
        return "Hex [" +
                "number=" + number +
                ", location=" + location +
                ", resource=" + resource +
                "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Hex) {
            return equals((Hex) other);
        }
        return false;
    }

    public boolean equals(Hex other) {
        return (
                number == other.number &&
                        Objects.equals(location, other.location) &&
                        Objects.equals(resource, other.resource)
        );
    }
}
