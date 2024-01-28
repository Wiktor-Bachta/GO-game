package tp.Server;

import java.util.ArrayList;
import java.util.Random;

import tp.Game.StoneState;
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
        ArrayList<Stone> emptySquares = new ArrayList<>();
        for (Stone[] stoneRow : board) {
            for (Stone stone : stoneRow) {
                if (stone.getState() == StoneState.EMPTY) {
                    emptySquares.add(stone);
                }
            }
        }
        Random random = new Random();
        Stone chosenStone = emptySquares.get(random.nextInt(emptySquares.size()));
        return chosenStone.getX() + ";" + chosenStone.getY();
    }

    public void placeOpponentMove(int x, int y) {
        board[x][y].setState(StoneState.BLACK);
    }

    public void clearStone(int x, int y) {
        board[x][y].setState(StoneState.EMPTY);
    }

    public void placeBotMove(int x, int y) {
        board[x][y].setState(StoneState.WHITE);
    }

    public Stone[][] getBoard() {
        return board;
    }

}
