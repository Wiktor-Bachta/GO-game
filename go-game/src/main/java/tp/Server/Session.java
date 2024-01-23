package tp.Server;

import tp.Client.Client;
import tp.Game.Move;
import tp.GameLogic.MoveAnalyzer;
import tp.Message.Message;

import java.util.UUID;

public class Session {
    private String ID;
    private ClientHandler player1;
    private ClientHandler player2;

    private MoveAnalyzer moveAnalyzer;

    private boolean ableToJoin = true;

    public Session(ClientHandler player1) {
        this.ID = generateID();
        this.player1 = player1;
        this.moveAnalyzer = new MoveAnalyzer(this);
    }


    private String generateID() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        return str.substring(0, 6);
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

    public ClientHandler getPlayer1() {
        return player1;
    }

    public ClientHandler getPlayer2() {
        return player2;
    }

    public boolean isAbleToJoin() {
        return ableToJoin;
    }

    public boolean analyzeMove(Move move) {
        return moveAnalyzer.analyzeMove(move);
    }

    public boolean hasPlayer(ClientHandler player) {
        return (player1 == player || player2 == player);
    }

    public ClientHandler getSecondPlayer(ClientHandler player) {
        if (player1 == player) {
            return player1;
        }
        return player2;
    }
}
