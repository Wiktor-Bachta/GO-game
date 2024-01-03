package tp.Server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Server is a simple server that can accept a single client connection
 */
public class Server {

    /**
     * Start the server
     */
    private boolean running = true;

    /**
     * Session game list
     */
    private List<Session> sessions;
    /**
     * Start the server at port 8000
     */
    public Server() {
        this.sessions = new ArrayList<Session>();
    }

    public void run() {
        System.out.println("Server started");
        try (ServerSocket serverSocket = new ServerSocket(8000)) {
            while (running) {
                Socket clientSocket = serverSocket.accept();

                new Thread(new ClientHandler(clientSocket, sessions)).start();
            }
        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Stop the server
     */
    public void stop() {
        running = false;
        System.out.println("Server stop");
    }


}