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
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Client handler
     */
    private void handleClient(Socket clientSocket)
    {
        System.out.println("Client connected");
        try(PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true)) {
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            boolean gameRunning = true;

            while (gameRunning) {
                String clientMessage = input.readLine();
                System.out.println("Client message: " + clientMessage);
                // instead of if else we can use a factory method to read if message is a move, info, endgame or error
                if(clientMessage.contains("endgame")) {
                    gameRunning = false;
                    output.println("endgame");
                }
                else if(clientMessage.contains("info")) {
                    output.println("Info: " + clientMessage);
                }
                else if(clientMessage.contains("move")) {
                    output.println("Move: " + clientMessage);
                }
                else if(clientMessage.contains("error")) {
                    output.println("Error: " + clientMessage);
                }
                else {
                    output.println("Message: " + clientMessage);
                }
            }

        } catch (IOException e) {
            System.out.println("Error handling client: " + e.getMessage());
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