package client.maritime;

import client.base.Controller;
import client.map.MapController;
import shared.definitions.ResourceType;
import shared.facades.TradingFacade;
import shared.models.game.ClientModel;
import shared.models.moves.MaritimeTradeAction;

import java.util.Arrays;
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
        tradeOverlay.setTradeEnabled(false);
        if (giveType == null) {
            tradeOverlay.setStateMessage("Choose a resource to give");
        } else if (getType == null) {
            tradeOverlay.setStateMessage("Choose a resource to get");
        } else {
            tradeOverlay.setStateMessage("Trade");
            tradeOverlay.setTradeEnabled(true);
        }
    }

    @Override
    public void startTrade() {
        LOGGER.info("Starting trade");
        giveType = null;
        getType = null;
        getTradeOverlay().reset();
        ResourceType[] options = getFacade().getTrading().maritimeSendOptions(getPlayer(), getType);
        LOGGER.info("Give options: " + Arrays.toString(options));
        tradeOverlay.showGiveOptions(options);
        updateOverlay();
        getTradeOverlay().showOneModal();
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
        MaritimeTradeAction action = new MaritimeTradeAction(getType,
                trade.maritimeTradeRatio(getPlayer(), giveType),
                getPlayer().getPlayerIndex(),
                giveType);
        LOGGER.info("Maritime Trading: " + action);
        getAsync().runModelMethod(server::maritimeTrade, action)
                .onError(e -> LOGGER.severe("Error with maritime trade: " + e.getMessage()))
                .start();
        getTradeOverlay().closeModal();
    }

    @Override
    public void cancelTrade() {
        unsetGetValue();
        unsetGiveValue();
        getTradeOverlay().reset();
        getTradeOverlay().closeModal();
    }

    @Override
    public void setGiveResource(ResourceType resource) {
        giveType = resource;
        tradeOverlay.selectGiveOption(
                resource,
                getFacade().getTrading().maritimeTradeRatio(getPlayer(), resource));
        ResourceType[] options = getFacade().getTrading().maritimeReceiveOptions(getPlayer(), giveType);
        LOGGER.info("Get options: " + Arrays.toString(options));
        tradeOverlay.showGetOptions(options);
        updateOverlay();
    }

    @Override
    public void setGetResource(ResourceType resource) {
        getType = resource;
        tradeOverlay.selectGetOption(resource, 1);
        updateOverlay();
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
        // I'm not sure when you can't cancel from maritime trade.
        tradeOverlay.setCancelEnabled(true);
        getTradeView().enableMaritimeTrade(
                getFacade().getTrading().canMaritimeTrade(getPlayer())
        );
    }
}

