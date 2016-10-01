package shared.facades;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;
import shared.definitions.TurnStatus;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import shared.models.game.ClientModel;
import shared.models.game.Player;
import shared.models.game.ResourceSet;
import shared.models.game.TradeOffer;
import shared.serialization.ModelExample;

public class TradingFacadeTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    private TradingFacade facade;
    private FacadeManager manager;
    private ClientModel model;
    private Player first, second, third;

    @Before
    public void setUp() {
        model = ModelExample.fullJsonModel();
        manager = new FacadeManager(model);
        facade = manager.getTrading();
        first = model.getPlayer(PlayerIndex.FIRST);
        second = model.getPlayer(PlayerIndex.SECOND);
        third = model.getPlayer(PlayerIndex.THIRD);
        first.setResources(new ResourceSet(5, 3, 5, 4, 0));
        second.setResources(new ResourceSet(2, 1, 3, 2, 2));
        third.setResources(new ResourceSet(0, 0, 0, 0, 0));
        model.getTurnTracker().setCurrentTurn(PlayerIndex.FIRST);
        model.getTurnTracker().setStatus(TurnStatus.PLAYING);
        model.setBank(new ResourceSet(12, 12, 13, 12, 3));

        model.getMap().addSettlement(
                new VertexLocation(new HexLocation(-2, 3), VertexDirection.NorthEast),
                PlayerIndex.FIRST, true);
        model.getMap().addSettlement(
                new VertexLocation(new HexLocation(-3, 0), VertexDirection.SouthEast),
                PlayerIndex.SECOND, true);
    }

    private void assertUnchanged() {
        ClientModel m = model;
        setUp();
        Assert.assertEquals(m, model);
    }

    @Test
    public void offerBadTrade() throws Exception {
        Assert.assertNull(model.getTradeOffer());
        ResourceSet resources = new ResourceSet(3, -2, 0, 0, 0);
        facade.offerTrade(
                first,
                second,
                resources
        );
        TradeOffer offer = model.getTradeOffer();
        // We can get the trade offer
        Assert.assertNotNull(offer);
        Assert.assertEquals(resources, model.getTradeOffer().getOffer());
        Assert.assertEquals(PlayerIndex.FIRST, model.getTradeOffer().getSender());
        Assert.assertEquals(PlayerIndex.SECOND, model.getTradeOffer().getReceiver());

        // The trade offer is shown as pending to the correct players
        Assert.assertEquals(offer, facade.getMadeTradeOffer(first));
        Assert.assertNull(facade.getMadeTradeOffer(second));
        Assert.assertEquals(offer, facade.getWaitingTradeOffer(second));
        Assert.assertNull(facade.getWaitingTradeOffer(first));

        // Cannot offer any more trades
        Assert.assertFalse(facade.canOfferTrade(first, second));
        Assert.assertFalse(facade.canOfferTrade(second, first));
        Assert.assertFalse(facade.canOfferTrade(first, third));
        Assert.assertFalse(facade.canOfferTrade(third, first));

        Assert.assertTrue(facade.canRespondToTradeOffer(second, false));
        Assert.assertFalse(facade.canRespondToTradeOffer(first, false));
        Assert.assertFalse(facade.canRespondToTradeOffer(second, true));
        Assert.assertFalse(facade.canRespondToTradeOffer(third, false));

        try {
            facade.respondToTradeOffer(first, false);
            Assert.fail("trade did not fail");
        } catch (IllegalArgumentException e) {
            // successfully failed
        }

        try {
            facade.respondToTradeOffer(second, true);
            Assert.fail("trade did not fail");
        } catch (IllegalArgumentException e) {
            // successfully failed
        }

        facade.respondToTradeOffer(second, false);

        Assert.assertNull(model.getTradeOffer());
        Assert.assertNull(facade.getWaitingTradeOffer(second));
        Assert.assertNull(facade.getMadeTradeOffer(first));

        assertUnchanged();
    }

    @Test
    public void offerGoodTrade() throws Exception {
        Assert.assertNull(model.getTradeOffer());
        ResourceSet resources = new ResourceSet(3, -1, 0, 0, 0);
        facade.offerTrade(
                first,
                second,
                resources
        );
        facade.respondToTradeOffer(second, true);
        // first.setResources(new ResourceSet(5, 3, 5, 4, 0));
        // second.setResources(new ResourceSet(2, 1, 3, 2, 2));
        Assert.assertEquals(new ResourceSet(2, 4, 5, 4, 0), first.getResources());
        Assert.assertEquals(new ResourceSet(5, 0, 3, 2, 2), second.getResources());
    }

    @Test
    public void canOfferTrade() throws Exception {
        Assert.assertTrue(facade.canOfferTrade(
                first,
                second
        ));
        Assert.assertTrue(facade.canOfferTrade(
                first,
                second,
                new ResourceSet(3, -2, 0, 0, 0)
        ));
        Assert.assertTrue(facade.canOfferTrade(
                first,
                second,
                new ResourceSet(5, -3, 0, 0, -2)
        ));
        assertUnchanged();
    }

    @Test
    public void cannotOfferTradeIfCannotGive() throws Exception {
        Assert.assertFalse(facade.canOfferTrade(
                first,
                second,
                new ResourceSet(6, -2, 0, 0, 0)
        ));
        try {
            facade.offerTrade(
                    first,
                    second,
                    new ResourceSet(6, -2, 0, 0, 0)
            );
            Assert.fail("Did not throw exception");
        } catch (IllegalArgumentException e) {
            assertUnchanged();
        }
    }

    @Test
    public void canOfferTradeIfCannotGet() throws Exception {
        Assert.assertTrue(facade.canOfferTrade(
                first,
                second,
                new ResourceSet(3, -4, 0, 0, 0)
        ));
    }

    @Test
    public void cannotOfferTradeToSelf() throws Exception {
        Assert.assertFalse(facade.canOfferTrade(
                first,
                first
        ));
        Assert.assertFalse(facade.canOfferTrade(
                first,
                first,
                new ResourceSet(3, -2, 0, 0, 0)
        ));
        try {
            facade.offerTrade(
                    first,
                    first,
                    new ResourceSet(3, -2, 0, 0, 0)
            );
            Assert.fail("Did not throw exception");
        } catch (IllegalArgumentException e) {
            assertUnchanged();
        }
    }

    @Test
    public void maritimeTradeRatio() throws Exception {
        Assert.assertEquals(2, facade.maritimeTradeRatio(first, ResourceType.BRICK));
        Assert.assertEquals(4, facade.maritimeTradeRatio(first, ResourceType.ORE));
        Assert.assertEquals(3, facade.maritimeTradeRatio(second, ResourceType.ORE));
    }

    @Test
    public void canMaritimeTrade() throws Exception {
        model.getTurnTracker().setCurrentTurn(PlayerIndex.FIRST);
        Assert.assertFalse(facade.canMaritimeTrade(first, ResourceType.BRICK, ResourceType.BRICK));
        Assert.assertTrue(facade.canMaritimeTrade(first, ResourceType.BRICK, ResourceType.ORE));
        Assert.assertFalse(facade.canMaritimeTrade(second, ResourceType.BRICK, ResourceType.ORE));
    }

}