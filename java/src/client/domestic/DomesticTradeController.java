package client.domestic;

import client.base.Controller;
import client.misc.IWaitView;
import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;
import shared.definitions.TurnStatus;
import shared.models.game.ClientModel;
import shared.models.game.ResourceSet;
import shared.models.game.TradeOffer;
import shared.models.games.PlayerInfo;
import shared.models.moves.AcceptTradeAction;
import shared.models.moves.OfferTradeAction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


/**
 * Domestic trade controller implementation
 */
public class DomesticTradeController extends Controller implements IDomesticTradeController {

    private IDomesticTradeOverlay tradeOverlay;
    private IWaitView waitOverlay;
    private IAcceptTradeOverlay acceptOverlay;
    private OfferTradeAction tradeOffer;
    private ResourceSet sendOrReceive;
    private PlayerIndex receiver; //only for accepting trades

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
        observeClientModel();
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
    protected void updateFromModel(ClientModel cm) {
        getTradeView().enableDomesticTrade(getFacade().getTrading().canOfferTrade(getPlayer(), null));
        TradeOffer to = cm.getTradeOffer();
        if (getWaitOverlay().isModalShowing()) {
            if (cm.getTradeOffer() == null) {
                getWaitOverlay().closeModal();
            }
        }
        if (to != null && !getAcceptOverlay().isModalShowing()) {
            if (Objects.equals(to.getReceiver(), (getPlayer().getPlayerIndex()))) {
                getAcceptOverlay().reset();
                getAcceptOverlay().setAcceptEnabled(true);
                receiver = to.getReceiver();
                getAcceptOverlay().setPlayerName(getModel().getPlayer(to.getSender()).getName());
                for (ResourceType r : ResourceType.values()) {
                    if (to.getOffer().getOfType(r) > 0) {
                        getAcceptOverlay().addGetResource(r, to.getOffer().getOfType(r));
                    } else if (to.getOffer().getOfType(r) < 0) {
                        getAcceptOverlay().addGiveResource(r, -to.getOffer().getOfType(r));
                        if (getPlayer().getResources().getOfType(r) < Math.abs(to.getOffer().getOfType(r))) {
                            getAcceptOverlay().setAcceptEnabled(false);
                        }
                    }
                }
                getAcceptOverlay().showModal();
            }
        }
    }

