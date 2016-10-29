package client.domestic;

import client.base.Controller;
import client.misc.IWaitView;
import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;
import shared.models.game.ResourceSet;
import shared.models.game.TradeOffer;


/**
 * Domestic trade controller implementation
 */
public class DomesticTradeController extends Controller implements IDomesticTradeController {

    private IDomesticTradeOverlay tradeOverlay;
    private IWaitView waitOverlay;
    private IAcceptTradeOverlay acceptOverlay;
    private TradeOffer tradeOffer;
    private boolean sendWood;
    private boolean sendOre;
    private boolean sendWool;
    private boolean sendBrick;
    private boolean sendWheat;

    /**
     * DomesticTradeController constructor
     *
     * @param tradeView     Domestic trade view (i.e., view that contains the "Domestic Trade" button)
     * @param tradeOverlay  Domestic trade overlay (i.e., view that lets the user propose a domestic trade)
     * @param waitOverlay   Wait overlay used to notify the user they are waiting for another player to accept a trade
     * @param acceptOverlay Accept trade overlay which lets the user accept or reject a proposed trade
     */
    public DomesticTradeController(IDomesticTradeView tradeView, IDomesticTradeOverlay tradeOverlay,
                                   IWaitView waitOverlay, IAcceptTradeOverlay acceptOverlay) {

        super(tradeView);

        setTradeOverlay(tradeOverlay);
        setWaitOverlay(waitOverlay);
        setAcceptOverlay(acceptOverlay);
        tradeOffer = null;
    }

    public IDomesticTradeView getTradeView() {

        return (IDomesticTradeView) super.getView();
    }

    public IDomesticTradeOverlay getTradeOverlay() {
        return tradeOverlay;
    }

    public void setTradeOverlay(IDomesticTradeOverlay tradeOverlay) {
        this.tradeOverlay = tradeOverlay;
    }

    public IWaitView getWaitOverlay() {
        return waitOverlay;
    }

    public void setWaitOverlay(IWaitView waitView) {
        this.waitOverlay = waitView;
    }

    public IAcceptTradeOverlay getAcceptOverlay() {
        return acceptOverlay;
    }

    public void setAcceptOverlay(IAcceptTradeOverlay acceptOverlay) {
        this.acceptOverlay = acceptOverlay;
    }

    @Override
    public void startTrade() {
        tradeOffer = new TradeOffer(null, new ResourceSet(0, 0, 0, 0, 0), getPlayer().getPlayerIndex());
        getTradeOverlay().showModal();
    }

