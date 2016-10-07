package shared.facades;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import shared.definitions.PortType;
import shared.definitions.ResourceType;
import shared.definitions.TurnStatus;
import shared.models.game.*;
import shared.models.moves.OfferTradeAction;

import java.util.Set;

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
     * <li>The sender can afford to send the given resources</li>
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
        return canOfferTrade(sender, receiver) &&
                offer.hasNegative() &&
                offer.hasPositive() &&
                offer.isSubset(sender.getResources());
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
        return getModel().getTradeOffer() == null &&
                getModel().getTurnTracker().getStatus() == TurnStatus.PLAYING &&
                getFacades().getTurn().isPlayersTurn(sender) &&
                sender.getPlayerIndex() != receiver.getPlayerIndex();
    }

    /**
     * Offer to trade with another player with the given conditions.
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
        getModel().setTradeOffer(new TradeOffer(receiver.getPlayerIndex(), offer, sender.getPlayerIndex()));
    }

    /**
     * Detect if a player has made any outstanding trade offers waiting to be accepted by someone else.
     *
     * @param sender the person who has made the trade offer
     * @return the trade offers made by the player, or null if none
     */
    @Nullable
    public TradeOffer getMadeTradeOffer(@NotNull Player sender) {
        TradeOffer trade = getModel().getTradeOffer();
        if (trade != null) {
            return trade.getSender() == sender.getPlayerIndex() ? trade : null;
        }
        return null;
    }

    /**
     * Detect if a player has any outstanding trade offers waiting to be accepted/rejected by them.
     *
     * @param receiver the player to check if they have any trade offers waiting for them
     * @return the trade offers waiting for a response from the player, or null if none
     */
    @Nullable
    public TradeOffer getWaitingTradeOffer(@NotNull Player receiver) {
        TradeOffer trade = getModel().getTradeOffer();
        if (trade != null) {
            return trade.getReceiver() == receiver.getPlayerIndex() ? trade : null;
        }
        return null;
    }

    public boolean canRespondToTradeOffer(@NotNull Player receiver, boolean willAccept) {
        TradeOffer offer = getWaitingTradeOffer(receiver);
        return offer != null &&
                (!willAccept ||
                        ResourceSet.toNegative(offer.getOffer()).isSubset(receiver.getResources()));
    }

    /**
     * Respond to a trade offer that is withstanding with {@code receiver} as the receiver of the trade.
     *
     * @param receiver   the player that is receiving the trade
     * @param willAccept whether the player will accept the trade desired
     */
    public void respondToTradeOffer(@NotNull Player receiver, boolean willAccept) {
        if (!canRespondToTradeOffer(receiver, willAccept)) {
            throw new IllegalArgumentException("Invalid trade response");
        }
        TradeOffer trade = getModel().getTradeOffer();
        ResourceSet offer = trade.getOffer();
        getModel().setTradeOffer(null);
        if (willAccept) {
            receiver.getResources().combine(offer);
            offer.toNegative();
            getModel().getPlayer(trade.getSender()).getResources().combine(offer);
        }
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
        Set<Port> ports = getModel().getMap().getPlayerPorts(player.getPlayerIndex());
        if (ports.stream().anyMatch(p -> p.getPortType().getResource() == resourceType)) {
            return 2;
        }
        if (ports.stream().anyMatch(p -> p.getPortType() == PortType.THREE)) {
            return 3;
        }
        return 4;
    }

    /**
     * Determines whether a player can perform a maritime trade with the given resource.
     * <p>
     * It must be the player's turn.
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
        int ratio = maritimeTradeRatio(player, inputResource);
        return inputResource != outputResource &&
                getModel().getTurnTracker().getStatus() == TurnStatus.PLAYING &&
                player.getResources().getOfType(inputResource) > 0 &&
                getModel().getBank().getOfType(outputResource) >= ratio &&
                getFacades().getTurn().isPlayersTurn(player);
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
        int ratio = maritimeTradeRatio(player, inputResource);
        ResourcesFacade resources = getFacades().getResources();
        resources.returnToBank(player, new ResourceSet(inputResource, 1));
        resources.receiveFromBank(player, new ResourceSet(outputResource, ratio));
    }
}
