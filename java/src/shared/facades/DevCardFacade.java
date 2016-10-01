package shared.facades;

import org.jetbrains.annotations.NotNull;
import shared.definitions.ResourceType;
import shared.models.game.ClientModel;
import shared.models.game.Player;

/**
 * Created by Philip on 9/17/2016.
 */
public class DevCardFacade extends AbstractFacade {

    /**
     * Constructor. Requires a valid game model to work.
     *
     * @param manager the manager to use, not null
     * @throws NullPointerException if {@code model} is null
     * @pre {@code model} is a valid {@link ClientModel}.
     * @post This provides valid operations on {@code model}.
     */
    public DevCardFacade(@NotNull FacadeManager manager) {
        super(manager);
    }

    /**
     * Checks if the {@code currentPlayer} can buy a development card.  The resources needed are one ore, wool, and grain
     * @param currentPlayer The player who is buying the development card during their turn
     * @pre {@link DevCardFacade#buyDevCard(Player)} calls this function
     *      {@code currentPlayer} is part of the current {@link ClientModel}
     * @post None.
     * @return True if {@code currentPlayer} can buy a development card, False if not
     */
    public boolean canBuyDevCard(@NotNull Player currentPlayer) {
        return false;
    }

    /**
     * The {@code currentPlayer} buys a random development card and returns the required resources to the bank or deck.
     * @param currentPlayer The player who is buying the development card during their turn.
     * @pre The player calls this method during their turn when they want to buy a development card.
     *      The method {@link DevCardFacade#canBuyDevCard(Player)} returns a true statement.
     * @post The {@code currentPlayer} gets a random development card
     */
    public void buyDevCard(@NotNull Player currentPlayer) {

    }

    /**
     * Checks to see if the {@code currentPlayer} can use a development card.  A player can use only one development card
     *  during their turn and that card cannot be bought and used on the same turn
     *  Note: Only victory point cards can be played on the same turn, though they can only be played when the player has a total of 10 victory points
     * @param currentPlayer The player who is using their development card during their turn.
     * @pre This method is called by {@link DevCardFacade#canUseDevCard(Player)}
     *      {@code currentPlayer} is part of the current {@link ClientModel}.
     * @post None.
     * @return True if the player can use a development card, False if not
     */
    public boolean canUseDevCard(@NotNull Player currentPlayer) {
        return false;
    }

    /**
     * The {@code currentPlayer} uses the development card and discards it.  This method calls other methods that relate to the individual cards
     * @param currentPlayer The player who is using their development card during their turn.
     * @pre This method is called when the player uses a development card
     *      {@link DevCardFacade#canUseDevCard(Player)} returns a true statement
     * @post This calls another method that is connected to the development card action
     *       The card is discarded and cannot be used for the rest of the game
     */
    public void useDevCard(@NotNull Player currentPlayer) {

    }

    /**
     * The {@code currentPlayer} uses the Soldier development card
     * @param currentPlayer The player who is using their development card during their turn.
     * @pre This method is called by {@link DevCardFacade#useDevCard(Player)} when the card to be used is a Soldier card
     * @post The robber is activated
     *       The card cannot be reused for the rest of the game
     *       If applicable, the {@code currentPlayer} gets the Largest Army card
     */
    public void useSoldierCard(@NotNull Player currentPlayer) {

    }

    /**
     * Does the {@code currentPlayer} have 10 victory points, including the unplayed victory point cards.  This method takes the {@code currentPlayer}'s current victory points and adds the unplayed victory point cards to see if they equal 10
     * @param currentPlayer The player who is using their development card during their turn.
     * @pre This method is called by {@link DevCardFacade#useVictoryPointCards(Player)}
     * @post None.
     * @return True if the total victory point count is 10 (or more), False otherwise
     */
    public boolean canUseVictoryPointCards(@NotNull Player currentPlayer) {
        return false;
    }

