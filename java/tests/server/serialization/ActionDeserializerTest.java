package server.serialization;

import org.junit.Test;
import server.models.RegisterAction;
import shared.definitions.PlayerIndex;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.models.ICommandAction;
import shared.models.moves.BuildRoadAction;
import shared.models.user.Credentials;
import shared.serialization.ModelSerializer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ActionDeserializerTest {
    @Test
    public void deserializeGameAction() throws Exception {
        BuildRoadAction roadAction = new BuildRoadAction(
                false,
                new EdgeLocation(new HexLocation(2, 3), EdgeDirection.NorthEast),
                PlayerIndex.THIRD
        );
        String s = ModelSerializer.getInstance().toJson(roadAction, roadAction.getClass());
        // Now it's stored in a database, and then we get it back out
        ICommandAction a = ActionDeserializer.getInstance().deserializeAction(s);
        if (!(a instanceof BuildRoadAction)) {
            fail("Did not deserialize to a BuildRoadAction");
        }
        assertEquals(roadAction, a);
    }

    @Test
    public void deserializeServerAction() throws Exception {
        RegisterAction register = new RegisterAction(new Credentials("foo", "bar"));
        String s = ModelSerializer.getInstance().toJson(register, register.getClass());
        // Now it's stored in a database, and then we get it back out
        ICommandAction a = ActionDeserializer.getInstance().deserializeAction(s);
        if (!(a instanceof RegisterAction)) {
            fail("Did not deserialize to a RegisterAction");
        }
        assertEquals(register, a);
    }
}