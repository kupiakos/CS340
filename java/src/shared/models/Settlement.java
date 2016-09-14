package shared.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Settlement {

    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("location")
    @Expose
    private EdgeLocation location;

    /**
     * No args constructor for use in serialization
     */
    public Settlement() {
    }

    /**
     * @param location
     * @param owner
     */
    public Settlement(String owner, EdgeLocation location) {
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

    public Settlement withOwner(String owner) {
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

    public Settlement withLocation(EdgeLocation location) {
        this.location = location;
        return this;
    }

}
