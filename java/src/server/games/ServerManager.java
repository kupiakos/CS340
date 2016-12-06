package server.games;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import server.client.GameServer;
import server.client.IServerCommunicator;
import server.client.ServerCommunicator;
import server.db.IPersistenceProvider;
import server.models.ServerModel;
import server.plugin.IPlugin;
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

public class ServerManager implements IServerManager {
    private Map<Integer, IServer> runningServers = new HashMap<>();
    private IServerCommunicator communicator;
    private ServerModel model;
    private IPluginLoader pluginLoader;
    private List<IPlugin> plugins = new ArrayList<>();
    private IPersistenceProvider persistenceProvider;

    public ServerManager() throws IOException {
        communicator = new ServerCommunicator(this);
        pluginLoader = new PluginLoader();

        //TODO:: fix args for real
        // pluginCmds String must be in the format of 'name:option, name2:option' Example - mongo:100
        // pluginDir is the directory that we want to put plugins in
        String pluginCmds = null;
        File pluginDir = null;

        List<PluginConfig> pc = pluginLoader.parseConfig(pluginCmds);
        List<IPlugin> lc = pluginLoader.loadConfig(pc, pluginDir);
        plugins = pluginLoader.startPlugins(lc);
        persistenceProvider = findPersistenceProvider(plugins);
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
     * @return PersistenceProvider for server
     */
    @Override
    public IPersistenceProvider findPersistenceProvider(List<IPlugin> plugins) {
        return null;
    }

    @Override
    public IPersistenceProvider getPersistenceProvider() {
        return persistenceProvider;
    }
}
