package shared.models.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sun.istack.internal.NotNull;

import javax.annotation.Generated;
import java.util.Objects;

@Generated("net.kupiakos")
public class ChangeLogLevelRequest {

    @SerializedName("logLevel")
    @Expose
    private String logLevel;


    // CUSTOM CODE
    // END CUSTOM CODE

    /**
     * No args constructor for use in serialization
     */
    public ChangeLogLevelRequest() {
    }

    /**
      * @param logLevel The server's new log level.  The following values are allowed: ALL, SEVERE, WARNING ,INFO, CONFIG, FINE, FINER, FINEST, OFF
     */
    public ChangeLogLevelRequest(String logLevel) {
            this.logLevel = logLevel;
    }

    /**
     * @return The server's new log level.  The following values are allowed: ALL, SEVERE, WARNING ,INFO, CONFIG, FINE, FINER, FINEST, OFF
     */
    public String getLogLevel() { return logLevel; }

    /**
     * @param logLevel The server's new log level.  The following values are allowed: ALL, SEVERE, WARNING ,INFO, CONFIG, FINE, FINER, FINEST, OFF
     */
    public void setLogLevel(@NotNull String logLevel) { this.logLevel = logLevel; }

    public ChangeLogLevelRequest withLogLevel(@NotNull String logLevel) {
        setLogLevel(logLevel);
        return this;
    }

    @Override
    public String toString() {
        return "ChangeLogLevelRequest [" +
            "logLevel=" + logLevel +
            "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ChangeLogLevelRequest) {
            return equals((ChangeLogLevelRequest)other);
        }
        return false;
    }

    public boolean equals(ChangeLogLevelRequest other) {
        return (
                Objects.equals(logLevel, other.logLevel)
        );
    }
}
