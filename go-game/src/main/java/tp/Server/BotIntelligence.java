package tp.Server;

import java.util.Random;

import tp.Game.SquareState;
import tp.GameLogic.Stone;

public class BotIntelligence {

    private Stone[][] board;

    public BotIntelligence() {
        this.board = new Stone[19][19];
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 19; j++) {
                board[i][j] = new Stone(i, j);
            }
        }
    }

    // random moves for now
    public String getMove() {
        Random random = new Random();
        int x;
        int y;
        do {
            x = random.nextInt(19) + 1;
            y = random.nextInt(19) + 1;
        } while (board[x][y].getState() != SquareState.EMPTY);
        return x + ";" + y;
    }

    public void placeOpponentMove(int x, int y) {
        board[x][y].setState(SquareState.BLACK);
    }

    public void clearStone(int x, int y) {
        board[x][y].reset();
    }

    public void placeBotMove(int x, int y) {
        board[x][y].setState(SquareState.WHITE);
    }

}
