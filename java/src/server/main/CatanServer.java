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
            String hostname = "localhost";
            int port = 8081;
            String persistence = "postgres";
            int N = 20;

            if (args.length == 2) {
                hostname = args[0];
                port = Integer.parseInt(args[1]);
            }

            if (args.length == 4) {
                persistence = args[2];
                N = Integer.parseInt(args[3]);
            }

            ServerManager serverManager = new ServerManager(persistence, N);
            serverManager.startServer(hostname, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
