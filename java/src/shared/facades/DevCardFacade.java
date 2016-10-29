package shared.facades;

import org.jetbrains.annotations.NotNull;
import shared.definitions.DevCardType;
import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;
import shared.definitions.TurnStatus;
import shared.locations.HexLocation;
import shared.models.game.ClientModel;
import shared.models.game.DevCardSet;
import shared.models.game.Player;
import shared.models.game.ResourceSet;

import java.util.Objects;

/**
 * Created by Philip on 9/17/2016.
 */
public class DevCardFacade extends AbstractFacade {

    ResourcesFacade resourcesFacade;

    /**
     * Constructor. Requires a valid game model to work. Also sets the {@code resourcesFacade} so that the
     * {@link DevCardFacade#useYearOfPlentyCard(Player, ResourceType, ResourceType)} can give the player two resource cards
     *
     * @param manager the manager to use, not null
     * @throws NullPointerException if {@code model} is null
     * @pre {@code model} is a valid {@link ClientModel}.
     * @post This provides valid operations on {@code model}.
     */
    public DevCardFacade(@NotNull FacadeManager manager) {
        super(manager);
        resourcesFacade = manager.getResources();
    }

    /**
     * Checks if the {@code currentPlayer} can buy a development card.  The resources needed are one ore, wool, and grain
     *
     * @param currentPlayer The player who is buying the development card during their turn
     * @return True if {@code currentPlayer} can buy a development card, False if not
     * @pre The {@code currentPlayer} calls this method when they want to buy a development card.
     * {@code currentPlayer} is part of the current {@link ClientModel}
     * {@link TurnStatus#PLAYING} is the turn status.
     * It is the {@code currentPlayer} turn.
     * There is at least one development card available
     * @post None.
     */
    public boolean canBuyDevCard(Player currentPlayer) {
        if (currentPlayer == null) {
            return false;
        }
        if (getFacades().getTurn().getPhase() != TurnStatus.PLAYING || getFacades().getTurn().isPlayersTurn(currentPlayer) == false) {
            return false;
        }
        DevCardSet all = new DevCardSet();
        for (Player p : getModel().getPlayers()) {
            all.combine(p.getNewDevCards());
            all.combine(p.getOldDevCards());
        }

        DevCardSet bank = new DevCardSet(2, 2, 5, 14, 2);
        if (all.getTotal() == bank.getTotal()) {
            return false;
        }
        ResourceSet set = currentPlayer.getResources();
        return set.getOre() > 0 && set.getSheep() > 0 && set.getWheat() > 0;
    }

    /**
     * The {@code currentPlayer} buys a random development card and returns the required resources to the bank or deck.
     *
     * @param currentPlayer The player who is buying the development card during their turn.
     * @throws IllegalArgumentException
     * @pre The {@code currentPlayer} calls this method during their turn when they want to buy a development card.
     * The method {@link DevCardFacade#canBuyDevCard(Player)} returns a true statement.
     * @post The {@code currentPlayer} gets a random development card
     */
    public void buyDevCard(@NotNull Player currentPlayer) {
        if (!canBuyDevCard(currentPlayer)) {
            throw new IllegalArgumentException();
        }
        DevCardSet bank = new DevCardSet(2, 2, 5, 14, 2);
        DevCardSet all = new DevCardSet();
        for (Player p : getModel().getPlayers()) {
            all.combine(p.getNewDevCards());
            all.combine(p.getOldDevCards());
        }
        for (DevCardType t : DevCardType.values()) {
            bank.setOfType(t, bank.getOfType(t) - all.getOfType(t));
        }
        DevCardType randomType = bank.getRandom();
        currentPlayer.getNewDevCards().setOfType(randomType, currentPlayer.getNewDevCards().getOfType(randomType) + 1);
    }

    /**
     * The {@code currentPlayer} attempts to use the Soldier development card
     *
     * @param currentPlayer The {@link Player} who is using their development card during their turn.
     * @return true if the {@code currentPlayer} can use the Soldier card. False if not
     * @pre This method is called by the controller when the card to be used is a Soldier card
     * {@code currentPlayer} is part of the current {@link ClientModel}
     * {@link TurnStatus#PLAYING} is the turn status.
     * It is the {@code currentPlayer} turn.
     * @post None.
     */
    public boolean canUseSoldierCard(Player currentPlayer) {
        if (currentPlayer == null) {
            return false;
        }
        if (getFacades().getTurn().getPhase() != TurnStatus.PLAYING || getFacades().getTurn().isPlayersTurn(currentPlayer) == false) {
            return false;
        }
        if (currentPlayer.isPlayedDevCard()) {
            return false;
        }
        return currentPlayer.getOldDevCards().getSoldier() > 0;
    }

