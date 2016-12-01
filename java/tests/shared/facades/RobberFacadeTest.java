package shared.facades;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import shared.definitions.PlayerIndex;
import shared.definitions.TurnStatus;
import shared.locations.HexLocation;
import shared.models.game.ClientModel;
import shared.models.game.GameMap;
import shared.models.game.Player;
import shared.models.game.ResourceSet;
import shared.serialization.ModelExample;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Philip on 9/26/2016.
 */
@SuppressWarnings("ConstantConditions")
public class RobberFacadeTest {

    private ClientModel model;
    private Player currentPlayer;
    private List<Player> listOfPlayers;
    private RobberFacade facade;
    private GameMap gameMap;

//    @Rule
//    public final ExpectedException exception = ExpectedException.none();//EdgeLocationSerializerTest line 49

    @Before
    public void setup() {
        model = ModelExample.fullJsonModel();
        FacadeManager facadeManager = new FacadeManager(model);
        facade = facadeManager.getRobber();
        listOfPlayers = model.getPlayers();
        gameMap = model.getMap();
        gameMap.setRobber(new HexLocation(-1, 1));

        Player player = listOfPlayers.get(0);
        player.getResources().setBrick(2);
        player.getResources().setOre(2);
        player.getResources().setSheep(2);
        player.getResources().setWheat(2);
        player.getResources().setWood(2);

        player = listOfPlayers.get(1);
        player.getResources().setBrick(0);
        player.getResources().setOre(0);
        player.getResources().setSheep(0);
        player.getResources().setWheat(0);
        player.getResources().setWood(0);

        player = listOfPlayers.get(2);
        player.getResources().setOre(1);
        player.getResources().setBrick(3);
        player.getResources().setSheep(2);
        player.getResources().setWood(2);
        player.getResources().setWheat(1);


        player = listOfPlayers.get(3);
        player.getResources().setBrick(0);
        player.getResources().setOre(3);
        player.getResources().setSheep(0);
        player.getResources().setWheat(2);
        player.getResources().setWood(2);

        currentPlayer = listOfPlayers.get(0);
    }

    @Test
    public void shouldDiscardHalf() throws Exception {
        model.getTurnTracker().setStatus(TurnStatus.FIRST_ROUND);
        assertFalse(facade.shouldDiscardHalf(listOfPlayers.get(0)));

        model.getTurnTracker().setStatus(TurnStatus.DISCARDING);
//        assertFalse(facade.shouldDiscardHalf(null));
        assertTrue(facade.shouldDiscardHalf(listOfPlayers.get(0)));
        assertFalse(facade.shouldDiscardHalf(listOfPlayers.get(1)));
        assertTrue(facade.shouldDiscardHalf(listOfPlayers.get(2)));
        assertFalse(facade.shouldDiscardHalf(listOfPlayers.get(3)));
    }

    @Test
    public void canDiscard() throws Exception {
        ResourceSet trueSet = new ResourceSet(1, 1, 1, 1, 1);
        ResourceSet falseSet = new ResourceSet(3, 1, 1, 1, 1);
        ResourceSet roundSet = new ResourceSet(1, 0, 1, 1, 1);

        //Pre-condition tests
        model.getTurnTracker().setStatus(TurnStatus.FIRST_ROUND);
        assertFalse(facade.canDiscard(trueSet, listOfPlayers.get(0)));
//        assertFalse(facade.canDiscard(null, listOfPlayers.get(0)));
        model.getTurnTracker().setStatus(TurnStatus.DISCARDING);
//        assertFalse(facade.canDiscard(null, listOfPlayers.get(0)));
        assertFalse(facade.canDiscard(trueSet, listOfPlayers.get(1)));
        assertFalse(facade.canDiscard(trueSet, listOfPlayers.get(3)));

        //Subset tests
        assertTrue(facade.canDiscard(trueSet, listOfPlayers.get(0)));
        assertFalse(facade.canDiscard(falseSet, listOfPlayers.get(0)));

        //Rounding tests
        assertFalse(facade.canDiscard(roundSet, listOfPlayers.get(0)));
        assertFalse(facade.canDiscard(trueSet, listOfPlayers.get(2)));
        assertTrue(facade.canDiscard(roundSet, listOfPlayers.get(2)));
    }

