package tp.Game;

import javafx.scene.paint.Color;
import tp.Game.GUI.SquareGUI;

public class Square {
    private int x, y;
    private SquareGUI squareGUI;
    Board board;

    public Square(int x, int y, Board board) {
        this.x = x;
        this.y = y;
        this.board = board;
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
}
