package tp.Server;

import tp.Client.Client;

import java.util.UUID;

public class Session {
    private String ID;
    private ClientHandler player1;
    private ClientHandler player2;

    private boolean ableToJoin = true;

    public Session(ClientHandler player1) {
        this.ID = generateID();
        this.player1 = player1;

    }


    private String generateID() {
           return UUID.randomUUID().toString();
    }

    public String getID() {
        return this.ID;
    }

    public void addPlayer2(ClientHandler player2){
        this.player2 = player2;
        ableToJoin = false;
    }

    public void addPlayer2(){
        //TODO: add bot
        ableToJoin = false;
    }

    public boolean isAbleToJoin() {
        return ableToJoin;
    }
}
