package shared.facades;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;
import shared.definitions.TurnStatus;
import shared.models.game.ClientModel;
import shared.models.game.DevCardSet;
import shared.models.game.Player;
import shared.models.game.ResourceSet;
import shared.serialization.ModelExample;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Philip on 9/26/2016.
 */
public class DevCardFacadeTest {

    private ClientModel model;
    private Player currentPlayer;
    private List<Player> listOfPlayers;
    private DevCardFacade facade;
    private FacadeManager facades;

    @Before
    public void setup() {
        model = ModelExample.fullJsonModel();
        facades = new FacadeManager(model);
        facade = facades.getDevCards();
        listOfPlayers = model.getPlayers();
        currentPlayer = listOfPlayers.get(0);
        ResourceSet resourceSet = new ResourceSet(1, 0, 1, 0, 1);
        currentPlayer.setResources(resourceSet);
        DevCardSet set = new DevCardSet(0, 0, 1, 1, 0);
        currentPlayer.setOldDevCards(set);
        set = new DevCardSet(0, 0, 1, 0, 0);
        currentPlayer.setNewDevCards(set);
        currentPlayer.setVictoryPoints(8);
        currentPlayer.setSoldiers(2);

        Player p = listOfPlayers.get(1);
        resourceSet = new ResourceSet(0, 0, 0, 0, 0);
        p.setResources(resourceSet);
        set = new DevCardSet(0, 1, 1, 2, 0);
        p.setOldDevCards(set);
        set = new DevCardSet(0, 0, 0, 0, 0);
        p.setNewDevCards(set);
        p.setVictoryPoints(5);
        p.setSoldiers(2);

        p = listOfPlayers.get(2);
        resourceSet = new ResourceSet(0, 1, 1, 1, 0);
        p.setResources(resourceSet);
        set = new DevCardSet(1, 0, 0, 0, 1);
        p.setOldDevCards(set);
        set = new DevCardSet(0, 0, 0, 0, 0);
        p.setNewDevCards(set);
        p.setVictoryPoints(3);

        p = listOfPlayers.get(3);
        resourceSet = new ResourceSet(1, 0, 1, 0, 1);
        p.setResources(resourceSet);
        set = new DevCardSet(0, 0, 0, 0, 0);
        p.setOldDevCards(set);
        set = new DevCardSet(0, 0, 0, 0, 0);
        p.setNewDevCards(set);
        p.setVictoryPoints(9);
    }

    @Test
    public void canBuyDevCard() throws Exception {
        model.getTurnTracker().setCurrentTurn(PlayerIndex.FOURTH);
        model.getTurnTracker().setStatus(TurnStatus.ROBBING);
        assertFalse(facade.canBuyDevCard(model.getPlayer(PlayerIndex.FOURTH)));//Wrong turn status
        model.getTurnTracker().setStatus(TurnStatus.PLAYING);
//        assertFalse(facade.canBuyDevCard(null));                        //Null
        model.getTurnTracker().setCurrentTurn(PlayerIndex.FIRST);
        assertFalse(facade.canBuyDevCard(model.getPlayer(PlayerIndex.FOURTH)));//Not player's turn

        listOfPlayers.get(2).setNewDevCards(new DevCardSet(1, 1, 2, 11, 1));
        assertFalse(facade.canBuyDevCard(model.getPlayer(PlayerIndex.FIRST)));
        listOfPlayers.get(2).setNewDevCards(new DevCardSet(0, 0, 0, 0, 0));
        model.getTurnTracker().setStatus(TurnStatus.PLAYING);//Returns SECOND ROUND INSTEAD OF PLAYING
        assertTrue(facade.canBuyDevCard(model.getPlayer(PlayerIndex.FIRST)));
    }

