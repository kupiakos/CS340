package client.maritime;

import client.base.Controller;
import client.map.MapController;
import shared.definitions.ResourceType;
import shared.facades.TradingFacade;
import shared.models.game.ClientModel;
import shared.models.game.Player;
import shared.models.moves.MaritimeTradeAction;

import java.util.logging.Logger;


/**
 * Implementation for the maritime trade controller
 */
public class MaritimeTradeController extends Controller implements IMaritimeTradeController {

    private static final Logger LOGGER = Logger.getLogger(MapController.class.getSimpleName());

    private ResourceType giveType, getType;
    private IMaritimeTradeOverlay tradeOverlay;

    public MaritimeTradeController(IMaritimeTradeView tradeView, IMaritimeTradeOverlay tradeOverlay) {
        super(tradeView);
        setTradeOverlay(tradeOverlay);
        observeClientModel();
    }

    public IMaritimeTradeView getTradeView() {
        return (IMaritimeTradeView) super.getView();
    }

    public IMaritimeTradeOverlay getTradeOverlay() {
        return tradeOverlay;
    }

    public void setTradeOverlay(IMaritimeTradeOverlay tradeOverlay) {
        this.tradeOverlay = tradeOverlay;
    }

    private void updateOverlay() {
        TradingFacade trade = getFacade().getTrading();

        ResourceType[] options;

        options = trade.maritimeReceiveOptions(getPlayer(), giveType);
        LOGGER.fine(options::toString);
        tradeOverlay.showGetOptions(options);

        options = trade.maritimeSendOptions(getPlayer(), getType);
        LOGGER.fine(options::toString);
        tradeOverlay.showGiveOptions(options);

        tradeOverlay.setTradeEnabled(false);
        if (getType == null) {
            tradeOverlay.setStateMessage("Choose a resource to give");
        } else if (giveType == null) {
            tradeOverlay.setStateMessage("Choose a resource to get");
        } else {
            LOGGER.info("Valid trade options selected");
            tradeOverlay.setTradeEnabled(true);
        }
    }

    @Override
    public void startTrade() {
        LOGGER.info("Starting trade");
        giveType = null;
        getType = null;
        updateOverlay();
        getTradeOverlay().showModal();
    }

    @Override
    public void makeTrade() {
        if (giveType == null ||
                getType == null ||
                !getFacade().getTrading().canMaritimeTrade(getPlayer(), giveType, getType)) {
            tradeOverlay.setStateMessage("Invalid trade");
            return;
        }
        TradingFacade trade = getFacade().getTrading();
        Player p = getPlayer();
        getAsync().runModelMethod(server::maritimeTrade,
                new MaritimeTradeAction(getType,
                        trade.maritimeTradeRatio(p, giveType),
                        p.getPlayerIndex(),
                        giveType))
                .onError(e -> LOGGER.severe("Error with maritime trade: " + e.getMessage()));
        getTradeOverlay().closeModal();
    }

    @Override
    public void cancelTrade() {
        unsetGetValue();
        unsetGiveValue();
        getTradeOverlay().closeModal();
    }

    @Override
    public void setGetResource(ResourceType resource) {
        getType = resource;
        tradeOverlay.selectGetOption(resource, 1);
    }

    @Override
    public void setGiveResource(ResourceType resource) {
        giveType = resource;
        tradeOverlay.selectGetOption(
                resource,
                getFacade().getTrading().maritimeTradeRatio(getPlayer(), resource));
    }

    @Override
    public void unsetGetValue() {
        getType = null;
    }

    @Override
    public void unsetGiveValue() {
        giveType = null;
    }

    @Override
    protected void updateFromModel(ClientModel model) {
        getTradeView().enableMaritimeTrade(
                getFacade().getTrading().canMaritimeTrade(getPlayer())
        );
        // I'm not sure when you can't cancel from maritime trade.
        tradeOverlay.setCancelEnabled(true);
        updateOverlay();
    }
}

