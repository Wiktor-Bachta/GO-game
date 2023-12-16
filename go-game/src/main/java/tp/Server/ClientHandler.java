package tp.Server;

import tp.Message.Message;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private boolean gameRunning;
    private tp.Server.ClientConnection clientConnection;

    public ClientHandler(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.clientConnection = new tp.Server.ClientConnection(clientSocket);
    }

    @Override
    public void run() {
        System.out.println("Client connected");
        try{

            gameRunning = true;

            while (gameRunning) {
                Message message = clientConnection.getResponse();
                System.out.println("Received message: " + message.getMessage());

                clientConnection.sendMessage(message);
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
