package shared.utils;

import java.util.HashMap;
import java.util.Map;


public class FileUtils {

    public static final Map<String, String> MIMETYPES = makeMimeTypes();

    private static Map<String, String> makeMimeTypes() {
        final Map<String, String> mimeTypes = new HashMap<String, String>();
        mimeTypes.put(".js", "application/javascript");
        mimeTypes.put(".css", "text/css");
        mimeTypes.put(".html", "text/html");
        mimeTypes.put(".json", "application/json");
        mimeTypes.put(".png", "image/png");
        return mimeTypes;
    }

    public static String getMimeType(final String path) {
        final String ending = path.substring(path.lastIndexOf('.'), path.length());
        if (MIMETYPES.get(ending) != null) {
            return MIMETYPES.get(ending);
        } else {
            return "";
        }
    }
}


