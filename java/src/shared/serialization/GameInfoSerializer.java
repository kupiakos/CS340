package shared.serialization;


import com.google.gson.*;
import shared.definitions.PlayerIndex;
import shared.models.games.GameInfo;
import shared.models.games.PlayerInfo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GameInfoSerializer implements JsonSerializer<GameInfo>, JsonDeserializer<GameInfo> {
    @Override
    public GameInfo deserialize(
            JsonElement jsonElement,
            Type type,
            JsonDeserializationContext context) throws JsonParseException {
        if (jsonElement == null) {
            return null;
        }
        JsonObject obj = jsonElement.getAsJsonObject();
        // Handles nulls a bit better than obj.get("title").getAsString()
        String title = context.deserialize(obj.get("title"), String.class);
        if (title == null) {
            title = "";
        }
        int id = context.deserialize(obj.get("id"), Integer.class);
        JsonArray playersJson = obj.getAsJsonArray("players");
        if (playersJson.size() > PlayerIndex.MAX_PLAYERS) {
            throw new IllegalArgumentException("Too many players defined in a GameInfo");
        }

        List<PlayerInfo> players = new ArrayList<>();
        PlayerIndex index = PlayerIndex.FIRST;
        for (JsonElement playerElement : playersJson) {
            if (!playerElement.isJsonObject() ||
                    !playerElement.getAsJsonObject().has("name")) {
                break;
            }
            PlayerInfo player = context.deserialize(playerElement, PlayerInfo.class);
            if (player == null) {
                throw new IllegalArgumentException("Could not deserialize PlayerInfo!");
            }
            player.setPlayerIndex(index);
            players.add(player);
            assert index != null;
            index = index.nextPlayer();
        }

        return new GameInfo(players, title, id);
    }

    @Override
    public JsonElement serialize(
            GameInfo gameInfo,
            Type type,
            JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.addProperty("id", gameInfo.getId());
        obj.addProperty("title", gameInfo.getTitle());
        JsonArray playersJson = new JsonArray();
        for (PlayerInfo player : gameInfo.getPlayers()) {
            playersJson.add(context.serialize(player, PlayerInfo.class));
        }
        if (playersJson.size() > PlayerIndex.MAX_PLAYERS) {
            throw new IllegalArgumentException("Too many players defined in a GameInfo");
        }
        while (playersJson.size() < PlayerIndex.MAX_PLAYERS) {
            playersJson.add(new JsonObject());
        }
        obj.add("players", playersJson);
        return obj;
    }
}
