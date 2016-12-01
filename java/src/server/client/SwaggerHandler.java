package server.client;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
<<<<<<< HEAD
import server.games.IServerManager;
import shared.utils.FileUtils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
=======
import shared.utils.FileUtils;

>>>>>>> 6787c1787953a4bebea561e274086f1c3dfea985
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Logger;

import static server.client.ServerCommunicator.sendResponse;
<<<<<<< HEAD
=======
import static shared.utils.ClassUtils.getStackTrace;
>>>>>>> 6787c1787953a4bebea561e274086f1c3dfea985

/**
 * Handles all the html and json requests to run swagger
 * Appends ".json" to the request before getting the proper file from the file system
 */
<<<<<<< HEAD
public class SwaggerHandler implements HttpHandler {
    private EndpointDispatcher getMethod, postMethod;
=======
class SwaggerHandler implements HttpHandler {
>>>>>>> 6787c1787953a4bebea561e274086f1c3dfea985
    private static final Logger LOGGER = Logger.getLogger("SwaggerHandler");

    @Override
    public void handle(HttpExchange exchange) throws IOException {

<<<<<<< HEAD
        if (exchange.getRequestMethod().toUpperCase().equals("GET")) {
            String uri = exchange.getRequestURI().toString();

            String filepath = System.getProperty("user.dir") +
                    exchange.getRequestURI().toString() +
                    ((uri.contains("data")) ? ".json" : "");

            LOGGER.warning("GET Swagger: " + filepath);


            byte[] encoded = Files.readAllBytes(Paths.get(filepath));

=======
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
>>>>>>> 6787c1787953a4bebea561e274086f1c3dfea985
            ArrayList<String> mimetypes = new ArrayList<>();
            mimetypes.add(FileUtils.getMimeType(filepath));
            exchange.getResponseHeaders().add("Content-Type", mimetypes.get(0));
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Origin, X-Atmosphere-tracking-id, X-Atmosphere-Framework, X-Cache-Date, Content-Type, X-Atmosphere-Transport, *");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, GET, OPTIONS , PUT");
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Request-Headers", "Origin, X-Atmosphere-tracking-id, X-Atmosphere-Framework, X-Cache-Date, Content-Type, X-Atmosphere-Transport,  *");

<<<<<<< HEAD
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
    void setServerManager(IServerManager serverManager) {
        if (getMethod != null) {
            getMethod.setServerManager(serverManager);
        }
        if (postMethod != null) {
            postMethod.setServerManager(serverManager);
=======
            exchange.sendResponseHeaders(200, Files.size(Paths.get(filepath)));
            Files.copy(Paths.get(filepath), exchange.getResponseBody());
            exchange.close();
        } catch (Exception e) {
            LOGGER.warning("GET Swagger: " + uri);
            LOGGER.warning(getStackTrace(e));
            sendResponse(exchange, 400, e.getMessage());
>>>>>>> 6787c1787953a4bebea561e274086f1c3dfea985
        }
    }
}