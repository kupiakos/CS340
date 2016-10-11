package shared.facades;

import org.junit.Before;
import org.junit.Test;
import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;
import shared.definitions.TurnStatus;
import shared.locations.EdgeLocation;
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
    private ResourcesFacade resFacade;
    private BuildingFacade buiFacade;
    private FacadeManager facades;

    @Before
    public void setup() {
        model = ModelExample.fullJsonModel();
        facades = new FacadeManager(model);
        facade = facades.getDevCards();
        resFacade = facades.getResources();
        buiFacade = facades.getBuilding();
        listOfPlayers = model.getPlayers();
        currentPlayer = listOfPlayers.get(0);
        ResourceSet resourceSet = new ResourceSet(1, 0, 1, 0, 1);
        currentPlayer.setResources(resourceSet);
        DevCardSet set = new DevCardSet(0, 0, 1, 1, 0);
        currentPlayer.setOldDevCards(set);
        set = new DevCardSet(0, 0, 1, 0, 0);
        currentPlayer.setNewDevCards(set);
        currentPlayer.setVictoryPoints(8);

        Player p = listOfPlayers.get(1);
        resourceSet = new ResourceSet(0, 0, 0, 0, 0);
        p.setResources(resourceSet);
        set = new DevCardSet(0, 1, 1, 0, 0);
        p.setOldDevCards(set);
        set = new DevCardSet(0, 0, 0, 0, 0);
        p.setNewDevCards(set);
        p.setVictoryPoints(5);

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

        listOfPlayers.get(2).setNewDevCards(new DevCardSet(1, 1, 2, 13, 1));
        assertFalse(facade.canBuyDevCard(model.getPlayer(PlayerIndex.FIRST)));
        listOfPlayers.get(2).setNewDevCards(new DevCardSet(0, 0, 0, 0, 0));
//        assertTrue(facade.canBuyDevCard(model.getTurn(PlayerIndex.FIRST)));
    }

    @Test
    public void buyDevCard() throws Exception {
        model.getTurnTracker().setStatus(TurnStatus.PLAYING);
        model.getTurnTracker().setCurrentTurn(PlayerIndex.FIRST);
//        facade.buyDevCard(currentPlayer);
//       assertEquals(2, currentPlayer.getNewDevCards().getTotal());
    }

    @Test
    public void canUseSoldierCard() throws Exception {
        model.getTurnTracker().setCurrentTurn(PlayerIndex.FOURTH);
        model.getTurnTracker().setStatus(TurnStatus.PLAYING);
        assertFalse(facade.canUseSoldierCard(currentPlayer));
        model.getTurnTracker().setCurrentTurn(PlayerIndex.FIRST);
//        assertTrue(facade.canUseSoldierCard(currentPlayer));
    }

    @Test
    public void useSoldierCard() throws Exception {
        model.getTurnTracker().setStatus(TurnStatus.PLAYING);
        model.getTurnTracker().setCurrentTurn(PlayerIndex.FIRST);
//        facade.useSoldierCard(currentPlayer);
        //Test if robber worked
    }

    @Test
    public void canUseVictoryPointCards() throws Exception {
        model.getTurnTracker().setStatus(TurnStatus.PLAYING);
        model.getTurnTracker().setCurrentTurn(PlayerIndex.FIRST);
//        assertTrue(facade.canUseVictoryPointCards(currentPlayer));
    }

    //@Test
    public void useVictoryPointCards() throws Exception {

    }

    @Test
    public void canUseRoadBuildingCard() throws Exception {
        Player p = null;
        assertNull(p);
        //Normally we would use the currentPlayer only.  For testing purposes, we will check every player
        assertNotNull(currentPlayer);

        EdgeLocation edgeLocation1 = null;
        EdgeLocation edgeLocation2 = null;
        //Road Building; Player 2; False first then True
    }

    //@Test
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

    //@Test
    public void useYearOfPlentyCard() throws Exception {

    }

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

    //@Test
    public void useMonopolyCard() throws Exception {

    }

}