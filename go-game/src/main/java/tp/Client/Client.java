package tp.Client;

import tp.Client.GUI.ClientGUI;
import tp.Connection.ServerConnection;
import tp.Message.ClientMessageHandler;
import tp.Message.Message;

import java.io.*;

public class Client {

    private ClientState state;
    private ServerConnection serverConnection;
    private ClientMessageHandler clientMessageHandler;
    private ClientGUI clientGUI;
    private String currentSessionID;
    private ServerHandler serverHandler;

    public Client(ClientGUI clientGUI) {
        this.clientGUI = clientGUI;
        try {
            this.serverConnection = new ServerConnection("localhost", 8000);
        } catch (IOException e) {
        }
        this.clientMessageHandler = new ClientMessageHandler(this);
    }

    public void run() {

        try {
            serverHandler = new ServerHandler(serverConnection, clientMessageHandler);
            new Thread(serverHandler).start();
        } catch (IOException e) {
        }
    }

    public void stop() {
        serverHandler.stopGame();
    }

    public void setState(ClientState state) {
        this.state = state;
    }

    public ClientState getState() {
        return state;
    }

    public void nextState() {
        if (state == ClientState.WAITING_FOR_MOVE) {
            setMove();
        } else {
            setWait();
        }
    }

    public void setWait() {
        state = ClientState.WAITING_FOR_MOVE;
        getClientGUI().getSidePanelGUI().labelUpdateWait();
    }

    public void setMove() {
        state = ClientState.DOING_MOVE;
        getClientGUI().getSidePanelGUI().labelUpdateMove();
    }

    public ServerConnection getServerConnection() {
        return serverConnection;
    }

    public ClientGUI getClientGUI() {
        return clientGUI;
    }

    public String getCurrentessionID() {
        return currentSessionID;
    }

    public void setCurrentSessionID(String sessionID) {
        this.currentSessionID = sessionID;
    }

    public void sendMessage(String message) {
        serverConnection.sendMessage(new Message(message));
    }

    public void displayMessage(String s) {
    }
}