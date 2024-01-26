package tp.Client;

import tp.Message.ClientMessageHandler;
import tp.Message.Message;
import tp.Message.MessageHandler;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import tp.Connection.ClientConnection;
import tp.Connection.ServerConnection;
import tp.Message.ServerMessageHandler;


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

    private void stopGame() {
        System.out.println("Client disconnected");
        gameRunning = false;
    }

    public ServerConnection getServerConnection() {
        return serverConnection;
    }
}
