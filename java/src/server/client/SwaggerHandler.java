package server.client;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import shared.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Logger;

import static server.client.ServerCommunicator.sendResponse;
import static shared.utils.ClassUtils.getStackTrace;

/**
 * Handles all the html and json requests to run swagger
 * Appends ".json" to the request before getting the proper file from the file system
 */
class SwaggerHandler implements HttpHandler {
    private static final Logger LOGGER = Logger.getLogger("SwaggerHandler");

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {
            // Method not allowed
            sendResponse(exchange, 405, null);
            return;
        }
        String uri = exchange.getRequestURI().toString();

        String filepath = System.getProperty("user.dir") +
                exchange.getRequestURI().toString() +
                ((uri.contains("data")) ? ".json" : "");

        LOGGER.warning("GET Swagger: " + filepath);

        try {
            ArrayList<String> mimetypes = new ArrayList<>();
            mimetypes.add(FileUtils.getMimeType(filepath));
            exchange.getResponseHeaders().add("Content-Type", mimetypes.get(0));
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Origin, X-Atmosphere-tracking-id, X-Atmosphere-Framework, X-Cache-Date, Content-Type, X-Atmosphere-Transport, *");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, GET, OPTIONS , PUT");
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Request-Headers", "Origin, X-Atmosphere-tracking-id, X-Atmosphere-Framework, X-Cache-Date, Content-Type, X-Atmosphere-Transport,  *");

            exchange.sendResponseHeaders(200, Files.size(Paths.get(filepath)));
            Files.copy(Paths.get(filepath), exchange.getResponseBody());
            exchange.close();
        } catch (Exception e) {
            LOGGER.warning("GET Swagger: " + uri);
            LOGGER.warning(getStackTrace(e));
            sendResponse(exchange, 400, e.getMessage());
        }
    }
}