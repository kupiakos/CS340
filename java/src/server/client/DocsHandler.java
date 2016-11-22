package server.client;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import shared.utils.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Logger;

import static server.client.ServerCommunicator.sendResponse;
import static shared.utils.ClassUtils.getStackTrace;

/**
 * Handles all the html and json requests to run swagger
 * Appends ".json" to the request before getting the proper file from the file system
 */

class DocsHandler implements HttpHandler {
    private static final Logger LOGGER = Logger.getLogger("DocsHandler");

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (!exchange.getRequestMethod().toUpperCase().equals("GET")) {
            sendResponse(exchange, 405, null);
        }

        String uri = exchange.getRequestURI().toString();
        Function<String, String> ending = (p) -> p.contains("data") ? ".json" : "";
        Supplier<String> curDir = () -> System.getProperty("user.dir");
        BiFunction<String, String, String> path = (u, d) -> curDir.get() + d + uri + ending.apply(u);

        // If you are running with ant build vs Intelij run/debug
        String filepath = (new File(path.apply(uri, ""))).exists() ?
                path.apply(uri, "") : path.apply(uri, "/demo");

        if (!new File(filepath).exists()) {
            throw new FileNotFoundException();
        }

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