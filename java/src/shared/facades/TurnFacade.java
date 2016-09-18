package shared.facades;

import shared.definitions.TurnStatus;
import shared.models.game.ClientModel;
import shared.models.game.Player;

import java.util.List;

public class TurnFacade {
    private ClientModel model;

    /**
     * Create a new {@code TurnFacade} and set its model.
     *
     * @pre {@code model} is a valid {@link ClientModel}.
     * @post This is a valid object.
     * @param model the model to set
     */
    public TurnFacade(ClientModel model) {
        this.model = model;
    }

    /**
     * Ends a player's turn.
     *
     * @param player the {@link Player} to end the turn of
     * @pre @{code player} belongs to the current {@link ClientModel}, and it's their turn.
     * @post {@code player}'s turn is ended
     */
    public static void endTurn(Player player) {
//        if (canEndTurn(player)) {
//
//        }
    }

    /**
     * Sets the current {@link ClientModel}
     *
     * @param model the {@link ClientModel} to set
     * @pre {@code model} is a valid {@link ClientModel}.
     * @post This will set its current model
     */
    public void setModel(ClientModel model) {
        this.model = model;
    }

    /**
     * Will check to see if the current {@link Player} can end their turn.
     *
     * @param player which {@link Player} to check
     * @pre @{code player} belongs to the current {@link ClientModel}.
     * @post None.
     * @return whether the turn could be ended
     */
    public boolean canEndTurn(Player player) {
        return false;
    }

    /**
     * Returns whether it is a specific {@link Player}'s turn.
     *
     * @param player the {@link Player} to check against
     * @pre @{code player} belongs to the current {@link ClientModel}.
     * @post None.
     * @return true if it is that {@link Player}'s turn
     */
    private boolean isPlayersTurn(Player player) {
        return false;
    }

    /**
     * Lets you check if you can trade, build, buy etc.
     *
     * @pre A model is currently set, and it is a valid game.
     * @post None.
     * @return the current {@link TurnStatus} of the game
     */
    public TurnStatus getPhase() {
        return TurnStatus.FIRST_ROUND;
    }

    /**
     * Returns all {@link Player}s of the current game.
     *
     * @pre A model is currently set.
     * @post None.
     * @return a list of {@link Player}s, from 2-4
     */
    public List<Player> getPlayers() {
        return null;
    }
}
