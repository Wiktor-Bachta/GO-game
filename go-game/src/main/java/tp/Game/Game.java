package tp.Game;

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

    public void launch()
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

        switch (choice) {
            case 1:
                System.out.println("Play with bot");
                break;
            case 2:
                System.out.println("Play with user");
                break;
            case 3:
                System.out.println("Exit");
                running = false;
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }

    }

    private void stopGame() {
        System.out.println("Game over");
        running = false;
    }
    public boolean isRunning() {
        return running;
    }
}
