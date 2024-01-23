package tp.GameLogic;

import tp.Game.Move;
import tp.Game.Square;
import tp.Game.SquareState;
import tp.Message.Message;
import tp.Server.Session;

/**
 * This class is responsible for analyzing moves and checking if they are valid.
 * It will return board state after move.
 */
public class MoveAnalyzer {

    private Session session;
    private SquareState[][] board;
    private SquareState currentSquareState;

    public MoveAnalyzer(Session session) {
        board = new SquareState[19][19];
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 19; j++) {
                board[i][j] = SquareState.EMPTY;
            }
        }
        currentSquareState = SquareState.BLACK;
        this.session = session;
    }

    public boolean analyzeMove(Move move) {

        String thisMove = move.getMove();

        String[] splitMove = thisMove.split(";");

        int x = Integer.parseInt(splitMove[0]);
        int y = Integer.parseInt(splitMove[1]);
        String message = "Move;Invalid";

        if (board[x][y] == SquareState.EMPTY) {
            message = "Move;Confirmed;" + x + ";" + y + ";";
            // TODO: send move to database
            board[x][y] = currentSquareState;
            flipCurrentSqareState();
            return true;
        }

        System.out.println(message);
        return false;
    }

    private void flipCurrentSqareState() {
        if (currentSquareState == SquareState.BLACK) {
            currentSquareState = SquareState.WHITE;
        }
        else {
            currentSquareState = SquareState.WHITE;
        }
    }
}