    @Test
    public void buyDevCard() throws Exception {
        model.getTurnTracker().setStatus(TurnStatus.ROBBING);
        model.getTurnTracker().setCurrentTurn(PlayerIndex.FIRST);
        try {
            facade.buyDevCard(currentPlayer);
            Assert.fail("Wrong turn status");
        } catch (IllegalArgumentException e) {

        }
        model.getTurnTracker().setStatus(TurnStatus.PLAYING);
        model.getTurnTracker().setCurrentTurn(PlayerIndex.FOURTH);
        try {
            facade.buyDevCard(currentPlayer);
            Assert.fail("Wrong player; must be current player");
        } catch (IllegalArgumentException e) {

        }
        model.getTurnTracker().setCurrentTurn(PlayerIndex.FIRST);
        try {
            facade.buyDevCard(null);
            Assert.fail("Parameter is null");
        } catch (IllegalArgumentException e) {

        }

        facade.buyDevCard(currentPlayer);
        assertEquals(2, currentPlayer.getNewDevCards().getTotal());
    }

    @Test
    public void canUseSoldierCard() throws Exception {
        model.getTurnTracker().setCurrentTurn(PlayerIndex.FOURTH);
        model.getTurnTracker().setStatus(TurnStatus.PLAYING);
        assertFalse(facade.canUseSoldierCard(currentPlayer));   //Wrong player turn
        model.getTurnTracker().setCurrentTurn(PlayerIndex.FIRST);
        model.getTurnTracker().setStatus(TurnStatus.ROBBING);
        assertFalse(facade.canUseSoldierCard(currentPlayer));   //Wrong status
        model.getTurnTracker().setStatus(TurnStatus.PLAYING);
        assertFalse(facade.canUseSoldierCard(null));            //Null param
        TurnStatus ts = model.getTurnTracker().getStatus();
        assertTrue(facade.canUseSoldierCard(currentPlayer));
    }

    @Test
    public void useSoldierCard() throws Exception {
        model.getTurnTracker().setCurrentTurn(PlayerIndex.FOURTH);
        model.getTurnTracker().setStatus(TurnStatus.PLAYING);
        try {
            facade.useSoldierCard(currentPlayer);
            Assert.fail("Wrong Player");
        } catch (IllegalArgumentException e) {
        }
        model.getTurnTracker().setCurrentTurn(PlayerIndex.FIRST);
        model.getTurnTracker().setStatus(TurnStatus.ROBBING);
        try {
            facade.useSoldierCard(currentPlayer);
            Assert.fail("Wrong Status");
        } catch (IllegalArgumentException e) {
        }
        model.getTurnTracker().setStatus(TurnStatus.PLAYING);
        try {
            facade.useSoldierCard(null);
            Assert.fail("Null");
        } catch (IllegalArgumentException e) {
        }

        facade.useSoldierCard(currentPlayer);
        assertTrue(currentPlayer.getOldDevCards().getSoldier() == 0);
        assertTrue(model.getTurnTracker().getLargestArmy() == currentPlayer.getPlayerIndex());
        model.getTurnTracker().setCurrentTurn(PlayerIndex.SECOND);
        Player p = listOfPlayers.get(1);
        model.getTurnTracker().setStatus(TurnStatus.PLAYING);
        facade.useSoldierCard(p);
        assertTrue(p.getOldDevCards().getSoldier() == 1);
        assertFalse(model.getTurnTracker().getLargestArmy() == p.getPlayerIndex());
        model.getTurnTracker().setStatus(TurnStatus.PLAYING);
        try {
            facade.useSoldierCard(p);
            Assert.fail("Played Card");
        } catch (IllegalArgumentException e) {

        }
//        assertTrue(p.getOldDevCards().getSoldier() == 0);
//        assertTrue(model.getTurnTracker().getLargestArmy() == p.getPlayerIndex());
    }

