package shared.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import shared.definitions.PlayerIndex;
import shared.locations.EdgeLocation;
import shared.locations.VertexLocation;
import shared.models.game.GameMap;
import shared.models.games.GameInfo;

import java.io.Reader;
import java.lang.reflect.Type;

public class ModelSerializer {
    private static ModelSerializer instance;
    private Gson gson;

    private ModelSerializer() {
        GsonBuilder builder = new GsonBuilder();

        // Register custom type adapters
        builder.registerTypeAdapter(PlayerIndex.class, new PlayerIndexTypeAdapter());
        builder.registerTypeAdapter(EdgeLocation.class, new EdgeLocationSerializer());
        builder.registerTypeAdapter(VertexLocation.class, new VertexLocationSerializer());
        builder.registerTypeAdapter(GameMap.class, new GameMapSerializer());
        builder.registerTypeAdapter(GameInfo.class, new GameInfoSerializer());

        gson = builder.create();
    }

    public static ModelSerializer getInstance() {
        if (instance == null) {
            instance = new ModelSerializer();
        }
        return instance;
    }

    public String toJson(Object src, Type typeOfSrc) {
        return gson.toJson(src, typeOfSrc);
    }

    public JsonElement toJsonTree(Object src, Type typeOfSrc) {
        return gson.toJsonTree(src, typeOfSrc);
    }


    public <T> T fromJson(Reader r, Class<T> classOfT) {
        return gson.fromJson(r, classOfT);
    }

    public <T> T fromJson(String s, Class<T> classOfT) {
        return gson.fromJson(s, classOfT);
    }

    public <T> T fromJson(JsonElement e, Class<T> classOfT) {
        return gson.fromJson(e, classOfT);
    }
}
