package shared.facades;

import org.jetbrains.annotations.NotNull;
import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;
import shared.definitions.TurnStatus;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.models.game.ClientModel;
import shared.models.game.Player;
import shared.models.game.ResourceSet;

import java.util.Set;

/**
 * RobberFacade is a facade for the robber.  It checks if players have 8 or more resource cards.
 * It also checks where the robber is being moved and who the player can steal from.
 */
public class RobberFacade extends AbstractFacade {

    /**
     * Constructor. Requires a valid game model to work.
     *
     * @param manager the manager to use, not null
     * @throws NullPointerException if {@code model} is null
     * @pre {@code model} is a valid {@link ClientModel}.
     * @post This provides valid operations on {@code model}.
     */
    public RobberFacade(@NotNull FacadeManager manager) {
        super(manager);
    }

    /**
     * If a {@link Player} has 8 or more resource cards, they must discard half, rounded down.
     *
     * @param player The {@link Player} part of the current {@link ClientModel}.
     * @return True if the {@link Player} has 8 or more resource cards, False if 7 or less.
     * @pre The {@code player} must be part of the current {@link ClientModel}.
     * The current state of the turn is {@link shared.definitions.TurnStatus#ROBBING} and this method is called by the controller.
     * @post None.
     */
    public boolean shouldDiscardHalf(@NotNull Player player) {
        if (getFacades().getTurn().getPhase() != TurnStatus.DISCARDING) {
            return false;
        }
        if (player == null) {
            return false;
        }
        int total = player.getResources().getTotal();
        if (total < 8) {
            return false;
        }
        return true;
    }

    /**
     * The method checks if the {@code resourceDiscard} are valid cards that can be discarded.
     *
     * @param resourceDiscard
     * @param player
     * @return True if the {@code resourceDiscard} has the correct number of cards to discard and the resources in the {@code resourceDiscard} are also in the {@link Player#getResources()}
     * @pre The current state of the turn is {@link shared.definitions.TurnStatus#ROBBING} and this method is called by the controller.
     * The {@code player} must be part of the current {@link ClientModel}.
     * The {@code resourceDiscard} is not null and contains only {@link shared.definitions.ResourceType}
     * {@link RobberFacade#shouldDiscardHalf(Player)} returned a true statement for {@code player}.
     * @post If returns false, sends an error message of the problem
     * If returns true, this method does nothing more
     */
    public boolean canDiscard(@NotNull ResourceSet resourceDiscard, @NotNull Player player) {
        if (!shouldDiscardHalf(player) || resourceDiscard == null) {
            return false;
        }
        int half = (int) Math.ceil(player.getResources().getTotal() / 2.0);
        if (half != resourceDiscard.getTotal()) {
            return false;
        }
        return true;
    }

    /**
     * The methods discards cards listed in the {@code resourceDiscard} from the {@link Player#getResources()}
     *
     * @param resourceDiscard A {@link ResourceSet} of resources meant to be discarded from the {@code player}
     * @param player          A {@link Player} that is supposed to discard half of their cards
     * @throws IllegalArgumentException if the precondition is violated
     * @pre This method is called by the controller after {@link RobberFacade#canDiscard(ResourceSet, Player)} returns true
     * @post The {@code player} removes the resources that are listed in {@code resourceDiscard}
     */
    public void discard(@NotNull ResourceSet resourceDiscard, @NotNull Player player) {
        if (!canDiscard(resourceDiscard, player)) {
            throw new IllegalArgumentException();
        }
        player.getResources().setOre(player.getResources().getOre() - resourceDiscard.getOre());
        player.getResources().setWheat(player.getResources().getWheat() - resourceDiscard.getWheat());
        player.getResources().setBrick(player.getResources().getBrick() - resourceDiscard.getBrick());
        player.getResources().setSheep(player.getResources().getSheep() - resourceDiscard.getSheep());
        player.getResources().setWood(player.getResources().getWood() - resourceDiscard.getWood());
    }

