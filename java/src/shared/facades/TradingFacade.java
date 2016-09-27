package shared.facades;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import shared.definitions.ResourceType;
import shared.models.game.ClientModel;
import shared.models.game.Player;
import shared.models.game.ResourceSet;
import shared.models.game.TradeOffer;
import shared.models.moves.OfferTradeAction;

/**
 * Provides common operations for users to trade with each other.
 */
public class TradingFacade extends AbstractFacade {

    /**
     * Constructor. Requires a valid game model to work.
     *
     * @param manager the manager to use, not null
     * @throws NullPointerException if {@code model} is null
     * @pre {@code model} is a valid {@link ClientModel}.
     * @post This provides valid operations on {@code model}.
     */
    public TradingFacade(@NotNull FacadeManager manager) {
        super(manager);
    }

    /**
     * Returns whether a player can trade with another player with the given conditions.
     * <p>
     * These are the conditions to be met:
     * <ul>
     * <li>{@code sender} and {@code receiver} must be in the same game.</li>
     * <li>It must be {@code sender}'s turn.</li>
     * <li>The game must be in a state in which trading is allowed.</li>
     * <li>The offer must add and subtract at least one resource.</li>
     * </ul>
     *
     * @param sender   the sender of the trade, not null
     * @param receiver the receiver of the trade, not null
     * @param offer    the offer to make - what {@code sender} gets (+) and receives (-)
     * @return true if all of the conditions are met; otherwise false
     * @pre None of the parameters are null.
     * @see #canOfferTrade(Player, Player)
     */
    public boolean canOfferTrade(@NotNull Player sender, @NotNull Player receiver, @NotNull ResourceSet offer) {
        return false;
    }

    /**
     * Returns whether a player can trade with another player at all.
     * <p>
     * These are the conditions to be met:
     * <ul>
     * <li>{@code sender} and {@code receiver} must be in the same game.</li>
     * <li>It must be {@code sender}'s turn.</li>
     * <li>The game must be in a state in which trading is allowed.</li>
     * </ul>
     *
     * @param sender   the sender of the trade, not null
     * @param receiver the receiver of the trade, not null
     * @return true if all of the conditions are met; otherwise false
     * @pre None of the parameters are null.
     * @see #canOfferTrade(Player, Player, ResourceSet)
     */
    public boolean canOfferTrade(@NotNull Player sender, @NotNull Player receiver) {
        return false;
    }

    /**
     * Offer to trade with another player with the given conditions.
     * <p>
     * Note that this only modifies the local model. The server must be called as well.
     *
     * @param sender   the sender of the trade, not null
     * @param receiver the receiver of the trade, not null
     * @param offer    the offer to make - what {@code sender} gets (+) and receives (-)
     * @throws IllegalArgumentException if the precondition is violated
     * @pre {@link #canOfferTrade(Player, Player, ResourceSet)} returns true
     * @post A pending trade offer will be pending for the sender and receiver.
     * @see #canOfferTrade(Player, Player, ResourceSet)
     * @see client.server.ServerProxy#offerTrade(OfferTradeAction)
     */
    public void offerTrade(@NotNull Player sender, @NotNull Player receiver, @NotNull ResourceSet offer) {
        if (!canOfferTrade(sender, receiver, offer)) {
            throw new IllegalArgumentException("Invalid trade!");
        }
    }

    @Nullable
    public TradeOffer getMadeTradeOffer(@NotNull Player sender) {
        return null;
    }

    /**
     * Detect if a player has any outstanding trade offers waiting to be accepted/rejected by them.
     *
     * @param receiver the player to check if they have any trade offers waiting for them
     * @return whether the player has any trade offers waiting for a response from them, or null if none
     */
    @Nullable
    public TradeOffer getWaitingTradeOffer(@NotNull Player receiver) {
        return null;
    }

    /**
     * Get the best maritime trade ratio available to a player.
     * <p>
     * For example, a trade ratio of 3 for ore means that the player can trade 3 ore for any resource card.
     * <p>
     * Depending on the player's harbor configuration, this returns 2-4.
     *
     * @param player       the player to check for, not null
     * @param resourceType the type of resource to check, not null
     * @return the best trade ratio available to the player for the given resource type, 2-4
     */
    public int maritimeTradeRatio(@NotNull Player player, @NotNull ResourceType resourceType) {
        return 4;
    }

    /**
     * Determines whether a player can perform a maritime trade with the given resource.
     * <p>
     * It must be the player's turn, and at the ratio desired must be valid.
     *
     * @param player         the player that wants to perform the trade, not null
     * @param inputResource  the type of resource to trade with, not null
     * @param outputResource the type of resource to trade for, not null
     * @return true if the player can maritime trade with these conditions; false otherwise
     * @pre {@code ratio} is equal to the value returned by {@link #maritimeTradeRatio}
     * @post None.
     */
    public boolean canMaritimeTrade(@NotNull Player player,
                                    @NotNull ResourceType inputResource,
                                    @NotNull ResourceType outputResource) {
        return false;
    }

    /**
     * Performs a maritime trade with the given resource and ratio.
     * <p>
     * It must be the player's turn, and at the ratio desired must be valid.
     *
     * @param player         the player that wants to perform the trade, not null
     * @param inputResource  the type of resource to trade with, not null
     * @param outputResource the type of resource to trade for, not null
     * @pre {@link #canMaritimeTrade} returns true.
     * @post the user trades {@code ratio} of the {@code resourceType} for 1
     */
    public void maritimeTrade(@NotNull Player player,
                              @NotNull ResourceType inputResource,
                              @NotNull ResourceType outputResource) {
        if (!canMaritimeTrade(player, inputResource, outputResource)) {
            throw new IllegalArgumentException("Invalid maritime trade!");
        }
    }
}