    @Test
    public void canUseVictoryPointCards() throws Exception {
        model.getTurnTracker().setStatus(TurnStatus.PLAYING);
        model.getTurnTracker().setCurrentTurn(PlayerIndex.FOURTH);
        assertFalse(facade.canUseVictoryPointCards(currentPlayer));
        model.getTurnTracker().setCurrentTurn(PlayerIndex.FIRST);
        model.getTurnTracker().setStatus(TurnStatus.ROBBING);
        assertFalse(facade.canUseVictoryPointCards(currentPlayer));
        model.getTurnTracker().setStatus(TurnStatus.PLAYING);
        assertFalse(facade.canUseVictoryPointCards(null));

        assertTrue(facade.canUseVictoryPointCards(currentPlayer));
        model.getTurnTracker().setCurrentTurn(PlayerIndex.SECOND);
        assertFalse(facade.canUseVictoryPointCards(listOfPlayers.get(0)));
        assertTrue(facade.canUseVictoryPointCards(listOfPlayers.get(1)));
    }

    @Test
    public void useVictoryPointCards() throws Exception {
        model.getTurnTracker().setCurrentTurn(PlayerIndex.FOURTH);
        model.getTurnTracker().setStatus(TurnStatus.PLAYING);
        try {
            facade.useVictoryPointCards(currentPlayer);
            Assert.fail("Wrong Player");
        } catch (IllegalArgumentException e) {
        }
        model.getTurnTracker().setCurrentTurn(PlayerIndex.FIRST);
        model.getTurnTracker().setStatus(TurnStatus.ROBBING);
        try {
            facade.useVictoryPointCards(currentPlayer);
            Assert.fail("Wrong Status");
        } catch (IllegalArgumentException e) {
        }
        model.getTurnTracker().setStatus(TurnStatus.PLAYING);
        try {
            facade.useVictoryPointCards(null);
            Assert.fail("Null");
        } catch (IllegalArgumentException e) {
        }

        facade.useVictoryPointCards(currentPlayer);
        assertEquals(10, currentPlayer.getVictoryPoints());
        Player p = listOfPlayers.get(1);
        try {
            facade.useVictoryPointCards(p);
            Assert.fail("Not enough victory points");
        } catch (IllegalArgumentException e) {
        }
        assertNotEquals(10, p.getVictoryPoints() + p.getNewDevCards().getMonument() + p.getOldDevCards().getMonument());
        assertEquals(listOfPlayers.get(0).getPlayerID(), model.getWinner());
    }

    @Test
    public void canUseRoadBuildingCard() throws Exception {
        Player p = null;
        assertNull(p);
        //Normally we would use the currentPlayer only.  For testing purposes, we will check every player
        assertNotNull(currentPlayer);

        //Road Building; Player 2; False first then True
    }

    @Test
    public void useRoadBuildingCard() throws Exception {

    }

    @Test
    public void canUseYearOfPlentyCard() throws Exception {
        Player p = null;
        assertNull(p);
        //Normally we would use the currentPlayer only.  For testing purposes, we will check every player
        assertNotNull(currentPlayer);

        ResourceType type1 = null;
        ResourceType type2 = null;
        //Year Of Plenty; Player 3; False first then True
        p = listOfPlayers.get(2);
        assertTrue(p.getOldDevCards().getYearOfPlenty() > 0);

        //Year Of Plenty; Others; False
        assertFalse(currentPlayer.getOldDevCards().getYearOfPlenty() > 0);
        p = listOfPlayers.get(1);
        assertFalse(p.getOldDevCards().getYearOfPlenty() > 0);
        p = listOfPlayers.get(3);
        assertFalse(p.getOldDevCards().getYearOfPlenty() > 0);
    }

    @Test
    public void useYearOfPlentyCard() throws Exception {

    }

    @Test
    public void canUseMonopolyCardTest() {

    }

    @Test
    public void canUseMonopolyCard() throws Exception {
        Player p = null;
        assertNull(p);
        //Normally we would use the currentPlayer only.  For testing purposes, we will check every player
        assertNotNull(currentPlayer);

        //Monopoly; Player 3; True
    }

    @Test
    public void useMonopolyCard() throws Exception {

    }

}