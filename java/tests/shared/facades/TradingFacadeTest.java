package shared.facades;

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

import static client.utils.ExceptionHelpers.assertThrows;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.*;

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
        second.setResources(new ResourceSet(3, 1, 3, 2, 2));
        third.setResources(new ResourceSet(0, 0, 0, 0, 0));
        model.getTurnTracker().setCurrentTurn(PlayerIndex.FIRST);
        model.getTurnTracker().setStatus(TurnStatus.PLAYING);
        model.setBank(new ResourceSet(12, 12, 13, 12, 0));

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
        assertEquals(m, model);
    }

    @Test
    public void offerBadTrade() throws Exception {
        assertNull(model.getTradeOffer());
        ResourceSet resources = new ResourceSet(3, -2, 0, 0, 0);
        facade.offerTrade(
                first,
                second,
                resources
        );
        TradeOffer offer = model.getTradeOffer();
        // We can get the trade offer
        assertNotNull(offer);
        assertEquals(resources, model.getTradeOffer().getOffer());
        assertEquals(PlayerIndex.FIRST, model.getTradeOffer().getSender());
        assertEquals(PlayerIndex.SECOND, model.getTradeOffer().getReceiver());

        // The trade offer is shown as pending to the correct players
        assertEquals(offer, facade.getMadeTradeOffer(first));
        assertNull(facade.getMadeTradeOffer(second));
        assertEquals(offer, facade.getWaitingTradeOffer(second));
        assertNull(facade.getWaitingTradeOffer(first));

        // Cannot offer any more trades
        assertFalse(facade.canOfferTrade(first, second));
        assertFalse(facade.canOfferTrade(second, first));
        assertFalse(facade.canOfferTrade(first, third));
        assertFalse(facade.canOfferTrade(third, first));

        assertTrue(facade.canRespondToTradeOffer(second, false));
        assertFalse(facade.canRespondToTradeOffer(first, false));
        assertFalse(facade.canRespondToTradeOffer(second, true));
        assertFalse(facade.canRespondToTradeOffer(third, false));

        assertThrows(() -> facade.respondToTradeOffer(first, false), IllegalArgumentException.class);
        assertThrows(() -> facade.respondToTradeOffer(second, true), IllegalArgumentException.class);

        facade.respondToTradeOffer(second, false);

        assertNull(model.getTradeOffer());
        assertNull(facade.getWaitingTradeOffer(second));
        assertNull(facade.getMadeTradeOffer(first));

        assertUnchanged();
    }

    @Test
    public void offerGoodTrade() throws Exception {
        assertNull(model.getTradeOffer());
        ResourceSet resources = new ResourceSet(3, -1, 0, 0, 0);
        facade.offerTrade(
                first,
                second,
                resources
        );
        facade.respondToTradeOffer(second, true);
        // first.setResources(new ResourceSet(5, 3, 5, 4, 0));
        // second.setResources(new ResourceSet(2, 1, 3, 2, 2));
        assertEquals(new ResourceSet(2, 4, 5, 4, 0), first.getResources());
        assertEquals(new ResourceSet(6, 0, 3, 2, 2), second.getResources());
    }

    @Test
    public void canOfferTrade() throws Exception {
        assertTrue(facade.canOfferTrade(
                first,
                second
        ));
        assertTrue(facade.canOfferTrade(
                first,
                second,
                new ResourceSet(3, -2, 0, 0, 0)
        ));
        assertTrue(facade.canOfferTrade(
                first,
                second,
                new ResourceSet(5, -3, 0, 0, -2)
        ));
        assertUnchanged();
    }

    @Test
    public void cannotOfferTradeIfCannotGive() throws Exception {
        assertFalse(facade.canOfferTrade(
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
            fail("Did not throw exception");
        } catch (IllegalArgumentException e) {
            assertUnchanged();
        }
    }

    @Test
    public void canOfferTradeIfCannotGet() throws Exception {
        assertTrue(facade.canOfferTrade(
                first,
                second,
                new ResourceSet(3, -4, 0, 0, 0)
        ));
        assertUnchanged();
    }

    @Test
    public void cannotOfferTradeToSelf() throws Exception {
        assertFalse(facade.canOfferTrade(
                first,
                first
        ));
        assertFalse(facade.canOfferTrade(
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
            fail("Did not throw exception");
        } catch (IllegalArgumentException e) {
            assertUnchanged();
        }
    }

    @Test
    public void maritimeTradeRatio() throws Exception {
        assertEquals(2, facade.maritimeTradeRatio(first, ResourceType.BRICK));
        assertEquals(4, facade.maritimeTradeRatio(first, ResourceType.ORE));
        assertEquals(3, facade.maritimeTradeRatio(second, ResourceType.ORE));
        assertUnchanged();
    }

    @Test
    public void maritimeTrade() throws Exception {
        assertThrows(() -> facade.maritimeTrade(second, ResourceType.BRICK, ResourceType.SHEEP),
                IllegalArgumentException.class);
        assertUnchanged();
        facade.maritimeTrade(first, ResourceType.ORE, ResourceType.WOOD);
        assertEquals(new ResourceSet(1, 3, 5, 5, 0), first.getResources());
        assertEquals(new ResourceSet(16, 12, 13, 11, 0), model.getBank());
    }

    @Test
    public void canMaritimeTrade() throws Exception {
        // first has 5 ore, 3 brick
        // first can trade 4 ore for anything
        // first can trade 2 brick for anything
        // second has 3 ore and 1 brick
        // second can trade 3 ore for anything
        model.getTurnTracker().setCurrentTurn(PlayerIndex.FIRST);
        model.setBank(new ResourceSet(1, 1, 0, 0, 0));
        assertTrue(facade.canMaritimeTrade(first));
        assertTrue(facade.canMaritimeTrade(first, ResourceType.BRICK, ResourceType.ORE));
        assertTrue(facade.canMaritimeTrade(first, ResourceType.ORE, ResourceType.BRICK));
    }

    @Test
    public void cannotMaritimeTradeSameResource() throws Exception {
        assertFalse(facade.canMaritimeTrade(first, ResourceType.BRICK, ResourceType.BRICK));
        assertUnchanged();
    }

    @Test
    public void maritimeTradeOnTurn() throws Exception {
        model.getTurnTracker().setCurrentTurn(PlayerIndex.FIRST);
        assertFalse(facade.canMaritimeTrade(second));
        assertFalse(facade.canMaritimeTrade(second, ResourceType.ORE, ResourceType.BRICK));
        model.getTurnTracker().setCurrentTurn(PlayerIndex.SECOND);
        assertFalse(facade.canMaritimeTrade(first));
        assertTrue(facade.canMaritimeTrade(second));
        assertTrue(facade.canMaritimeTrade(second, ResourceType.ORE, ResourceType.BRICK));
    }

    @Test
    public void cannotMaritimeTradeIfPlayerCannotGive() throws Exception {
        // Ore, Brick, Sheep, Wood, Wheat
        first.setResources(new ResourceSet(3, 3, 5, 3, 0));
        model.setBank(new ResourceSet(0, 0, 0, 0, 1));
        assertFalse(facade.canMaritimeTrade(first, ResourceType.ORE, ResourceType.WHEAT));
        assertFalse(facade.canMaritimeTrade(first, ResourceType.WOOD, ResourceType.WHEAT));
        // Boundary value analysis - ratio for brick is 2
        first.setResources(new ResourceSet(0, 2, 0, 0, 0));
        assertTrue(facade.canMaritimeTrade(first));
        first.setResources(new ResourceSet(3, 1, 3, 3, 0));
        assertFalse(facade.canMaritimeTrade(first));
    }

    @Test
    public void cannotMaritimeTradeIfOnlyOptionIsSame() throws Exception {
        first.setResources(new ResourceSet(0, 2, 0, 0, 0));
        model.setBank(new ResourceSet(0, 1, 0, 0, 0));
        assertFalse(facade.canMaritimeTrade(first));
    }

    @Test
    public void cannotMaritimeTradeIfBankCannotGive() throws Exception {
        model.setBank(new ResourceSet(0, 0, 0, 0, 1));
        assertTrue(facade.canMaritimeTrade(first, ResourceType.BRICK, ResourceType.WHEAT));
        model.setBank(new ResourceSet(0, 0, 0, 0, 0));
        assertFalse(facade.canMaritimeTrade(first, ResourceType.BRICK, ResourceType.WHEAT));
    }
    // TODO: Maritime Trade tests
}