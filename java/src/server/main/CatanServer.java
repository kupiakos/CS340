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
            String hostname = "localhost";
            int port = 8081;
            if (args.length == 2) {
                hostname = args[0];
                port = Integer.parseInt(args[1]);
            }
            serverManager.startServer(hostname, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
