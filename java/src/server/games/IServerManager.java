package server.games;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import server.models.ServerModel;
import server.plugin.IPlugin;
import server.plugin.persistence.IPersistenceProvider;
import shared.IServer;

import java.io.IOException;
import java.util.List;

public interface IServerManager {
    @Nullable
    IServer getGameServer(int gameId);

    @NotNull
    ServerModel getServerModel();

    void startServer(@NotNull String hostname, int port) throws IOException;

    void stopServer();

    IPersistenceProvider getPersistenceProvider(List<IPlugin> plugins);
}
