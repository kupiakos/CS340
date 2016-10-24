package client.communication;

import shared.definitions.CatanColor;

import java.util.Objects;

/**
 * Message (or entry) displayed in the LogComponent
 */
public class LogEntry {

    /**
     * Color used when displaying the message
     */
    private CatanColor color;

    /**
     * Message text
     */
    private String message;

    public LogEntry(CatanColor color, String message) {
        this.color = color;
        this.message = message;
    }

    public CatanColor getColor() {
        return color;
    }

    public void setColor(CatanColor color) {
        this.color = color;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof LogEntry) {
            return equals((LogEntry)o);
        }
        return false;
    }

    public boolean equals(LogEntry o) {
        return color == o.color && Objects.equals(message, o.message);
    }

}

