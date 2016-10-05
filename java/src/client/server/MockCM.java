package client.server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import shared.models.game.ClientModel;
import shared.serialization.ModelSerializer;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import shared.models.game.ClientModel;

public class MockCM {
    public static final String fullJson = "{\n" +
            "  \"deck\": {\n" +
            "    \"yearOfPlenty\": 2,\n" +
            "    \"monopoly\": 2,\n" +
            "    \"soldier\": 14,\n" +
            "    \"roadBuilding\": 2,\n" +
            "    \"monument\": 5\n" +
            "  },\n" +
            "  \"map\": {\n" +
            "    \"hexes\": [\n" +
            "      {\n" +
            "        \"resource\": \"wood\",\n" +
            "        \"location\": {\n" +
            "          \"x\": 0,\n" +
            "          \"y\": -2\n" +
            "        },\n" +
            "        \"number\": 6\n" +
            "      },\n" +
            "      {\n" +
            "        \"resource\": \"brick\",\n" +
            "        \"location\": {\n" +
            "          \"x\": 1,\n" +
            "          \"y\": -2\n" +
            "        },\n" +
            "        \"number\": 4\n" +
            "      },\n" +
            "      {\n" +
            "        \"resource\": \"sheep\",\n" +
            "        \"location\": {\n" +
            "          \"x\": 2,\n" +
            "          \"y\": -2\n" +
            "        },\n" +
            "        \"number\": 10\n" +
            "      },\n" +
            "      {\n" +
            "        \"resource\": \"wood\",\n" +
            "        \"location\": {\n" +
            "          \"x\": -1,\n" +
            "          \"y\": -1\n" +
            "        },\n" +
            "        \"number\": 3\n" +
            "      },\n" +
            "      {\n" +
            "        \"resource\": \"ore\",\n" +
            "        \"location\": {\n" +
            "          \"x\": 0,\n" +
            "          \"y\": -1\n" +
            "        },\n" +
            "        \"number\": 5\n" +
            "      },\n" +
            "      {\n" +
            "        \"resource\": \"sheep\",\n" +
            "        \"location\": {\n" +
            "          \"x\": 1,\n" +
            "          \"y\": -1\n" +
            "        },\n" +
            "        \"number\": 10\n" +
            "      },\n" +
            "      {\n" +
            "        \"resource\": \"wood\",\n" +
            "        \"location\": {\n" +
            "          \"x\": 2,\n" +
            "          \"y\": -1\n" +
            "        },\n" +
            "        \"number\": 11\n" +
            "      },\n" +
            "      {\n" +
            "        \"resource\": \"ore\",\n" +
            "        \"location\": {\n" +
            "          \"x\": -2,\n" +
            "          \"y\": 0\n" +
            "        },\n" +
            "        \"number\": 9\n" +
            "      },\n" +
            "      {\n" +
            "        \"resource\": \"brick\",\n" +
            "        \"location\": {\n" +
            "          \"x\": -1,\n" +
            "          \"y\": 0\n" +
            "        },\n" +
            "        \"number\": 8\n" +
            "      },\n" +
            "      {\n" +
            "        \"resource\": \"wheat\",\n" +
            "        \"location\": {\n" +
            "          \"x\": 0,\n" +
            "          \"y\": 0\n" +
            "        },\n" +
            "        \"number\": 6\n" +
            "      },\n" +
            "      {\n" +
            "        \"resource\": \"sheep\",\n" +
            "        \"location\": {\n" +
            "          \"x\": 1,\n" +
            "          \"y\": 0\n" +
            "        },\n" +
            "        \"number\": 9\n" +
            "      },\n" +
            "      {\n" +
            "        \"resource\": \"wheat\",\n" +
            "        \"location\": {\n" +
            "          \"x\": 2,\n" +
            "          \"y\": 0\n" +
            "        },\n" +
            "        \"number\": 11\n" +
            "      },\n" +
            "      {\n" +
            "        \"resource\": \"wood\",\n" +
            "        \"location\": {\n" +
            "          \"x\": -2,\n" +
            "          \"y\": 1\n" +
            "        },\n" +
            "        \"number\": 4\n" +
            "      },\n" +
            "      {\n" +
            "        \"location\": {\n" +
            "          \"x\": -1,\n" +
            "          \"y\": 1\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"resource\": \"sheep\",\n" +
            "        \"location\": {\n" +
            "          \"x\": 0,\n" +
            "          \"y\": 1\n" +
            "        },\n" +
            "        \"number\": 12\n" +
            "      },\n" +
            "      {\n" +
            "        \"resource\": \"wheat\",\n" +
            "        \"location\": {\n" +
            "          \"x\": 1,\n" +
            "          \"y\": 1\n" +
            "        },\n" +
            "        \"number\": 8\n" +
            "      },\n" +
            "      {\n" +
            "        \"resource\": \"brick\",\n" +
            "        \"location\": {\n" +
            "          \"x\": -2,\n" +
            "          \"y\": 2\n" +
            "        },\n" +
            "        \"number\": 5\n" +
            "      },\n" +
            "      {\n" +
            "        \"resource\": \"wheat\",\n" +
            "        \"location\": {\n" +
            "          \"x\": -1,\n" +
            "          \"y\": 2\n" +
            "        },\n" +
            "        \"number\": 2\n" +
            "      },\n" +
            "      {\n" +
            "        \"resource\": \"ore\",\n" +
            "        \"location\": {\n" +
            "          \"x\": 0,\n" +
            "          \"y\": 2\n" +
            "        },\n" +
            "        \"number\": 3\n" +
            "      }\n" +
            "    ],\n" +
            "    \"roads\": [\n" +
            "      {\n" +
            "        \"owner\": 1,\n" +
            "        \"location\": {\n" +
            "          \"direction\": \"SE\",\n" +
            "          \"x\": 0,\n" +
            "          \"y\": -1\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"owner\": 2,\n" +
            "        \"location\": {\n" +
            "          \"direction\": \"SW\",\n" +
            "          \"x\": 2,\n" +
            "          \"y\": -1\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"owner\": 2,\n" +
            "        \"location\": {\n" +
            "          \"direction\": \"SW\",\n" +
            "          \"x\": -1,\n" +
            "          \"y\": 1\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"owner\": 0,\n" +
            "        \"location\": {\n" +
            "          \"direction\": \"S\",\n" +
            "          \"x\": 0,\n" +
            "          \"y\": -2\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"owner\": 1,\n" +
            "        \"location\": {\n" +
            "          \"direction\": \"S\",\n" +
            "          \"x\": 0,\n" +
            "          \"y\": 1\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"owner\": 3,\n" +
            "        \"location\": {\n" +
            "          \"direction\": \"S\",\n" +
            "          \"x\": 0,\n" +
            "          \"y\": -1\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"owner\": 3,\n" +
            "        \"location\": {\n" +
            "          \"direction\": \"SW\",\n" +
            "          \"x\": 0,\n" +
            "          \"y\": 1\n" +
            "        }\n" +
            "      }\n" +
            "    ],\n" +
            "    \"cities\": [],\n" +
            "    \"settlements\": [\n" +
            "      {\n" +
            "        \"owner\": 1,\n" +
            "        \"location\": {\n" +
            "          \"direction\": \"SW\",\n" +
            "          \"x\": 1,\n" +
            "          \"y\": -2\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"owner\": 3,\n" +
            "        \"location\": {\n" +
            "          \"direction\": \"SE\",\n" +
            "          \"x\": -1,\n" +
            "          \"y\": 1\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"owner\": 3,\n" +
            "        \"location\": {\n" +
            "          \"direction\": \"SW\",\n" +
            "          \"x\": 0,\n" +
            "          \"y\": -1\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"owner\": 2,\n" +
            "        \"location\": {\n" +
            "          \"direction\": \"SE\",\n" +
            "          \"x\": -2,\n" +
            "          \"y\": 1\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"owner\": 2,\n" +
            "        \"location\": {\n" +
            "          \"direction\": \"SE\",\n" +
            "          \"x\": 1,\n" +
            "          \"y\": -1\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"owner\": 0,\n" +
            "        \"location\": {\n" +
            "          \"direction\": \"SW\",\n" +
            "          \"x\": 0,\n" +
            "          \"y\": -2\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"owner\": 1,\n" +
            "        \"location\": {\n" +
            "          \"direction\": \"SE\",\n" +
            "          \"x\": 0,\n" +
            "          \"y\": 1\n" +
            "        }\n" +
            "      }\n" +
            "    ],\n" +
            "    \"radius\": 3,\n" +
            "    \"ports\": [\n" +
            "      {\n" +
            "        \"ratio\": 2,\n" +
            "        \"resource\": \"brick\",\n" +
            "        \"direction\": \"NE\",\n" +
            "        \"location\": {\n" +
            "          \"x\": -2,\n" +
            "          \"y\": 3\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"ratio\": 3,\n" +
            "        \"direction\": \"SE\",\n" +
            "        \"location\": {\n" +
            "          \"x\": -3,\n" +
            "          \"y\": 0\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"ratio\": 2,\n" +
            "        \"resource\": \"sheep\",\n" +
            "        \"direction\": \"NW\",\n" +
            "        \"location\": {\n" +
            "          \"x\": 3,\n" +
            "          \"y\": -1\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"ratio\": 3,\n" +
            "        \"direction\": \"NW\",\n" +
            "        \"location\": {\n" +
            "          \"x\": 2,\n" +
            "          \"y\": 1\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"ratio\": 3,\n" +
            "        \"direction\": \"N\",\n" +
            "        \"location\": {\n" +
            "          \"x\": 0,\n" +
            "          \"y\": 3\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"ratio\": 2,\n" +
            "        \"resource\": \"ore\",\n" +
            "        \"direction\": \"S\",\n" +
            "        \"location\": {\n" +
            "          \"x\": 1,\n" +
            "          \"y\": -3\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"ratio\": 3,\n" +
            "        \"direction\": \"SW\",\n" +
            "        \"location\": {\n" +
            "          \"x\": 3,\n" +
            "          \"y\": -3\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"ratio\": 2,\n" +
            "        \"resource\": \"wheat\",\n" +
            "        \"direction\": \"S\",\n" +
            "        \"location\": {\n" +
            "          \"x\": -1,\n" +
            "          \"y\": -2\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"ratio\": 2,\n" +
            "        \"resource\": \"wood\",\n" +
            "        \"direction\": \"NE\",\n" +
            "        \"location\": {\n" +
            "          \"x\": -3,\n" +
            "          \"y\": 2\n" +
            "        }\n" +
            "      }\n" +
            "    ],\n" +
            "    \"robber\": {\n" +
            "      \"x\": -1,\n" +
            "      \"y\": 1\n" +
            "    }\n" +
            "  },\n" +
            "  \"players\": [\n" +
            "    {\n" +
            "      \"resources\": {\n" +
            "        \"brick\": 0,\n" +
            "        \"wood\": 0,\n" +
            "        \"sheep\": 0,\n" +
            "        \"wheat\": 0,\n" +
            "        \"ore\": 0\n" +
            "      },\n" +
            "      \"oldDevCards\": {\n" +
            "        \"yearOfPlenty\": 0,\n" +
            "        \"monopoly\": 0,\n" +
            "        \"soldier\": 0,\n" +
            "        \"roadBuilding\": 0,\n" +
            "        \"monument\": 0\n" +
            "      },\n" +
            "      \"newDevCards\": {\n" +
            "        \"yearOfPlenty\": 0,\n" +
            "        \"monopoly\": 0,\n" +
            "        \"soldier\": 0,\n" +
            "        \"roadBuilding\": 0,\n" +
            "        \"monument\": 0\n" +
            "      },\n" +
            "      \"roads\": 14,\n" +
            "      \"cities\": 4,\n" +
            "      \"settlements\": 4,\n" +
            "      \"soldiers\": 0,\n" +
            "      \"victoryPoints\": 1,\n" +
            "      \"monuments\": 0,\n" +
            "      \"playedDevCard\": false,\n" +
            "      \"discarded\": false,\n" +
            "      \"playerID\": 12,\n" +
            "      \"playerIndex\": 0,\n" +
            "      \"name\": \"alyssa\",\n" +
            "      \"color\": \"purple\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"resources\": {\n" +
            "        \"brick\": 0,\n" +
            "        \"wood\": 0,\n" +
            "        \"sheep\": 1,\n" +
            "        \"wheat\": 1,\n" +
            "        \"ore\": 1\n" +
            "      },\n" +
            "      \"oldDevCards\": {\n" +
            "        \"yearOfPlenty\": 0,\n" +
            "        \"monopoly\": 0,\n" +
            "        \"soldier\": 0,\n" +
            "        \"roadBuilding\": 0,\n" +
            "        \"monument\": 0\n" +
            "      },\n" +
            "      \"newDevCards\": {\n" +
            "        \"yearOfPlenty\": 0,\n" +
            "        \"monopoly\": 0,\n" +
            "        \"soldier\": 0,\n" +
            "        \"roadBuilding\": 0,\n" +
            "        \"monument\": 0\n" +
            "      },\n" +
            "      \"roads\": 13,\n" +
            "      \"cities\": 4,\n" +
            "      \"settlements\": 3,\n" +
            "      \"soldiers\": 0,\n" +
            "      \"victoryPoints\": 2,\n" +
            "      \"monuments\": 0,\n" +
            "      \"playedDevCard\": false,\n" +
            "      \"discarded\": false,\n" +
            "      \"playerID\": -2,\n" +
            "      \"playerIndex\": 1,\n" +
            "      \"name\": \"Quinn\",\n" +
            "      \"color\": \"green\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"resources\": {\n" +
            "        \"brick\": 1,\n" +
            "        \"wood\": 1,\n" +
            "        \"sheep\": 0,\n" +
            "        \"wheat\": 0,\n" +
            "        \"ore\": 0\n" +
            "      },\n" +
            "      \"oldDevCards\": {\n" +
            "        \"yearOfPlenty\": 0,\n" +
            "        \"monopoly\": 0,\n" +
            "        \"soldier\": 0,\n" +
            "        \"roadBuilding\": 0,\n" +
            "        \"monument\": 0\n" +
            "      },\n" +
            "      \"newDevCards\": {\n" +
            "        \"yearOfPlenty\": 0,\n" +
            "        \"monopoly\": 0,\n" +
            "        \"soldier\": 0,\n" +
            "        \"roadBuilding\": 0,\n" +
            "        \"monument\": 0\n" +
            "      },\n" +
            "      \"roads\": 13,\n" +
            "      \"cities\": 4,\n" +
            "      \"settlements\": 3,\n" +
            "      \"soldiers\": 0,\n" +
            "      \"victoryPoints\": 2,\n" +
            "      \"monuments\": 0,\n" +
            "      \"playedDevCard\": false,\n" +
            "      \"discarded\": false,\n" +
            "      \"playerID\": -3,\n" +
            "      \"playerIndex\": 2,\n" +
            "      \"name\": \"Scott\",\n" +
            "      \"color\": \"orange\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"resources\": {\n" +
            "        \"brick\": 1,\n" +
            "        \"wood\": 0,\n" +
            "        \"sheep\": 0,\n" +
            "        \"wheat\": 1,\n" +
            "        \"ore\": 1\n" +
            "      },\n" +
            "      \"oldDevCards\": {\n" +
            "        \"yearOfPlenty\": 0,\n" +
            "        \"monopoly\": 0,\n" +
            "        \"soldier\": 0,\n" +
            "        \"roadBuilding\": 0,\n" +
            "        \"monument\": 0\n" +
            "      },\n" +
            "      \"newDevCards\": {\n" +
            "        \"yearOfPlenty\": 0,\n" +
            "        \"monopoly\": 0,\n" +
            "        \"soldier\": 0,\n" +
            "        \"roadBuilding\": 0,\n" +
            "        \"monument\": 0\n" +
            "      },\n" +
            "      \"roads\": 13,\n" +
            "      \"cities\": 4,\n" +
            "      \"settlements\": 3,\n" +
            "      \"soldiers\": 0,\n" +
            "      \"victoryPoints\": 2,\n" +
            "      \"monuments\": 0,\n" +
            "      \"playedDevCard\": false,\n" +
            "      \"discarded\": false,\n" +
            "      \"playerID\": -4,\n" +
            "      \"playerIndex\": 3,\n" +
            "      \"name\": \"Steve\",\n" +
            "      \"color\": \"red\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"log\": {\n" +
            "    \"lines\": [\n" +
            "      {\n" +
            "        \"source\": \"alyssa\",\n" +
            "        \"message\": \"alyssa built a road\"\n" +
            "      }\n" +
            "    ]\n" +
            "  },\n" +
            "  \"chat\": {\n" +
            "    \"lines\": []\n" +
            "  },\n" +
            "  \"bank\": {\n" +
            "    \"brick\": 22,\n" +
            "    \"wood\": 23,\n" +
            "    \"sheep\": 23,\n" +
            "    \"wheat\": 22,\n" +
            "    \"ore\": 22\n" +
            "  },\n" +
            "  \"turnTracker\": {\n" +
            "    \"status\": \"SecondRound\",\n" +
            "    \"currentTurn\": 0,\n" +
            "    \"longestRoad\": -1,\n" +
            "    \"largestArmy\": -1\n" +
            "  },\n" +
            "  \"winner\": -1,\n" +
            "  \"version\": 21\n" +
            "}";

    public static JsonObject fullJsonObject() {
        return new JsonParser().parse(fullJson).getAsJsonObject();
    }

    public static ClientModel fullJsonModel() {
        return ModelSerializer.getInstance().fromJson(fullJson, ClientModel.class);
    }
}