    @Test
    public void discard() throws Exception {
        ResourceSet set = new ResourceSet(1, 1, 1, 1, 1);
        model.getTurnTracker().setStatus(TurnStatus.FIRST_ROUND);
        try {
            facade.discard(set, listOfPlayers.get(0));
            Assert.fail("The turn status is wrong");
        } catch (IllegalArgumentException e) {
            // Successfully failed
        }

        model.getTurnTracker().setStatus(TurnStatus.DISCARDING);
        facade.discard(set, listOfPlayers.get(0));
        ResourceSet confirm = new ResourceSet(1, 1, 1, 1, 1);
        assertEquals(confirm, listOfPlayers.get(0).getResources());
        set = new ResourceSet(1, 0, 1, 1, 1);
        facade.discard(set, listOfPlayers.get(2));
        ResourceSet confirm2 = new ResourceSet(0, 3, 1, 1, 0);
        assertEquals(confirm2, listOfPlayers.get(2).getResources());
    }

    @Test
    public void canMoveRobber() throws Exception {
        HexLocation newLocation = new HexLocation(2, 2);
        //Preconditions
        model.getTurnTracker().setStatus(TurnStatus.DISCARDING);
        assertFalse(facade.canMoveRobber(newLocation));
        model.getTurnTracker().setStatus(TurnStatus.ROBBING);
//        assertFalse(facade.canMoveRobber(null));
        //Are the positions the same?
        assertFalse(facade.canMoveRobber(gameMap.getRobber()));
        assertTrue(facade.canMoveRobber(newLocation));
    }

    @Test
    public void moveRobber() throws Exception {
        HexLocation newLocation = new HexLocation(2, 2);
        //Preconditions
        model.getTurnTracker().setStatus(TurnStatus.DISCARDING);
        try {
            facade.moveRobber(newLocation);
            Assert.fail("Did not fail when expected");
        } catch (IllegalArgumentException e) {
            // Successfully failed
        }
        model.getTurnTracker().setStatus(TurnStatus.ROBBING);
        try {
            //noinspection ConstantConditions
            facade.moveRobber(null);
            Assert.fail("Did not fail when expected");
        } catch (IllegalArgumentException e) {
            // Successfully failed
        }
        try {
            facade.moveRobber(gameMap.getRobber());
            Assert.fail("Did not fail when expected");
        } catch (IllegalArgumentException e) {
            // Successfully failed
        }
        //Correct?
        facade.moveRobber(newLocation);
        assertEquals(newLocation, gameMap.getRobber());
    }

    @Test
    public void canStealFrom() throws Exception {
        //Pre-conditions
        PlayerIndex target = PlayerIndex.FIRST;
        model.getTurnTracker().setStatus(TurnStatus.DISCARDING);
        assertFalse(facade.canStealFrom(target, currentPlayer.getPlayerIndex())); //Turn status error
        model.getTurnTracker().setStatus(TurnStatus.ROBBING);
        assertFalse(facade.canStealFrom(target, currentPlayer.getPlayerIndex())); //Same player error
        assertTrue(facade.canStealFrom(null, currentPlayer.getPlayerIndex()));    //Null
        target = PlayerIndex.SECOND;
        assertTrue(facade.canStealFrom(target, currentPlayer.getPlayerIndex())); //No resources
        gameMap.setRobber(new HexLocation(0, -1));
        //No settlement
        target = PlayerIndex.THIRD;
        assertFalse(facade.canStealFrom(target, currentPlayer.getPlayerIndex()));
        //Settlement
        target = PlayerIndex.FOURTH;
        assertTrue(facade.canStealFrom(target, currentPlayer.getPlayerIndex()));
    }

    @Test
    public void steal() throws Exception {
        PlayerIndex target = PlayerIndex.FIRST;
        model.getTurnTracker().setStatus(TurnStatus.DISCARDING);
        try {
            facade.steal(target, currentPlayer.getPlayerIndex());
            Assert.fail("Did not fail when expected");

        } catch (IllegalArgumentException e) {
            // Successfully failed

        }

        model.getTurnTracker().setStatus(TurnStatus.ROBBING);

        try {
            facade.steal(target, null);
            Assert.fail("Did not fail when expected");
        } catch (IllegalArgumentException e) {
            // Successfully failed
        }

        try {
            facade.steal(target, currentPlayer.getPlayerIndex());
            Assert.fail("Did not fail when expected");
        } catch (IllegalArgumentException e) {
            // Successfully failed
        }

        target = PlayerIndex.FOURTH;
        facade.steal(target, currentPlayer.getPlayerIndex());
        assertEquals(6, model.getPlayer(target).getResources().getTotal());
        assertEquals(11, currentPlayer.getResources().getTotal());
    }


}