package server.client;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.games.IServerManager;

import java.io.IOException;
import java.util.logging.Logger;

import static server.client.ServerCommunicator.sendResponse;

public class EndpointHandler implements HttpHandler {
    private EndpointDispatcher getMethod, postMethod;
    private static final Logger LOGGER = Logger.getLogger("EndpointHandler");


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
