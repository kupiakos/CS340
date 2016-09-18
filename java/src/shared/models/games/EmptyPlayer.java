package shared.models.games;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javax.annotation.Generated;

/**
 * Empty player object
 */
@Generated("net.kupiakos")
public class EmptyPlayer {


    // CUSTOM CODE
    // END CUSTOM CODE


    /**
     */
    public EmptyPlayer() {
    }


    @Override
    public String toString() {
        return "EmptyPlayer []";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof EmptyPlayer) {
            return true;
        }
        return false;
    }

}
