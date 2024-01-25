package tp.Game;

import tp.Client.Client;
import tp.Client.ClientState;
import tp.Game.GUI.ChoiceGUI;
import tp.Message.Message;

import java.util.Scanner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class Game {
    String ID;
    Board board;
    Client client;

    public Game(Client client) {
        this.client = client;
        board = new Board(this, 19, 19*40);
    }

    public String getId() {
        return ID;
    }

    public void setId(String id) {
        this.ID = id;
    }

    public Board getBoard() {
        return board;
    }

    public void sendMessage(String string) {
        if (client.getState() == ClientState.DOING_MOVE) {
            client.getServerConnection().sendMessage(new Message("Move;" + string + ";" + ID + ";"));
        }
    }
}
