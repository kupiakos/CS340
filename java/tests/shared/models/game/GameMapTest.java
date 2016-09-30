package shared.models.game;

import org.junit.Assert;
import org.junit.Test;
import shared.serialization.ModelExample;

public class GameMapTest {

    @Test
    public void getPlayerCities() throws Exception {

    }

    @Test
    public void getPlayerSettlements() throws Exception {

    }

    @Test
    public void getPlayerRoads() throws Exception {

    }

    @Test
    public void getHex() throws Exception {

    }

    @Test
    public void getHexesWithNumber() throws Exception {

    }

    @Test
    public void getPort() throws Exception {

    }

    @Test
    public void getRoadOwner() throws Exception {

    }

    @Test
    public void getSettlementOwner() throws Exception {

    }

    @Test
    public void getCityOwner() throws Exception {

    }

    @Test
    public void canAddSettlement() throws Exception {

    }

    @Test
    public void addSettlement() throws Exception {

    }

    @Test
    public void canAddRoad() throws Exception {

    }

    @Test
    public void addRoad() throws Exception {

    }

    @Test
    public void canUpgradeSettlement() throws Exception {

    }

    @Test
    public void upgradeSettlement() throws Exception {

    }

    @Test
    public void createdEqual() throws Exception {
        ClientModel m1 = ModelExample.fullJsonModel(), m2 = ModelExample.fullJsonModel();
        Assert.assertEquals(m1.getMap(), m2.getMap());
    }

}