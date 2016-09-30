package shared.facades;

import org.jetbrains.annotations.NotNull;
import shared.definitions.PurchaseType;
import shared.models.game.ClientModel;
import shared.models.game.Player;
import shared.models.game.ResourceSet;

/**
 * Provides operations for users to manipulate their resources.
 */
public class ResourcesFacade extends AbstractFacade {

    /**
     * Constructor. Requires a valid game model to work.
     *
     * @param manager the manager to use, not null
     * @throws NullPointerException if {@code model} is null
     * @pre {@code model} is a valid {@link ClientModel}.
     * @post This provides valid operations on {@code model}.
     */
    public ResourcesFacade(@NotNull FacadeManager manager) {
        super(manager);
    }

    /**
     * Identifies if the given {@code player} has the resources
     * required to purchase the item requested.
     *
     * @param player the player to check against, not null
     * @param item   the type of purchase that is desired, not null
     * @return whether the player could purchase the item requested
     * @pre The {@code player} is in a valid game and they can purchase items
     * @pre {@code item} is valid
     * @post None.
     */
    public boolean canPurchaseItem(@NotNull Player player, @NotNull PurchaseType item) {
        return true;
    }

    /**
     * Deduct the resources required to purchase a request item for a player.
     *
     * @param player the player to deduct from, not null
     * @param item   the item to purchase, not null
     * @throws IllegalArgumentException if the preconditions are not met.
     * @pre {@link #canPurchaseItem(Player, PurchaseType)} returns true
     * @post The player will have the cost of {@code item} deducted from their resources.
     * @post The game's bank of resources will be updated accordingly.
     */
    public void purchaseItem(@NotNull Player player, @NotNull PurchaseType item) {
        if (!canPurchaseItem(player, item)) {
            throw new IllegalArgumentException("Invalid purchase attempt");
        }
    }

    /**
     * Whether the player can receive the resources from the bank.
     * <p>
     * The bank must have the resources to be able to fulfill the request.
     *
     * @param player    the player to check against, not null
     * @param resources the set of resources requested from the bank, not null
     * @return whether the player can receive the resources from the bank
     */
    public boolean canReceiveFromBank(@NotNull Player player, @NotNull ResourceSet resources) {
        return false;
    }

    /**
     * Whether the player can receive the resources from the bank.
     * <p>
     * The bank must have the resources to be able to fulfill the request.
     *
     * @param player    the player to check against, not null
     * @param resources the set of resources requested from the bank, not null
     * @pre {@link #canReceiveFromBank} returns true.
     * @post The bank has its resources deducted by {@code resources},
     * which are received by {@code player}.
     */
    public void receiveFromBank(@NotNull Player player, @NotNull ResourceSet resources) {
        if (!canReceiveFromBank(player, resources)) {
            throw new IllegalArgumentException("Invalid bank receive");
        }
    }

    /**
     * Whether the player can receive the resources from the bank.
     * <p>
     * The player must have the resources to be able to fulfill the request.
     *
     * @param player    the player to check against, not null
     * @param resources the set of resources returning to the bank, not null
     * @return whether the player can return the resources to the bank
     */
    public boolean canReturnToBank(@NotNull Player player, @NotNull ResourceSet resources) {
        return false;
    }

    /**
     * Whether the player can return the specified resources to the bank.
     * <p>
     * The player must have the resources to be able to fulfill the request.
     *
     * @param player    the player to check against, not null
     * @param resources the set of resources returning to the bank, not null
     * @pre {@link #canReturnToBank} returns true.
     * @post The player has their resources deducted by {@code resources},
     * which are received by the bank.
     */
    public void returnToBank(@NotNull Player player, @NotNull ResourceSet resources) {
        if (!canReceiveFromBank(player, resources)) {
            throw new IllegalArgumentException("Invalid bank receive");
        }
    }
}
