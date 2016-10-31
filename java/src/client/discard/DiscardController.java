package client.discard;

import client.base.Controller;
import client.misc.IWaitView;
import shared.definitions.ResourceType;
import shared.definitions.TurnStatus;
import shared.facades.RobberFacade;
import shared.models.game.ClientModel;
import shared.models.game.Player;
import shared.models.game.ResourceSet;
import shared.models.moves.DiscardCardsAction;


/**
 * Discard controller implementation
 */
public class DiscardController extends Controller implements IDiscardController {

    private IWaitView waitView;
    private ResourceSet discardAmount;

    /**
     * DiscardController constructor
     *
     * @param view     View displayed to let the user select cards to discard
     * @param waitView View displayed to notify the user that they are waiting for other players to discard
     */
    public DiscardController(IDiscardView view, IWaitView waitView) {
        super(view);
        observeClientModel();
        this.waitView = waitView;
        discardAmount = new ResourceSet();
    }

    public IDiscardView getDiscardView() {
        return (IDiscardView) super.getView();
    }

    public IWaitView getWaitView() {
        return waitView;
    }

    private void adjustAmount(ResourceType resource, int shift) {
        // This should not have to be here
        if (getMaxDiscard() < 4) {
            return;
        }
        int newAmount = discardAmount.getOfType(resource) + shift;
        if (newAmount < 0 ||
                newAmount > getPlayer().getResources().getOfType(resource) ||
                discardAmount.getTotal() + shift > getMaxDiscard()) {
            return;
        }
        discardAmount.setOfType(resource, newAmount);
        getDiscardView().setResourceDiscardAmount(resource, newAmount);
        displayValue();
    }

    private void displayValue() {
        boolean atMax = discardAmount.getTotal() == getMaxDiscard();
        getDiscardView().setDiscardButtonEnabled(atMax);
        getDiscardView().setStateMessage(String.format("%d/%d", discardAmount.getTotal(), getMaxDiscard()));
        for (ResourceType resource : ResourceType.values()) {
            int amount = discardAmount.getOfType(resource);
            getDiscardView().setResourceAmountChangeEnabled(
                    resource,
                    !atMax && amount < getPlayer().getResources().getOfType(resource),
                    amount > 0
            );
        }
    }

    @Override
    public void increaseAmount(ResourceType resource) {
        adjustAmount(resource, 1);
    }

    @Override
    public void decreaseAmount(ResourceType resource) {
        adjustAmount(resource, -1);
    }

    @Override
    public void discard() {
        Player player = getPlayer();
        RobberFacade robbing = getFacade().getRobber();

        if (robbing.canDiscard(discardAmount, player)) {
            robbing.discard(discardAmount, player);
            player.setDiscarded(true);
            getDiscardView().closeModal();
            getAsync().runModelMethod(server::discardCards,
                    new DiscardCardsAction(discardAmount, player.getPlayerIndex()))
                    .onError(Throwable::printStackTrace)
                    .start();
            discardAmount = new ResourceSet();
        } else {
            getDiscardView().setStateMessage("Could not discard");
        }
    }

    @Override
    public void updateFromModel(ClientModel model) {
        Player player = getPlayer();
        RobberFacade robbing = getFacade().getRobber();
        getDiscardView().setDiscardButtonEnabled(robbing.canDiscard(discardAmount, player));

        if (getModel().getTurnTracker().getStatus() != TurnStatus.DISCARDING) {
            getDiscardView().closeOneModal();
            getWaitView().closeOneModal();
        } else if (player.hasDiscarded() || !robbing.shouldDiscardHalf(player)) {
            getDiscardView().closeOneModal();
            getWaitView().showOneModal();
        } else {
            getWaitView().closeOneModal();
            if (!getDiscardView().isModalShowing()) {
                discardAmount = new ResourceSet();
                for (ResourceType type : ResourceType.values()) {
                    int amount = player.getResources().getOfType(type);
                    // This is what's shown above, really shouldn't be actual max, but how many resources
                    getDiscardView().setResourceMaxAmount(type, amount);
                    getDiscardView().setResourceAmountChangeEnabled(type, amount > 0, false);
                    getDiscardView().setResourceDiscardAmount(type, 0);
                }
                displayValue();
                getDiscardView().showModal();
            }
        }
    }

    public int getMaxDiscard() {
        return getPlayer().getResources().getTotal() / 2;
    }

    public ResourceSet getDiscardAmount() {
        return discardAmount;
    }
}

