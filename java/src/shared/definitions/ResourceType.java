package shared.definitions;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.stream.Stream;

public enum ResourceType {
    @SerializedName("wood")
    WOOD,

    @SerializedName("brick")
    BRICK,

    @SerializedName("sheep")
    SHEEP,

    @SerializedName("wheat")
    WHEAT,

    @SerializedName("ore")
    ORE;

    @NotNull
    public static Stream<ResourceType> valuesStream() {
        return Arrays.stream(values());
    }
}

