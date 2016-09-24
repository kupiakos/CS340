package shared.serialization;


import com.google.gson.*;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

import java.lang.reflect.Type;

public class VertexLocationSerializer implements JsonSerializer<VertexLocation>, JsonDeserializer<VertexLocation> {
    @Override
    public VertexLocation deserialize(
            JsonElement jsonElement,
            Type type,
            JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = jsonElement.getAsJsonObject();
        VertexDirection dir = context.deserialize(obj.get("direction"), VertexDirection.class);
        if (dir == null) {
            throw new JsonParseException("Invalid VertexDirection");
        }
        return new VertexLocation(
                new HexLocation(
                        obj.get("x").getAsInt(),
                        obj.get("y").getAsInt()
                ),
                dir
        );
    }

    @Override
    public JsonElement serialize(
            VertexLocation loc,
            Type type,
            JsonSerializationContext context) {
        JsonElement e = context.serialize(loc.getHexLoc(), HexLocation.class);
        JsonObject obj = e.getAsJsonObject();
        obj.add("direction", context.serialize(loc.getDir(), VertexDirection.class));
        return obj;
    }
}
