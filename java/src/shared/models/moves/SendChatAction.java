package shared.models.moves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javax.annotation.Generated;
import shared.definitions.PlayerIndex;

@Generated("net.kupiakos")
public class SendChatAction {

    @SerializedName("type")
    @Expose(deserialize = false)
    private final String TYPE = "SendChat";

    @SerializedName("content")
    @Expose
    private String content;

    @SerializedName("playerIndex")
    @Expose
    private PlayerIndex playerIndex;


    // CUSTOM CODE
    // END CUSTOM CODE

    /**
     * No args constructor for use in serialization
     */
    public SendChatAction() {
    }

    /**
      * @param TYPE The type
      * @param content The content
      * @param playerIndex Who's sending this chat message
     */
    public SendChatAction(String content, PlayerIndex playerIndex) {
            this.content = content;
            this.playerIndex = playerIndex;
    }

    /**
     * @return The type
     */
    public final String getType() { return TYPE; }

    /**
     * @return The content
     */
    public String getContent() { return content; }

    /**
     * @param content The content
     */
    public void setContent(String content) { this.content = content; }

    public SendChatAction withContent(String content) {
        setContent(content);
        return this;
    }
    /**
     * @return Who's sending this chat message
     */
    public PlayerIndex getPlayerIndex() { return playerIndex; }

    /**
     * @param playerIndex Who's sending this chat message
     */
    public void setPlayerIndex(PlayerIndex playerIndex) { this.playerIndex = playerIndex; }

    public SendChatAction withPlayerIndex(PlayerIndex playerIndex) {
        setPlayerIndex(playerIndex);
        return this;
    }

    @Override
    public String toString() {
        return "SendChatAction [" +
            "type=" + TYPE +
            ", content=" + content +
            ", playerIndex=" + playerIndex +
            "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof SendChatAction) {
            return equals((SendChatAction)other);
        }
        return false;
    }

    public boolean equals(SendChatAction other) {
        return (
            TYPE == other.TYPE &&
            content == other.content &&
            playerIndex == other.playerIndex
        );
    }
}
