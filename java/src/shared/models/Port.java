package shared.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Port {

    @SerializedName("resource")
    @Expose
    private String resource;
    @SerializedName("location")
    @Expose
    private shared.locations.HexLocation location;
    @SerializedName("direction")
    @Expose
    private String direction;
    @SerializedName("ratio")
    @Expose
    private String ratio;

    /**
     * No args constructor for use in serialization
     */
    public Port() {
    }

    /**
     * @param ratio
     * @param location
     * @param direction
     * @param resource
     */
    public Port(String resource, shared.locations.HexLocation location, String direction, String ratio) {
        this.resource = resource;
        this.location = location;
        this.direction = direction;
        this.ratio = ratio;
    }

    /**
     * @return The resource
     */
    public String getResource() {
        return resource;
    }

    /**
     * @param resource The resource
     */
    public void setResource(String resource) {
        this.resource = resource;
    }

    public Port withResource(String resource) {
        this.resource = resource;
        return this;
    }

    /**
     * @return The location
     */
    public shared.locations.HexLocation getLocation() {
        return location;
    }

    /**
     * @param location The location
     */
    public void setLocation(shared.locations.HexLocation location) {
        this.location = location;
    }

    public Port withLocation(shared.locations.HexLocation location) {
        this.location = location;
        return this;
    }

    /**
     * @return The direction
     */
    public String getDirection() {
        return direction;
    }

    /**
     * @param direction The direction
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Port withDirection(String direction) {
        this.direction = direction;
        return this;
    }

    /**
     * @return The ratio
     */
    public String getRatio() {
        return ratio;
    }

    /**
     * @param ratio The ratio
     */
    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public Port withRatio(String ratio) {
        this.ratio = ratio;
        return this;
    }

}
