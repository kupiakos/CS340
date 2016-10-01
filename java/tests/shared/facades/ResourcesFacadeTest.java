package shared.facades;

import client.game.GameManager;
import org.junit.Assert;
import org.junit.Test;
import shared.definitions.PurchaseType;
import shared.models.game.ClientModel;
import shared.models.game.Player;
import shared.models.game.ResourceSet;
import shared.serialization.ModelExample;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ResourcesFacadeTest {

    private ResourcesFacade facade;
    private Player player;

    @org.junit.Before
    public void setup() {
        ClientModel model = ModelExample.fullJsonModel();
        GameManager game = GameManager.getGame();
        game.setClientModel(model);
        facade = game.getFacade().getResources();
        ArrayList<Player> players = (ArrayList<Player>) model.getPlayers();
        player = players.get(0);
    }

    @Test
    public void canPurchaseItem() throws Exception {
        player.setResources(new ResourceSet(1, 1, 0, 1, 1));
        assertTrue(facade.canPurchaseItem(player, PurchaseType.ROAD));
        assertFalse(facade.canPurchaseItem(player, PurchaseType.SETTLEMENT));
    }

    @Test
    public void purchaseItem() throws Exception {

        player.setResources(new ResourceSet(0, 1, 0, 1, 0));
        facade.getModel().setBank(new ResourceSet(4, 4, 4, 4, 4));

        try {
            facade.purchaseItem(player, PurchaseType.SETTLEMENT);
            Assert.fail("expected failure");
        } catch (IllegalArgumentException e) {
            assertTrue(facade.getModel().getBank().equals(new ResourceSet(4, 4, 4, 4, 4)));
            assertTrue(player.getResources().equals(new ResourceSet(0, 1, 0, 1, 0)));
        }

        facade.purchaseItem(player, PurchaseType.ROAD);
        assertTrue(facade.getModel().getBank().equals(new ResourceSet(4, 5, 4, 5, 4)));
        assertTrue(player.getResources().equals(new ResourceSet(0, 0, 0, 0, 0)));

    }

    @Test
    public void canReceiveFromBank() throws Exception {
        facade.getModel().setBank(new ResourceSet(1, 1, 2, 1, 0));
        assertTrue(facade.canReceiveFromBank(new ResourceSet(1, 0, 0, 0, 0)));
        assertFalse(facade.canReceiveFromBank(new ResourceSet(0, 0, 0, 0, 0)));
        assertFalse(facade.canReceiveFromBank(new ResourceSet(0, 0, 0, 0, -2)));
        assertFalse(facade.canReceiveFromBank(new ResourceSet(1, 5, 2, 1, 0)));
    }

    @Test
    public void receiveFromBank() throws Exception {
        player.setResources(new ResourceSet(3, 3, 3, 3, 3));
        facade.getModel().setBank(new ResourceSet(4, 4, 4, 4, 4));
        ResourceSet resources = new ResourceSet(0, 0, 0, 0, 0);

        try {
            facade.receiveFromBank(player, resources);
            Assert.fail("expected failure");
        } catch (IllegalArgumentException e) {
            assertTrue(facade.getModel().getBank().equals(new ResourceSet(4, 4, 4, 4, 4)));
            assertTrue(player.getResources().equals(new ResourceSet(3, 3, 3, 3, 3)));
        }

        ResourceSet resourcesTwo = new ResourceSet(2, 2, 2, 2, 2);

        facade.receiveFromBank(player, resourcesTwo);
        assertTrue(facade.getModel().getBank().equals(new ResourceSet(2, 2, 2, 2, 2)));
        assertTrue(player.getResources().equals(new ResourceSet(5, 5, 5, 5, 5)));
    }

    @Test
    public void canReturnToBank() throws Exception {
        player.setResources(new ResourceSet(1, 1, 1, 0, 0));
        assertTrue(facade.canReturnToBank(player, new ResourceSet(1, 0, 1, 0, 0)));
        assertFalse(facade.canReturnToBank(player, new ResourceSet(1, 2, 1, 0, 0)));
        assertFalse(facade.canReturnToBank(player, new ResourceSet(0, 0, 0, 0, 0)));
        assertFalse(facade.canReturnToBank(player, new ResourceSet(-1, 0, 0, 0, 0)));
    }

    @Test
    public void returnToBank() throws Exception {

        player.setResources(new ResourceSet(3, 3, 3, 3, 3));
        facade.getModel().setBank(new ResourceSet(4, 4, 4, 4, 4));
        ResourceSet resources = new ResourceSet(0, -3, 0, 0, 0);

        try {
            facade.returnToBank(player, resources);
            Assert.fail("expected failure");
        } catch (IllegalArgumentException e) {
            assertTrue(facade.getModel().getBank().equals(new ResourceSet(4, 4, 4, 4, 4)));
            assertTrue(player.getResources().equals(new ResourceSet(3, 3, 3, 3, 3)));
        }

        ResourceSet resourcesTwo = new ResourceSet(2, 3, 0, 1, 1);

        facade.returnToBank(player, resourcesTwo);
        assertTrue(facade.getModel().getBank().equals(new ResourceSet(6, 7, 4, 5, 5)));
        assertTrue(player.getResources().equals(new ResourceSet(1, 0, 3, 2, 2)));

    }

}