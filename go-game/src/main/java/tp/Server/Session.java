package tp.Server;

import tp.Database.DatabaseFacade;
import tp.Game.StoneState;
import tp.GameLogic.MoveAnalyzer;
import tp.Database.dto.GameHistory;
import tp.Database.dto.MoveType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Session {
    private String ID;
    // player one is always first
    private ClientHandler player1;
    private ClientHandler player2;
    private DatabaseFacade databaseFacade;
    private int moveCount = 0;
    List<GameHistory> gameHistory;
    private MoveAnalyzer moveAnalyzer;
    private int boardSize;

    private boolean ableToJoin = true;

    public Session(ClientHandler player1) {
        this.ID = generateID();
        this.player1 = player1;
        databaseFacade = new DatabaseFacade();
    }

    public void setBoardSize(int size) {
        this.boardSize = size;
    }

    public void createMoveAnalyzer() {
        moveAnalyzer = new MoveAnalyzer(this);
    }

    public int getBoardSize() {
        return boardSize;
    }

    private String generateID() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        return str.substring(0, 6);
    }

    public String getID() {
        return this.ID;
    }

    public void addPlayer2(ClientHandler player2) {
        this.player2 = player2;
        ableToJoin = false;
    }

    public void addBot() {

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

    public int getPoints(StoneState state) {
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

    public int getMoveCount() {
        return moveCount;
    }

    public DatabaseFacade getDatabaseFacade() {
        return databaseFacade;
    }

    public void loadGameHistory() {
        if (gameHistory != null) {
            return;
        }
        gameHistory = databaseFacade.getGameHistory(ID);
    }

    public List<String> getMoves(int number) {
        List<String> result = new ArrayList<String>();
        for (GameHistory move : gameHistory) {
            if (move.getMoveNumber() == number) {
                result.add(getMoveForm(move));
            }
        }
        return result;
    }

    private String getMoveForm(GameHistory move) {
        if (move.getMoveType() == MoveType.Move) {
            return "Move;" + move.getX() + ";" + move.getY();
        } else if (move.getMoveType() == MoveType.Remove) {
            return "Remove;" + move.getX() + ";" + move.getY();
        } else {
            return "Pass";
        }
    }

    public void setDatabaseFacade(DatabaseFacade databaseFacade) {
        this.databaseFacade = databaseFacade;
    }

    public void setMoveAnalyzer(MoveAnalyzer moveAnalyzer) {
        this.moveAnalyzer = moveAnalyzer;
    }
}
