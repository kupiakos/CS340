package shared.facades;

import org.junit.Test;
import shared.definitions.PlayerIndex;
import shared.locations.*;
import shared.models.game.ClientModel;
import shared.serialization.ModelExample;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by elija on 9/30/2016.
 */
public class MapFacadeTest {
    private MapFacade facade;
    private ClientModel model;

    @org.junit.Before
    public void setup() {
        model = ModelExample.fullJsonModel();
        model.getMap().setRoads(new HashMap<>());
        model.getMap().setSettlements(new HashMap<>());
        model.getMap().setCities(new HashMap<>());
        FacadeManager facades = new FacadeManager(model);
        facade = facades.getMap();

        model.getMap().getRoads().put(new EdgeLocation(new HexLocation(0, 0), EdgeDirection.NorthWest).getNormalizedLocation(), PlayerIndex.FIRST);
        model.getMap().getRoads().put(new EdgeLocation(new HexLocation(0, 0), EdgeDirection.North).getNormalizedLocation(), PlayerIndex.FIRST);
        model.getMap().getRoads().put(new EdgeLocation(new HexLocation(0, 0), EdgeDirection.NorthEast).getNormalizedLocation(), PlayerIndex.FIRST);

        model.getMap().getRoads().put(new EdgeLocation(new HexLocation(1, 0), EdgeDirection.North).getNormalizedLocation(), PlayerIndex.SECOND);
        model.getMap().getRoads().put(new EdgeLocation(new HexLocation(1, 0), EdgeDirection.NorthEast).getNormalizedLocation(), PlayerIndex.SECOND);

        model.getMap().getRoads().put(new EdgeLocation(new HexLocation(1, 1), EdgeDirection.SouthEast).getNormalizedLocation(), PlayerIndex.THIRD);
        model.getMap().getRoads().put(new EdgeLocation(new HexLocation(1, 1), EdgeDirection.South).getNormalizedLocation(), PlayerIndex.THIRD);
        model.getMap().getRoads().put(new EdgeLocation(new HexLocation(1, 1), EdgeDirection.SouthWest).getNormalizedLocation(), PlayerIndex.THIRD);
        model.getMap().getRoads().put(new EdgeLocation(new HexLocation(1, 1), EdgeDirection.NorthWest).getNormalizedLocation(), PlayerIndex.THIRD);
    }

    @Test
    public void findLongestRoad() {
        assertNotEquals(PlayerIndex.FIRST, facade.findLongestRoad());
        model.getMap().getRoads().put(new EdgeLocation(new HexLocation(0, 0), EdgeDirection.SouthEast).getNormalizedLocation(), PlayerIndex.FIRST);
        assertNotEquals(PlayerIndex.FIRST, facade.findLongestRoad());
        model.getMap().getRoads().put(new EdgeLocation(new HexLocation(0, 0), EdgeDirection.South).getNormalizedLocation(), PlayerIndex.FIRST);
        assertEquals(PlayerIndex.FIRST, facade.findLongestRoad());
        assertNotEquals(PlayerIndex.THIRD, facade.findLongestRoad());
        model.getMap().getRoads().put(new EdgeLocation(new HexLocation(1, 1), EdgeDirection.North).getNormalizedLocation(), PlayerIndex.THIRD);
        assertEquals(PlayerIndex.FIRST, facade.findLongestRoad());

        model.getMap().getSettlements().put(new VertexLocation(new HexLocation(0, 0), VertexDirection.East).getNormalizedLocation(), PlayerIndex.SECOND);
        assertNotEquals(PlayerIndex.FIRST, facade.findLongestRoad());
        //assertNull(facade.findLongestRoad());

        assertEquals(PlayerIndex.THIRD, facade.findLongestRoad());
        model.getMap().getRoads().put(new EdgeLocation(new HexLocation(1, 1), EdgeDirection.NorthEast).getNormalizedLocation(), PlayerIndex.THIRD);
        assertEquals(PlayerIndex.THIRD, facade.findLongestRoad());
    }

    public void findLongestRoadAgain() {
        model.getMap().getRoads().put(new EdgeLocation(new HexLocation(1, 0), EdgeDirection.NorthWest).getNormalizedLocation(), PlayerIndex.FIRST);
        model.getMap().getRoads().put(new EdgeLocation(new HexLocation(0, 0), EdgeDirection.North).getNormalizedLocation(), PlayerIndex.FIRST);
        model.getMap().getRoads().put(new EdgeLocation(new HexLocation(0, 0), EdgeDirection.NorthEast).getNormalizedLocation(), PlayerIndex.FIRST);
        model.getMap().getRoads().put(new EdgeLocation(new HexLocation(1, -1), EdgeDirection.NorthWest).getNormalizedLocation(), PlayerIndex.FIRST);
        model.getMap().getRoads().put(new EdgeLocation(new HexLocation(1, -1), EdgeDirection.North).getNormalizedLocation(), PlayerIndex.FIRST);
        model.getMap().getRoads().put(new EdgeLocation(new HexLocation(1, -1), EdgeDirection.NorthEast).getNormalizedLocation(), PlayerIndex.FIRST);
        model.getMap().getRoads().put(new EdgeLocation(new HexLocation(0, -1), EdgeDirection.North).getNormalizedLocation(), PlayerIndex.FIRST);
        model.getMap().getRoads().put(new EdgeLocation(new HexLocation(0, -1), EdgeDirection.NorthEast).getNormalizedLocation(), PlayerIndex.FIRST);
        model.getMap().getRoads().put(new EdgeLocation(new HexLocation(2, -1), EdgeDirection.NorthWest).getNormalizedLocation(), PlayerIndex.FIRST);
        model.getMap().getRoads().put(new EdgeLocation(new HexLocation(-1, 1), EdgeDirection.NorthEast).getNormalizedLocation(), PlayerIndex.FIRST);

        assertNotEquals(PlayerIndex.FIRST, facade.findLongestRoad());

        model.getMap().getRoads().put(new EdgeLocation(new HexLocation(-1, 0), EdgeDirection.NorthEast).getNormalizedLocation(), PlayerIndex.FIRST);

        assertEquals(PlayerIndex.FIRST, facade.findLongestRoad());

    }
}
