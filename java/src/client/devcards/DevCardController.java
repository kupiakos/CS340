package client.devcards;

import client.base.Controller;
import client.base.IAction;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.models.moves.BuyDevCardAction;
import shared.models.moves.MonopolyAction;
import shared.models.moves.MonumentAction;
import shared.models.moves.YearofPlentyAction;
//import static org.mockito.Mockito.*;


/**
 * "Dev card" controller implementation
 */
public class DevCardController extends Controller implements IDevCardController {
    private IBuyDevCardView buyCardView;
    private IAction soldierAction;
    private IAction roadAction;

    /**
     * DevCardController constructor
     *
     * @param view          "Play dev card" view
     * @param buyCardView   "Buy dev card" view
     * @param soldierAction Action to be executed when the user plays a soldier card.  It calls "mapController.playSoldierCard()".
     * @param roadAction    Action to be executed when the user plays a road building card.  It calls "mapController.playRoadBuildingCard()".
     */
    public DevCardController(IPlayDevCardView view, IBuyDevCardView buyCardView,
                             IAction soldierAction, IAction roadAction) {
        super(view);
        this.buyCardView = buyCardView;
        this.soldierAction = soldierAction;
        this.roadAction = roadAction;
        observeClientModel();
    }

    public IPlayDevCardView getPlayCardView() {
        return (IPlayDevCardView) super.getView();
    }

    public IBuyDevCardView getBuyCardView() {
        return buyCardView;
    }

    @Override
    public void startBuyCard() {
        getBuyCardView().showOneModal();
    }

    @Override
    public void cancelBuyCard() {
        getBuyCardView().closeOneModal();
    }

    @Override
    public void buyCard() {
        if (getFacade().getDevCards().canBuyDevCard(getPlayer())) {
            getFacade().getDevCards().buyDevCard(getPlayer());
            getBuyCardView().closeModal();
            getAsync().runModelMethod(server::buyDevCard, new BuyDevCardAction(getPlayer().getPlayerIndex()))
                    .onError(Throwable::printStackTrace)
                    .start();
            getBuyCardView().closeOneModal();
        }
    }

    @Override
    public void startPlayCard() {
        getPlayCardView().reset();
        setPlayableDevCards();
        getPlayCardView().showModal();
    }

    @Override
    public void cancelPlayCard() {
        getPlayCardView().closeModal();
    }

    @Override
    public void playMonopolyCard(ResourceType resource) {
        if (getFacade().getDevCards().canUseMonopolyCard(getPlayer(), resource)) {
            getFacade().getDevCards().useMonopolyCard(getPlayer(), resource);
            getAsync().runModelMethod(server::useMonopoly, new MonopolyAction(getPlayer().getPlayerIndex(), resource))
                    .onError(Throwable::printStackTrace)
                    .start();
            getPlayCardView().closeModal();
        }
    }

    @Override
    public void playMonumentCard() {
        if (getFacade().getDevCards().canUseVictoryPointCards(getPlayer())) {
            getFacade().getDevCards().useVictoryPointCards(getPlayer());
            getAsync().runModelMethod(server::useMonument, new MonumentAction(getPlayer().getPlayerIndex()))
                    .onError(Throwable::printStackTrace)
                    .start();
            getPlayCardView().closeModal();
        }
    }

    @Override
    public void playRoadBuildCard() {
        if (getFacade().getDevCards().canUseRoadBuildingCard(getPlayer())) {
            getFacade().getDevCards().useRoadBuildingCard(getPlayer());
            getPlayCardView().closeModal();
            roadAction.execute();
        }
    }

    @Override
    public void playSoldierCard() {
        if (getFacade().getDevCards().canUseSoldierCard(getPlayer())) {
            getFacade().getDevCards().useSoldierCard(getPlayer());
            getPlayCardView().closeModal();
            soldierAction.execute();
        }
    }

    @Override
    public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2) {
        if (getFacade().getDevCards().canUseYearOfPlentyCard(getPlayer(), resource1, resource2)) {
            getFacade().getDevCards().useYearOfPlentyCard(getPlayer(), resource1, resource2);
            getAsync().runModelMethod(server::useYearOfPlenty,
                    new YearofPlentyAction(resource1, getPlayer().getPlayerIndex(), resource2))
                    .onError(Throwable::printStackTrace)
                    .start();
            getPlayCardView().closeModal();
            // DevCardFacade uses ResourceFacade to check two resource cards in year of plenty
        }
    }

    public void setPlayableDevCards() {
        for (DevCardType d : DevCardType.values()) {
            getPlayCardView().setCardAmount(d, getPlayer().getOldDevCards().getOfType(d));
            getPlayCardView().setCardEnabled(d, getFacade().getDevCards().canUseDevCard(getPlayer(), d));
        }
    }
}

