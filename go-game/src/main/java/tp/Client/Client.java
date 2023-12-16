package tp.Client;

import tp.Connection.ServerConnection;
import tp.Game.Game;
import tp.Game.Move;
import tp.Game.PawnColor;
import tp.Message.Message;

import java.io.*;
import java.net.*;
import java.util.Scanner;



public class Client {

    private static int nextId = 1;     // id generator
    private int id;        // unique id
    private Game game;
    private ServerConnection serverConnection;

    public Client() throws IOException {
        this.id = nextId++;
        this.game = new Game();
        this.serverConnection = new ServerConnection("localhost", 8000);
    }

    public void run(){
        System.out.println("Client started");
        try {

            game.launch();

            while (game.isRunning()) {
                Move move = game.doMove();

                serverConnection.sendMessage(new Message(move.getMove()));

                Message message = serverConnection.getResponse();

                game.handleResponse(message.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Client exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void stop() {
        System.out.println("Client stop");
    }






}