package client.discard;

import client.game.IGameManager;
import client.game.MockGM;
import client.misc.IWaitView;
import client.server.MockCM;
import client.server.MockProxy;
import client.utils.MockServerAsyncHelper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;
import shared.facades.RobberFacade;
import shared.models.game.ClientModel;
import shared.models.game.Player;
import shared.models.game.ResourceSet;
import shared.models.games.PlayerInfo;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DiscardControllerTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Spy
    private MockServerAsyncHelper async;

    @Mock
    private RobberFacade robberFacade;

    @Mock
    private IDiscardView discardView;
    @Mock
    private IWaitView waitView;

    private IGameManager gm;
    private DiscardController dc;
    private ClientModel model;
    private PlayerInfo clientPlayer1;
    private PlayerInfo clientPlayer2;
    private PlayerInfo clientPlayer3;
    private PlayerInfo clientPlayer4;

    @Before
    public void setUp() throws Exception {
        model = MockCM.fullJsonModel();
        model.getTurnTracker().setCurrentTurn(PlayerIndex.FIRST);
        gm = new MockGM();
        gm.setAsync(async);
        gm.setClientModel(model);
        MockProxy mockProxy = new MockProxy();
        gm.setServer(mockProxy);
        gm.getFacade().setRobber(robberFacade);
        for (Player p : model.getPlayers()) {
            if (p.getPlayerIndex() == PlayerIndex.FIRST) {
                clientPlayer1 = new PlayerInfo(p.getColor(), p.getName(), p.getPlayerID());
                clientPlayer1.setPlayerIndex(PlayerIndex.FIRST);
            } else if (p.getPlayerIndex() == PlayerIndex.SECOND) {
                clientPlayer2 = new PlayerInfo(p.getColor(), p.getName(), p.getPlayerID());
                clientPlayer2.setPlayerIndex(PlayerIndex.SECOND);
            } else if (p.getPlayerIndex() == PlayerIndex.THIRD) {
                clientPlayer3 = new PlayerInfo(p.getColor(), p.getName(), p.getPlayerID());
                clientPlayer3.setPlayerIndex(PlayerIndex.THIRD);
            } else {
                clientPlayer4 = new PlayerInfo(p.getColor(), p.getName(), p.getPlayerID());
                clientPlayer4.setPlayerIndex(PlayerIndex.FOURTH);
            }
        }
        gm.setPlayerInfo(clientPlayer1);
        gm.setThisPlayerIndex(clientPlayer1.getPlayerIndex());
        for (Player p : model.getPlayers()) {
            if (p.getPlayerIndex() == PlayerIndex.FIRST || p.getPlayerIndex() == PlayerIndex.THIRD) {
                p.setResources(new ResourceSet(2, 2, 2, 2, 2));
            } else if (p.getPlayerIndex() == PlayerIndex.SECOND) {
                p.setResources(new ResourceSet(2, 2, 2, 2, 1));
            }
        }
        model.setBank(new ResourceSet(12, 12, 13, 13, 13));
        dc = new DiscardController(discardView, waitView);
        dc.setServer(mockProxy);
        dc.setGameManager(gm);
    }

    private ResourceSet getAmounts() {
        return dc.getDiscardAmount();
    }

    @Test
    public void increaseAmount() throws Exception {
        for (ResourceType type : ResourceType.values()) {
            assertEquals(0, getAmounts().getOfType(type));
        }

        // Normal increases
        dc.increaseAmount(ResourceType.WHEAT);
        assertEquals(1, getAmounts().getOfType(ResourceType.WHEAT));
        dc.increaseAmount(ResourceType.WHEAT);
        assertEquals(2, getAmounts().getOfType(ResourceType.WHEAT));
        dc.increaseAmount(ResourceType.BRICK);
        assertEquals(2, getAmounts().getOfType(ResourceType.WHEAT));
        assertEquals(1, getAmounts().getOfType(ResourceType.BRICK));
        // You cannot go over the max number of a type, which depends on the number of that type a player has
        dc.increaseAmount(ResourceType.WHEAT);
        assertNotEquals(3, getAmounts().getOfType(ResourceType.WHEAT));
        // Now to test the maxDiscard
        dc.increaseAmount(ResourceType.ORE);
        dc.increaseAmount(ResourceType.SHEEP);
        assertEquals(2, getAmounts().getOfType(ResourceType.WHEAT));
        assertEquals(1, getAmounts().getOfType(ResourceType.BRICK));
        assertEquals(1, getAmounts().getOfType(ResourceType.ORE));
        assertEquals(1, getAmounts().getOfType(ResourceType.SHEEP));
        // If these next two are equal, then the max has been reached
        assertEquals(5, dc.getDiscardAmount().getTotal());
        assertEquals(5, dc.getMaxDiscard());
        // Now to check if we can go over the max, which we shouldn't
        dc.increaseAmount(ResourceType.WOOD);
        assertNotEquals(1, getAmounts().getOfType(ResourceType.WOOD));
        assertNotEquals(6, dc.getDiscardAmount().getTotal());
    }

    @Test
    public void testRoundsDown() throws Exception {
        // To test the odd number of resources and that maxDiscard rounds down
        gm.setPlayerInfo(clientPlayer2);
        gm.setThisPlayerIndex(clientPlayer2.getPlayerIndex());
        for (ResourceType type : ResourceType.values()) {
            assertEquals(0, getAmounts().getOfType(type));
        }
        assertEquals(4, dc.getMaxDiscard());
        dc.increaseAmount(ResourceType.WHEAT);
        dc.increaseAmount(ResourceType.BRICK);
        dc.increaseAmount(ResourceType.BRICK);
        dc.increaseAmount(ResourceType.ORE);
        // This increase should not work
        dc.increaseAmount(ResourceType.ORE);
        assertEquals(1, getAmounts().getOfType(ResourceType.WHEAT));
        assertEquals(2, getAmounts().getOfType(ResourceType.BRICK));
        assertEquals(1, getAmounts().getOfType(ResourceType.ORE));
    }

    @Test
    public void increaseWithDiscardLessThan4() throws Exception {
        // None of the increaseAmount should work because this player does not have enough cards to discard (maxDiscard < 4)
        gm.setPlayerInfo(clientPlayer4);
        gm.setThisPlayerIndex(clientPlayer4.getPlayerIndex());
        for (ResourceType type : ResourceType.values()) {
            assertEquals(0, getAmounts().getOfType(type));
        }
        assertFalse(dc.getMaxDiscard() >= 4);
        dc.increaseAmount(ResourceType.WHEAT);
        assertNotEquals(1, getAmounts().getOfType(ResourceType.WHEAT));
        assertEquals(0, getAmounts().getOfType(ResourceType.WOOD));
        dc.increaseAmount(ResourceType.WOOD);
        assertNotEquals(1, getAmounts().getOfType(ResourceType.WOOD));
    }

    @Test
    public void decreaseAmount() throws Exception {
        gm.setPlayerInfo(clientPlayer2);
        gm.setThisPlayerIndex(clientPlayer2.getPlayerIndex());
        for (ResourceType type : ResourceType.values()) {
            assertEquals(0, getAmounts().getOfType(type));
        }

        // setting up the decreaseAmount
        dc.increaseAmount(ResourceType.WHEAT);
        dc.increaseAmount(ResourceType.BRICK);
        dc.increaseAmount(ResourceType.BRICK);
        dc.increaseAmount(ResourceType.ORE);
        assertEquals(1, getAmounts().getOfType(ResourceType.WHEAT));
        assertEquals(2, getAmounts().getOfType(ResourceType.BRICK));
        assertEquals(1, getAmounts().getOfType(ResourceType.ORE));
        // Now to test
        assertEquals(0, getAmounts().getOfType(ResourceType.SHEEP));
        // This stays at 0
        dc.decreaseAmount(ResourceType.SHEEP);
        assertEquals(0, getAmounts().getOfType(ResourceType.SHEEP));
        // These should decrease, but not beyond 0
        dc.decreaseAmount(ResourceType.WHEAT);
        assertEquals(0, getAmounts().getOfType(ResourceType.WHEAT));
        dc.decreaseAmount(ResourceType.WHEAT);
        assertEquals(0, getAmounts().getOfType(ResourceType.WHEAT));
        assertEquals(2, getAmounts().getOfType(ResourceType.BRICK));
        assertEquals(1, getAmounts().getOfType(ResourceType.ORE));
        dc.decreaseAmount(ResourceType.BRICK);
        assertEquals(1, getAmounts().getOfType(ResourceType.BRICK));
        dc.decreaseAmount(ResourceType.BRICK);
        dc.decreaseAmount(ResourceType.ORE);
        for (ResourceType type : ResourceType.values()) {
            assertEquals(0, getAmounts().getOfType(type));
        }
    }

    @Test
    public void decreaseWithDiscardLessThan4() {
        // These should not work because the maxDiscard is less than 4
        gm.setPlayerInfo(clientPlayer4);
        gm.setThisPlayerIndex(clientPlayer4.getPlayerIndex());
        for (ResourceType type : ResourceType.values()) {
            assertEquals(0, getAmounts().getOfType(type));
        }
        assertFalse(dc.getMaxDiscard() >= 4);
        dc.increaseAmount(ResourceType.ORE);
        assertEquals(0, getAmounts().getOfType(ResourceType.ORE));
        dc.decreaseAmount(ResourceType.ORE);
        assertEquals(0, getAmounts().getOfType(ResourceType.ORE));
    }

    @Test
    public void discard() throws Exception {
        // Increase all by one
        for (ResourceType type : ResourceType.values()) {
            dc.increaseAmount(type);
            assertEquals(1, getAmounts().getOfType(type));
        }
        // These should be the same value: 5
        assertEquals(5, dc.getMaxDiscard());
        assertEquals(5, dc.getDiscardAmount().getTotal());
        // Now to discard: First player
        Player p = model.getPlayer(clientPlayer1.getPlayerIndex());
        ResourceSet discardSet = dc.getDiscardAmount();
        when(robberFacade.canDiscard(discardSet, p)).thenReturn(true);
        dc.discard();
        verify(robberFacade).discard(discardSet, p);
    }

    @Test
    public void discardIfCannot() {
        // Player 3; do not allow because the canDiscard returns false
        gm.setPlayerInfo(clientPlayer3);
        gm.setThisPlayerIndex(clientPlayer3.getPlayerIndex());
        dc.increaseAmount(ResourceType.ORE);
        dc.increaseAmount(ResourceType.ORE);
        dc.increaseAmount(ResourceType.WOOD);
        assertEquals(2, getAmounts().getOfType(ResourceType.ORE));
        assertEquals(1, getAmounts().getOfType(ResourceType.WOOD));
        Player p = model.getPlayer(clientPlayer3.getPlayerIndex());
        when(robberFacade.canDiscard(dc.getDiscardAmount(), p)).thenReturn(false);
        dc.discard();
        // This verifies that the robberFacade.discard is only called once instead of twice
        verify(robberFacade, atMost(1)).discard(dc.getDiscardAmount(), p);

    }

    @Test
    public void discardPlayer2() {
        // Player 2; will work
        gm.setPlayerInfo(clientPlayer2);
        gm.setThisPlayerIndex(clientPlayer2.getPlayerIndex());

        dc.increaseAmount(ResourceType.ORE);
        dc.increaseAmount(ResourceType.ORE);
        dc.increaseAmount(ResourceType.WOOD);
        dc.increaseAmount(ResourceType.WOOD);
        assertEquals(2, getAmounts().getOfType(ResourceType.ORE));
        assertEquals(2, getAmounts().getOfType(ResourceType.WOOD));
        Player p = model.getPlayer(clientPlayer2.getPlayerIndex());
        ResourceSet discardSet = dc.getDiscardAmount();
        when(robberFacade.canDiscard(discardSet, p)).thenReturn(true);
        dc.discard();
        // This verifies that the robberFacade.discard is only called twice
        verify(robberFacade, atMost(2)).discard(discardSet, p);

    }

    @Test
    public void discardLessThan4() {
        // Player 4; cannot work because maxDiscard is less than 4
        gm.setPlayerInfo(clientPlayer4);
        gm.setThisPlayerIndex(clientPlayer4.getPlayerIndex());
        Player p = model.getPlayer(clientPlayer4.getPlayerIndex());
        ResourceSet discardSet = dc.getDiscardAmount();
        verify(robberFacade, atMost(3)).canDiscard(discardSet, p);
        verify(robberFacade, atMost(2)).discard(discardSet, p);
    }

}