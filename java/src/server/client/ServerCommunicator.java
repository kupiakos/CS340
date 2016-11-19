package server.client;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import server.games.IServerManager;
import shared.IServer;
import shared.annotations.ServerEndpoint;
import shared.utils.FileUtils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static shared.utils.ClassUtils.getStackTrace;

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
    private SwaggerHandler swaggerHandler = new SwaggerHandler();

    public ServerCommunicator(IServerManager serverManager) throws IOException {
        initCommands();
        http = HttpServer.create();
        // Swagger calls are not cool and lambda-ed out
        http.createContext("/docs/api/", new SwaggerHandler());
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
    static void sendResponse(@NotNull HttpExchange exchange, int responseCode, @Nullable String message) throws IOException {
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
                    getStackTrace(e)));
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
            EndpointDispatcher em = new EndpointDispatcher(endpoint, method);
            if (endpoint.isPost()) {
                handler.setPostMethod(em);
            } else {
                handler.setGetMethod(em);
            }
        }
    }

    @Override
    public void setServerManager(IServerManager serverManager) {
        for (EndpointHandler h : contexts.values()) {
            h.setServerManager(serverManager);
        }

        swaggerHandler.setServerManager(serverManager);
    }

    // *************************
    // Handlers below
    // *************************

    private class EndpointHandler implements HttpHandler {
        private EndpointDispatcher getMethod, postMethod;

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

        void setServerManager(IServerManager serverManager) {
            if (getMethod != null) {
                getMethod.setServerManager(serverManager);
            }
            if (postMethod != null) {
                postMethod.setServerManager(serverManager);
            }
        }

        void setGetMethod(EndpointDispatcher getMethod) {
            this.getMethod = getMethod;
        }

        void setPostMethod(EndpointDispatcher postMethod) {
            this.postMethod = postMethod;
        }
    }

    /**
     * Handles all the html and json requests to run swagger
     * Appends ".json" to the request before getting the proper file from the file system
     */
    private class SwaggerHandler extends EndpointHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            if (exchange.getRequestMethod().toUpperCase().equals("GET")) {
                String uri = exchange.getRequestURI().toString();

                String filepath = System.getProperty("user.dir") +
                        exchange.getRequestURI().toString() +
                        ((uri.contains("data")) ? ".json" : "");

                LOGGER.warning("GET Swagger: " + filepath);


                byte[] encoded = Files.readAllBytes(Paths.get(filepath));

                ArrayList<String> mimetypes = new ArrayList<>();
                mimetypes.add(FileUtils.getMimeType(filepath));
                exchange.getResponseHeaders().add("Content-Type", mimetypes.get(0));
                exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Origin, X-Atmosphere-tracking-id, X-Atmosphere-Framework, X-Cache-Date, Content-Type, X-Atmosphere-Transport, *");
                exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, GET, OPTIONS , PUT");
                exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
                exchange.getResponseHeaders().add("Access-Control-Request-Headers", "Origin, X-Atmosphere-tracking-id, X-Atmosphere-Framework, X-Cache-Date, Content-Type, X-Atmosphere-Transport,  *");

                exchange.sendResponseHeaders(200, encoded.length);

                try (BufferedOutputStream out = new BufferedOutputStream(exchange.getResponseBody())) {
                    try (ByteArrayInputStream bis = new ByteArrayInputStream(encoded)) {
                        byte[] buffer = new byte[1024];
                        int count;
                        while ((count = bis.read(buffer)) != -1) {
                            out.write(buffer, 0, count);
                        }
                    }
                } catch (Exception e) {
                    LOGGER.warning("GET Swagger: " + uri);
                    e.printStackTrace();
                }
            } else {
                // Method not allowed
                sendResponse(exchange, 405, null);
            }
        }
    }
}
