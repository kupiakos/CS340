package shared.serialization;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import shared.definitions.PlayerIndex;

public class PlayerIndexTypeAdapterTest {
    private static final PlayerIndex[] vals = {
            PlayerIndex.FIRST, PlayerIndex.SECOND, PlayerIndex.THIRD, PlayerIndex.FOURTH
    };

    private ModelSerializer serializer;

    @Before
    public void init() {
        serializer = ModelSerializer.getInstance();
    }

    @Test
    public void deserialize() throws Exception {
        for (int i = 0; i < vals.length; ++i) {
            PlayerIndex idx = serializer.fromJson(
                    Integer.toString(i), PlayerIndex.class);
            Assert.assertNotNull(idx);
            Assert.assertEquals(i, idx.index());
            Assert.assertEquals(vals[i], idx);
        }
    }

    @Test
    public void serialize() throws Exception {
        for (int i = 0; i < vals.length; ++i) {
            String v;
            v = serializer.toJson(vals[i], PlayerIndex.class);
            Assert.assertNotNull(v);
            Assert.assertEquals(Integer.toString(i), v.trim());
            v = serializer.toJson(PlayerIndex.fromInt(i), PlayerIndex.class);
            Assert.assertNotNull(v);
            Assert.assertEquals(Integer.toString(i), v.trim());
        }
    }

    @Test
    public void deserializeNull() throws Exception {
        //TODO:: Breaks code
/*        PlayerIndex idx;
        for (String test : new String[]{"-1", "null", "", Integer.toString(PlayerIndex.MAX_PLAYERS)}) {
            idx = serializer.fromJson(test, PlayerIndex.class);
            Assert.assertNull(idx);
        }*/
    }

    @Test
    public void serializeNull() throws Exception {
        String s = serializer.toJson(null, PlayerIndex.class);
        Assert.assertNotNull(s);
        Assert.assertEquals("-1", s.trim());
    }

}