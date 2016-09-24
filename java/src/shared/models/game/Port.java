package shared.models.game;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javax.annotation.Generated;

import com.sun.istack.internal.NotNull;
import shared.locations.EdgeDirection;
import shared.definitions.PortType;
import shared.locations.HexLocation;

@Generated("net.kupiakos")
public class Port {

    @SerializedName("location")
    @Expose
    private HexLocation location;

    @SerializedName("direction")
    @Expose
    private EdgeDirection direction;

    @SerializedName("ratio")
    @Expose
    private int ratio;

    @SerializedName("resource")
    @Expose
    private PortType portType = PortType.THREE;


    // CUSTOM CODE
    // END CUSTOM CODE

    /**
     * No args constructor for use in serialization
     */
    public Port() {
    }

    /**
      * @param location Which hex this port is on. This shows the (ocean/non-existent) hex to draw the port on.
      * @param direction Which edge this port is on.
      * @param ratio The ratio for trade in (ie, if this is 2, then it's a 2:1 port.
      * @param portType What type resource this port trades for. If it's omitted, then it's for any resource.
     */
    public Port(HexLocation location, EdgeDirection direction, int ratio, PortType portType) {
            this.location = location;
            this.direction = direction;
            this.ratio = ratio;
            this.portType = portType;
    }

    /**
     * @return Which hex this port is on. This shows the (ocean/non-existent) hex to draw the port on.
     */
    public HexLocation getLocation() { return location; }

    /**
     * @param location Which hex this port is on. This shows the (ocean/non-existent) hex to draw the port on.
     */
    public void setLocation(@NotNull HexLocation location) { this.location = location; }

    public Port withLocation(@NotNull HexLocation location) {
        setLocation(location);
        return this;
    }
    /**
     * @return Which edge this port is on.
     */
    public EdgeDirection getDirection() { return direction; }

    /**
     * @param direction Which edge this port is on.
     */
    public void setDirection(@NotNull EdgeDirection direction) { this.direction = direction; }

    public Port withDirection(@NotNull EdgeDirection direction) {
        setDirection(direction);
        return this;
    }
    /**
     * @return The ratio for trade in (ie, if this is 2, then it's a 2:1 port.
     */
    public int getRatio() { return ratio; }

    /**
     * @param ratio The ratio for trade in (ie, if this is 2, then it's a 2:1 port.
     */
    public void setRatio(int ratio) { this.ratio = ratio; }

    public Port withRatio(int ratio) {
        setRatio(ratio);
        return this;
    }
    /**
     * @return What type resource this port trades for. If it's omitted, then it's for any resource.
     */
    public PortType getPortType() { return portType; }

    /**
     * @param portType What type resource this port trades for. If it's omitted, then it's for any resource.
     */
    public void setPortType(@NotNull PortType portType) { this.portType = portType; }

    public Port withPortType(@NotNull PortType resource) {
        setPortType(resource);
        return this;
    }

    @Override
    public String toString() {
        return "Port [" +
            "location=" + location +
            ", direction=" + direction +
            ", ratio=" + ratio +
            ", resource=" + portType +
            "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Port) {
            return equals((Port)other);
        }
        return false;
    }

    public boolean equals(Port other) {
        return (
            location == other.location &&
            direction == other.direction &&
            ratio == other.ratio &&
            portType == other.portType
        );
    }
}
