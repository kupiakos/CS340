package client.server;

import org.jetbrains.annotations.NotNull;
import shared.IServer;
import shared.annotations.ServerEndpoint;
import shared.definitions.functions.ThrowingFunction;
import shared.models.game.ClientModel;
import shared.serialization.ModelSerializer;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static shared.utils.ClassUtils.defaultWrapper;

/**
 * Created by elijahgk on 9/12/2016.
 * Implements the IServer Interface.
 * Receives calls from client controllers.  Calls methods in clientCommunicator to communicate with actual server.
 */
public interface ServerProxy extends IServer {
    @NotNull
    static ServerProxy getInstance(String host, String port) {
        return ServerProxyState.getProxyInstance(host, port);
    }

    void setMockCC(ClientModel cm);

    class ServerProxyState {
        private static IClientCommunicator comm;
        private static ServerProxy proxy;
        private static Map<String, ThrowingFunction<Object, Object>> commandMap = new HashMap<>();

        private ServerProxyState() {
        }

        private static ServerProxy getProxyInstance(String host, String port) {
            if (comm == null) {
                comm = ClientCommunicator.getSingleton(host, port);
            }
            if (proxy != null) {
                return proxy;
            }

            // This is also known as "magic"
            for (Method method : ServerProxy.class.getMethods()) {
                ServerEndpoint endpoint = method.getAnnotation(ServerEndpoint.class);
                if (endpoint == null) {
                    continue;
                }
                Class<?> deserializeType =
                        (!method.getReturnType().equals(Void.TYPE) && endpoint.returnsCookie().isEmpty()) ?
                                method.getReturnType() :
                                null;
                Class resultType = method.getReturnType();
                String paramName = method.getParameterCount() >= 1 ? method.getParameters()[0].getName() : null;
                Class<?> paramType = method.getParameterCount() >= 1 ? method.getParameterTypes()[0] : null;
                String URLSuffix = endpoint.value();
                String verb = endpoint.isPost() ? "POST" : "GET";
                commandMap.put(method.getName(), arg -> {
                    Object result = null;
                    String requestBody = "";
                    Map<String, String> parameters = null;
                    if (paramType != null) {
                        requestBody = ModelSerializer.getInstance().toJson(arg, paramType);
                        if (verb.equalsIgnoreCase("GET")) {
                            parameters = new HashMap<>();
                            parameters.put(paramName, URLEncoder.encode(requestBody, "UTF-8"));
                            requestBody = "";
                        }
                    }
                    String resultJson = comm.sendHTTPRequest(URLSuffix, requestBody, verb, parameters);
                    if (deserializeType != null) {
                        result = ModelSerializer.getInstance().fromJson(resultJson, deserializeType);
                    } else {
                        result = defaultWrapper(resultType);
                    }
                    return result;
                });
            }
            commandMap.put("setMockCC", cm -> ServerProxyState.comm = MockCC.initialize((ClientModel) cm));

            proxy = (ServerProxy) Proxy.newProxyInstance(
                    ServerProxy.class.getClassLoader(),
                    new Class<?>[]{ServerProxy.class},
                    (proxy, method, args) -> {
                        if (!commandMap.containsKey(method.getName())) {
                            return null;
                        }
                        return commandMap.get(method.getName()).apply(args == null ? null : args[0]);
                    });
            return proxy;
        }
    }
}