    @Override
    public void startTrade() {

        if (getFacade().getTurn().isPlayersTurn(getPlayer()) && getModel().getTurnTracker().getStatus() == TurnStatus.PLAYING) {
            server = getGameManager().getServer();
            tradeOffer = new OfferTradeAction(null, new ResourceSet(0, 0, 0, 0, 0), getPlayer().getPlayerIndex());
            sendOrReceive = new ResourceSet(0, 0, 0, 0, 0);
            List<PlayerInfo> players = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                if (getModel().getPlayers().get(i).getPlayerID() == getPlayer().getPlayerID()) {
                    continue;
                }
                PlayerInfo pi = new PlayerInfo(getModel().getPlayers().get(i).getColor(), getModel().getPlayers().get(i).getName(), getModel().getPlayers().get(i).getPlayerID());
                pi.setPlayerIndex(getModel().getPlayers().get(i).getPlayerIndex());
                players.add(pi);
            }
            getTradeOverlay().setPlayers(Arrays.copyOf(players.toArray(), players.size(), PlayerInfo[].class));
            getTradeOverlay().reset();
            getTradeOverlay().setPlayerSelectionEnabled(true);
            getTradeOverlay().setStateMessage("Please Select a Player to Trade with");
        } else {
            getTradeOverlay().setPlayerSelectionEnabled(false);
            getTradeOverlay().setStateMessage("Wait For Your Turn");
            getTradeOverlay().setTradeEnabled(false);
            getTradeOverlay().setResourceSelectionEnabled(false);
        }
        getTradeOverlay().showModal();
    }

    @Override
    public void decreaseResourceAmount(ResourceType resource) {
        if (sendOrReceive.getOfType(resource) == 1) {
            tradeOffer.getOffer().setOfType(resource, tradeOffer.getOffer().getOfType(resource) + 1);
            if (tradeOffer.getOffer().getOfType(resource) == 0) {
                getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);
            }
        } else if (sendOrReceive.getOfType(resource) == -1) {
            tradeOffer.getOffer().setOfType(resource, tradeOffer.getOffer().getOfType(resource) - 1);
            if (tradeOffer.getOffer().getOfType(resource) == 0) {
                getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);
            }
        }
        canSendTrade();
    }

    @Override
    public void increaseResourceAmount(ResourceType resource) {
        if (sendOrReceive.getOfType(resource) == 1) {
            tradeOffer.getOffer().setOfType(resource, tradeOffer.getOffer().getOfType(resource) - 1);
            getTradeOverlay().setResourceAmountChangeEnabled(resource, true, true);
        } else if (sendOrReceive.getOfType(resource) == -1) {
            tradeOffer.getOffer().setOfType(resource, tradeOffer.getOffer().getOfType(resource) + 1);
            getTradeOverlay().setResourceAmountChangeEnabled(resource, true, true);
            if (Math.abs(tradeOffer.getOffer().getOfType(resource)) >= getPlayer().getResources().getOfType(resource)) {
                getTradeOverlay().setResourceAmountChangeEnabled(resource, false, true);
            }
        }
        canSendTrade();
    }

    @Override
    public void sendTradeOffer() {
        getAsync().runMethod(server::offerTrade, tradeOffer)
                .start();
        getTradeOverlay().closeModal();
        getWaitOverlay().showModal();
    }

    @Override
    public void setPlayerToTradeWith(int playerIndex) {
        if (playerIndex == -1) {
            tradeOffer.setReceiver(null);
        } else {
            tradeOffer.setReceiver(PlayerIndex.fromInt(playerIndex));
        }
        canSendTrade();
    }

    @Override
    public void setResourceToReceive(ResourceType resource) {
        sendOrReceive.setOfType(resource, 1);
        tradeOffer.getOffer().setOfType(resource, 0);
        getTradeOverlay().setResourceAmount(resource, "0");
        getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);
        canSendTrade();
    }

    @Override
    public void setResourceToSend(ResourceType resource) {
        sendOrReceive.setOfType(resource, -1);
        tradeOffer.getOffer().setOfType(resource, 0);
        getTradeOverlay().setResourceAmount(resource, "0");
        getTradeOverlay().setResourceAmountChangeEnabled(resource, getPlayer().getResources().getOfType(resource) > 0, false);
        canSendTrade();
    }

    @Override
    public void unsetResource(ResourceType resource) {
        sendOrReceive.setOfType(resource, 0);
        tradeOffer.getOffer().setOfType(resource, 0);
        getTradeOverlay().setResourceAmountChangeEnabled(resource, false, false);
        canSendTrade();
    }

    @Override
    public void cancelTrade() {
        tradeOffer = null;
        getTradeOverlay().closeModal();
    }

    @Override
    public void acceptTrade(boolean willAccept) {
        AcceptTradeAction accept = new AcceptTradeAction(willAccept, receiver);
        getAsync().runMethod(server::acceptTrade, accept)
                .start();
        receiver = null;
        getAcceptOverlay().closeModal();
    }

    private void canSendTrade() {
        if (tradeOffer.getReceiver() != null) {
            if (getFacade().getTrading().canOfferTrade(getPlayer(), getModel().getPlayers().get(tradeOffer.getReceiver().index()), tradeOffer.getOffer())) {
                getTradeOverlay().setTradeEnabled(true);
                getTradeOverlay().setStateMessage("Click to Send Trade");
                return;
            }
            getTradeOverlay().setStateMessage("Set Trade Offer to Send");
        } else {
            getTradeOverlay().setStateMessage("Please Select a Player to Trade with");
        }
        getTradeOverlay().setTradeEnabled(false);
    }

}

