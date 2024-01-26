package tp.Server;

import tp.Client.Client;
import tp.Database.DatabaseFacade;
import tp.Game.SquareState;
import tp.GameLogic.MoveAnalyzer;
import tp.Message.Message;

import java.util.UUID;

public class Session {
    private String ID;
    //player one is always first
    private ClientHandler player1;
    private ClientHandler player2;
    private DatabaseFacade databaseFacade;
    private int moveCount = 0;

    private MoveAnalyzer moveAnalyzer;

    private boolean ableToJoin = true;

    public Session(ClientHandler player1) {
        this.ID = generateID();
        this.player1 = player1;
        this.moveAnalyzer = new MoveAnalyzer(this);
        databaseFacade = new DatabaseFacade();
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

    public boolean analyzeMove(int x, int y) {
        return moveAnalyzer.analyzeMove(x, y);
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

    public void setPassEndsGame(boolean val) {
        moveAnalyzer.setPassEndsGame(val);
    }

    public boolean getPassEndsGame() {
        return moveAnalyzer.getPassEndsGame();
    }

    public boolean getOnePlayerAgreedToEnd() {
        return moveAnalyzer.getOnePlayerAgreedToEnd();
    }

    public void setOnePlayerAgreedToEnd(boolean val) {
        moveAnalyzer.setOnePlayerAgreedToEnd(val);
    }

    public int getPoints(SquareState state) {
        return moveAnalyzer.calculatePoints(state);
    }

    public void swapPlayers() {
        ClientHandler temp = player1;
        player1 = player2;
        player2 = temp;
    }

    public void skipTurn() {
        moveAnalyzer.skipTurn();
    }

    public int getAndUpdateMoveCount() {
        moveCount++;
        return moveCount;
    }

    public DatabaseFacade getDatabaseFacade() {
        return databaseFacade;
    }

}
