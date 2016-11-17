package shared.models.game;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import shared.models.moves.SendChatAction;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Generated("net.kupiakos")
public class MessageList {

    @SerializedName("lines")
    @Expose
    private List<MessageEntry> lines = new ArrayList<MessageEntry>();


    // CUSTOM CODE
    // END CUSTOM CODE

    /**
     * No args constructor for use in serialization
     */
    public MessageList() {
    }

    /**
     * @param lines The lines
     */
    public MessageList(List<MessageEntry> lines) {
        this.lines = lines;
    }

    /**
     * @return The lines
     */
    public List<MessageEntry> getLines() {
        if (lines == null) lines = new ArrayList<>();

        return lines;
    }

    /**
     * @param lines The lines
     */
    public void setLines(@NotNull List<MessageEntry> lines) {
        this.lines = lines;
    }

    /**
     * Adds just one message to the list, creates a new one if needed
     *
     * @param line
     */
    public void addMessage(@NotNull MessageEntry line) {
        getLines().add(line);
    }

    public void addMessage(@NotNull Player player, @NotNull String message) {
        addMessage(new MessageEntry(player.getName(), message));
    }

    public void prefixMessage(@NotNull Player player, @NotNull String message) {
        addMessage(player, player.getName() + message);
    }

    /**
     * Changes the SendChatAction to a MessageEntry then
     * Adds just one message to the list, creates a new one if needed
     *
     * @param c
     */
    public void addChat(@NotNull SendChatAction c) {

        getLines().add(c.asMessageEntry());
    }

    public MessageList withLines(@NotNull List<MessageEntry> lines) {
        setLines(lines);
        return this;
    }

    @Override
    public String toString() {
        return "MessageList [" +
                "lines=" + lines +
                "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof MessageList) {
            return equals((MessageList) other);
        }
        return false;
    }

    public boolean equals(MessageList other) {
        return (
                Objects.equals(lines, other.lines)
        );
    }
}
