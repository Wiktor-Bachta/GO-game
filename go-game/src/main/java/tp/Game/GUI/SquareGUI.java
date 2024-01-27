package tp.Game.GUI;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import tp.Game.SquareState;

public class SquareGUI extends Rectangle {
    BoardGUI boardGUI;
    int x;
    int y;
    Circle circle;

    public SquareGUI(int x, int y, BoardGUI boardGUI) {
        this.x = x;
        this.y = y;
        setFill(Color.SANDYBROWN);
        setWidth(40);
        setHeight(40);
        setX(40 * x);
        setY(40 * y);
        setCircle(x * 40 + 40 / 2, y * 40 + 40 / 2);
        setOnMouseClicked(event -> {
            boardGUI.sendMessage(x + ";" + y);
        });
        setOnMouseEntered(event -> {
            setFill(Color.DARKGRAY);
        });

        setOnMouseExited(event -> {
            setFill(Color.SANDYBROWN);
        });
    }

    public void placePawn(Color color) {
        setFill(color);
    }

    public void setCircle(int x, int y) {
        circle = new Circle(10);
        circle.setVisible(false);
        circle.setCenterX(x);
        circle.setCenterY(y);
    }

    public Circle getCircle() {
        return circle;
    }

    public void placeMove(Color colorBySquareState) {
        circle.setFill(colorBySquareState);
        circle.setVisible(true);
    }

    public void clearMove() {
        circle.setVisible(false);
    }

}
