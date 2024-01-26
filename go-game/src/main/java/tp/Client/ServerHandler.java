package tp.Client;

import tp.Message.Message;
import tp.Message.MessageHandler;

import java.io.IOException;
import java.net.Socket;

import tp.Connection.ServerConnection;


public class ServerHandler implements Runnable {
    private Socket serverSocket;
    private boolean gameRunning;
    private ServerConnection serverConnection;
    private MessageHandler messageHandler;

    public ServerHandler(ServerConnection serverConnection, MessageHandler messageHandler) throws IOException {
        this.serverConnection = serverConnection;
        this.messageHandler = messageHandler;
    }

    @Override
    public void run() {
        System.out.println("Client connected");
        try{

            gameRunning = true;

            while (gameRunning) {
                Message message = serverConnection.getResponse();

                System.out.println("Received message: " + message.getMessage());

                messageHandler.handleMessage(message);

            }

        } catch (IOException e) {
            System.out.println("Error handling client: " + e.getMessage());
            e.printStackTrace();

        }
    }

    public void stopGame() {
        gameRunning = false;
    }

    public ServerConnection getServerConnection() {
        return serverConnection;
    }
}
