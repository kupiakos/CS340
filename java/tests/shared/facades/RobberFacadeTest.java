package shared.facades;

import client.game.GameManager;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
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
public class RobberFacadeTest {

    private ClientModel model;
    private Player currentPlayer;
    private List<Player> listOfPlayers;
    private RobberFacade facade;
    private HexLocation currentLocation;
    private GameMap gameMap;
    private PlayerIndex index;

    @Rule
    public final ExpectedException exception = ExpectedException.none();//EdgeLocationSerializerTest line 49

    @org.junit.Before
    public void setup()
    {
        GameManager game = GameManager.getGame();
        model = ModelExample.fullJsonModel();
        game.setClientModel(model);
        facade = game.getFacade().getRobber();
        listOfPlayers = model.getPlayers();
        gameMap = model.getMap();
        gameMap.setRobber(new HexLocation(-1, 1));
        currentLocation = gameMap.getRobber();
        //index = new PlayerIndex(4);

        Player player = listOfPlayers.get(0);
        player.getResources().setBrick(2);
        player.getResources().setOre(2);
        player.getResources().setSheep(2);
        player.getResources().setWheat(2);
        player.getResources().setWood(2);
        HexLocation hexLocation = new HexLocation(2, 0);
        VertexLocation vertexLocation = new VertexLocation(hexLocation, VertexDirection.East);
        //gameMap.addSettlement(vertexLocation, player);

        player = listOfPlayers.get(1);
        player.getResources().setBrick(0);
        player.getResources().setOre(0);
        player.getResources().setSheep(0);
        player.getResources().setWheat(0);
        player.getResources().setWood(0);
        vertexLocation = new VertexLocation(hexLocation, VertexDirection.NorthWest);
        //gameMap.addSettlement(vertexLocation, );

        player = listOfPlayers.get(2);
        player.getResources().setBrick(3);
        player.getResources().setOre(1);
        player.getResources().setSheep(2);
        player.getResources().setWheat(1);
        player.getResources().setWood(2);


        player = listOfPlayers.get(3);
        player.getResources().setBrick(0);
        player.getResources().setOre(3);
        player.getResources().setSheep(0);
        player.getResources().setWheat(2);
        player.getResources().setWood(2);
        vertexLocation = new VertexLocation(hexLocation, VertexDirection.SouthWest);
        //gameMap.addSettlement(vertexLocation, player);
        //gameMap.upgradeSettlement(vertexLocation, player);

        currentPlayer = listOfPlayers.get(0);
    }

    @Test
    public void shouldDiscardHalf() throws Exception {
        Player player = null;
        assertNull(player);

        player = listOfPlayers.get(0);
        assertNotNull(player);
        assertNotNull(player.getResources());
        int bricks = player.getResources().getBrick();
        int ores = player.getResources().getOre();
        int grains = player.getResources().getWheat();
        int wools = player.getResources().getSheep();
        int woods = player.getResources().getWood();
        int total = bricks + ores + grains + wools + woods;
        assertTrue(total >= 8);

        player = listOfPlayers.get(1);
        assertNotNull(player);
        assertNotNull(player.getResources());
        bricks = player.getResources().getBrick();
        ores = player.getResources().getOre();
        grains = player.getResources().getWheat();
        wools = player.getResources().getSheep();
        woods = player.getResources().getWood();
        total = bricks + ores + grains + wools + woods;
        assertFalse(total >= 8);

        player = listOfPlayers.get(2);
        assertNotNull(player);
        assertNotNull(player.getResources());
        bricks = player.getResources().getBrick();
        ores = player.getResources().getOre();
        grains = player.getResources().getWheat();
        wools = player.getResources().getSheep();
        woods = player.getResources().getWood();
        total = bricks + ores + grains + wools + woods;
        assertTrue(total >= 8);

        player = listOfPlayers.get(3);
        assertNotNull(player);
        assertNotNull(player.getResources());
        bricks = player.getResources().getBrick();
        ores = player.getResources().getOre();
        grains = player.getResources().getWheat();
        wools = player.getResources().getSheep();
        woods = player.getResources().getWood();
        total = bricks + ores + grains + wools + woods;
        assertFalse(total >= 8);
    }