    /**
     * Checks to see if the new {@link HexLocation} is valid for the robber.
     *
     * @param newLocation A {@link HexLocation} that is the new place for the robber.
     * @return True if {@code newLocation} is valid, False if not
     * @pre The current state of the turn is {@link shared.definitions.TurnStatus#ROBBING} and this method is called by the controller after every {@link RobberFacade#shouldDiscardHalf(Player)} for each {@link Player} is called.
     * {@code newLocation} and {@code currentLocation} are not null and are valid {@link HexLocation}.
     * This method is called by the controller.
     * @post None.
     */
    public boolean canMoveRobber(@NotNull HexLocation newLocation) {
        if (newLocation == null || getFacades().getTurn().getPhase() != TurnStatus.ROBBING) {
            return false;
        }
        if (newLocation == getModel().getMap().getRobber()) {
            return false;
        }
        return true;
    }

    /**
     * Moves the robber to the {@code newLocation}.
     *
     * @param newLocation A {@link HexLocation} that is the new place for the robber.
     * @throws IllegalArgumentException if the precondition is violated
     * @pre The current state of the turn is {@link shared.definitions.TurnStatus#ROBBING} and this method is called by the controller after {@link RobberFacade#canMoveRobber(HexLocation)} is called.
     * {@link RobberFacade#canMoveRobber(HexLocation)} returns a true statement.
     * @post The robber is moved to the {@code newLocation}.
     */
    public void moveRobber(@NotNull HexLocation newLocation) {
        if (!canMoveRobber(newLocation)) {
            throw new IllegalArgumentException();
        }
        getModel().getMap().setRobber(newLocation);
    }

    /**
     * Checks if the current player can be targeted by robbing
     *
     * @param targetPlayer  the {@link PlayerIndex} of the {@link Player} to be checked if they can be stolen from
     * @param currentPlayer the {@link PlayerIndex} of the {@link Player} that is going to steal
     * @return true if {@code targetPlayer} can be stolen from; false if not
     * @pre The current state of the turn is {@link shared.definitions.TurnStatus#ROBBING} and this method is called by the controller after {@link RobberFacade#moveRobber(HexLocation)} is called.
     * The {@code targetPlayer} and {@code currentPlayer} must not be null and be valid {@link PlayerIndex}
     * The {@code targetPlayer} must not equal {@code currentPlayer}
     * The {@code targetPlayer} must have at least one resource
     * @post None.
     */
    public boolean canStealFrom(PlayerIndex targetPlayer, PlayerIndex currentPlayer) {
        if (targetPlayer == null || currentPlayer == null || getFacades().getTurn().getPhase() != TurnStatus.ROBBING) {
            return false;
        }
        if (getModel().getPlayer(targetPlayer).getResources().getTotal() == 0 || currentPlayer == targetPlayer) {
            return false;
        }
        HexLocation hexLocation = getModel().getMap().getRobber();
        Set<VertexLocation> vertexLocationSet = hexLocation.getVertices();
        for (VertexLocation v : vertexLocationSet) {
            if (getModel().getMap().getBuildingOwner(v) == targetPlayer) {
                return true;
            }
        }
        return false;
    }

    /**
     * The {@code currentPlayer} steals one random resource card from {@code targetPlayer}
     *
     * @param currentPlayer The {@link PlayerIndex} of the {@link Player} who wants to steal
     * @param targetPlayer  The {@link PlayerIndex} of the {@link Player} that the {@code currentPlayer} wants to take a resource card from
     * @throws IllegalArgumentException
     * @pre The current state of the turn is {@link shared.definitions.TurnStatus#ROBBING} and this method is called by the controller after {@link RobberFacade#canStealFrom(PlayerIndex, PlayerIndex)} is called.
     * {@link RobberFacade#canStealFrom(PlayerIndex, PlayerIndex)} returns a true statement.
     * {@code currentPlayer} and {@code targetPlayer} are both valid (not null) and are both part of the current {@link ClientModel}.
     * @post The {@code currentPlayer} takes one resource card that originally belonged to the {@code targetPlayer}
     */
    public void steal(@NotNull PlayerIndex targetPlayer, @NotNull PlayerIndex currentPlayer) {
        if (!canStealFrom(targetPlayer, currentPlayer)) {
            throw new IllegalArgumentException();
        }
        ResourceSet targetSet = getModel().getPlayer(targetPlayer).getResources();
        ResourceSet currentSet = getModel().getPlayer(currentPlayer).getResources();
        ResourceType randType = getModel().getPlayer(targetPlayer).getResources().getRandom();
        targetSet.setOfType(randType, targetSet.getOfType(randType) - 1);
        currentSet.setOfType(randType, currentSet.getOfType(randType) + 1);
    }
}
