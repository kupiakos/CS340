package shared.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.lang.reflect.Type;

public class ModelSerializer {
    private Gson gson;
    private static ModelSerializer instance;

    private ModelSerializer() {
        GsonBuilder builder = new GsonBuilder();
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

    public <T> T fromJson(Reader r, Class<T> classOfT) {
        return gson.fromJson(r, classOfT);
    }
}
