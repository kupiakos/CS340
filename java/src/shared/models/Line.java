package shared.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Line {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("source")
    @Expose
    private String source;

    /**
     * No args constructor for use in serialization
     */
    public Line() {
    }

    /**
     * @param message
     * @param source
     */
    public Line(String message, String source) {
        this.message = message;
        this.source = source;
    }

    /**
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public Line withMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * @return The source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source The source
     */
    public void setSource(String source) {
        this.source = source;
    }

    public Line withSource(String source) {
        this.source = source;
        return this;
    }

}
