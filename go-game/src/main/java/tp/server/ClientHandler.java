package tp.server;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import tp.connection.ClientConnection;
import tp.message.Message;
import tp.message.ServerMessageHandler;


public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private boolean gameRunning;
    private ClientConnection clientConnection;
    private ServerMessageHandler messageHandler;

    public ClientHandler(Socket clientSocket, List<Session> sessions) throws IOException {
        this.clientSocket = clientSocket;
        this.clientConnection = new ClientConnection(clientSocket);
        this.messageHandler = new ServerMessageHandler(sessions, this);
    }

    @Override
    public void run() {
        System.out.println("Client connected");
        try{

            gameRunning = true;

            while (gameRunning) {
                Message message = clientConnection.getResponse();

                System.out.println("Received message: " + message.getMessage());

                messageHandler.handleMessage(message);

                if(message.getMessage().equals("Launch;Disconnect")) {
                    stopGame();
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("Error handling client: " + e.getMessage());
            e.printStackTrace();

        }
    }

    public void stopGame() {
        System.out.println("Client disconnected");
        gameRunning = false;
    }

    public boolean isGameRunning() {
        return gameRunning;
    }

    public ClientConnection getClientConnection() {
        return clientConnection;
    }

}
