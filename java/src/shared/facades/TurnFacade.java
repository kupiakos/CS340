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
     * @pre The given model is not null
     * @post This will set its current model
     */
    public void setModel(ClientModel model) {
        this.model = model;
    }

    /**
     * Will check to see if the current {@link Player} can end their turn.
     *
     * @param player which {@link Player} to check
     * @return whether the turn could be ended
     * @pre @{code player} belongs to the current {@link ClientModel}.
     */
    public boolean canEndTurn(Player player) {
        return false;
    }

    /**
     * Asks the game if it is a specific {@link Player}'s turn.
     *
     * @param player the {@link Player} to check against
     * @return true if it is that {@link Player}'s turn
     * @pre @{code player} belongs to the current {@link ClientModel}.
     */
    private boolean isPlayersTurn(Player player) {
        return false;
    }

    /**
     * Lets you check if you can trade, build, buy etc.
     *
     * @return the current {@link TurnStatus} of the game's turn
     * @pre A model is currently set, and it is a valid game.
     */
    public TurnStatus getPhase() {
        return TurnStatus.FIRST_ROUND;
    }

    /**
     * Returns all {@link Player}s of the current game.
     *
     * @return a list of {@link Player}s, from 2-4
     * @pre A model is currently set.
     */
    public List<Player> getPlayers() {
        return null;
    }
}
