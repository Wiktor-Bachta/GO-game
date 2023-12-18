package tp.Game;

import javafx.scene.paint.Color;
import tp.Game.GUI.SquareGUI;

public class Square {
    private int x, y;
    private SquareGUI squareGUI;

    public Square(int x, int y) {
        this.x = x;
        this.y = y;
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
}
