package client.server;

import shared.IServerProxy;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

/**
 * Created by elijahgk on 9/12/2016.
 * This class is used for testing purposes by hard coding results for Catan Server API requests.
 */
public class MockProxy implements IServerProxy{
    @Override
    public boolean discardCards() {
        return false;
    }

    @Override
    public int rollNumber() {
        return 0;
    }

    @Override
    public boolean buildRoad(boolean free, EdgeLocation roadLocation) {
        return false;
    }

    @Override
    public boolean buildSettlement(boolean free, VertexLocation settlementLocation) {
        return false;
    }

    @Override
    public boolean buildCity(VertexLocation cityLocation) {
        return false;
    }

    @Override
    public boolean offerTrade() {
        return false;
    }

    @Override
    public boolean maritimeTrade(int ratio, ResourceType inputResource, ResourceType outputResource) {
        return false;
    }

    @Override
    public boolean robPlayer(HexLocation newLocation) {
        return false;
    }

    @Override
    public void finishTurn() {

    }

    @Override
    public boolean buyDevCard() {
        return false;
    }

    @Override
    public boolean useSoldier(HexLocation newLocation) {
        return false;
    }

    @Override
    public boolean useYearOfPlenty(ResourceType Resource1, ResourceType Resource2) {
        return false;
    }

    @Override
    public boolean useRoadBuildinng(EdgeLocation edge1, EdgeLocation edge2) {
        return false;
    }

    @Override
    public boolean useMonopoly(ResourceType resourceType) {
        return false;
    }

    @Override
    public boolean useMonument() {
        return false;
    }
}
