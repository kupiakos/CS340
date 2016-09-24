package shared.serialization;


import com.google.gson.*;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import shared.definitions.PlayerIndex;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.models.game.GameMap;
import shared.models.game.Hex;
import shared.models.game.Port;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GameMapSerializer implements JsonSerializer<GameMap>, JsonDeserializer<GameMap> {
    @Override
    public GameMap deserialize(
            JsonElement jsonElement,
            Type type,
            JsonDeserializationContext context) throws JsonParseException {
        GameMap result = new GameMap();
        JsonObject obj = jsonElement.getAsJsonObject();
        result.setRadius(context.deserialize(obj.get("radius"), Integer.class));
        result.setRobber(context.deserialize(obj.get("robber"), HexLocation.class));

        // Even if Java 8 is nice, this'd be 10 times easier in Python or Scala.
        // Cities/Settlements
        List<Map<VertexLocation, PlayerIndex>> vertexProps = new ArrayList<>();
        for (String name : new String[]{"cities", "settlements"}) {
            VertexObject[] objects =
                    context.deserialize(obj.get(name), VertexObject[].class);
            vertexProps.add(Arrays.stream(objects)
                    .collect(Collectors.toMap(v -> v.location, v -> v.owner)));
        }
        result.setCities(vertexProps.get(0));
        result.setSettlements(vertexProps.get(1));

        // Roads
        Road[] roads = context.deserialize(obj.get("roads"), Road[].class);
        result.setRoads(Arrays.stream(roads)
                .collect(Collectors.toMap(v -> v.location, v -> v.owner)));

        // Hexes
        Hex[] hexes = context.deserialize(obj.get("hexes"), Hex[].class);
        result.setHexes(Arrays.stream(hexes)
                .collect(Collectors.toMap(Hex::getLocation, Function.identity())));

        // Ports
        Port[] ports = context.deserialize(obj.get("ports"), Port[].class);
        result.setPorts(Arrays.stream(ports)
                .collect(Collectors.toMap(Port::getLocation, Function.identity())));

        return result;
    }

    @Override
    public JsonElement serialize(
            GameMap map,
            Type type,
            JsonSerializationContext context) {
        JsonObject result = new JsonObject();

        result.add("radius", context.serialize(map.getRadius()));
        result.add("robber", context.serialize(map.getRobber()));

        result.add("cities", context.serialize(map.getCities().entrySet()
                .stream()
                .map(e -> new VertexObject(e.getKey(), e.getValue()))
                .collect(Collectors.toList())
        ));

        result.add("settlements", context.serialize(map.getSettlements().entrySet()
                .stream()
                .map(e -> new VertexObject(e.getKey(), e.getValue()))
                .collect(Collectors.toList())
        ));

        result.add("roads", context.serialize(map.getRoads().entrySet()
                .stream()
                .map(e -> new Road(e.getKey(), e.getValue()))
                .collect(Collectors.toList())
        ));

        result.add("hexes", context.serialize(map.getCities().values()));
        result.add("ports", context.serialize(map.getPorts().values()));

        return result;
    }

    private class VertexObject {
        @Expose
        @SerializedName("location")
        public VertexLocation location;
        @Expose
        @SerializedName("owner")
        PlayerIndex owner;

        VertexObject(VertexLocation location, PlayerIndex owner) {
            this.owner = owner;
            this.location = location;
        }
    }

    private class Road {
        @Expose
        @SerializedName("location")
        public EdgeLocation location;
        @Expose
        @SerializedName("owner")
        PlayerIndex owner;

        Road(EdgeLocation location, PlayerIndex owner) {
            this.owner = owner;
            this.location = location;
        }
    }
}
