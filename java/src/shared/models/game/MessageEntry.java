package shared.models.game;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javax.annotation.Generated;

import com.sun.istack.internal.NotNull;
import shared.definitions.PlayerIndex;

@Generated("net.kupiakos")
public class MessageEntry {

    @SerializedName("source")
    @Expose
    private PlayerIndex source;

    @SerializedName("message")
    @Expose
    private String message;


    // CUSTOM CODE
    // END CUSTOM CODE

    /**
     * No args constructor for use in serialization
     */
    public MessageEntry() {
    }

    /**
      * @param source The source
      * @param message The message
     */
    public MessageEntry(PlayerIndex source, String message) {
            this.source = source;
            this.message = message;
    }

    /**
     * @return The source
     */
    public PlayerIndex getSource() { return source; }

    /**
     * @param source The source
     */
    public void setSource(@NotNull PlayerIndex source) { this.source = source; }

    public MessageEntry withSource(@NotNull PlayerIndex source) {
        setSource(source);
        return this;
    }
    /**
     * @return The message
     */
    public String getMessage() { return message; }

    /**
     * @param message The message
     */
    public void setMessage(@NotNull String message) { this.message = message; }

    public MessageEntry withMessage(@NotNull String message) {
        setMessage(message);
        return this;
    }

    @Override
    public String toString() {
        return "MessageLine [" +
            "source=" + source +
            ", message=" + message +
            "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof MessageEntry) {
            return equals((MessageEntry)other);
        }
        return false;
    }

    public boolean equals(MessageEntry other) {
        return (
            source == other.source &&
            message == other.message
        );
    }
}
