package tp.Client;

import tp.Game.Move;
import tp.Game.PawnColor;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    /**
     * Here will be board display
     */
    // private Board board;
    private static int nextId = 1;     // id generator
    private int id;        // unique id
    private boolean running;     // game running flag
    private PawnColor pawnColor; // player color

    public Client() {
        // board = new Board();
        this.id = nextId++;
    }

    public void run() throws IOException {
        System.out.println("Client started");
        try (Socket clientSocket = new Socket("localhost", 8000)) {
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream());
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            launchGame();

            while (running) {
                Move move = doMove();
                // send move to server

                output.println(move.getMove());
                output.flush(); // flush the stream to ensure that the data has been written to the stream

                // receive move from server
                // here can be  a factory method to read if message is a move, info, endgame or error but for now lets suppose its a move

                String serverMessage = input.readLine();
                if (serverMessage == null) {
                    throw new IOException("Server disconnected");
                }
                // instead of if else we can use a factory method to read if message is a move, info, endgame or error
                if (serverMessage.contains("endgame")) {
                    running = false;
                    System.out.println("Game ended");
                } else if (serverMessage.contains("error")) {
                    System.out.println("Error: " + serverMessage);
                } else if (serverMessage.contains("info")) {
                    System.out.println("Info: " + serverMessage);
                } else if (serverMessage.contains("move")) {
                    System.out.println("Move: " + serverMessage);
                } else {
                    System.out.println("Message: " + serverMessage);
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

    private void launchGame() {
        // display the board
        // choose game with user or bot
        this.running = true;
        System.out.println("Launch Game");
    }

    private Move doMove() {
        // send move to server
        // get move from user
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter move: ");
        String moveString = scanner.nextLine();

        return new Move(moveString);
    }

    private Move getMove(String moveString) {
        // receive move from server
        // here can be  a factory method to read if message is a move, info or error
        System.out.println("Get move: " + moveString);
        return new Move(moveString);
    }


}