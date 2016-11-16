package server.client;

import com.google.gson.JsonParseException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import server.games.IServerManager;
import server.models.UserSession;
import shared.IServer;
import shared.annotations.ServerEndpoint;
import shared.serialization.ModelSerializer;
import shared.utils.CookieUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpCookie;
import java.net.InetSocketAddress;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

// TODO: Add logging

/**
 * The server to the client communicator.
 * Listens to communications and calls the IServer accordingly.
 */
public class
ServerCommunicator implements HttpHandler, IServerCommunicator {
    private static final Logger LOGGER = Logger.getLogger("ServerCommunicator");
    private Map<String, EndpointHandler> contexts;
    private HttpServer http;
    private IServerManager serverManager;

    public ServerCommunicator(IServerManager serverManager) throws IOException {
        initCommands();
        http = HttpServer.create();
        http.createContext("/", this);
        setServerManager(serverManager);
    }

    /**
     * Send back a response through an HttpExchange.
     *
     * @param exchange     the HttpExchange to send through
     * @param responseCode the response code, 200 for OK
     * @param message      the message to send, or null if none
     * @pre the exchange has not sent any data back
     * @post the exchange will have sent back the requested data and is now closed
     */
    private static void sendResponse(@NotNull HttpExchange exchange, int responseCode, @Nullable String message) throws IOException {
        if (message == null) {
            exchange.sendResponseHeaders(responseCode, 0);
        } else {
            byte[] messageData = message.getBytes("UTF-8");
            exchange.sendResponseHeaders(responseCode, messageData.length);
            exchange.getResponseBody().write(messageData);
        }
        exchange.close();
    }

    @Override
    public void bind(String hostname, int port) throws IOException {
        LOGGER.info(String.format("Binding server to %s:%d", hostname, port));
        http.bind(new InetSocketAddress(hostname, port), 0);
    }

    @Override
    public void start() {
        LOGGER.info("Starting server...");
        http.start();
    }

    @Override
    public void stop() {
        LOGGER.info("Stopping server...");
        http.stop(0);
    }

    // Only one request will be handled at a time due to the synchronization
    @Override
    public synchronized void handle(HttpExchange exchange) throws IOException {
        try {
            LOGGER.info(String.format("%s %s", exchange.getRequestMethod(), exchange.getRequestURI().getPath()));
            String path = exchange.getRequestURI().getPath();
            if (!contexts.containsKey(path)) {
                sendResponse(exchange, 404, null);
                return;
            }
            contexts.get(path).handle(exchange);
        } catch (Exception e) {
            LOGGER.severe(String.format("Error handling %s %s: %s",
                    exchange.getRequestMethod(),
                    exchange.getRequestURI().getPath(),
                    e.getMessage()));
        }
    }

    private void initCommands() {
        contexts = new HashMap<>();
        for (Method method : IServer.class.getMethods()) {
            ServerEndpoint endpoint = method.getAnnotation(ServerEndpoint.class);
            if (endpoint == null) {
                continue;
            }
            EndpointHandler handler = contexts.computeIfAbsent(endpoint.value(), k -> new EndpointHandler());
            EndpointMethod em = new EndpointMethod(endpoint, method);
            if (endpoint.isPost()) {
                handler.setPostMethod(em);
            } else {
                handler.setGetMethod(em);
            }
        }
    }

    @Override
    public void setServerManager(IServerManager serverManager) {
        this.serverManager = serverManager;
    }

    private class EndpointMethod implements HttpHandler {
        private Method method;
        private String destCookie;
        private Class<?> returnType, paramType;
        private boolean needLogin, needGame;

        EndpointMethod(ServerEndpoint endpoint, Method method) {
            returnType = method.getReturnType().equals(Void.TYPE) ? null : method.getReturnType();
            paramType = method.getParameterCount() >= 1 ? method.getParameterTypes()[0] : null;
            destCookie = endpoint.returnsCookie();
            needLogin = endpoint.requiresAuth();
            needGame = endpoint.gameSpecific();
            this.method = method;
        }

        private Map<String, String> getCookies(HttpExchange exchange) {
            return CookieUtils.getCookieMap(exchange.getRequestHeaders()
                    .getOrDefault("Cookie", new ArrayList<>()).stream()
                    .flatMap(header -> HttpCookie.parse(header).stream()));
        }

        private void setCookie(HttpExchange exchange, String name, String value) throws UnsupportedEncodingException {
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
            }
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

            try {
                if (paramType == null) {
                    result = method.invoke(serverManager.getGameServer(gameId));
                } else {
                    Object arg = ModelSerializer.getInstance().fromJson(
                            new InputStreamReader(exchange.getRequestBody()),
                            paramType
                    );
                    result = method.invoke(serverManager.getGameServer(gameId), arg);
                }
            } catch (InvocationTargetException e) {
                LOGGER.warning(e.getTargetException().getMessage());
                sendResponse(exchange, 400, e.getTargetException().getMessage());
                return;
            } catch (Exception e) {
                LOGGER.warning(e.getMessage());
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
    }

    private class EndpointHandler implements HttpHandler {
        private EndpointMethod getMethod, postMethod;

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            switch (exchange.getRequestMethod().toUpperCase()) {
                case "GET":
                    if (getMethod != null) {
                        getMethod.handle(exchange);
                        return;
                    }
                    break;
                case "POST":
                    if (postMethod != null) {
                        postMethod.handle(exchange);
                        return;
                    }
                    break;
            }
            // Method not allowed
            sendResponse(exchange, 405, null);
        }

        void setGetMethod(EndpointMethod getMethod) {
            this.getMethod = getMethod;
        }

        void setPostMethod(EndpointMethod postMethod) {
            this.postMethod = postMethod;
        }
    }


}
