package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import shared.IServer;
import shared.annotations.ServerEndpoint;
import shared.serialization.ModelSerializer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * The server to the client communicator.
 * Listens to communications and calls the IServer accordingly.
 */
public class ServerCommunicator implements HttpHandler {
    private Map<String, EndpointHandler> contexts;
    private HttpServer http;

    public ServerCommunicator(String hostname, int port) throws IOException {
        http = HttpServer.create();
        http.createContext("/", this);
    }

    public void bind(String hostname, int port) throws IOException {
        http.bind(new InetSocketAddress(hostname, port), 0);
    }

    public void start() {
        http.start();
    }

    public void stop() {
        http.stop(0);
    }

    // Only one request will be handled at a time due to the synchronization
    @Override
    public synchronized void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getHttpContext().getPath();
        if (!contexts.containsKey(path)) {
            exchange.sendResponseHeaders(404, -1);
            exchange.close();
            return;
        }
        contexts.get(path).handle(exchange);
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

    private class EndpointMethod implements HttpHandler {
        private Method method;
        private String destCookie;
        private Class<?> returnType, paramType;

        public EndpointMethod(ServerEndpoint endpoint, Method method) {
            returnType = method.getReturnType().equals(Void.TYPE) ? null : method.getReturnType();
            paramType = method.getParameterCount() >= 1 ? method.getParameterTypes()[0] : null;
            destCookie = endpoint.returnsCookie();
            this.method = method;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            Object result;

            try {
                if (paramType == null) {
                    // TODO: Correct dispatching
                    result = method.invoke(null);
                } else {
                    Object arg = ModelSerializer.getInstance().fromJson(
                            new InputStreamReader(exchange.getRequestBody()),
                            paramType
                    );
                    result = method.invoke(null, arg);
                }
            } catch (InvocationTargetException | IllegalAccessException e) {
                exchange.sendResponseHeaders(500, -1);
                e.printStackTrace();
                exchange.close();
                return;
            } catch (Exception e) {
                exchange.sendResponseHeaders(400, 0);
                new OutputStreamWriter(exchange.getResponseBody()).write(e.getMessage());
                exchange.close();
                return;
            }

            String responseBody = "";
            if (returnType != null) {
                responseBody = ModelSerializer.getInstance().toJson(result, returnType);
            }
            if (!destCookie.isEmpty()) {
                exchange.getResponseHeaders().add("Set-Cookie", String.format(
                        "%s=%s",
                        destCookie,
                        URLEncoder.encode(responseBody, "UTF-8")));
                responseBody = "";
            }
            exchange.sendResponseHeaders(200, 0);
            new OutputStreamWriter(exchange.getResponseBody()).write(responseBody);
            exchange.close();
        }
    }

    private class EndpointHandler implements HttpHandler {
        private EndpointMethod getMethod, postMethod;

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            switch (exchange.getRequestMethod()) {
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
            exchange.sendResponseHeaders(405, -1);
            exchange.close();
        }

        public void setGetMethod(EndpointMethod getMethod) {
            this.getMethod = getMethod;
        }

        public void setPostMethod(EndpointMethod postMethod) {
            this.postMethod = postMethod;
        }
    }


}
