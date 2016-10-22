package shared.definitions;

import com.google.gson.annotations.SerializedName;

/**
 * The type of AI for the game to run.
 * Used only by the {@code /game/addAI} and {@code /game/listAI} types.
 * <p>
 * Currently only {@link AIType#LARGEST_ARMY} is supported.
 *
 * @see <a href="/docs/api/view/index.html#!/game/addAI_post_4">{@code /game/addAI}</a>
 * @see <a href="/docs/api/view/index.html#!/game/listAI_get_5">{@code /game/listAI}</a>
 */
public enum AIType {
    /**
     * Optimize for the largest army
     */
    @SerializedName("LARGEST_ARMY")
    LARGEST_ARMY
}
