package shared.serialization;


import com.google.gson.*;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;

import java.lang.reflect.Type;

public class EdgeLocationSerializer implements JsonSerializer<EdgeLocation>, JsonDeserializer<EdgeLocation> {
    @Override
    public EdgeLocation deserialize(
            JsonElement jsonElement,
            Type type,
            JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = jsonElement.getAsJsonObject();
        EdgeDirection dir = context.deserialize(obj.get("direction"), EdgeDirection.class);
        if (dir == null) {
            throw new JsonParseException("Invalid EdgeDirection");
        }
        return new EdgeLocation(
                new HexLocation(
                        obj.get("x").getAsInt(),
                        obj.get("y").getAsInt()
                ),
                dir
        );
    }

    @Override
    public JsonElement serialize(
            EdgeLocation loc,
            Type type,
            JsonSerializationContext context) {
        if (loc == null) {
            return null;
        }
        JsonElement e = context.serialize(loc.getHexLoc(), HexLocation.class);
        JsonObject obj = e.getAsJsonObject();
        obj.add("direction", context.serialize(loc.getDir(), EdgeDirection.class));
        return obj;
    }
}
