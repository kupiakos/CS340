package shared.facades;

import org.jetbrains.annotations.NotNull;
import shared.definitions.Constants;
import shared.definitions.PlayerIndex;
import shared.definitions.PurchaseType;
import shared.definitions.ResourceType;
import shared.models.game.ClientModel;
import shared.models.game.GameMap;
import shared.models.game.Player;
import shared.models.game.ResourceSet;
import shared.utils.MapUtils;

import java.util.HashMap;
import java.util.Map;

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
        ResourceSet itemCost = item.purchaseCost();
        return itemCost.isSubset(player.getResources());

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
        returnToBank(player, item.purchaseCost());
    }

    /**
     * Whether the player can receive the resources from the bank.
     * <p>
     * The bank must have the resources to be able to fulfill the request from the player
     *
     * @param resources the set of resources requested to be given to the player from the bank, not null
     * @return whether the player can receive the resources from the bank
     */
    public boolean canReceiveFromBank(@NotNull ResourceSet resources) {
        return !(resources.isEmpty() || resources.hasNegative()) && !resources.isSubset(getModel().getBank());
    }

    /**
     * Whether the player can receive the resources from the bank.
     * <p>
     * The bank must have the resources to be able to fulfill the request made by the player.
     *
     * @param player    the player to check against, not null
     * @param resources the set of resources requested from the bank, not null
     * @pre {@link #canReceiveFromBank} returns true.
     * @post The bank has its resources deducted by {@code resources},
     * which are received by {@code player}.
     */
    public void receiveFromBank(@NotNull Player player, @NotNull ResourceSet resources) {
        if (!canReceiveFromBank(resources)) {
            throw new IllegalArgumentException("Invalid bank receive");
        }
        getModel().getBank().subtract(resources);
        player.getResources().combine(resources);
    }

    /**
     * Whether the player can return the specified resources to the bank.
     * <p>
     * The player must have the resources to be able to fulfill the request.
     *
     * @param player    the player to check against, not null
     * @param resources the set of resources returning to the bank, not null
     * @return whether the player can return the resources to the bank
     */
    public boolean canReturnToBank(@NotNull Player player, @NotNull ResourceSet resources) {
        return !(resources.isEmpty() || resources.hasNegative()) && resources.isSubset(player.getResources());
    }

    /**
     * Return the specified resources to the bank.
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
        if (!canReturnToBank(player, resources)) {
            throw new IllegalArgumentException("Invalid bank receive");
        }
        player.getResources().subtract(resources);
        getModel().getBank().combine(resources);
    }

    /**
     * Determine the award all players would receive from a given roll based on the
     * hexes they have settlements or cities on.
     * <p>
     * <ul>
     * <li>All settlements award 1 of a hex's resource.</li>
     * <li>All cities award 2 of a hex's resource.</li>
     * <li>
     * If there is not enough of a given resource in the bank to
     * fulfill everyone's production, then no one can receive any of the offending resource.
     * </li>
     * <li>
     * If a hex is of the desert or water type or has the robber, it will not award anything.
     * </li>
     * </ul>
     * <p>
     * The map returned describes what each player's would earn from their settlements/cities.
     * The null key of the map returned will contain all resources that would not be awarded.
     *
     * @param roll the number rolled by the die, in range [1-6],[8-12].
     * @return a map from player to the resources awarded, null key is rejected resources
     */
    public Map<PlayerIndex, ResourceSet> getAwardsFromHexes(int roll) {
        if (roll < 0 || roll > 12 || roll == Constants.ROBBER_ROLL) {
            throw new IllegalArgumentException("Illegal roll value to award");
        }
        GameMap gameMap = getModel().getMap();

        // Get the resources awarded for each player
        Map<PlayerIndex, ResourceSet> awards = gameMap.getHexesWithNumber(roll).stream()
                // Filter out desert and water hexes
                .filter(hex -> hex.getResource().getResource() != null)
                // Filter out the hex with the robber
                .filter(hex -> hex.getLocation() != gameMap.getRobber())
                // Map one Hex to multiple <VertexLocation, ResourceType> for every building
                .flatMap(hex -> gameMap.getHexBuildings(hex.getLocation()).stream()
                        .map(loc -> MapUtils.createEntry(loc, hex.getResource().getResource())))
                // Map <VertexLocation, ResourceType> to <PlayerIndex, ResourceSet>
                .map(e -> MapUtils.createEntry(
                        // Map VertexLocation -> PlayerIndex
                        gameMap.getBuildingOwner(e.getKey()),
                        // Create a new ResourceSet from each settlement/city containing the award from it
                        new ResourceSet(e.getValue(), gameMap.getCityOwner(e.getKey()) == null ? 1 : 2)))
                // Merge <PlayerIndex, ResourceSet> to create a map from PlayerIndex -> combined ResourceSets
                .collect(HashMap<PlayerIndex, ResourceSet>::new,
                        (m, e) -> m.merge(e.getKey(), e.getValue(), ResourceSet::combined),
                        (m1, m2) -> MapUtils.mergeMaps(m1, m2, ResourceSet::combined));
        // Ensure we have one of each PlayerIndex
        PlayerIndex.valuesStream().forEach(idx -> awards.putIfAbsent(idx, new ResourceSet()));
        awards.put(null, new ResourceSet());

        ResourceSet totalRequested = awards.values().stream().reduce(new ResourceSet(), ResourceSet::combined);

        ResourceType.valuesStream()
                // Only keep the resources the bank can't afford
                .filter(type -> !canReceiveFromBank(new ResourceSet(type, totalRequested.getOfType(type))))
                // For each of the offending resources, make sure no player gets them and put them in null
                .forEach(type -> {
                    PlayerIndex.valuesStream().forEach(idx -> awards.get(idx).setOfType(type, 0));
                    awards.get(null).setOfType(type, totalRequested.getOfType(type));
                });

        return awards;
    }

    /**
     * Whether the specified awards (returned from {@link #getAwardsFromHexes(int)}.
     *
     * @param awards the awards to check
     * @return true if the awards could be given; false otherwise
     */
    public boolean canGiveAwards(Map<PlayerIndex, ResourceSet> awards) {
        return awards.entrySet().stream()
                .filter(e -> e.getKey() != null)
                .map(Map.Entry::getValue)
                .allMatch(this::canReceiveFromBank);
    }

    /**
     * Give the specified awards to the players (returned from {@link #getAwardsFromHexes(int)}.
     *
     * @param awards the awards to check
     */
    public void giveAwards(Map<PlayerIndex, ResourceSet> awards) {
        if (!canGiveAwards(awards)) {
            throw new IllegalArgumentException("Illegal giving of awards");
        }
        awards.entrySet().forEach(e -> {
            if (e.getValue() != null)
                receiveFromBank(getModel().getPlayer(e.getKey()), e.getValue());
        });
    }
}
