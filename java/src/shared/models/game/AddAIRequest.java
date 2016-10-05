package shared.models.game;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import shared.definitions.AIType;

import javax.annotation.Generated;
import java.util.Objects;

@Generated("net.kupiakos")
public class AddAIRequest {

    @SerializedName("AIType")
    @Expose
    private AIType aIType;


    // CUSTOM CODE
    // END CUSTOM CODE

    /**
     * No args constructor for use in serialization
     */
    public AddAIRequest() {
    }

    /**
      * @param aIType Only strings returned by /game/listAI are valid.
     */
    public AddAIRequest(AIType aIType) {
            this.aIType = aIType;
    }

    /**
     * @return Only strings returned by /game/listAI are valid.
     */
    @NotNull
    public AIType getAIType() { return aIType; }

    /**
     * @param aIType Only strings returned by /game/listAI are valid.
     */
    public void setAIType(@NotNull AIType aIType) { this.aIType = aIType; }

    public AddAIRequest withAIType(@NotNull AIType aIType) {
        setAIType(aIType);
        return this;
    }

    @Override
    public String toString() {
        return "AddAIRequest [" +
            "aIType=" + aIType +
            "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof AddAIRequest) {
            return equals((AddAIRequest)other);
        }
        return false;
    }

    public boolean equals(AddAIRequest other) {
        return (
                Objects.equals(aIType, other.aIType)
        );
    }
}
