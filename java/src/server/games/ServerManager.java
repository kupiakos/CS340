package server.games;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import server.client.GameServer;
import server.client.IServerCommunicator;
import server.client.ServerCommunicator;
import server.db.IPersistenceProvider;
import server.models.ServerModel;
import server.plugin.AbstractPlugin;
import server.plugin.IPluginLoader;
import server.plugin.PluginConfig;
import server.plugin.PluginLoader;
import shared.IServer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static server.plugin.PluginConfig.PluginType.PERSISTENCE;

public class ServerManager implements IServerManager {
    private Map<Integer, IServer> runningServers = new HashMap<>();
    private IServerCommunicator communicator;
    private ServerModel model;
    private IPluginLoader pluginLoader;
    private List<AbstractPlugin> plugins = new ArrayList<>();
    private IPersistenceProvider persistenceProvider;

    public ServerManager() throws IOException {
        communicator = new ServerCommunicator(this);
        pluginLoader = new PluginLoader();

        //TODO:: fix args for real
        String pluginConfigFile = "java/" + "plugins/config.yaml";
        String persitence = "mongo";
        File pluginDir = new File("java/plugins");

        List<PluginConfig> pc = pluginLoader.parseConfig(pluginConfigFile);
        List<AbstractPlugin> lc = pluginLoader.loadConfig(pc, pluginDir);
        plugins = pluginLoader.startPlugins(lc);
        persistenceProvider = getPersistenceProvider(plugins, persitence);
    }

    @Nullable
    @Override
    public IServer getGameServer(int gameId) {
        // TODO: Check if game exists in server model
        return runningServers.computeIfAbsent(gameId, id -> new GameServer(this, id));
    }

    public void setGameServer(int gameId, IServer server) {
        runningServers.put(gameId, server);
    }

    @NotNull
    @Override
    public ServerModel getServerModel() {
        if (model == null) {
            model = new ServerModel();
        }
        return model;
    }

    @Override
    public void startServer(@NotNull String hostname, int port) throws IOException {
        communicator.bind(hostname, port);
        communicator.start();
    }

    @Override
    public void stopServer() {
        communicator.stop();
    }

    /**
     * Takes a list of available running plugins and checks if there is one of type PERSISTENCE and
     * returns that to be the servers current database
     *
     * @param plugins
     * @param name
     * @return PersistenceProvider for server
     */
    @Override
    public IPersistenceProvider getPersistenceProvider(List<AbstractPlugin> plugins, String name) {
        return (IPersistenceProvider) plugins.stream()
                .filter(x -> x.getType() == PERSISTENCE && name.equals(x.getName()))
                .collect(Collectors.toList()).get(0);
    }
}
