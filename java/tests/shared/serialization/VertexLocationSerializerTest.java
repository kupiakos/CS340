package shared.serialization;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

public class VertexLocationSerializerTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void deserialize() throws Exception {
        VertexLocation loc = ModelSerializer.getInstance().fromJson(
                "{\"x\": 3, \"y\": -2, \"direction\": \"W\"}",
                VertexLocation.class
        );
        Assert.assertNotNull(loc);
        Assert.assertEquals(3, loc.getHexLoc().getX());
        Assert.assertEquals(-2, loc.getHexLoc().getY());
        Assert.assertEquals(new HexLocation(3, -2), loc.getHexLoc());
        Assert.assertEquals(VertexDirection.West, loc.getDir());
    }

    @Test
    public void serialize() throws Exception {
        JsonElement e = ModelSerializer.getInstance().toJsonTree(
                new VertexLocation(
                        new HexLocation(42, 0),
                        VertexDirection.SouthEast
                ),
                VertexLocation.class
        );
        Assert.assertEquals(
                new JsonParser().parse("{\"x\": 42, \"y\": 0, \"direction\": \"SE\"}"),
                e
        );
    }

    @Test
    public void deserializeFailsWithInvalidDirection() throws Exception {
        // West is an invalid EdgeDirection
        exception.expect(JsonParseException.class);
        ModelSerializer.getInstance().fromJson(
                "{\"x\": 3, \"y\": -2, \"direction\": \"S\"}",
                VertexLocation.class
        );
    }

}