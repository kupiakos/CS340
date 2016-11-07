package shared.facades;

import org.junit.Test;
import shared.definitions.PlayerIndex;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.models.game.ClientModel;
import shared.serialization.ModelExample;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by elija on 9/30/2016.
 */
public class MapFacadeTest {
    private MapFacade facade;
    private ClientModel model;

    @org.junit.Before
    public void setup() {
        model = ModelExample.fullJsonModel();
        FacadeManager facades = new FacadeManager(model);
        facade = facades.getMap();
    }

    @Test
    public void findLongestRoad() {
        // TODO: Test enemy settlements breaking roads and cycles
        assertEquals(PlayerIndex.FIRST, facade.findLongestRoad());
        model.getMap().getRoads().put(new EdgeLocation(new HexLocation(0, 0), EdgeDirection.NorthWest), PlayerIndex.FOURTH);
        assertEquals(PlayerIndex.FOURTH, facade.findLongestRoad());
        model.getMap().getRoads().put(new EdgeLocation(new HexLocation(0, 2), EdgeDirection.NorthEast), PlayerIndex.SECOND);
        assertNotEquals(PlayerIndex.SECOND, facade.findLongestRoad());
        assertEquals(PlayerIndex.FOURTH, facade.findLongestRoad());
    }
}
