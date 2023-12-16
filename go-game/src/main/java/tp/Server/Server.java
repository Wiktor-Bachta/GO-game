package tp.Server;

import java.io.*;
import java.net.*;

/**
 * Class Server is a simple server that can accept a single client connection
 *
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
        try (ServerSocket serverSocket = new ServerSocket(8000)) {
            while (running) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

                    String clientInput = input.readLine();
                    System.out.println("Received from client: " + clientInput);

                    String serverResponse = "Message received";
                    output.println(serverResponse);
                }
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
    }
}