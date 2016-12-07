package server.games;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import server.db.IPersistenceProvider;
import server.models.ServerModel;
import server.plugin.IPlugin;
import shared.IServer;
import shared.models.ICommandAction;

import java.io.IOException;
import java.util.List;

public interface IServerManager {
    @Nullable
    IServer getGameServer(int gameId);

    @NotNull
    ServerModel getServerModel();

    void startServer(@NotNull String hostname, int port) throws IOException;

    void stopServer();

    IPersistenceProvider getPersistenceProvider(List<IPlugin> plugins, String persistence);

    IPersistenceProvider getPersistenceProvider();

    void storeCommand(ICommandAction command, int GameId);
}
