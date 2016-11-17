package shared.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class ClassUtils {
    private static final Map<Class, Object> PRIMITIVE_TYPES = new HashMap<Class, Object>() {{
        put(Boolean.class, false);
        put(Boolean.TYPE, false);
        put(Character.class, '\0');
        put(Character.TYPE, '\0');
        put(Byte.class, (byte) 0);
        put(Byte.TYPE, (byte) 0);
        put(Short.class, (short) 0);
        put(Short.TYPE, (short) 0);
        put(Integer.class, 0);
        put(Integer.TYPE, 0);
        put(Long.class, 0L);
        put(Long.TYPE, 0L);
        put(Float.class, 0f);
        put(Float.TYPE, 0f);
        put(Double.class, 0.);
        put(Double.TYPE, 0.);
    }};

    public static boolean isPrimitiveType(Class clazz) {
        return PRIMITIVE_TYPES.containsKey(clazz);
    }

    public static Object defaultWrapper(Class clazz) {
        return PRIMITIVE_TYPES.getOrDefault(clazz, null);
    }

    public static String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
