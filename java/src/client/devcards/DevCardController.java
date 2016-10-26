package client.devcards;

import client.base.Controller;
import client.base.IAction;
import client.game.IGameManager;
import shared.IServer;
import shared.definitions.DevCardType;
import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;
import client.base.*;
import shared.facades.DevCardFacade;
import shared.models.game.ClientModel;
import shared.models.game.Player;
import shared.models.moves.*;
//import static org.mockito.Mockito.*;


/**
 * "Dev card" controller implementation
 */
public class DevCardController extends Controller implements IDevCardController {

    private IBuyDevCardView buyCardView;
    private IAction soldierAction;
    private IAction roadAction;
    //New
    private DevCardFacade facade;
    private Player currentPlayer;
    private PlayerIndex index;
    private boolean playingCard;
    private boolean buyingCard;

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
        facade = getGameManager().getFacade().getDevCards();
        index = null;
        currentPlayer = null;
        playingCard = false;
        buyingCard = false;
    }

    public DevCardController(IPlayDevCardView view, IBuyDevCardView buyCardView,
                             IAction soldierAction, IAction roadAction,
                             IGameManager gm, IServer server) {
        super(view);
        setGameManager(gm);
        setServer(server);
        this.buyCardView = buyCardView;
        this.soldierAction = soldierAction;
        this.roadAction = roadAction;
        facade = getGameManager().getFacade().getDevCards();
        index = getModel().getTurnTracker().getCurrentTurn();
        currentPlayer = getModel().getPlayer(index);
        playingCard = false;
        buyingCard = false;
    }

    public IPlayDevCardView getPlayCardView() {
        return (IPlayDevCardView) super.getView();
    }

    public IBuyDevCardView getBuyCardView() {
        return buyCardView;
    }

    @Override
    public void startBuyCard() {
        buyingCard = true;
        getBuyCardView().showModal();
    }

    @Override
    public void cancelBuyCard() {
        buyingCard = false;
        getBuyCardView().closeModal();
    }

    @Override
    public void buyCard() {
        if (facade.canBuyDevCard(currentPlayer)) {
            facade.buyDevCard(currentPlayer);
            getBuyCardView().closeModal();
            getAsync().runModelMethod(server::buyDevCard, new BuyDevCardAction(index))
                    .onError(Throwable::printStackTrace)
                    .start();
            buyingCard = false;
            getBuyCardView().closeModal();
        }
    }

    @Override
    public void startPlayCard() {
        setPlayableDevCards();
        playingCard = true;
        getPlayCardView().showModal();
    }

    @Override
    public void cancelPlayCard() {
        playingCard = false;
        getPlayCardView().closeModal();
    }

    @Override
    public void playMonopolyCard(ResourceType resource) {
        if (facade.canUseMonopolyCard(currentPlayer, resource)) {
            facade.useMonopolyCard(currentPlayer, resource);
            getAsync().runModelMethod(server::useMonopoly, new MonopolyAction(index, resource))
                    .onError(Throwable::printStackTrace)
                    .start();
            playingCard = false;
            getPlayCardView().closeModal();
        }
    }

    @Override
    public void playMonumentCard() {
        if (facade.canUseVictoryPointCards(currentPlayer)) {
            facade.useVictoryPointCards(currentPlayer);
            getAsync().runModelMethod(server::useMonument, new MonumentAction(index))
                    .onError(Throwable::printStackTrace)
                    .start();
            playingCard = false;
            getPlayCardView().closeModal();
        }
    }

    @Override
    public void playRoadBuildCard() {
        if (facade.canUseRoadBuildingCard(currentPlayer)) {
            facade.useRoadBuildingCard(currentPlayer);
            roadAction.execute();
            playingCard = false;
            getPlayCardView().closeModal();
        }
    }

    @Override
    public void playSoldierCard() {
        if (facade.canUseSoldierCard(currentPlayer)) {
            facade.useSoldierCard(currentPlayer);
            soldierAction.execute();
            playingCard = false;
            getPlayCardView().closeModal();
        }
    }

    @Override
    public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2) {
        if (facade.canUseYearOfPlentyCard(currentPlayer, resource1, resource2)) {
            facade.useYearOfPlentyCard(currentPlayer, resource1, resource2);
            getAsync().runModelMethod(server::useYearOfPlenty, new YearofPlentyAction(resource1, index, resource2))
                    .onError(Throwable::printStackTrace)
                    .start();
            playingCard = false;
            getPlayCardView().closeModal();
            //DevCardFacade uses ResourceFacade to check two resource cards in year of plenty
        }
    }

    @Override
    public void updateFromModel(ClientModel model) {
        index = getModel().getTurnTracker().getCurrentTurn();
        currentPlayer = getModel().getPlayer(index);
        if (buyingCard) {
            getBuyCardView().showModal();
        } else if (playingCard) {
            setPlayableDevCards();
            getPlayCardView().showModal();
        }
    }

    public void setPlayableDevCards() {
        for (DevCardType d : DevCardType.values()) {
            getPlayCardView().setCardAmount(d, currentPlayer.getOldDevCards().getOfType(d));
            if (currentPlayer.getOldDevCards().getOfType(d) > 0) {
                getPlayCardView().setCardEnabled(d, true);
            } else {
                getPlayCardView().setCardEnabled(d, false);
            }
        }
    }
}

