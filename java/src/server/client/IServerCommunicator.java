package server.client;

import server.games.IServerManager;

import java.io.IOException;

public interface IServerCommunicator {
    void bind(String hostname, int port) throws IOException;

    void start();

    void stop();

    void setServerManager(IServerManager serverManager);
}
