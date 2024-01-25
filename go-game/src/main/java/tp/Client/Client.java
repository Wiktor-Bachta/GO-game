package tp.Client;

import tp.Client.GUI.ClientGUI;
import tp.Connection.ServerConnection;
import tp.Game.Game;
import tp.Game.GUI.ChoiceGUI;
import tp.Message.ClientMessageHandler;
import tp.Message.Message;
import tp.Server.ClientHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.application.Platform;

public class Client {

    private Game game;
    private ClientState state;
    private ServerConnection serverConnection;
    private ClientMessageHandler clientMessageHandler;
    private ClientGUI clientGUI;

    public Client(ClientGUI clientGUI) {
        this.game = new Game(this);
        this.clientGUI = clientGUI;
        try {
            this.serverConnection = new ServerConnection("localhost", 8000);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.clientMessageHandler = new ClientMessageHandler(this);
        this.state = ClientState.SETTING_UP;
    }

    public void run() {

        try {
            new Thread(new ServerHandler(serverConnection, clientMessageHandler)).start();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        /*
         * try {
         * 
         * 
         * while (state == ClientState.SETTING_UP) {
         * Message serverMessage = serverConnection.getResponse();
         * clientMessageHandler.handleMessage(serverMessage);
         * }
         * 
         * while (game.isRunning()) {
         * 
         * switch (state) {
         * case DOING_MOVE:
         * System.out.println("DOING MOVE");
         * Message clientMessage = game.doMove();
         * serverConnection.sendMessage(clientMessage);
         * break;
         * case WAITING_FOR_MOVE:
         * System.out.println("WAITING FOR MOVE");
         * Message serverMessage = serverConnection.getResponse();
         * clientMessageHandler.handleMessage(serverMessage);
         * break;
         * default:
         * System.out.println("Unknown state");
         * break;
         * }
         * 
         * }
         * } catch (IOException e) {
         * System.out.println("Client exception: " + e.getMessage());
         * e.printStackTrace();
         * }
         */

    }

    public void stop() {
        System.out.println("Client stop");
    }

    public void setState(ClientState state) {
        this.state = state;
    }

    public ClientState getState() {
        return state;
    }

    public Game getGame() {
        return game;
    }

    public void newGame() {
        game = new Game(this);
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

    public void displayError(String error) {
        /**
         * TODO: tutaj osobne gui z errorem
         */
        System.out.println(error);
    }

    public void displayMessage(String message) {
        /**
         * TODO: tutaj osobne gui z message
         */
        System.out.println(message);
    }

    public ServerConnection getServerConnection() {
        return serverConnection;
    }

    public ClientGUI getClientGUI() {
        return clientGUI;
    }
}