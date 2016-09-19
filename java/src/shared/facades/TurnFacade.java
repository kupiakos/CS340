package shared.facades;

import com.sun.istack.internal.NotNull;
import shared.definitions.TurnStatus;
import shared.models.game.ClientModel;
import shared.models.game.Player;

import java.util.List;

/**
 * Provides common operations on the turn of a game.
 */
public class TurnFacade extends AbstractFacade {

    /**
     * Constructor. Requires a valid game model to work.
     *
     * @param model the model to use, not null
     * @throws NullPointerException if {@code model} is null
     * @pre {@code model} is a valid {@link ClientModel}.
     * @post This provides valid operations on {@code model}.
     */
    public TurnFacade(@NotNull ClientModel model) {
        super(model);
    }

    /**
     * Ends a player's turn.
     *
     * @param player the {@link Player} to end the turn of
     * @pre @{code player} belongs to the current {@link ClientModel}, and it's their turn.
     * @post {@code player}'s turn is ended
     */
    public static void endTurn(@NotNull Player player) {
//        if (canEndTurn(player)) {
//
//        }
    }

    /**
     * Will check to see if the current {@link Player} can end their turn.
     *
     * @param player which {@link Player} to check
     * @return whether the turn could be ended
     * @pre @{code player} belongs to the current {@link ClientModel}.
     * @post None.
     */
    public boolean canEndTurn(@NotNull Player player) {
        return false;
    }

    /**
     * Returns whether it is a specific {@link Player}'s turn.
     *
     * @param player the {@link Player} to check against
     * @return true if it is that {@link Player}'s turn
     * @pre @{code player} belongs to the current {@link ClientModel}.
     * @post None.
     */
    private boolean isPlayersTurn(@NotNull Player player) {
        return false;
    }

    /**
     * Lets you check if you can trade, build, buy etc.
     *
     * @return the current {@link TurnStatus} of the game
     * @pre A model is currently set, and it is a valid game.
     * @post None.
     */
    public TurnStatus getPhase() {
        return TurnStatus.FIRST_ROUND;
    }

    /**
     * Returns all {@link Player}s of the current game.
     *
     * @return a list of {@link Player}s, from 2-4
     * @pre A model is currently set.
     * @post None.
     */
    public List<Player> getPlayers() {
        return null;
    }
}
