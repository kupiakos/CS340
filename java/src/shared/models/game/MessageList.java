package shared.models.game;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javax.annotation.Generated;
import java.util.List;
import java.util.ArrayList;

@Generated("net.kupiakos")
public class MessageList {

    @SerializedName("lines")
    @Expose
    private List<MessageLine> lines = new ArrayList<MessageLine>();


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
    public MessageList(List<MessageLine> lines) {
            this.lines = lines;
    }

    /**
     * @return The lines
     */
    public List<MessageLine> getLines() { return lines; }

    /**
     * @param lines The lines
     */
    public void setLines(List<MessageLine> lines) { this.lines = lines; }

    public MessageList withLines(List<MessageLine> lines) {
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
            return equals((MessageList)other);
        }
        return false;
    }

    public boolean equals(MessageList other) {
        return (
            lines == other.lines
        );
    }
}
