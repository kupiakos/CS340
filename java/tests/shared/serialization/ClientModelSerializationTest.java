package shared.serialization;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.junit.Assert;
import org.junit.Test;
import shared.models.game.ClientModel;

public class ClientModelSerializationTest {

    @Test
    public void deserializeFull() throws Exception {
        ClientModel model = ModelSerializer.getInstance().fromJson(
                ModelExample.fullJson, ClientModel.class);
        Assert.assertNotNull(model);
        // Need to add more
    }

    @Test
    public void serializeDefault() throws Exception {
        ClientModel model = new ClientModel();

        JsonElement e = ModelSerializer.getInstance().toJsonTree(
                model, ClientModel.class
        );

        Assert.assertEquals(
                new JsonParser().parse("{\n" +
                        "  \"players\": [],\n" +
                        "  \"winner\": -1,\n" +
                        "  \"version\": 0\n" +
                        "}"),
                e
        );
    }


    // TODO: Add extra de/serialization tests
}
