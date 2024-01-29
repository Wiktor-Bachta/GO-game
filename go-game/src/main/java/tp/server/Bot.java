package tp.server;

import tp.client.ServerHandler;
import tp.connection.ServerConnection;
import tp.message.BotMessageHandler;
import tp.message.Message;

import java.io.*;

public class Bot {

    private ServerConnection serverConnection;
    private BotMessageHandler botMessageHandler;
    private String currentSessionID;
    private String sessionID;
    private ServerHandler serverHandler;
    private BotIntelligence botIntelligence;

    public Bot(String sessionID, int size) {
        this.sessionID = sessionID;
        botIntelligence = new BotIntelligence(size);
        try {
            this.serverConnection = new ServerConnection("localhost", 8000);
        } catch (IOException e) {
        }
        this.botMessageHandler = new BotMessageHandler(this);
    }

    public void run() {

        try {
            serverHandler = new ServerHandler(serverConnection, botMessageHandler);
            new Thread(serverHandler).start();
            sendMessage("Launch;Join;" + sessionID);
        } catch (IOException e) {
        }
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

    public void stop() {
        serverHandler.stopGame();
    }

    public String getMove() {
        return botIntelligence.getMove();
    }

    public void placeOpponentMove(int x, int y) {
        botIntelligence.placeOpponentMove(x, y);
    }

    public void clearStone(int x, int y) {
        botIntelligence.clearStone(x, y);
    }

    public void placeBotMove(int x, int y) {
        botIntelligence.placeBotMove(x, y);
    }
}