    /**
     * The {@code currentPlayer} uses the Soldier development card
     *
     * @param currentPlayer The player who is using their development card during their turn.
     * @throws IllegalArgumentException
     * @pre This method is called by the controller when the card to be used is a Soldier card
     * {@code currentPlayer} is part of the current {@link ClientModel}
     * {@link DevCardFacade#canUseSoldierCard(Player)} returns a true statement
     * @post The robber is activated
     * The card cannot be reused for the rest of the game
     * If applicable, the {@code currentPlayer} gets the Largest Army card
     */
    public void useSoldierCard(@NotNull Player currentPlayer) {//, @NotNull HexLocation hexLocation, @NotNull Player targetPlayer
        if (!canUseSoldierCard(currentPlayer)) {
            throw new IllegalArgumentException();
        }
        currentPlayer.setPlayedDevCard(true);
        currentPlayer.getOldDevCards().setSoldier(currentPlayer.getOldDevCards().getSoldier() - 1);
        currentPlayer.setSoldiers(currentPlayer.getSoldiers() + 1);
        if (currentPlayer.getSoldiers() >= 3) {
            if (getModel().getTurnTracker().getLargestArmy() == null) {
                getModel().getTurnTracker().setLargestArmy(currentPlayer.getPlayerIndex());
                currentPlayer.setVictoryPoints(currentPlayer.getVictoryPoints() + 2);
            } else {
                PlayerIndex largestArmy = getModel().getTurnTracker().getLargestArmy();
                if (currentPlayer.getSoldiers() > getModel().getPlayer(largestArmy).getSoldiers()) {
                    getModel().getTurnTracker().setLargestArmy(currentPlayer.getPlayerIndex());
                    currentPlayer.setVictoryPoints(currentPlayer.getVictoryPoints() + 2);
                }
            }
        }

        getFacades().getTurn().startRobbing();


//        RobberFacade robberFacade = getFacades().getRobber();
//        getModel().getTurnTracker().setStatus(TurnStatus.ROBBING);
//        do {
//
//            Objects.requireNonNull(hexLocation);
//            Objects.requireNonNull(targetPlayer);
//            if (robberFacade.canMoveRobber(hexLocation)) {
//
//            }
//        } while (!robberFacade.canMoveRobber(hexLocation));
    }

    /**
     * Does the {@code currentPlayer} have 10 victory points, including the unplayed victory point cards.  This method takes the {@code currentPlayer}'s current victory points and adds the unplayed victory point cards to see if they equal 10
     *
     * @param currentPlayer The player who is using their development card during their turn.
     * @return True if the total victory point count is 10 (or more), False otherwise
     * @pre This method is called by the controller when the card to be used is a Victory Point card
     * {@code currentPlayer} is part of the current {@link ClientModel}
     * {@link TurnStatus#PLAYING} is the turn status.
     * It is the {@code currentPlayer} turn.
     * @post None.
     */
    public boolean canUseVictoryPointCards(Player currentPlayer) {
        if (currentPlayer == null) {
            return false;
        }
        if (getFacades().getTurn().getPhase() != TurnStatus.PLAYING || getFacades().getTurn().isPlayersTurn(currentPlayer) == false) {
            return false;
        }
        if (currentPlayer.isPlayedDevCard() == true) {
            return false;
        }
        int total = currentPlayer.getOldDevCards().getMonument() + currentPlayer.getNewDevCards().getMonument();
        return total > 0 && (currentPlayer.getVictoryPoints() + total) >= 10;
    }

    /**
     * The {@code currentPlayer} uses all of their the Victory Point development cards.  This can only be played when the {@code currentPlayer}'s total victory points equal 10, including unplayed victory point cards
     *
     * @param currentPlayer The player who is using their development card during their turn.
     * @throws IllegalArgumentException
     * @pre This method is called by the controller when the card to be used is a Victory Point card
     * {@code currentPlayer} is part of the current {@link ClientModel}
     * The method {@link DevCardFacade#canUseVictoryPointCards(Player)} returns a true statement
     * @post Played victory point cards add points to the {@code currentPlayer}'s visible victory point count
     * The card cannot be reused for the rest of the game
     */
    public void useVictoryPointCards(@NotNull Player currentPlayer) {
        if (!canUseVictoryPointCards(currentPlayer)) {
            throw new IllegalArgumentException();
        }
        currentPlayer.setPlayedDevCard(true);
        currentPlayer.setVictoryPoints(currentPlayer.getVictoryPoints() + currentPlayer.getOldDevCards().getMonument() + currentPlayer.getNewDevCards().getMonument());
        getModel().setWinner(currentPlayer.getPlayerIndex());
    }

