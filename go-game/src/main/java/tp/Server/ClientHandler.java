package tp.Server;

import tp.Game.Game;
import tp.Message.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket clientSocket;
    private boolean gameRunning;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
    @Override
    public void run() {
        System.out.println("Client connected");
        try (PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true)) {
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            gameRunning = true;

            while (gameRunning) {
                Message clientMessage = new Message(input.readLine());

                if(clientMessage.getMessage() == null) {
                    stopGame();
                    continue;
                }

                System.out.println("Client message: " + clientMessage);
                // instead of if else we can use a factory method to read if message is a move, info, endgame or error
                if (clientMessage.getMessage().contains("endgame")) {
                    output.println("endgame");
                    stopGame();
                } else if (clientMessage.getMessage().contains("info")) {
                    output.println("Info: " + clientMessage);
                } else if (clientMessage.getMessage().contains("move")) {
                    output.println("Move: " + clientMessage);
                } else if (clientMessage.getMessage().contains("error")) {
                    output.println("Error: " + clientMessage);
                } else {
                    output.println("Message: " + clientMessage);
                }
            }

        } catch (IOException e) {
            System.out.println("Error handling client: " + e.getMessage());
            e.printStackTrace();

        }
    }

    private void stopGame() {
        System.out.println("Client disconnected");
        gameRunning = false;
    }
}
