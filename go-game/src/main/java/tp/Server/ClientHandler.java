package tp.Server;

import tp.Message.Message;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import tp.Connection.ClientConnection;
import tp.Message.MessageHandler;


public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private boolean gameRunning;
    private ClientConnection clientConnection;


    private MessageHandler messageHandler;

    public ClientHandler(Socket clientSocket, List<Session> sessions) throws IOException {
        this.clientSocket = clientSocket;
        this.clientConnection = new ClientConnection(clientSocket);
        this.messageHandler = new MessageHandler(sessions, this);
    }

    @Override
    public void run() {
        System.out.println("Client connected");
        try{

            gameRunning = true;

            while (gameRunning) {
                Message message = clientConnection.getResponse();

                messageHandler.handleMessage(message);

                if(message.getMessage().equals("Launch;Disconnect")) {
                    stopGame();
                    break;
                }
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
