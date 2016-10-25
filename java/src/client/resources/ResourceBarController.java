package client.resources;

import client.base.Controller;
import client.base.IAction;
import shared.definitions.PlayerIndex;
import shared.definitions.PurchaseType;
import shared.facades.ResourcesFacade;
import shared.models.game.ClientModel;
import shared.models.game.Player;

import java.util.HashMap;
import java.util.Map;


/**
 * Implementation for the resource bar controller
 */
public class ResourceBarController extends Controller implements IResourceBarController {

    private Map<ResourceBarElement, IAction> elementActions;

    public ResourceBarController(IResourceBarView view) {
        super(view);
        observeClientModel();
        elementActions = new HashMap<ResourceBarElement, IAction>();
    }

    @Override
    public IResourceBarView getView() {
        return (IResourceBarView) super.getView();
    }

    /**
     * Sets the action to be executed when the specified resource bar element is clicked by the user
     *
     * @param element The resource bar element with which the action is associated
     * @param action  The action to be executed
     */
    public void setElementAction(ResourceBarElement element, IAction action) {

        elementActions.put(element, action);
    }

    /**
     * Called when the user presses the enabled build road button.
     * Activates the modal to display, so that it can then build a road
     */
    @Override
    public void buildRoad() {
        executeElementAction(ResourceBarElement.ROAD);
    }

    /**
     * Called when the user presses the enabled build settlement button.
     * Activates the modal to display, so that it can then build a settlement
     */
    @Override
    public void buildSettlement() {
        executeElementAction(ResourceBarElement.SETTLEMENT);
    }

    /**
     * Called when the user presses the enabled build city button.
     * Activates the modal to display, so that it can then build a city
     */
    @Override
    public void buildCity() {
        executeElementAction(ResourceBarElement.CITY);
    }

    /**
     * Called when the user presses the enabled buy card button.
     * Activates the modal to display, so that it can then buy a card
     */
    @Override
    public void buyCard() {
        executeElementAction(ResourceBarElement.BUY_CARD);
    }

    /**
     * Called when the user presses the play card button
     * Activates the modal to display, so that it can then play a card
     */
    @Override
    public void playCard() {
        executeElementAction(ResourceBarElement.PLAY_CARD);
    }

    private void executeElementAction(ResourceBarElement element) {

        if (elementActions.containsKey(element)) {

            IAction action = elementActions.get(element);
            action.execute();
        }
    }

    /**
     * Updates the Resource Bar to match the contents of the player's hand in the model
     */
    @Override
    public void updateFromModel(ClientModel model) {

        Player player = getPlayer();
        ResourcesFacade resourcesFacade = getFacade().getResources();

        int brick = player.getResources().getBrick();
        getView().setElementAmount(ResourceBarElement.BRICK, brick);
        int ore = player.getResources().getOre();
        getView().setElementAmount(ResourceBarElement.ORE, ore);
        int sheep = player.getResources().getSheep();
        getView().setElementAmount(ResourceBarElement.SHEEP, sheep);
        int wood = player.getResources().getWood();
        getView().setElementAmount(ResourceBarElement.WOOD, wood);
        int wheat = player.getResources().getWheat();
        getView().setElementAmount(ResourceBarElement.WHEAT, wheat);

        int roads = player.getRoads();
        getView().setElementAmount(ResourceBarElement.ROAD, roads);
        getView().setElementEnabled(ResourceBarElement.ROAD, resourcesFacade.canPurchaseItem(player, PurchaseType.ROAD));

        int settlements = player.getSettlements();
        getView().setElementAmount(ResourceBarElement.SETTLEMENT, settlements);
        getView().setElementEnabled(ResourceBarElement.SETTLEMENT, resourcesFacade.canPurchaseItem(player, PurchaseType.SETTLEMENT));

        int cities = player.getCities();
        getView().setElementAmount(ResourceBarElement.CITY, cities);
        getView().setElementEnabled(ResourceBarElement.CITY, resourcesFacade.canPurchaseItem(player, PurchaseType.CITY));

        PlayerIndex currentPlayerIndex = model.getTurnTracker().getCurrentTurn();
        Player currentPlayer = model.getPlayer(currentPlayerIndex);

        if (player.equals(currentPlayer)) {
            getView().setElementEnabled(ResourceBarElement.BUY_CARD, true);
            getView().setElementEnabled(ResourceBarElement.PLAY_CARD, true);
        } else {
            getView().setElementEnabled(ResourceBarElement.BUY_CARD, false);
            getView().setElementEnabled(ResourceBarElement.PLAY_CARD, false);
        }

        int soldiers = player.getSoldiers();
        getView().setElementAmount(ResourceBarElement.SOLDIERS, soldiers);

    }
}

