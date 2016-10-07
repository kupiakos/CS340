package shared.models.game;

import org.junit.Assert;
import org.junit.Test;
import shared.serialization.ModelExample;

public class ClientModelTest {

    @Test
    public void createdEqual() throws Exception {
        ClientModel m1 = ModelExample.fullJsonModel(), m2 = ModelExample.fullJsonModel();
//        Assert.assertTrue(m1.getPlayers().get(0).equals(m2.getPlayers().get(0)));
        Assert.assertEquals(m1, m2);
    }
}
