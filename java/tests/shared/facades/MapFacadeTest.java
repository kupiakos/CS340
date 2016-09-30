package shared.facades;

import client.game.GameManager;
import org.junit.Test;
import shared.definitions.PlayerIndex;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.models.game.ClientModel;
import shared.models.game.Player;
import shared.serialization.ModelExample;

import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 * Created by elija on 9/30/2016.
 */
public class MapFacadeTest {
    private MapFacade facade;
    private Player player;

    @org.junit.Before
    public void setup() {
        ClientModel model = ModelExample.fullJsonModel();
        GameManager game = GameManager.getGame();
        game.setClientModel(model);
        game.getFacade();
        facade = game.getFacade().getMap();
        ArrayList<Player> players = (ArrayList<Player>) model.getPlayers();
        player = players.get(0);
    }

    @Test
    public void findLongestRoad(){
        assertTrue(facade.findLongestRoad()==PlayerIndex.FIRST);
        GameManager.getGame().getClientModel().getMap().getRoads().put(new EdgeLocation(new HexLocation(0,0), EdgeDirection.NorthWest), PlayerIndex.FOURTH);
        assertTrue(facade.findLongestRoad()==PlayerIndex.FOURTH);
        GameManager.getGame().getClientModel().getMap().getRoads().put(new EdgeLocation(new HexLocation(0,2), EdgeDirection.NorthEast), PlayerIndex.SECOND);
        assertFalse(facade.findLongestRoad()==PlayerIndex.SECOND);
        assertTrue(facade.findLongestRoad()==PlayerIndex.FOURTH);
    }
}
