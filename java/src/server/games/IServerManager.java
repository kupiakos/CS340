package server.games;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import server.models.ServerModel;
import shared.IServer;

import java.io.IOException;

public interface IServerManager {
    @Nullable
    IServer getGameServer(int gameId);

    @NotNull
    ServerModel getServerModel();

    void startServer(@NotNull String hostname, int port) throws IOException;

    void stopServer();
}
