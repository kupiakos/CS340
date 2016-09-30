package shared.facades;


import client.game.GameManager;
import shared.locations.*;
import shared.models.game.ClientModel;
import shared.models.game.Player;
import shared.models.game.ResourceSet;
import shared.serialization.ModelExample;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by elija on 9/23/2016.
 */
public class BuildingFacadeTest {
    private BuildingFacade facade;
    private Player player;

    @org.junit.Before
    public void setup() {
        ClientModel model = ModelExample.fullJsonModel();
        GameManager game = GameManager.getGame();
        game.setClientModel(model);
        game.getFacade();
        facade = game.getFacade().getBuilding();
        ArrayList<Player> players = (ArrayList<Player>) model.getPlayers();
        player = players.get(0);
    }

    @org.junit.Test
    public void testBuildRoad() {
        facade.getModel().getMap().setRoads(new HashMap<>());
        facade.getModel().getMap().setSettlements(new HashMap<>());
        facade.getModel().getMap().setCities(new HashMap<>());
        facade.getModel().getMap().getRoads().put(new EdgeLocation(new HexLocation(0, 0), EdgeDirection.North).getNormalizedLocation(), player.getPlayerIndex());
        player.setRoads(2);
        player.setResources(new ResourceSet(0, 2, 0, 1, 0));
        assertTrue(facade.canBuildRoad(player, new EdgeLocation(new HexLocation(1, -1), EdgeDirection.NorthWest), false));
        facade.buildRoad(player, new EdgeLocation(new HexLocation(1, -1), EdgeDirection.NorthWest), false);
        assertTrue(facade.getTotalRoadsBuilt(player) == 2);
        assertTrue(player.getRoads() == 1);
        //assertFalse(facade.canBuildRoad(player,new EdgeLocation(new HexLocation(1,-1), EdgeDirection.North),false));
        assertTrue(facade.canBuildRoad(player, new EdgeLocation(new HexLocation(1, -1), EdgeDirection.North), true));
        facade.buildRoad(player, new EdgeLocation(new HexLocation(1, -1), EdgeDirection.North), true);
        assertTrue(facade.getTotalRoadsBuilt(player) == 3);
        assertTrue(player.getRoads() == 0);
        player.setResources(new ResourceSet(0, 2, 0, 1, 0));
        assertFalse(facade.canBuildRoad(player, new EdgeLocation(new HexLocation(0, 0), EdgeDirection.NorthWest), false));
        player.setRoads(1);
        assertTrue(facade.canBuildRoad(player, new EdgeLocation(new HexLocation(0, 0), EdgeDirection.NorthWest), false));
        facade.buildRoad(player, new EdgeLocation(new HexLocation(0, 0), EdgeDirection.NorthWest), false);
        assertFalse(facade.canBuildRoad(player, new EdgeLocation(new HexLocation(0, 0), EdgeDirection.NorthWest), false));

    }

    @org.junit.Test
    public void testBuildSettlement() {
        facade.getModel().getMap().setRoads(new HashMap<>());
        facade.getModel().getMap().setSettlements(new HashMap<>());
        facade.getModel().getMap().setCities(new HashMap<>());
        facade.getModel().getMap().getRoads().put(new EdgeLocation(new HexLocation(0, 0), EdgeDirection.North).getNormalizedLocation(), player.getPlayerIndex());
        facade.getModel().getMap().getRoads().put(new EdgeLocation(new HexLocation(0, 0), EdgeDirection.NorthEast).getNormalizedLocation(), player.getPlayerIndex());
        facade.getModel().getMap().getRoads().put(new EdgeLocation(new HexLocation(1, -1), EdgeDirection.NorthWest).getNormalizedLocation(), player.getPlayerIndex());
        player.setSettlements(2);
        player.setResources(new ResourceSet(0, 2, 1, 1, 1));
        assertTrue(facade.canBuildSettlement(player, new VertexLocation(new HexLocation(0, 0), VertexDirection.NorthWest), false));
        facade.buildSettlement(player, new VertexLocation(new HexLocation(0, 0), VertexDirection.NorthWest), false);
        assertTrue(player.getSettlements() == 1);
        //assertFalse(facade.canBuildSettlement(player, new VertexLocation(new HexLocation(0, 0), VertexDirection.East), false));
        assertTrue(facade.canBuildSettlement(player, new VertexLocation(new HexLocation(0, 0), VertexDirection.East), true));
        facade.buildSettlement(player, new VertexLocation(new HexLocation(0, 0), VertexDirection.East), true);
        assertTrue(player.getSettlements() == 0);
        player.setResources(new ResourceSet(0, 2, 1, 1, 1));
        assertFalse(facade.canBuildSettlement(player, new VertexLocation(new HexLocation(1, -1), VertexDirection.NorthWest), false));
        player.setSettlements(1);
        assertTrue(facade.canBuildSettlement(player, new VertexLocation(new HexLocation(1, -1), VertexDirection.NorthWest), false));
    }

