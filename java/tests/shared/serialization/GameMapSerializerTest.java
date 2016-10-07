package shared.serialization;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import shared.models.game.GameMap;

public class GameMapSerializerTest {

    private static final String fullJson = "{\n" +
            "    \"cities\": [],\n" +
            "    \"hexes\": [\n" +
            "        {\n" +
            "            \"location\": {\n" +
            "                \"x\": 0,\n" +
            "                \"y\": -2\n" +
            "            },\n" +
            "            \"number\": 6,\n" +
            "            \"resource\": \"wood\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"location\": {\n" +
            "                \"x\": 1,\n" +
            "                \"y\": -2\n" +
            "            },\n" +
            "            \"number\": 4,\n" +
            "            \"resource\": \"brick\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"location\": {\n" +
            "                \"x\": 2,\n" +
            "                \"y\": -2\n" +
            "            },\n" +
            "            \"number\": 10,\n" +
            "            \"resource\": \"sheep\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"location\": {\n" +
            "                \"x\": -1,\n" +
            "                \"y\": -1\n" +
            "            },\n" +
            "            \"number\": 3,\n" +
            "            \"resource\": \"wood\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"location\": {\n" +
            "                \"x\": 0,\n" +
            "                \"y\": -1\n" +
            "            },\n" +
            "            \"number\": 5,\n" +
            "            \"resource\": \"ore\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"location\": {\n" +
            "                \"x\": 1,\n" +
            "                \"y\": -1\n" +
            "            },\n" +
            "            \"number\": 10,\n" +
            "            \"resource\": \"sheep\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"location\": {\n" +
            "                \"x\": 2,\n" +
            "                \"y\": -1\n" +
            "            },\n" +
            "            \"number\": 11,\n" +
            "            \"resource\": \"wood\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"location\": {\n" +
            "                \"x\": -2,\n" +
            "                \"y\": 0\n" +
            "            },\n" +
            "            \"number\": 9,\n" +
            "            \"resource\": \"ore\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"location\": {\n" +
            "                \"x\": -1,\n" +
            "                \"y\": 0\n" +
            "            },\n" +
            "            \"number\": 8,\n" +
            "            \"resource\": \"brick\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"location\": {\n" +
            "                \"x\": 0,\n" +
            "                \"y\": 0\n" +
            "            },\n" +
            "            \"number\": 6,\n" +
            "            \"resource\": \"wheat\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"location\": {\n" +
            "                \"x\": 1,\n" +
            "                \"y\": 0\n" +
            "            },\n" +
            "            \"number\": 9,\n" +
            "            \"resource\": \"sheep\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"location\": {\n" +
            "                \"x\": 2,\n" +
            "                \"y\": 0\n" +
            "            },\n" +
            "            \"number\": 11,\n" +
            "            \"resource\": \"wheat\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"location\": {\n" +
            "                \"x\": -2,\n" +
            "                \"y\": 1\n" +
            "            },\n" +
            "            \"number\": 4,\n" +
            "            \"resource\": \"wood\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"location\": {\n" +
            "                \"x\": -1,\n" +
            "                \"y\": 1\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"location\": {\n" +
            "                \"x\": 0,\n" +
            "                \"y\": 1\n" +
            "            },\n" +
            "            \"number\": 12,\n" +
            "            \"resource\": \"sheep\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"location\": {\n" +
            "                \"x\": 1,\n" +
            "                \"y\": 1\n" +
            "            },\n" +
            "            \"number\": 8,\n" +
            "            \"resource\": \"wheat\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"location\": {\n" +
            "                \"x\": -2,\n" +
            "                \"y\": 2\n" +
            "            },\n" +
            "            \"number\": 5,\n" +
            "            \"resource\": \"brick\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"location\": {\n" +
            "                \"x\": -1,\n" +
            "                \"y\": 2\n" +
            "            },\n" +
            "            \"number\": 2,\n" +
            "            \"resource\": \"wheat\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"location\": {\n" +
            "                \"x\": 0,\n" +
            "                \"y\": 2\n" +
            "            },\n" +
            "            \"number\": 3,\n" +
            "            \"resource\": \"ore\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"ports\": [\n" +
            "        {\n" +
            "            \"direction\": \"NE\",\n" +
            "            \"location\": {\n" +
            "                \"x\": -2,\n" +
            "                \"y\": 3\n" +
            "            },\n" +
            "            \"ratio\": 2,\n" +
            "            \"resource\": \"brick\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"direction\": \"SE\",\n" +
            "            \"location\": {\n" +
            "                \"x\": -3,\n" +
            "                \"y\": 0\n" +
            "            },\n" +
            "            \"ratio\": 3\n" +
            "        },\n" +
            "        {\n" +
            "            \"direction\": \"NW\",\n" +
            "            \"location\": {\n" +
            "                \"x\": 3,\n" +
            "                \"y\": -1\n" +
            "            },\n" +
            "            \"ratio\": 2,\n" +
            "            \"resource\": \"sheep\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"direction\": \"NW\",\n" +
            "            \"location\": {\n" +
            "                \"x\": 2,\n" +
            "                \"y\": 1\n" +
            "            },\n" +
            "            \"ratio\": 3\n" +
            "        },\n" +
            "        {\n" +
            "            \"direction\": \"N\",\n" +
            "            \"location\": {\n" +
            "                \"x\": 0,\n" +
            "                \"y\": 3\n" +
            "            },\n" +
            "            \"ratio\": 3\n" +
            "        },\n" +
            "        {\n" +
            "            \"direction\": \"S\",\n" +
            "            \"location\": {\n" +
            "                \"x\": 1,\n" +
            "                \"y\": -3\n" +
            "            },\n" +
            "            \"ratio\": 2,\n" +
            "            \"resource\": \"ore\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"direction\": \"SW\",\n" +
            "            \"location\": {\n" +
            "                \"x\": 3,\n" +
            "                \"y\": -3\n" +
            "            },\n" +
            "            \"ratio\": 3\n" +
            "        },\n" +
            "        {\n" +
            "            \"direction\": \"S\",\n" +
            "            \"location\": {\n" +
            "                \"x\": -1,\n" +
            "                \"y\": -2\n" +
            "            },\n" +
            "            \"ratio\": 2,\n" +
            "            \"resource\": \"wheat\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"direction\": \"NE\",\n" +
            "            \"location\": {\n" +
            "                \"x\": -3,\n" +
            "                \"y\": 2\n" +
            "            },\n" +
            "            \"ratio\": 2,\n" +
            "            \"resource\": \"wood\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"radius\": 3,\n" +
            "    \"roads\": [\n" +
            "        {\n" +
            "            \"location\": {\n" +
            "                \"direction\": \"SE\",\n" +
            "                \"x\": 0,\n" +
            "                \"y\": -1\n" +
            "            },\n" +
            "            \"owner\": 1\n" +
            "        },\n" +
            "        {\n" +
            "            \"location\": {\n" +
            "                \"direction\": \"SW\",\n" +
            "                \"x\": 2,\n" +
            "                \"y\": -1\n" +
            "            },\n" +
            "            \"owner\": 2\n" +
            "        },\n" +
            "        {\n" +
            "            \"location\": {\n" +
            "                \"direction\": \"SW\",\n" +
            "                \"x\": -1,\n" +
            "                \"y\": 1\n" +
            "            },\n" +
            "            \"owner\": 2\n" +
            "        },\n" +
            "        {\n" +
            "            \"location\": {\n" +
            "                \"direction\": \"S\",\n" +
            "                \"x\": 0,\n" +
            "                \"y\": -2\n" +
            "            },\n" +
            "            \"owner\": 0\n" +
            "        },\n" +
            "        {\n" +
            "            \"location\": {\n" +
            "                \"direction\": \"S\",\n" +
            "                \"x\": 0,\n" +
            "                \"y\": 1\n" +
            "            },\n" +
            "            \"owner\": 1\n" +
            "        },\n" +
            "        {\n" +
            "            \"location\": {\n" +
            "                \"direction\": \"S\",\n" +
            "                \"x\": 0,\n" +
            "                \"y\": -1\n" +
            "            },\n" +
            "            \"owner\": 3\n" +
            "        },\n" +
            "        {\n" +
            "            \"location\": {\n" +
            "                \"direction\": \"SW\",\n" +
            "                \"x\": 0,\n" +
            "                \"y\": 1\n" +
            "            },\n" +
            "            \"owner\": 3\n" +
            "        }\n" +
            "    ],\n" +
            "    \"robber\": {\n" +
            "        \"x\": -1,\n" +
            "        \"y\": 1\n" +
            "    },\n" +
            "    \"settlements\": [\n" +
            "        {\n" +
            "            \"location\": {\n" +
            "                \"direction\": \"SW\",\n" +
            "                \"x\": 1,\n" +
            "                \"y\": -2\n" +
            "            },\n" +
            "            \"owner\": 1\n" +
            "        },\n" +
            "        {\n" +
            "            \"location\": {\n" +
            "                \"direction\": \"SE\",\n" +
            "                \"x\": -1,\n" +
            "                \"y\": 1\n" +
            "            },\n" +
            "            \"owner\": 3\n" +
            "        },\n" +
            "        {\n" +
            "            \"location\": {\n" +
            "                \"direction\": \"SW\",\n" +
            "                \"x\": 0,\n" +
            "                \"y\": -1\n" +
            "            },\n" +
            "            \"owner\": 3\n" +
            "        },\n" +
            "        {\n" +
            "            \"location\": {\n" +
            "                \"direction\": \"SE\",\n" +
            "                \"x\": -2,\n" +
            "                \"y\": 1\n" +
            "            },\n" +
            "            \"owner\": 2\n" +
            "        },\n" +
            "        {\n" +
            "            \"location\": {\n" +
            "                \"direction\": \"SE\",\n" +
            "                \"x\": 1,\n" +
            "                \"y\": -1\n" +
            "            },\n" +
            "            \"owner\": 2\n" +
            "        },\n" +
            "        {\n" +
            "            \"location\": {\n" +
            "                \"direction\": \"SW\",\n" +
            "                \"x\": 0,\n" +
            "                \"y\": -2\n" +
            "            },\n" +
            "            \"owner\": 0\n" +
            "        },\n" +
            "        {\n" +
            "            \"location\": {\n" +
            "                \"direction\": \"SE\",\n" +
            "                \"x\": 0,\n" +
            "                \"y\": 1\n" +
            "            },\n" +
            "            \"owner\": 1\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    private static ModelSerializer serializer;

