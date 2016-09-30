package shared.models.moves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import shared.definitions.PlayerIndex;
import shared.models.game.MessageEntry;

import javax.annotation.Generated;
import java.util.Objects;

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
     * @param content     The content
     * @param playerIndex Who's sending this chat message
     */
    public SendChatAction(String content, PlayerIndex playerIndex) {
        this.content = content;
        this.playerIndex = playerIndex;
    }

    /**
     * @return The type
     */
    public final String getType() {
        return TYPE;
    }

    /**
     * @return The content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content The content
     */
    public void setContent(@NotNull String content) {
        this.content = content;
    }

    public SendChatAction withContent(@NotNull String content) {
        setContent(content);
        return this;
    }

    /**
     * @return Who's sending this chat message
     */
    public PlayerIndex getPlayerIndex() {
        return playerIndex;
    }

    /**
     * @param playerIndex Who's sending this chat message
     */
    public void setPlayerIndex(@NotNull PlayerIndex playerIndex) {
        this.playerIndex = playerIndex;
    }

    public SendChatAction withPlayerIndex(@NotNull PlayerIndex playerIndex) {
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
            return equals((SendChatAction) other);
        }
        return false;
    }

    public boolean equals(SendChatAction other) {
        return (
                TYPE == other.TYPE &&
                        Objects.equals(content, other.content) &&
                        Objects.equals(playerIndex, other.playerIndex)
        );
    }

    /**
     * Converter that lets us change btwn chats and messages.... not sure why both are needed
     * @return new MessageEntry
     */
    public MessageEntry asMessageEntry() {
        // TODO:: How are we keeping track of who is sending messages? Enums of id?
        return new MessageEntry(playerIndex.index() + "", content);
    }
}
