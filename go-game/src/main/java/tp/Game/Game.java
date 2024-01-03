package tp.Game;

import tp.Message.Message;

import java.util.Scanner;

public class Game {
    static int nextId = 1;
    int id;
    boolean running;
    public Game() {
        this.id = nextId++;
    }

    public int getId() {
        return id;
    }

    public Move doMove() {
        // send move to server
        // get move from user
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter move: ");
        String moveString = scanner.nextLine();

        return new Move(moveString);
    }

    public void handleResponse(String moveString) {
        // receive move from server
        // here can be  a factory method to read if message is a move, info or error
        System.out.println("Get move: " + moveString);

        if(moveString.contains("endgame")) {
            stopGame();
        }
    }

    public Message launch()
    {
        // choose game with user or bot
        // display the board
        this.running = true;
        System.out.println("Launch Game");

        System.out.println("Choose: ");
        System.out.println("1. Play with bot");
        System.out.println("2. Play with user");
        System.out.println("3. Exit");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        String msg = "Launch;";

        switch (choice) {
            case 1:
                System.out.println("Play with bot");
                msg = createGame(msg,"bot");
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

                break;
            case 3:
                System.out.println("Exit");
                running = false;
                msg = "Disconnect";
                break;
            default:
                System.out.println("Invalid choice");
                running = false;
                msg = "Disconnect";
                break;
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

    private void stopGame() {
        System.out.println("Game over");
        running = false;
    }
    public boolean isRunning() {
        return running;
    }
}