    @Test
    public void canDiscard() throws Exception {
        Player p = null;
        ResourceSet discardSet = null;
        assertNull(p);
        assertNull(discardSet);

        p = listOfPlayers.get(0);
        discardSet = new ResourceSet(3, 1, 1, 1, 1);
        assertFalse(discardSet.isSubset(p.getResources()));

        discardSet = new ResourceSet(1, 1, 0, 1, 1);
        assertTrue(discardSet.isSubset(p.getResources()));
        int bricks = p.getResources().getBrick();
        int ores = p.getResources().getOre();
        int grains = p.getResources().getWheat();
        int sheep = p.getResources().getSheep();
        int woods = p.getResources().getWood();
        int total = bricks + ores + grains + sheep + woods;
        int half = total / 2;
        assertEquals(5, half);
        assertNotEquals(half, 4);
        if (half == 4)
        {
            discard(discardSet);
        }

        discardSet = new ResourceSet(1, 1, 1, 1, 1);
        assertTrue(discardSet.isSubset(p.getResources()));
        bricks = p.getResources().getBrick();
        ores = p.getResources().getOre();
        grains = p.getResources().getWheat();
        sheep = p.getResources().getSheep();
        woods = p.getResources().getWood();
        total = bricks + ores + grains + sheep + woods;
        half = total / 2;
        assertEquals(half, 5);
        if (half == 5)
        {
            discard(discardSet);
        }

        Player p2 = listOfPlayers.get(2);
        ResourceSet discardSet2 = new ResourceSet(0, 1, 1, 1, 1);
        assertTrue(discardSet2.isSubset(p2.getResources()));
        bricks = p2.getResources().getBrick();
        ores = p2.getResources().getOre();
        grains = p2.getResources().getWheat();
        sheep = p2.getResources().getSheep();
        woods = p2.getResources().getWood();
        total = bricks + ores + grains + sheep + woods;
        half = total / 2;
        assertNotEquals(half, 4);
    }

    //@Test
    public void discard(ResourceSet discardSet) throws Exception {
        Player p = null;

        p = listOfPlayers.get(0);
        assertNotNull(discardSet);
        assertNotNull(p);
        int bricks = p.getResources().getBrick();
        int ores = p.getResources().getOre();
        int grains = p.getResources().getWheat();
        int wools = p.getResources().getSheep();
        int woods = p.getResources().getWood();
        bricks = bricks - discardSet.getBrick();
        ores = ores - discardSet.getOre();
        grains = grains - discardSet.getWheat();
        wools = wools - discardSet.getSheep();
        woods = woods - discardSet.getWood();
        p.getResources().setBrick(bricks);
        p.getResources().setOre(ores);
        p.getResources().setWheat(grains);
        p.getResources().setSheep(wools);
        p.getResources().setWood(woods);

        assertEquals(1, p.getResources().getBrick());
        assertEquals(1, p.getResources().getOre());
        assertEquals(1, p.getResources().getWheat());
        assertEquals(1, p.getResources().getSheep());
        assertEquals(1, p.getResources().getWood());

    }

    @Test
    public void canMoveRobber() throws Exception {
        HexLocation newLocation = null;
        assertNull(newLocation);

        newLocation = gameMap.getRobber();
        assertNotNull(newLocation);
        assertNotNull(currentLocation);
        assertEquals(newLocation, currentLocation);//Robber cannot move to the same place
        if (newLocation != currentLocation)
        {
            moveRobber(newLocation);
        }

        newLocation = new HexLocation(2, 2);
        assertNotNull(newLocation);
        assertNotNull(currentLocation);
        assertNotEquals(newLocation, currentLocation);//Robber can move
        if (newLocation != currentLocation)
        {
            moveRobber(newLocation);
        }
    }

    //@Test
    public void moveRobber(HexLocation newLocation) throws Exception {
        assertNotNull(newLocation);
        assertNotNull(currentLocation);
        assertNotEquals(newLocation, currentLocation);
        gameMap.setRobber(newLocation);
        assertEquals(newLocation, gameMap.getRobber());
    }

    @Test
    public void canStealFrom() throws Exception {
        Player p = null;
        ResourceType resourceType = null;
        assertNull(p);
        assertNull(resourceType);
        ResourceSet renew = new ResourceSet(2, 2, 2, 2, 2);
        currentPlayer.setResources(renew);

        p = listOfPlayers.get(1);
        //Check if a players settlement is there; assertTrue
        resourceType = ResourceType.BRICK;
        assertNotNull(p);
        assertNotNull(currentPlayer);
        assertEquals(resourceType, ResourceType.BRICK);
        int bricks = p.getResources().getBrick();
        assertFalse(bricks > 0);
        //Cannot steal a brick

        p = listOfPlayers.get(2);
        //There should be no settlement; assertFalse here

        p = listOfPlayers.get(3);
        //There should be a city; assertTrue
        resourceType = ResourceType.ORE;
        assertNotNull(p);
        assertNotNull(currentPlayer);
        assertEquals(resourceType, ResourceType.ORE);
        int ore = p.getResources().getOre();
        assertTrue(ore > 0);
        steal(p, resourceType);
    }

    //@Test
    public void steal(Player p, ResourceType resourceType) throws Exception {
        p.getResources().setOre(p.getResources().getOre() - 1);
        assertEquals(p.getResources().getOre(), 2);
        currentPlayer.getResources().setOre(currentPlayer.getResources().getOre() + 1);
        assertEquals(currentPlayer.getResources().getOre(), 3);
    }

}