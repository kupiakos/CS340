package shared.facades;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import shared.definitions.PlayerIndex;
import shared.definitions.TurnStatus;
import shared.models.game.ClientModel;
import shared.models.game.MessageEntry;
import shared.models.game.Player;
import shared.models.game.TurnTracker;

import static shared.definitions.Constants.LOG_FINISH_TURN_MSG;
import static shared.definitions.TurnStatus.FIRST_ROUND;

/**
 * Provides common operations on the turn of a game.
 */
public class TurnFacade extends AbstractFacade {

    /**
     * Constructor. Requires a valid game model to work.
     *
     * @param manager the manager to use, not null
     * @throws NullPointerException if {@code model} is null
     * @pre {@code model} is a valid {@link ClientModel}.
     * @post This provides valid operations on {@code model}.
     */
    public TurnFacade(@NotNull FacadeManager manager) {
        super(manager);
    }

    private void updateTracker(@Nullable TurnStatus.TurnResult result) {
        if (result == null) {
            return;
        }
        if (result.getPhase() != null)
            tt().setStatus(result.getPhase());
        if (result.getTurn() != null)
            tt().setCurrentTurn(result.getTurn());
    }


    /**
     * i am lazy and hate typing
     *
     * @return the turnTracker
     */
    @NotNull
    private TurnTracker tt() {
        return getModel().getTurnTracker();
    }

    /**
     * Ends a player's turn.
     *
     * @param player the {@link Player} to end the turn of
     * @pre {@code player} belongs to the current {@link ClientModel}, and it's their turn.
     * @post {@code player}'s turn is ended
     */
    public void endTurn(@NotNull Player player) {
        // TODO:: Figure out if we need to consolidate cards or anything or reset them to a start state
        updateTracker(getPhase().endTurn(tt(), player));
        getModel().writeLog(new MessageEntry(player.getName(), LOG_FINISH_TURN_MSG));
    }


    /**
     * Will check to see if the current {@link Player} can end their turn.
     *
     * @param player which {@link Player} to check
     * @return whether the turn could be ended
     * @pre {@code player} belongs to the current {@link ClientModel}.
     * @post None.
     */
    public boolean canEndTurn(@NotNull Player player) {
        return getPhase().canEndTurn(tt(), player);
    }


    /**
     * Returns whether it is a specific {@link Player}'s turn.
     *
     * @param player the {@link Player} to check against
     * @return true if it is that {@link Player}'s turn
     * @pre {@code player} belongs to the current {@link ClientModel}.
     * @post None.
     */
    public boolean isPlayersTurn(@NotNull Player player) {
        return (player.getPlayerIndex() == tt().getCurrentTurn());
    }

    /**
     * Lets you check if you can trade, build, buy etc.
     *
     * @return the current {@link TurnStatus} of the game
     * @pre A model is currently set, and it is a valid game.
     * @post None.
     */
    public TurnStatus getPhase() {
        return tt().getStatus();
    }

    public void setPhase(TurnStatus ts) {
        tt().setStatus(ts);
    }

    /**
     * Sets the game round and player once all have joined
     */
    public void startGame() {
        tt().setStatus(FIRST_ROUND);
        tt().setCurrentTurn(PlayerIndex.FIRST);
    }

    public void finishDiscarding() {
        updateTracker(getPhase().finishDiscarding());
    }

    /**
     * Sets the game state to the same player's turn (build phase)
     *
     * @return successful or not (almost always is true)
     */
    public void finishRolling(boolean moveRobber) {
        updateTracker(getPhase().finishRolling(moveRobber));
    }

    /**
     * Start robbing
     *
     * @return
     */
    public void startRobbing() {
        updateTracker(getPhase().startRobbing());
    }

    /**
     * Stop the robbing
     *
     * @return
     */
    public void finishRobbing() {
        updateTracker(getPhase().finishRobbing());
    }

    public boolean canEndGame() {
        return getPhase().canEndGame(getModel().getPlayers());
    }

    public void endGame() {
        updateTracker(getPhase().endGame(getModel().getPlayers()));
    }

    /**
     * checks if the game state is finished, check if someone has 10 points or not
     *
     * @return successful or not
     */
    public boolean isEndGame() {
        return getPhase().isEndGame();
    }

    /**
     * Whether the game is in setup or not
     *
     * @return true if firstround or second round
     */
    public boolean isSetup() {
        return getPhase().isSetup();
    }

    public Player getCurrentPlayer() {
        return getModel().getPlayer(tt().getCurrentTurn());
    }
}
