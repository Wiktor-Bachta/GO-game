package tp.Server;

import tp.Client.Client;
import tp.Client.ServerHandler;
import tp.Client.GUI.ClientGUI;
import tp.Connection.ServerConnection;
import tp.Message.BotMessageHandler;
import tp.Message.ClientMessageHandler;
import tp.Message.Message;

import java.io.*;

public class Bot {

    private ServerConnection serverConnection;
    private BotMessageHandler botMessageHandler;
    private String currentSessionID;
    private String sessionID;

    public Bot(String sessionID) {
        this.sessionID = sessionID;
        try {
            this.serverConnection = new ServerConnection("localhost", 8000);
        } catch (IOException e) {
        }
        this.botMessageHandler = new BotMessageHandler(this);
    }

    public void run() {

        try {
            new Thread(new ServerHandler(serverConnection, botMessageHandler)).start();
            sendMessage("Launch;Join;" + sessionID);
        } catch (IOException e) {
        }
    }

    public void stop() {
        System.out.println("Client stop");
    }

    public ServerConnection getServerConnection() {
        return serverConnection;
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
}