    /**
     * This method checks if the {@code currentPlayer} have 2 roads that are not placed on the map.
     *
     * @param currentPlayer The player who is using their development card during their turn.
     * @return True if the {@code currentPlayer} has 2 roads that are not placed on the map, False otherwise
     * @pre This method is called by the controller when the card to be used is a Road Building card.
     * {@code currentPlayer} is part of the current {@link ClientModel}
     * @post None.
     */
    public boolean canUseRoadBuildingCard(Player currentPlayer) {
        if (currentPlayer == null) {
            return false;
        }
        if (getFacades().getTurn().getPhase() != TurnStatus.PLAYING || getFacades().getTurn().isPlayersTurn(currentPlayer) == false) {
            return false;
        }
        if (currentPlayer.isPlayedDevCard() == true) {
            return false;
        }
        if (currentPlayer.getOldDevCards().getRoadBuilding() == 0 || currentPlayer.getRoads() < 2) {
            return false;
        }
        return true;
    }

    /**
     * The {@code currentPlayer} uses the Road Building development card.  The {@code currentPlayer} places 2 roads down
     *
     * @param currentPlayer The player who is using their development card during their turn.
     * @throws IllegalArgumentException
     * @pre This method is called by the controller when the card to be used is a Road Building card
     * {@code currentPlayer} is part of the current {@link ClientModel}
     * The method {@link DevCardFacade#canUseRoadBuildingCard(Player)} returns a true statement.
     * @post Place 2 roads on the map according to the rules of placing roads
     * The card is discarded and cannot be used for the rest of the game
     * If applicable, the {@code currentPlayer} gets the Longest Road card
     */
    public void useRoadBuildingCard(@NotNull Player currentPlayer) {
        if (!canUseRoadBuildingCard(currentPlayer)) {
            throw new IllegalArgumentException();
        }
        currentPlayer.setPlayedDevCard(true);
        currentPlayer.getOldDevCards().setRoadBuilding(currentPlayer.getOldDevCards().getRoadBuilding() - 1);
        //Let the roads be placed through buildingFacade and check if the Longest Road card is given
    }

    /**
     * Are the requested {@code resourceType1} and {@code resourceType2} available to draw?
     *
     * @param currentPlayer The player who is using their development card during their turn.
     * @param resourceType1 The first resource desired by the {@code currentPlayer}
     * @param resourceType2 The second resource desired by the {@code currentPlayer}
     * @return True if both {@code resourceType1} and {@code resourceType2} are available, False if not
     * @pre This method is called by the controller when the card to be used is a Year of Plenty card
     * {@code currentPlayer} is part of the current {@link ClientModel}
     * {@code resourceType1} and {@code resourceType2} are not null
     * @post None.
     */
    public boolean canUseYearOfPlentyCard(Player currentPlayer, ResourceType resourceType1, ResourceType resourceType2) {
        if (currentPlayer == null || resourceType1 == null || resourceType2 == null) {
            return false;
        }
        if (getFacades().getTurn().getPhase() != TurnStatus.PLAYING || getFacades().getTurn().isPlayersTurn(currentPlayer) == false) {
            return false;
        }
        if (currentPlayer.isPlayedDevCard() == true) {
            return false;
        }
        if (currentPlayer.getOldDevCards().getYearOfPlenty() == 0) {
            return false;
        }
        if (resourceType1 == resourceType2) {
            ResourceSet resources = new ResourceSet(resourceType1, 2);
            if (!resourcesFacade.canReceiveFromBank(resources)) {
                return false;
            }
//            if (getModel().getBank().getOfType(resourceType1) < 2) {
//                return false;
//            }
        } else {
            ResourceSet resources1 = new ResourceSet(resourceType1, 1);
            ResourceSet resources2 = new ResourceSet(resourceType2, 1);
            if (!resourcesFacade.canReceiveFromBank(resources1) || !resourcesFacade.canReceiveFromBank(resources2)) {
                return false;
            }
//            if (getModel().getBank().getOfType(resourceType1) < 1) {
//                return false;
//            }
//            if (getModel().getBank().getOfType(resourceType2) < 1) {
//                return false;
//            }
        }
        return true;
    }

