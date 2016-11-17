package server.client;

import com.google.gson.JsonParseException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jetbrains.annotations.NotNull;
import server.games.IServerManager;
import server.models.UserSession;
import shared.IServer;
import shared.annotations.ServerEndpoint;
import shared.serialization.ModelSerializer;
import shared.utils.CookieUtils;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static server.client.ServerCommunicator.sendResponse;
import static shared.utils.ClassUtils.getStackTrace;

class EndpointDispatcher implements HttpHandler {
    private static final Logger LOGGER = Logger.getLogger("EndpointDispatcher");
    private Method method;
    private String paramName;
    private String destCookie;
    private Class<?> returnType, paramType;
    private boolean needLogin, needGame;
    private IServerManager serverManager;

    EndpointDispatcher(ServerEndpoint endpoint, Method method) {
        returnType = method.getReturnType().equals(Void.TYPE) ? null : method.getReturnType();
        paramName = method.getParameterCount() >= 1 ? method.getParameters()[0].getName() : null;
        paramType = method.getParameterCount() >= 1 ? method.getParameterTypes()[0] : null;
        destCookie = endpoint.returnsCookie();
        needLogin = endpoint.requiresAuth();
        needGame = endpoint.gameSpecific();
        this.method = method;
    }

    // See http://stackoverflow.com/a/13592567/1530134
    private static Map<String, List<String>> splitQuery(@NotNull URI uri) {
        if (uri.getQuery() == null || uri.getQuery().isEmpty()) {
            return Collections.emptyMap();
        }
        return Arrays.stream(uri.getQuery().split("&"))
                .map(EndpointDispatcher::splitQueryParameter)
                .collect(Collectors.groupingBy(AbstractMap.SimpleImmutableEntry::getKey, LinkedHashMap::new, mapping(Map.Entry::getValue, toList())));
    }

    private static AbstractMap.SimpleImmutableEntry<String, String> splitQueryParameter(String it) {
        final int idx = it.indexOf("=");
        final String key = idx > 0 ? it.substring(0, idx) : it;
        final String value = idx > 0 && it.length() > idx + 1 ? it.substring(idx + 1) : null;
        return new AbstractMap.SimpleImmutableEntry<>(key, value);
    }

    private static Map<String, String> getCookies(HttpExchange exchange) {
        return CookieUtils.getCookieMap(exchange.getRequestHeaders()
                .getOrDefault("Cookie", new ArrayList<>()).stream()
                .flatMap(CookieUtils::parseCookieHeader));
    }

    private static void setCookie(HttpExchange exchange, String name, String value) throws UnsupportedEncodingException {
        // HttpCookie follows the *client-to-server* specification of RFC 2965.
        // The key difference this makes is that the path is set with '; $Path="/"' client-to-server,
        // but is set with '; Path="/"' when using Set-Cookie server-to-client.
        // That's why HttpCookie is not being used here.
        exchange.getResponseHeaders().add("Set-Cookie", String.format(
                "%s=%s; Path=\"/\"",
                name,
                URLEncoder.encode(value, "UTF-8")));
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Object result;

        int gameId = -1;
        Map<String, String> cookies = getCookies(exchange);
        if (needGame) {
            try {
                gameId = Integer.parseInt(cookies.get("catan.game"));
            } catch (NumberFormatException n) {
                LOGGER.warning(String.format("%s %s: %s",
                        exchange.getRequestMethod(),
                        exchange.getRequestURI().getPath(),
                        "Client did not have a valid game set"));
                sendResponse(exchange, 400,
                        "The catan.game HTTP cookie is missing.  " +
                                "You must join a game before calling this method."
                );
                return;
            }
        }

        IServer server = serverManager.getGameServer(gameId);
        if (server == null) {
            sendResponse(exchange, 400, "No game with ID " + gameId + " exists");
            return;
        }

        if (needLogin) {
            UserSession session;
            try {
                session = ModelSerializer.getInstance().fromJson(
                        cookies.get("catan.user"), UserSession.class);
            } catch (JsonParseException e) {
                session = null;
            }
            if (session == null || !serverManager.getServerModel().validateSession(session)) {
                LOGGER.warning(String.format("%s %s: %s",
                        exchange.getRequestMethod(),
                        exchange.getRequestURI().getPath(),
                        "Client did not have a valid session"));
                sendResponse(exchange, 400, "You must be logged in to perform this action");
                return;
            }
            server.setUserId(session.getUserId());
        }

        try {
            if (paramType == null) {
                result = method.invoke(server);
            } else {
                InputStream dataStream;
                if (exchange.getRequestMethod().equalsIgnoreCase("GET")) {
                    // Split parameter string instead
                    Map<String, List<String>> params = splitQuery(exchange.getRequestURI());

                    if (!params.containsKey(paramName) || params.get(paramName).size() != 1) {
                        sendResponse(exchange, 400, "Query string does not contain required value " + paramName);
                        return;
                    }
                    String value = params.get(paramName).get(0);
                    dataStream = new ByteArrayInputStream(value.getBytes(StandardCharsets.UTF_8));
                } else {
                    dataStream = exchange.getRequestBody();
                }
                Object arg = ModelSerializer.getInstance().fromJson(
                        new InputStreamReader(dataStream),
                        paramType
                );
                result = method.invoke(server, arg);
            }
        } catch (InvocationTargetException e) {
            LOGGER.warning(getStackTrace(e.getTargetException()));
            sendResponse(exchange, 400, e.getTargetException().getMessage());
            return;
        } catch (Exception e) {
            LOGGER.warning(getStackTrace(e));
            sendResponse(exchange, 500, e.getMessage());
            return;
        }

        String responseBody = "";
        if (returnType != null) {
            responseBody = ModelSerializer.getInstance().toJson(result, returnType);
        }
        if (!destCookie.isEmpty()) {
            setCookie(exchange, destCookie, responseBody);
            responseBody = "";
        }
        sendResponse(exchange, 200, responseBody);
    }

    void setServerManager(IServerManager serverManager) {
        this.serverManager = serverManager;
    }
}
