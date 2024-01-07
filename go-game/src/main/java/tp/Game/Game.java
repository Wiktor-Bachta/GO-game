package tp.Game;

import tp.Client.Client;
import tp.Client.ClientState;
import tp.Message.Message;

import java.util.Scanner;

public class Game {
    String ID;
    boolean running;
    Client client;
    public Game(Client client) {
        this.client = client;

    }

    public String getId() {
        return ID;
    }

    public void setId(String id) {
        this.ID = id;
    }

    public Message doMove() {
        /**
         * TODO: tu trzeba bedzie stworzyc okno umozliwiajace wybor ruchu ewentualnie button pass i resign
         */

        boolean pass = false;
        boolean resign = false;

        client.setState(ClientState.WAITING_FOR_MOVE);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter move X: ");
        String Xmove = scanner.nextLine();

        if(Xmove.equals("pass")) {
            pass = true;
            return new Message("Pass;"+ID+";");
        }
        else if(Xmove.equals("resign")) {
            resign = true;
            return new Message("Surrender;"+ID+";");
        }

        System.out.println("Enter move Y: ");
        String Ymove = scanner.nextLine();

        return new Message("Move;"+Xmove+";"+Ymove+";"+ID+";");
    }

    public Message launch()
    {
        /**
         * TODO: tu trzeba bedzie stworzyc okno startowe umozliwiajace wybor tak jak ponizej
         */
        this.running = true;
        System.out.println("Launch Game");

        System.out.println("Choose: ");
        System.out.println("1. Play with bot");
        System.out.println("2. Play with user");
        System.out.println("3. Exit");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        String msg = "Launch;";

        boolean validChoice = false;
        while(!validChoice)
        {
        switch (choice) {
            case 1:
                System.out.println("Play with bot");
                msg = createGame(msg,"bot");
                validChoice = true;
                break;
            case 2:
                System.out.println("Play with user");
                System.out.println("a. Create game");
                System.out.println("b. Join game");

                String gameChoice = scanner.next();
                if(gameChoice.equals("a"))
                    msg = createGame(msg,"user");
                else if(gameChoice.equals("b"))
                    msg = joinGame(msg);
                validChoice = true;
                break;
            case 3:
                System.out.println("Exit");
                running = false;
                msg = "Disconnect";
                validChoice = true;
                break;
            default:
                System.out.println("Invalid choice");
                running = false;
                msg = "Disconnect";
                break;
        }
        }
        return new Message(msg);
    }

    private String createGame(String msg,String opponent) {
        // create game
        // wait for user to join
        // start game
        return msg+"Create;"+opponent+";";
    }

    private String joinGame(String msg) {
        // join game
        // type game id
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter game id: ");
        String gameId = scanner.nextLine();
        return msg+"Join;"+gameId+";";
    }

    public void stopGame() {
        System.out.println("Bye");
        running = false;
    }
    public boolean isRunning() {
        return running;
    }
}
