package server.games;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import server.client.GameServer;
import server.client.IServerCommunicator;
import server.client.ServerCommunicator;
import server.models.ServerModel;
import shared.IServer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ServerManager implements IServerManager {
    private Map<Integer, IServer> runningServers = new HashMap<>();
    private IServerCommunicator communicator;

    public ServerManager() throws IOException {
        communicator = new ServerCommunicator(this);
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
        return null;
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
}
