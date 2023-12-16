package tp.Server;

import java.io.*;
import java.net.*;

/**
 * Class Server is a simple server that can accept a single client connection
 */
public class Server {

    /**
     * Start the server
     */
    private boolean running = true;

    /**
     * Start the server at port 8000
     */
    public void run() {
        System.out.println("Server started");
        try (ServerSocket serverSocket = new ServerSocket(8000)) {
            while (running) {
                Socket clientSocket = serverSocket.accept();

                new Thread(new ClientHandler(clientSocket)).start();
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