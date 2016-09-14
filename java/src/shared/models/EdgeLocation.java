package shared.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class EdgeLocation {

    @SerializedName("x")
    @Expose
    private String x;
    @SerializedName("y")
    @Expose
    private String y;
    @SerializedName("direction")
    @Expose
    private String direction;

    /**
     * No args constructor for use in serialization
     */
    public EdgeLocation() {
    }

    /**
     * @param direction
     * @param y
     * @param x
     */
    public EdgeLocation(String x, String y, String direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    /**
     * @return The x
     */
    public String getX() {
        return x;
    }

    /**
     * @param x The x
     */
    public void setX(String x) {
        this.x = x;
    }

    public EdgeLocation withX(String x) {
        this.x = x;
        return this;
    }

    /**
     * @return The y
     */
    public String getY() {
        return y;
    }

    /**
     * @param y The y
     */
    public void setY(String y) {
        this.y = y;
    }

    public EdgeLocation withY(String y) {
        this.y = y;
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

    public EdgeLocation withDirection(String direction) {
        this.direction = direction;
        return this;
    }

}
