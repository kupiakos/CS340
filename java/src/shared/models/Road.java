package shared.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Road {

    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("location")
    @Expose
    private EdgeLocation location;

    /**
     * No args constructor for use in serialization
     */
    public Road() {
    }

    /**
     * @param location
     * @param owner
     */
    public Road(String owner, EdgeLocation location) {
        this.owner = owner;
        this.location = location;
    }

    /**
     * @return The owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param owner The owner
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Road withOwner(String owner) {
        this.owner = owner;
        return this;
    }

    /**
     * @return The location
     */
    public EdgeLocation getLocation() {
        return location;
    }

    /**
     * @param location The location
     */
    public void setLocation(EdgeLocation location) {
        this.location = location;
    }

    public Road withLocation(EdgeLocation location) {
        this.location = location;
        return this;
    }

}