    /**
     * The {@code currentPlayer} uses all of their the Victory Point development cards.  This can only be played when the {@code currentPlayer}'s total victory points equal 10, including unplayed victory point cards
     * @param currentPlayer The player who is using their development card during their turn.
     * @pre This method is called by {@link DevCardFacade#useDevCard(Player)} when the card to be used is a Victory Point card
     *      The method {@link DevCardFacade#canUseVictoryPointCards(Player)} returns a true statement
     * @post Played victory point cards add points to the {@code currentPlayer}'s visible victory point count
     *       The card cannot be reused for the rest of the game
     */
    public void useVictoryPointCards(@NotNull Player currentPlayer) {

    }

    /**
     * This method checks if the {@code currentPlayer} have 2 roads that are not placed on the map.
     * @param currentPlayer The player who is using their development card during their turn.
     * @pre This method is called by {@link DevCardFacade#useRoadBuildingCard(Player)}.
     * @post None.
     * @return True if the {@code currentPlayer} has 2 roads that are not placed on the map, False otherwise
     */
    public boolean canUseRoadBuildingCard(@NotNull Player currentPlayer) {
        return false;
    }

    /**
     * The {@code currentPlayer} uses the Road Building development card.  The {@code currentPlayer} places 2 roads down
     * @param currentPlayer The player who is using their development card during their turn.
     * @pre This method is called by {@link DevCardFacade#useDevCard(Player)} when the card to be used is a Road Building card
     *      The method {@link DevCardFacade#canUseRoadBuildingCard(Player)} returns a true statement.
     * @post Place 2 roads on the map according to the rules of placing roads
     *       The card is discarded and cannot be used for the rest of the game
     *       If applicable, the {@code currentPlayer} gets the Longest Road card
     */
    public void useRoadBuildingCard(@NotNull Player currentPlayer) {

    }

    /**
     * Are the requested {@code resourceType1} and {@code resourceType2} available to draw?
     * @param currentPlayer The player who is using their development card during their turn.
     * @param resourceType1 The first resource desired by the {@code currentPlayer}
     * @param resourceType2 The second resource desired by the {@code currentPlayer}
     * @pre This method is called by {@link DevCardFacade#useYearOfPlentyCard(Player, ResourceType, ResourceType)}
     *      {@code resourceType1} and {@code resourceType2} are not null
     * @post None.
     * @return True if both {@code resourceType1} and {@code resourceType2} are available, False if not
     */
    public boolean canUseYearOfPlentyCard(@NotNull Player currentPlayer, @NotNull ResourceType resourceType1, @NotNull ResourceType resourceType2) {
        return false;
    }

    /**
     * The {@code currentPlayer} uses the Year of Plenty development card.  The {@code currentPlayer} draws 2 resources of the {@code currentPlayer}'s choice
     * @param currentPlayer The player who is using their development card during their turn.
     * @param resourceType1 The first resource desired by the {@code currentPlayer}
     * @param resourceType2 The second resource desired by the {@code currentPlayer}
     * @pre This method is called by {@link DevCardFacade#useDevCard(Player)} when the card to be used is a Year of Plenty card
     *      The method {@link DevCardFacade#canUseYearOfPlentyCard(Player, ResourceType, ResourceType)} returns a true statement
     * @post Draw 2 resource cards of the {@code currentPlayer}'s choice, as long as the cards are available
     *       The card is discarded and cannot be used for the rest of the game
     */
    public void useYearOfPlentyCard(@NotNull Player currentPlayer, @NotNull ResourceType resourceType1, @NotNull ResourceType resourceType2) {

    }

    /**
     * The {@code currentPlayer} uses the Monopoly development card.  The {@code currentPlayer} declares one resource card and all other players give the {@code currentPlayer} all resources of that same resource
     * @param currentPlayer The player who is using their development card during their turn.
     * @param monopolyType The resource type that the {@code currentPlayer} wants
     * @pre This method is called by {@link DevCardFacade#useDevCard(Player)} when the card to be used is a Monopoly card
     * @post The {@code currentPlayer} declares one resource card and all other players give the {@code currentPlayer} all resources of that same resource type
     *       The card is discarded and cannot be used for the rest of the game
     */
    public void useMonopolyCard(@NotNull Player currentPlayer, @NotNull ResourceType monopolyType) {

    }
}
