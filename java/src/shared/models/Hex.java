package shared.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Hex {

    @SerializedName("location")
    @Expose
    private shared.locations.HexLocation location;
    @SerializedName("resource")
    @Expose
    private String resource;
    @SerializedName("number")
    @Expose
    private String number;

    /**
     * No args constructor for use in serialization
     */
    public Hex() {
    }

    /**
     * @param location
     * @param resource
     * @param number
     */
    public Hex(shared.locations.HexLocation location, String resource, String number) {
        this.location = location;
        this.resource = resource;
        this.number = number;
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

    public Hex withLocation(shared.locations.HexLocation location) {
        this.location = location;
        return this;
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

    public Hex withResource(String resource) {
        this.resource = resource;
        return this;
    }

    /**
     * @return The number
     */
    public String getNumber() {
        return number;
    }

    /**
     * @param number The number
     */
    public void setNumber(String number) {
        this.number = number;
    }

    public Hex withNumber(String number) {
        this.number = number;
        return this;
    }

}
