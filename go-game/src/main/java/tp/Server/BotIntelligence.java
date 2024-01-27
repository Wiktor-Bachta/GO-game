package tp.Server;

import java.util.Random;

import tp.Game.SquareState;
import tp.GameLogic.Stone;

public class BotIntelligence {

    private Stone[][] board;
    private int size;

    public BotIntelligence(int size) {
        this.size = size;
        this.board = new Stone[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new Stone(i, j);
            }
        }
    }

    // random moves for now
    // important not to be deterministic 
    // in case move is invalid
    public String getMove() {
        Random random = new Random();
        int x;
        int y;
        do {
            x = random.nextInt(size);
            y = random.nextInt(size);
        } while (board[x][y].getState() != SquareState.EMPTY);
        return x + ";" + y;
    }

    public void placeOpponentMove(int x, int y) {
        board[x][y].setState(SquareState.BLACK);
    }

    public void clearStone(int x, int y) {
        board[x][y].setState(SquareState.EMPTY);
    }

    public void placeBotMove(int x, int y) {
        board[x][y].setState(SquareState.WHITE);
    }

}
