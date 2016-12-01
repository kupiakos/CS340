package shared.models.game;

import org.junit.Assert;
import org.junit.Test;
import shared.serialization.ModelExample;

public class GameMapTest {


    @Test
    public void createdEqual() throws Exception {
        ClientModel m1 = ModelExample.fullJsonModel(), m2 = ModelExample.fullJsonModel();
        Assert.assertEquals(m1.getMap(), m2.getMap());
    }

}