    /**
     * The {@code currentPlayer} uses the Year of Plenty development card.  The {@code currentPlayer} draws 2 resources of the {@code currentPlayer}'s choice
     *
     * @param currentPlayer The player who is using their development card during their turn.
     * @param resourceType1 The first resource desired by the {@code currentPlayer}
     * @param resourceType2 The second resource desired by the {@code currentPlayer}
     * @throws IllegalArgumentException
     * @pre This method is called by the controller when the card to be used is a Year of Plenty card
     * {@code currentPlayer} is part of the current {@link ClientModel}
     * {@code resourceType1} and {@code resourceType2} are valid {@link ResourceType}
     * The method {@link DevCardFacade#canUseYearOfPlentyCard(Player, ResourceType, ResourceType)} returns a true statement
     * @post Draw 2 resource cards of the {@code currentPlayer}'s choice, as long as the cards are available
     * The card is discarded and cannot be used for the rest of the game
     */
    public void useYearOfPlentyCard(@NotNull Player currentPlayer, @NotNull ResourceType resourceType1, @NotNull ResourceType resourceType2) {
        if (!canUseYearOfPlentyCard(currentPlayer, resourceType1, resourceType2)) {
            throw new IllegalArgumentException();
        }
        currentPlayer.setPlayedDevCard(true);
        currentPlayer.getOldDevCards().setYearOfPlenty(currentPlayer.getOldDevCards().getYearOfPlenty() - 1);
        if (resourceType1 == resourceType2) {
            ResourceSet resources = new ResourceSet(resourceType1, 2);
            resourcesFacade.receiveFromBank(currentPlayer, resources);
        } else {
            ResourceSet resources1 = new ResourceSet(resourceType1, 1);
            ResourceSet resources2 = new ResourceSet(resourceType2, 1);
            resourcesFacade.receiveFromBank(currentPlayer, resources1);
            resourcesFacade.receiveFromBank(currentPlayer, resources2);
        }
    }

    /**
     * The {@code currentPlayer} tries to use the Monopoly development card.
     *
     * @param currentPlayer The player who is using their development card during their turn.
     * @param monopolyType  The resource type that the {@code currentPlayer} wants
     * @return true if the Monopoly card can be used; False if not
     * @pre This method is called by the controller when the card to be used is a Monopoly card
     * {@code currentPlayer} is part of the current {@link ClientModel}
     * {@code monopolyType} is valid {@link ResourceType}
     * @post None.
     */
    public boolean canUseMonopolyCard(Player currentPlayer, ResourceType monopolyType) {
        if (currentPlayer == null || monopolyType == null) {
            return false;
        }
        if (getFacades().getTurn().getPhase() != TurnStatus.PLAYING || getFacades().getTurn().isPlayersTurn(currentPlayer) == false) {
            return false;
        }
        if (currentPlayer.isPlayedDevCard() == true) {
            return false;
        }
        if (currentPlayer.getOldDevCards().getMonopoly() == 0) {
            return false;
        }
        return true;
    }

    /**
     * The {@code currentPlayer} uses the Monopoly development card.  The {@code currentPlayer} declares one resource card and all other players give the {@code currentPlayer} all resources of that same resource
     *
     * @param currentPlayer The player who is using their development card during their turn.
     * @param monopolyType  The resource type that the {@code currentPlayer} wants
     * @throws IllegalArgumentException
     * @pre This method is called by the controller when the card to be used is a Monopoly card
     * {@code currentPlayer} is part of the current {@link ClientModel}
     * {@code monopolyType} is valid {@link ResourceType}
     * {@link DevCardFacade#canUseMonopolyCard(Player, ResourceType)} returns a true statement
     * @post The {@code currentPlayer} declares one resource card and all other players give the {@code currentPlayer} all resources of that same resource type
     * The card is discarded and cannot be used for the rest of the game
     */
    public void useMonopolyCard(@NotNull Player currentPlayer, @NotNull ResourceType monopolyType) {
        if (!canUseMonopolyCard(currentPlayer, monopolyType)) {
            throw new IllegalArgumentException();
        }
        currentPlayer.setPlayedDevCard(true);
        currentPlayer.getOldDevCards().setMonopoly(currentPlayer.getOldDevCards().getMonopoly() - 1);
        for (Player p : getModel().getPlayers()) {
            if (p != currentPlayer && p.getResources().getOfType(monopolyType) > 0) {
                int number = p.getResources().getOfType(monopolyType);
                p.getResources().setOfType(monopolyType, 0);
                currentPlayer.getResources().setOfType(monopolyType, currentPlayer.getResources().getOfType(monopolyType) + number);
            }
        }
    }
}
