package client.resources;

import client.base.Controller;
import client.base.IAction;
import shared.definitions.PurchaseType;
import shared.definitions.ResourceType;
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
        elementActions = new HashMap<>();
        observeClientModel();
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

    @Override
    public void buildRoad() {
        executeElementAction(ResourceBarElement.ROAD); //calls mapcontroller.placeRoad
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
    //play roadBuilder

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

        for (ResourceType resource : ResourceType.values()) {
            getView().setElementAmount(
                    ResourceBarElement.fromResource(resource),
                    player.getResources().getOfType(resource));
        }

        int roads = player.getRoads();
        getView().setElementAmount(ResourceBarElement.ROAD, roads);
        getView().setElementEnabled(ResourceBarElement.ROAD,
                resourcesFacade.canPurchaseItem(player, PurchaseType.ROAD));

        int settlements = player.getSettlements();
        getView().setElementAmount(ResourceBarElement.SETTLEMENT, settlements);
        getView().setElementEnabled(ResourceBarElement.SETTLEMENT,
                resourcesFacade.canPurchaseItem(player, PurchaseType.SETTLEMENT));

        int cities = player.getCities();
        getView().setElementAmount(ResourceBarElement.CITY, cities);
        getView().setElementEnabled(ResourceBarElement.CITY,
                resourcesFacade.canPurchaseItem(player, PurchaseType.CITY));

        int soldiers = player.getSoldiers();
        getView().setElementAmount(ResourceBarElement.SOLDIERS, soldiers);

        getView().setElementEnabled(ResourceBarElement.BUY_CARD,
                getFacade().getDevCards().canBuyDevCard(player));
        getView().setElementEnabled(ResourceBarElement.PLAY_CARD,
                getFacade().getDevCards().canUseDevCard(player));
    }
}

