package server.main;


import server.games.ServerManager;

/**
 *
 */
public class CatanServer {

    public CatanServer() {

    }

    public static void main(final String[] args) {
        try {
            ServerManager serverManager = new ServerManager();
            serverManager.startServer("localhost", 8081);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
