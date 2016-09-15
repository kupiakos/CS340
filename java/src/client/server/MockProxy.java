package client.server;

import shared.IServerProxy;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.models.*;

/**
 * Created by elijahgk on 9/12/2016.
 * This class is used for testing purposes by hard coding results for Catan Server API requests.
 */
public class MockProxy implements IServerProxy{

    public MockProxy(){}

    @Override
    public boolean discardCards(DiscardCards discardCardsObject) {
        return false;
    }

    @Override
    public int rollNumber() {
        return 0;
    }

    @Override
    public boolean buildRoad(BuildRoad buildRoadObject) {
        return false;
    }

    @Override
    public boolean buildSettlement(BuildSettlement buildSettlementObject) {
        return false;
    }

    @Override
    public boolean buildCity(BuildCity buildCityObject) {
        return false;
    }

    @Override
    public boolean offerTrade(OfferTrade offerTradeObject) {
        return false;
    }

    @Override
    public boolean maritimeTrade(MaritimeTrade maritimeTradeObject) {
        return false;
    }

    @Override
    public boolean robPlayer(RobPlayer robPlayerObject) {
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
    public boolean useSoldier(Soldier soldierObject) {
        return false;
    }

    @Override
    public boolean useYearOfPlenty(YearOfPlenty yearOfPlentyObject) {
        return false;
    }

    @Override
    public boolean useRoadBuildinng(RoadBuilding roadBuildingObject) {
        return false;
    }

    @Override
    public boolean useMonopoly(Monopoly monopolyObject) {
        return false;
    }

    @Override
    public boolean useMonument() {
        return false;
    }
}
