package tp;

import tp.Server.Server;

/**
 * App Server runs the server
 */
public class AppServer {
    public static void main(String[] args) {
        Server server = new Server();
        server.run();

        server.stop();
    }

    public static void terminate() {
        System.exit(0);
    }
}
