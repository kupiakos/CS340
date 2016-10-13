package client.resources;

import client.base.Controller;
import client.base.IAction;
import shared.models.game.ClientModel;

import java.util.HashMap;
import java.util.Map;


/**
 * Implementation for the resource bar controller
 */
public class ResourceBarController extends Controller implements IResourceBarController {

    private Map<ResourceBarElement, IAction> elementActions;

    public ResourceBarController(IResourceBarView view) {
        super(view);
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
//		int settlements = currentPlayer.getSettlements();
//		getView().setElementAmount(ResourceBarElement.SETTLEMENT, settlements);
//		if(buildingFacade.canBuildSettlement(currentPlayer,) && its the current person's turn') {
//			getView().setElementEnabled(ResourceBarElement.SETTLEMENT, true);
//		}
//		else {
//			getView().setElementEnabled(ResourceBarElement.SETTLEMENT, false);
//		}
//
//		int cities = currentPlayer.getCities();
//		getView().setElementAmount(ResourceBarElement.CITY, cities);
//		if(buildingFacade.canBuildCity(currentPlayer, ) && its the current person's turn') {
//			getView().setElementEnabled(ResourceBarElement.CITY, true);
//		}
//		else {
//			getView().setElementEnabled(ResourceBarElement.CITY, false);
//		}
//
//		if(its the players turn){
//			getView().setElementEnabled(ResourceBarElement.PLAY_CARD, true);
//		}
//		else {
//			getView().setElementEnabled(ResourceBarElement.PLAY_CARD, false);
//		}
//		//get old or new dev cards?
//		int roadBuilder = currentPlayer.getNewDevCards().getRoadBuilding();
//		getView().setElementAmount(ResourceBarElement, roadBuilder)
//		if(devCardFacade.canUseRoadBuildingCard(currentPlayer)) {
//			getView().setElementEnabled(ResourceBarElement, true);
//		}
//		else {
//			getView().setElementEnabled(ResourceBarElement., false);
//		}

    }
}

