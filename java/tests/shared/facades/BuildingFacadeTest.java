package shared.facades;


import client.game.GameManager;
import shared.locations.*;
import shared.models.game.ClientModel;
import shared.models.game.Player;
import shared.models.game.ResourceSet;
import shared.serialization.ModelExample;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by elija on 9/23/2016.
 */
public class BuildingFacadeTest {
    private BuildingFacade facade;
    private Player player;

    @org.junit.Before
    public void setup(){
        GameManager game = GameManager.getGame();
        ClientModel model = ModelExample.fullJsonModel();
        game.setClientModel(model);
        facade = game.getFacade().getBuilding();
        ArrayList<Player> players = (ArrayList<Player>) model.getPlayers();
        player = players.get(0);
    }

    @org.junit.Test
    public void TestBuildRoad(){
        player.setRoads(2);
        player.setResources(new ResourceSet(0,2,0,1,0));
        assertTrue(facade.canBuildRoad(player,new EdgeLocation(new HexLocation(2,2), EdgeDirection.NorthWest),false));
        facade.buildRoad(player,new EdgeLocation(new HexLocation(2,2), EdgeDirection.NorthWest),false);
        assertTrue(facade.getTotalRoadsBuilt(player)==1);
        assertTrue(player.getRoads()==1);
        assertFalse(facade.canBuildRoad(player,new EdgeLocation(new HexLocation(2,3), EdgeDirection.NorthWest),false));
        assertTrue(facade.canBuildRoad(player,new EdgeLocation(new HexLocation(2,3), EdgeDirection.NorthWest),true));
        facade.buildRoad(player,new EdgeLocation(new HexLocation(2,3), EdgeDirection.NorthWest),true);
        assertTrue(facade.getTotalRoadsBuilt(player)==2);
        assertTrue(player.getRoads()==0);
        player.setResources(new ResourceSet(0,2,0,1,0));
        assertFalse(facade.canBuildRoad(player,new EdgeLocation(new HexLocation(3,3), EdgeDirection.NorthWest),false));
        player.setRoads(1);
        assertFalse(facade.canBuildRoad(player,new EdgeLocation(new HexLocation(2,2), EdgeDirection.NorthWest),false));
    }

    @org.junit.Test
    public void buildSettlement(){
        player.setSettlements(2);
        player.setResources(new ResourceSet(0,2,0,1,0));
        assertTrue(facade.canBuildSettlement(player,new VertexLocation(new HexLocation(2,2), VertexDirection.NorthWest),false));
        facade.buildSettlement(player,new VertexLocation(new HexLocation(2,2), VertexDirection.NorthWest),false);
        assertTrue(player.getSettlements()==1);
        assertFalse(facade.canBuildSettlement(player,new VertexLocation(new HexLocation(2,3), VertexDirection.NorthWest),false));
        assertTrue(facade.canBuildSettlement(player,new VertexLocation(new HexLocation(2,3), VertexDirection.NorthWest),true));
        facade.buildSettlement(player,new VertexLocation(new HexLocation(2,3), VertexDirection.NorthWest),true);
        assertTrue(player.getRoads()==0);
        player.setResources(new ResourceSet(0,2,0,1,0));
        assertFalse(facade.canBuildSettlement(player,new VertexLocation(new HexLocation(3,3), VertexDirection.NorthWest),false));
        player.setSettlements(1);
        assertFalse(facade.canBuildSettlement(player,new VertexLocation(new HexLocation(2,2), VertexDirection.NorthWest),false));
    }

    @org.junit.Test
    public void buildCity(){
        player.setCities(2);
        player.setResources(new ResourceSet(0,2,0,1,0));
        assertTrue(facade.canBuildCity(player,new VertexLocation(new HexLocation(2,2), VertexDirection.NorthWest)));
        facade.buildCity(player,new VertexLocation(new HexLocation(2,2), VertexDirection.NorthWest));
        assertTrue(player.getCities()==1);
        assertFalse(facade.canBuildCity(player,new VertexLocation(new HexLocation(2,3), VertexDirection.NorthWest)));
        facade.buildCity(player,new VertexLocation(new HexLocation(2,3), VertexDirection.NorthWest));
        assertTrue(player.getCities()==0);
        player.setResources(new ResourceSet(0,2,0,1,0));
        assertFalse(facade.canBuildCity(player,new VertexLocation(new HexLocation(3,3), VertexDirection.NorthWest)));
        player.setCities(1);
        assertFalse(facade.canBuildCity(player,new VertexLocation(new HexLocation(2,2), VertexDirection.NorthWest)));
    }
}
