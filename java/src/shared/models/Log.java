package shared.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class Log {

    @SerializedName("lines")
    @Expose
    private List<Line> lines = new ArrayList<Line>();

    /**
     * No args constructor for use in serialization
     */
    public Log() {
    }

    /**
     * @param lines
     */
    public Log(List<Line> lines) {
        this.lines = lines;
    }

    /**
     * @return The lines
     */
    public List<Line> getLines() {
        return lines;
    }

    /**
     * @param lines The lines
     */
    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public Log withLines(List<Line> lines) {
        this.lines = lines;
        return this;
    }

}