    @BeforeClass
    public static void setUp() {
        serializer = ModelSerializer.getInstance();
    }

    @Test
    public void deserializeFull() throws Exception {
        GameMap map = serializer.fromJson(
                fullJson, GameMap.class);
        Assert.assertNotNull(map);
        // If anyone has a better solution to this, let me know
        Assert.assertFalse(map.toString().contains("null"));
    }

    @Test
    public void serializeDefault() throws Exception {
        GameMap map = new GameMap();

        JsonElement e = serializer.toJsonTree(
                map, GameMap.class
        );

        Assert.assertEquals(
                new JsonParser().parse("{\n" +
                        "    \"cities\": [],\n" +
                        "    \"hexes\": [],\n" +
                        "    \"ports\": [],\n" +
                        "    \"radius\": 0,\n" +
                        "    \"roads\": [],\n" +
                        "    \"settlements\": []\n" +
                        "}"),
                e
        );
    }

    @Test
    public void serializeDeserialize() throws Exception {
        GameMap map = serializer.fromJson(fullJson, GameMap.class);
        String mapSerialized = serializer.toJson(map, GameMap.class);
        GameMap fromSerialized = serializer.fromJson(mapSerialized, GameMap.class);
        String mapSerialized2 = serializer.toJson(fromSerialized, GameMap.class);

        Assert.assertEquals(map, fromSerialized);
    }

    // TODO: Add extra de/serialization tests
}