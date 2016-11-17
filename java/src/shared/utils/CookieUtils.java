package shared.utils;

import java.io.UnsupportedEncodingException;
import java.net.HttpCookie;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CookieUtils {
    public static String decodeCookie(HttpCookie c) {
        try {
            return URLDecoder.decode(c.getValue(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static Map<String, String> getCookieMap(Stream<HttpCookie> cookies) {
        return cookies.collect(Collectors.toMap(HttpCookie::getName, CookieUtils::decodeCookie));
    }

    public static Map<String, String> getCookieMap(Collection<HttpCookie> cookies) {
        return getCookieMap(cookies.stream());
    }

    public static Stream<HttpCookie> parseCookieHeader(String header) {
        return Arrays.stream(header.split(";")).map(h -> HttpCookie.parse(h).get(0));
    }
}
