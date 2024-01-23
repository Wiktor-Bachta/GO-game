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
    boolean running;
    Board board;
    Client client;

    public Game(Client client) {
        this.client = client;
        board = new Board(19, 19*40);
    }

    public String getId() {
        return ID;
    }

    public void setId(String id) {
        this.ID = id;
    }

    public Message doMove() {
        /**
         * TODO: tu trzeba bedzie stworzyc okno umozliwiajace wybor ruchu ewentualnie
         * button pass i resign
         */

        boolean pass = false;
        boolean resign = false;

        client.setState(ClientState.WAITING_FOR_MOVE);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter move X: ");
        String Xmove = scanner.nextLine();

        if (Xmove.equals("pass")) {
            pass = true;
            return new Message("Pass;" + ID + ";");
        } else if (Xmove.equals("resign")) {
            resign = true;
            return new Message("Surrender;" + ID + ";");
        }

        System.out.println("Enter move Y: ");
        String Ymove = scanner.nextLine();

        return new Message("Move;" + Xmove + ";" + Ymove + ";" + ID + ";");
    }

/*     public Message launch() {
        choiceGUI = new ChoiceGUI();
        Thread thread = new Thread(() -> {
            Application.launch(ChoiceGUI.class);
        });
        Platform.runLater(() -> {
            try {
                thread.start();
                thread.join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        while (!choiceGUI.isChoiceMade()) {

        }
        System.out.println(choiceGUI.getMsg());
        return new Message(choiceGUI.getMsg());

    } */

    public void stopGame() {
        System.out.println("Bye");
        running = false;
    }

    public boolean isRunning() {
        return running;
    }

    public Board getBoard() {
        return board;
    }
}
