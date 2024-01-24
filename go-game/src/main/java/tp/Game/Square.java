package tp.Game;

import javafx.scene.paint.Color;
import tp.Game.GUI.SquareGUI;

public class Square {
    private int x, y;
    private SquareGUI squareGUI;
    private SquareState squareState;
    Board board;

    public Square(int x, int y, Board board) {
        this.x = x;
        this.y = y;
        this.board = board;
        squareState = SquareState.EMPTY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setFill() {
        squareGUI.setFill(Color.BEIGE);
    }

    public SquareGUI getSquareGUI() {
        return squareGUI;
    }

    public void setSquareGUI(SquareGUI squareGUI) {
        this.squareGUI = squareGUI;
    }

    public void sendMessage() {
        board.sendMessage(x + ";" + y);
    }

    public void placeMove(int x, int y, SquareState playerSquareState) {
        this.squareState = playerSquareState;
        squareGUI.placeMove(SquareState.getColorBySquareState(playerSquareState));
    }

    public void clearMove(int x, int y) {
        this.squareState = SquareState.EMPTY;
        squareGUI.clearMove();
    }
}