    @Override
    public void decreaseResourceAmount(ResourceType resource) {
        switch (resource) {
            case WOOD:
                if (sendWood) {
                    tradeOffer.getOffer().setWood(tradeOffer.getOffer().getWood() + 1);
                    if (tradeOffer.getOffer().getWood() >= getPlayer().getResources().getWood())
                        getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.WOOD, false, true);
                } else {
                    tradeOffer.getOffer().setWood(tradeOffer.getOffer().getWood() - 1);
                }
                break;
            case BRICK:
                if (sendBrick) {
                    tradeOffer.getOffer().setBrick(tradeOffer.getOffer().getBrick() + 1);
                    if (tradeOffer.getOffer().getBrick() >= getPlayer().getResources().getBrick())
                        getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.BRICK, false, true);
                } else {
                    tradeOffer.getOffer().setWood(tradeOffer.getOffer().getWood() - 1);
                }
                break;
            case WHEAT:
                if (sendWood) {
                    tradeOffer.getOffer().setWheat(tradeOffer.getOffer().getWheat() + 1);
                    if (tradeOffer.getOffer().getWheat() >= getPlayer().getResources().getWheat())
                        getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.WHEAT, false, true);
                } else {
                    tradeOffer.getOffer().setWheat(tradeOffer.getOffer().getWheat() - 1);
                }
                break;
            case ORE:
                if (sendWood) {
                    tradeOffer.getOffer().setOre(tradeOffer.getOffer().getOre() + 1);
                    if (tradeOffer.getOffer().getOre() >= getPlayer().getResources().getOre())
                        getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.ORE, false, true);
                } else {
                    tradeOffer.getOffer().setOre(tradeOffer.getOffer().getOre() - 1);
                }
                break;
            case SHEEP:
                if (sendWood) {
                    tradeOffer.getOffer().setSheep(tradeOffer.getOffer().getSheep() + 1);
                    if (tradeOffer.getOffer().getSheep() >= getPlayer().getResources().getSheep())
                        getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.SHEEP, false, true);
                } else {
                    tradeOffer.getOffer().setSheep(tradeOffer.getOffer().getSheep() - 1);
                }
                break;
        }
    }

    @Override
    public void increaseResourceAmount(ResourceType resource) {
        switch (resource) {
            case WOOD:
                if (sendWood) {
                    tradeOffer.getOffer().setWood(tradeOffer.getOffer().getWood() - 1);
                    if (tradeOffer.getOffer().getWood() == 0)
                        getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.WOOD, false, true);
                } else {
                    tradeOffer.getOffer().setWood(tradeOffer.getOffer().getWood() + 1);
                }
                break;
            case BRICK:
                if (sendBrick) {
                    tradeOffer.getOffer().setBrick(tradeOffer.getOffer().getBrick() - 11);
                    if (tradeOffer.getOffer().getBrick() == 0)
                        getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.BRICK, false, true);
                } else {
                    tradeOffer.getOffer().setWood(tradeOffer.getOffer().getWood() + 1);
                }
                break;
            case WHEAT:
                if (sendWood) {
                    tradeOffer.getOffer().setWheat(tradeOffer.getOffer().getWheat() - 1);
                    if (tradeOffer.getOffer().getWheat() == 0)
                        getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.WHEAT, false, true);
                } else {
                    tradeOffer.getOffer().setWheat(tradeOffer.getOffer().getWheat() + 1);
                }
                break;
            case ORE:
                if (sendWood) {
                    tradeOffer.getOffer().setOre(tradeOffer.getOffer().getOre() - 1);
                    if (tradeOffer.getOffer().getOre() == 0)
                        getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.ORE, false, true);
                } else {
                    tradeOffer.getOffer().setOre(tradeOffer.getOffer().getOre() + 1);
                }
                break;
            case SHEEP:
                if (sendWood) {
                    tradeOffer.getOffer().setSheep(tradeOffer.getOffer().getSheep() - 1);
                    if (tradeOffer.getOffer().getSheep() == 0)
                        getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.SHEEP, false, true);
                } else {
                    tradeOffer.getOffer().setSheep(tradeOffer.getOffer().getSheep() + 1);
                }
                break;
        }
    }

    @Override
    public void sendTradeOffer() {
        getTradeOverlay().closeModal();
//		getWaitOverlay().showModal();
    }

    @Override
    public void setPlayerToTradeWith(int playerIndex) {
        tradeOffer.setReceiver(PlayerIndex.fromInt(playerIndex));
    }

    @Override
    public void setResourceToReceive(ResourceType resource) {
        switch (resource) {
            case WOOD:
                sendWood = true;
                tradeOffer.getOffer().setWood(Math.abs(tradeOffer.getOffer().getWood()));
                break;
            case BRICK:
                sendBrick = true;
                tradeOffer.getOffer().setBrick(Math.abs(tradeOffer.getOffer().getBrick()));
                break;
            case WHEAT:
                sendWheat = true;
                tradeOffer.getOffer().setWheat(Math.abs(tradeOffer.getOffer().getWheat()));
                break;
            case ORE:
                sendOre = true;
                tradeOffer.getOffer().setOre(Math.abs(tradeOffer.getOffer().getOre()));
                break;
            case SHEEP:
                sendWool = true;
                tradeOffer.getOffer().setSheep(Math.abs(tradeOffer.getOffer().getSheep()));
                break;
        }
    }

    @Override
    public void setResourceToSend(ResourceType resource) {
        switch (resource) {
            case WOOD:
                sendWood = false;
                tradeOffer.getOffer().setWood(-(tradeOffer.getOffer().getWood()));
                break;
            case BRICK:
                sendBrick = false;
                tradeOffer.getOffer().setBrick(-(tradeOffer.getOffer().getBrick()));
                break;
            case WHEAT:
                sendWheat = false;
                tradeOffer.getOffer().setWheat(-(tradeOffer.getOffer().getWheat()));
                break;
            case ORE:
                sendOre = false;
                tradeOffer.getOffer().setOre(-(tradeOffer.getOffer().getOre()));
                break;
            case SHEEP:
                sendWool = false;
                tradeOffer.getOffer().setSheep(-(tradeOffer.getOffer().getSheep()));
                break;
        }

    }

    @Override
    public void unsetResource(ResourceType resource) {
        switch (resource) {
            case WOOD:
                tradeOffer.getOffer().setWood(0);
                break;
            case BRICK:
                tradeOffer.getOffer().setBrick(0);
                break;
            case WHEAT:
                tradeOffer.getOffer().setWheat(0);
                break;
            case ORE:
                tradeOffer.getOffer().setOre(0);
                break;
            case SHEEP:
                tradeOffer.getOffer().setSheep(0);
                break;
        }
    }

    @Override
    public void cancelTrade() {
        tradeOffer = null;
        getTradeOverlay().closeModal();
    }

    @Override
    public void acceptTrade(boolean willAccept) {

        getAcceptOverlay().closeModal();
    }

}

