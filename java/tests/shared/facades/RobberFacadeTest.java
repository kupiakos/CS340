package shared.facades;

import client.game.GameManager;
import org.junit.Test;
import shared.locations.HexLocation;
import shared.models.game.ClientModel;
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
    private HexLocation newLocation;
    private HexLocation currentLocation;

    @org.junit.Before
    public void setup()
    {
        GameManager game = GameManager.getGame();
        model = ModelExample.fullJsonModel();
        game.setClientModel(model);
        facade = game.getFacade().getRobber();
        currentLocation = model.getMap().getRobber();
        listOfPlayers = model.getPlayers();
    }

    @Test
    public void callRobberMethodsByRollingA7() throws Exception {

    }

    @Test
    public void callRobberMethodsByAKnightCard() throws Exception {

    }

    @Test
    public void shouldDiscardHalf() throws Exception {
        assertNotNull(currentPlayer);
        assertNotNull(currentPlayer.getResources());
        int bricks = currentPlayer.getResources().getBrick();
        int ores = currentPlayer.getResources().getOre();
        int grains = currentPlayer.getResources().getWheat();
        int wools = currentPlayer.getResources().getSheep();
        int woods = currentPlayer.getResources().getWood();
        int total = bricks + ores + grains + wools + woods;
        assertTrue(total >= 8);
    }

    @Test
    public void discardHalf() throws Exception {

    }

    @Test
    public void canMoveRobber() throws Exception {
        assertNotNull(newLocation);
        assertNotNull(currentLocation);
        assertNotEquals(newLocation, currentLocation);
    }

    @Test
    public void moveRobber() throws Exception {

    }

    @Test
    public void canStealFrom() throws Exception {

    }

    @Test
    public void stealFrom() throws Exception {

    }

}