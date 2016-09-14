package shared.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Robber {

    @SerializedName("x")
    @Expose
    private String x;
    @SerializedName("y")
    @Expose
    private String y;

    /**
     * No args constructor for use in serialization
     */
    public Robber() {
    }

    /**
     * @param y
     * @param x
     */
    public Robber(String x, String y) {
        this.x = x;
        this.y = y;
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

    public Robber withX(String x) {
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

    public Robber withY(String y) {
        this.y = y;
        return this;
    }

}
