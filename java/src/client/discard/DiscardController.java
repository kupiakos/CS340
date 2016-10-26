package client.discard;

import client.base.Controller;
import client.game.IGameManager;
import client.misc.IWaitView;
import shared.IServer;
import shared.definitions.ResourceType;
import shared.definitions.TurnStatus;
import shared.facades.RobberFacade;
import shared.models.game.ClientModel;
import shared.models.game.Player;
import shared.models.game.ResourceSet;
import shared.models.moves.DiscardCardsAction;

import java.util.HashMap;
import java.util.Map;


/**
 * Discard controller implementation
 */
public class DiscardController extends Controller implements IDiscardController {

    private IWaitView waitView;
    private RobberFacade facade;
    private Player player;
    private ResourceSet discardResources;
    private Map<ResourceType, Integer> resourceAmounts;
    private int maxDiscard;

    /**
     * DiscardController constructor
     *
     * @param view     View displayed to let the user select cards to discard
     * @param waitView View displayed to notify the user that they are waiting for other players to discard
     */
    public DiscardController(IDiscardView view, IWaitView waitView) {

        super(view);
        this.waitView = waitView;
        discardResources = new ResourceSet(0, 0, 0, 0, 0);
        facade = getFacade().getRobber();
        player = null;
        resourceAmounts = new HashMap<>();
        setResourceAmounts();
        maxDiscard = 0;
    }

    public DiscardController(IDiscardView view, IWaitView waitView, IGameManager gm, IServer server) {
        super(view);
        setGameManager(gm);
        setServer(server);
        this.waitView = waitView;
        discardResources = new ResourceSet(0, 0, 0, 0, 0);
        facade = getFacade().getRobber();
        player = getPlayer();
        resourceAmounts = new HashMap<>();
        setResourceAmounts();
        maxDiscard = player.getResources().getTotal() / 2;
    }

    public IDiscardView getDiscardView() {
        return (IDiscardView) super.getView();
    }

    public IWaitView getWaitView() {
        return waitView;
    }

    @Override
    public void increaseAmount(ResourceType resource) {
        if (maxDiscard < 4) {
            getDiscardView().closeModal();
            getWaitView().showModal();
            return;
        }

        int total = totalResourceAmounts();
        if (discardResources.getOfType(resource) < player.getResources().getOfType(resource) && total < maxDiscard) {
            discardResources.setOfType(resource, discardResources.getOfType(resource) + 1);
            resourceAmounts.replace(resource, discardResources.getOfType(resource));
            total++;
        }

        if (total == maxDiscard) {
            getDiscardView().setDiscardButtonEnabled(true);
        }
    }

    @Override
    public void decreaseAmount(ResourceType resource) {
        if (maxDiscard < 4) {
            getDiscardView().closeModal();
            getWaitView().showModal();
            return;
        }

        if (discardResources.getOfType(resource) > 0) {
            discardResources.setOfType(resource, discardResources.getOfType(resource) - 1);
            resourceAmounts.replace(resource, discardResources.getOfType(resource));
        }
    }

    @Override
    public void discard() {
        if (maxDiscard < 4) {
            getDiscardView().closeModal();
            getWaitView().showModal();
            return;
        }

        if (facade.canDiscard(discardResources, player)) {
            facade.discard(discardResources, player);
            player.setDiscarded(true);
            getDiscardView().closeModal();
            getAsync().runModelMethod(server::discardCards, new DiscardCardsAction(discardResources, player.getPlayerIndex()))
                    .onError(Throwable::printStackTrace)
                    .start();
            discardResources = new ResourceSet(0, 0, 0, 0, 0);
            setResourceAmounts();
        }
    }

    @Override
    public void updateFromModel(ClientModel model) {
        player = getPlayer();
        maxDiscard = player.getResources().getTotal() / 2;
        for (ResourceType type : ResourceType.values()) {//This is just a precaution to ensure no null values are used, if there are null values
            if (!resourceAmounts.containsKey(type)) {
                resourceAmounts.put(type, 0);
            }
        }

        if (facade.canDiscard(discardResources, player)) {
            getDiscardView().setDiscardButtonEnabled(true);
        } else {
            getDiscardView().setDiscardButtonEnabled(false);
        }

        if (!player.isDiscarded() && facade.shouldDiscardHalf(player) && getModel().getTurnTracker().getStatus().equals(TurnStatus.DISCARDING)) {
            for (ResourceType type : ResourceType.values()) {
                if (player.getResources().getOfType(type) > 0) {
                    getDiscardView().setResourceMaxAmount(type, player.getResources().getOfType(type));
                    getDiscardView().setResourceAmountChangeEnabled(type, true, false);
                    getDiscardView().setResourceDiscardAmount(type, resourceAmounts.get(type));
                } else {
                    getDiscardView().setResourceMaxAmount(type, 0);
                    getDiscardView().setResourceAmountChangeEnabled(type, false, false);
                    getDiscardView().setResourceDiscardAmount(type, 0);
                }
            }
            getDiscardView().showModal();
        } else if ((player.isDiscarded() && getModel().getTurnTracker().getStatus().equals(TurnStatus.DISCARDING)) || !facade.shouldDiscardHalf(player)) {
            getWaitView().showModal();
        } else {
            player.setDiscarded(false);
        }
    }

    public void setResourceAmounts() {
        for (ResourceType type : ResourceType.values()) {
            resourceAmounts.put(type, 0);
        }
    }

    public Map<ResourceType, Integer> getResourceAmounts() {
        return resourceAmounts;
    }

    public int totalResourceAmounts() {
        int total = 0;
        for (ResourceType type : ResourceType.values()) {
            total = total + resourceAmounts.get(type);
        }
        return total;
    }

    public int getMaxDiscard() {
        return maxDiscard;
    }

    public ResourceSet getDiscardResources() {
        return discardResources;
    }
}

