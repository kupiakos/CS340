package shared.serialization;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.junit.Test;
import shared.definitions.CatanColor;
import shared.definitions.PlayerIndex;
import shared.models.games.GameInfo;
import shared.models.games.PlayerInfo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class GameInfoSerializerTest {
    @Test
    public void deserialize() throws Exception {
        GameInfo info = ModelSerializer.getInstance().fromJson(
                "{\"id\": 2, \"title\": \"Empty Game\", \"players\": [" +
                        "{\"id\": 0, \"color\": \"orange\", \"name\": \"Sam\"}, " +
                        "{\"id\": 1, \"color\": \"blue\", \"name\": \"Brooke\"}, " +
                        "{\"id\": 10, \"color\": \"red\", \"name\": \"Pete\"}, " +
                        "{\"id\": 11, \"color\": \"green\", \"name\": \"Mark\"}" +
                        "]}",
                GameInfo.class
        );
        assertNotNull(info);
        List<PlayerInfo> players = info.getPlayers();
        assertNotNull(players);
        assertEquals(4, players.size());
        for (int i = 0; i < players.size(); ++i) {
            assertEquals(PlayerIndex.fromInt(i), players.get(i).getPlayerIndex());
        }
    }

    @Test
    public void deserializeWithEmptyPlayers() throws Exception {
        GameInfo info = ModelSerializer.getInstance().fromJson(
                "{\"id\": 3, \"title\": \"blah\", \"players\": " +
                        "[{\"id\": 12, \"color\": \"puce\", \"name\": \"alyssa\"}, " +
                        "{}, {}, {}]}",
                GameInfo.class
        );
        assertNotNull(info);
        assertNotNull(info.getPlayers());
        assertEquals(1, info.getPlayers().size());
        assertEquals(PlayerIndex.FIRST, info.getPlayers().get(0).getPlayerIndex());
    }

    @Test
    public void deserializeNull() throws Exception {
        for (String j : new String[]{"", "null"}) {
            assertNull(ModelSerializer.getInstance().fromJson(j, GameInfo.class));
        }
    }

    @Test
    public void serialize() throws Exception {
        JsonElement e = ModelSerializer.getInstance().toJsonTree(
                new GameInfo(
                        Arrays.asList(
                                new PlayerInfo(CatanColor.ORANGE, "Sam", 0),
                                new PlayerInfo(CatanColor.BLUE, "Brooke", 1),
                                new PlayerInfo(CatanColor.RED, "Pete", 10),
                                new PlayerInfo(CatanColor.GREEN, "Mark", 11)
                        ),
                        "Empty Game",
                        2
                ),
                GameInfo.class
        );
        assertEquals(new JsonParser().parse(
                "{\"id\": 2, \"title\": \"Empty Game\", \"players\": [" +
                        "{\"id\": 0, \"color\": \"orange\", \"name\": \"Sam\"}, " +
                        "{\"id\": 1, \"color\": \"blue\", \"name\": \"Brooke\"}, " +
                        "{\"id\": 10, \"color\": \"red\", \"name\": \"Pete\"}, " +
                        "{\"id\": 11, \"color\": \"green\", \"name\": \"Mark\"}" +
                        "]}"),
                e
        );
    }

    @Test
    public void serializeWithEmptyPlayers() throws Exception {
        JsonElement e = ModelSerializer.getInstance().toJsonTree(
                new GameInfo(
                        Collections.singletonList(
                                new PlayerInfo(CatanColor.PUCE, "alyssa", 12)
                        ),
                        "blah",
                        3
                ),
                GameInfo.class
        );
        assertEquals(new JsonParser().parse(
                "{\"id\": 3, \"title\": \"blah\", \"players\": " +
                        "[{\"id\": 12, \"color\": \"puce\", \"name\": \"alyssa\"}, " +
                        "{}, {}, {}]}"),
                e
        );
    }

    // TODO: Add more failure tests
}