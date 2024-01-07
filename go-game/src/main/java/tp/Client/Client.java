package tp.Client;

import tp.Connection.ServerConnection;
import tp.Game.Game;
import tp.Message.ClientMessageHandler;
import tp.Message.Message;

import java.io.*;


public class Client {

    private Game game;
    private ClientState state;
    private ServerConnection serverConnection;
    private ClientMessageHandler clientMessageHandler;

    public Client() throws IOException {
        this.game = new Game(this);
        this.serverConnection = new ServerConnection("localhost", 8000);
        this.clientMessageHandler = new ClientMessageHandler(this);
        this.state = ClientState.SETTING_UP;
    }

    public void run(){
        System.out.println("Client started");
        try {

            Message launchMessage= game.launch();
            serverConnection.sendMessage(launchMessage);

            while(state == ClientState.SETTING_UP)
            {
                Message serverMessage = serverConnection.getResponse();
                clientMessageHandler.handleMessage(serverMessage);
            }

            while (game.isRunning()) {

                switch (state) {
                    case DOING_MOVE:
                        System.out.println("DOING MOVE");
                        Message clientMessage = game.doMove();
                        serverConnection.sendMessage(clientMessage);

                        break;
                    case WAITING_FOR_MOVE:
                        System.out.println("WAITING FOR MOVE");
                        Message serverMessage = serverConnection.getResponse();
                        clientMessageHandler.handleMessage(serverMessage);
                        break;
                    default:
                        System.out.println("Unknown state");
                        break;
                }

            }
        } catch (IOException e) {
            System.out.println("Client exception: " + e.getMessage());
            e.printStackTrace();
        }
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

    public void displayBoard() {
        /**
         * TODO: display the board
         * tutaj trzeba wysiwetlic plansze ( nie wiem czy nie jako nowy watek)
         * klikniecie spowoduje zwrocenie ruchu w postaci message w postci Move;X;Y;ID (ID to id gry)
         *
         */
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

}