    @org.junit.Test
    public void testBuildCity() {
        facade.getModel().getMap().setRoads(new HashMap<>());
        facade.getModel().getMap().setSettlements(new HashMap<>());
        facade.getModel().getMap().setCities(new HashMap<>());
        facade.getModel().getMap().getSettlements().put(new VertexLocation(new HexLocation(0,0), VertexDirection.NorthWest).getNormalizedLocation(), player.getPlayerIndex());
        facade.getModel().getMap().getSettlements().put(new VertexLocation(new HexLocation(-1,2), VertexDirection.SouthWest).getNormalizedLocation(), player.getPlayerIndex());
        assertTrue(true);
        player.setCities(2);
        player.setResources(new ResourceSet(3,2,1,1,3));
        assertTrue(facade.canBuildCity(player,new VertexLocation(new HexLocation(0,0), VertexDirection.NorthWest)));
        facade.buildCity(player,new VertexLocation(new HexLocation(0,0), VertexDirection.NorthWest));
        assertTrue(player.getCities()==1);
        //assertFalse(facade.canBuildCity(player,new VertexLocation(new HexLocation(-1,2), VertexDirection.SouthWest)));
        player.setResources(new ResourceSet(3,2,1,1,2));
        assertTrue(facade.canBuildCity(player,new VertexLocation(new HexLocation(-1,2), VertexDirection.SouthWest)));
        facade.buildCity(player,new VertexLocation(new HexLocation(-1,2), VertexDirection.SouthWest));
        assertTrue(player.getCities()==0);
        assertFalse(facade.canBuildCity(player,new VertexLocation(new HexLocation(0,0), VertexDirection.NorthWest)));
    }

    @org.junit.Test
    public void testGetTotalRoadsBuilt(){
        facade.getModel().getMap().setRoads(new HashMap<>());
        facade.getModel().getMap().setSettlements(new HashMap<>());
        facade.getModel().getMap().setCities(new HashMap<>());
        assertTrue(facade.getTotalRoadsBuilt(player)==0);
        facade.getModel().getMap().getRoads().put(new EdgeLocation(new HexLocation(0, 0), EdgeDirection.North).getNormalizedLocation(), player.getPlayerIndex());
        assertTrue(facade.getTotalRoadsBuilt(player)==1);
        facade.getModel().getMap().getRoads().put(new EdgeLocation(new HexLocation(0, 0), EdgeDirection.NorthEast).getNormalizedLocation(), player.getPlayerIndex());
        assertTrue(facade.getTotalRoadsBuilt(player)==2);
        facade.getModel().getMap().getRoads().put(new EdgeLocation(new HexLocation(0, 0), EdgeDirection.SouthWest).getNormalizedLocation(), player.getPlayerIndex());
        assertTrue(facade.getTotalRoadsBuilt(player)==3);
        facade.getModel().getMap().getRoads().put(new EdgeLocation(new HexLocation(0, 0), EdgeDirection.South).getNormalizedLocation(), player.getPlayerIndex());
        assertTrue(facade.getTotalRoadsBuilt(player)==4);
    }
}
