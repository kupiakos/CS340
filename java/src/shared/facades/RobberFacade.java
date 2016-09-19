package shared.facades;

import com.sun.istack.internal.NotNull;
import shared.locations.HexLocation;
import shared.models.game.ClientModel;
import shared.models.game.Player;

/**
 * RobberFacade is a facade for the robber.  It checks if players have 8 or more resource cards.
 * It also checks where the robber is being moved and who the player can steal from.
 */
public class RobberFacade extends AbstractFacade {

    /**
     * Constructor. Requires a valid game model to work.
     *
     * @param model the model to use, not null
     * @throws NullPointerException if {@code model} is null
     * @pre {@code model} is a valid {@link ClientModel}.
     * @post This provides valid operations on {@code model}.
     */
    RobberFacade(@NotNull ClientModel model) {
        super(model);
    }

    /**
     * This method is activated by rolling a 7 and calls the {@link RobberFacade#discardHalf(Player)}, {@link RobberFacade#moveRobber(HexLocation, HexLocation)}, and {@link RobberFacade#stealFrom(Player, Player)}, in left-to-right order
     * Note: {@link RobberFacade#discardHalf(Player)} will be called for each individual {@link Player} in the {@link ClientModel}
     * @pre The robber is activated by rolling a 7.
     * @post All described robber actions are executed correctly.
     */
    public void callRobberMethodsByRollingA7() {

    }

    /**
     * This method is activated by playing a Soldier Development card and calls the {@link RobberFacade#moveRobber(HexLocation, HexLocation)} and {@link RobberFacade#stealFrom(Player, Player)}, in left-to-right order
     * Note: The {@link RobberFacade#discardHalf(Player)} will not be called with this method
     * @pre The robber is activated by a {@link Player} playing a Soldier Development card (a result of activating {@link DevCardFacade#useSoldierCard(Player)})
     * @post All described robber actions are executed correctly.
     */
    public void callRobberMethodsByAKnightCard() {

    }

    /**
     * If a {@link Player} has 8 or more resource cards, they must discard half, rounded down.
     * @param player The {@link Player} part of the current {@link ClientModel}.
     * @pre The {@code player} must be part of the current {@link ClientModel}.
     *      This method must have been called by {@link RobberFacade#discardHalf(Player)}.
     * @post None
     * @return True if the {@link Player} has 8 or more resource cards, False if 7 or less.
     */
    public boolean shouldDiscardHalf(Player player)
    {
        return false;
    }

    /**
     * The specified {@link Player} discards half of their resource cards, rounded down.
     * @param player The {@link Player} part of the current {@link ClientModel}.
     * @pre This method is called by {@link RobberFacade#callRobberMethodsByRollingA7()}.
     *      The {@code player} must be part of the current {@link ClientModel}.
     *      {@link RobberFacade#shouldDiscardHalf(Player)} returned a true statement.
     * @post The {@link Player} must have half of their cards.
     */
    public void discardHalf(Player player) {

    }

    /**
     * Checks to see if the new {@link HexLocation} is valid for the robber.
     * @param newLocation A {@link HexLocation} that is the new place for the robber.
     * @param currentLocation A {@link HexLocation} that is the current location for the robber.
     * @pre {@code newLocation} and {@code currentLocation} are not null and are valid {@link HexLocation}.
     *      This method is called by {@link RobberFacade#moveRobber(HexLocation, HexLocation)}.
     * @post None.
     * @return True if {@code newLocation} is valid, False if not
     */
    public boolean canMoveRobber(HexLocation newLocation, HexLocation currentLocation)
    {
        return false;
    }

    /**
     * Moves the robber to the {@code newLocation}.
     * @param newLocation A {@link HexLocation} that is the new place for the robber.
     * @param currentLocation A {@link HexLocation} that is the current location for the robber.
     * @pre {@link RobberFacade#callRobberMethodsByRollingA7()} calls this method after every {@link RobberFacade#discardHalf(Player)} method is called or this method is called by {@link RobberFacade#callRobberMethodsByAKnightCard()}
     *      {@link RobberFacade#canMoveRobber(HexLocation, HexLocation)} returns a true statement.
     * @post The robber is moved to the {@code newLocation}.
     */
    public void moveRobber(HexLocation newLocation, HexLocation currentLocation) {

    }

    /**
     * Checks if the {@code currentPlayer} can steal a resource card from {@code targetPlayer}
     * @param currentPlayer The player who is moving the robber (who's turn it currently is in this game)
     * @param targetPlayer The player that the current player wants to take a resource card from
     * @pre This method is called by {@link RobberFacade#stealFrom(Player, Player)}.
     *      {@code currentPlayer} and {@code targetPlayer} are both valid (not null) and are both part of the current {@link ClientModel}.
     * @post None.
     * @return True if the {@code currentPlayer} can steal from the {@code targetPlayer}, False if not
     */
    public boolean canStealFrom(Player currentPlayer, Player targetPlayer)
    {
        return false;
    }

    /**
     * The {@code currentPlayer} steals one random resource card from {@code targetPlayer}
     * @param currentPlayer The player who is moving the robber (who's turn it currently is in this game)
     * @param targetPlayer The player that the current player wants to take a resource card from
     * @pre This method is called by {@link RobberFacade#callRobberMethodsByRollingA7()} or {@link RobberFacade#callRobberMethodsByAKnightCard()} after {@link RobberFacade#moveRobber(HexLocation, HexLocation)} is called.
     *      {@link RobberFacade#canStealFrom(Player, Player)} returns a true statement.
     *      {@code currentPlayer} and {@code targetPlayer} are both valid (not null) and are both part of the current {@link ClientModel}.
     * @post The {@code currentPlayer} takes one resource card that originally belonged to the {@code targetPlayer}
     */
    public void stealFrom(Player currentPlayer, Player targetPlayer)
    {

    }
}
