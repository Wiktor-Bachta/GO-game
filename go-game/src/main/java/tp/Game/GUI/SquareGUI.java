package tp.Game.GUI;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import tp.Game.StoneState;

public class SquareGUI extends Rectangle {
    BoardGUI boardGUI;
    int x;
    int y;
    Circle circle;

    public SquareGUI(int x, int y, BoardGUI boardGUI, int size) {
        this.x = x;
        this.y = y;
        setFill(Color.SANDYBROWN);
        setWidth(size);
        setHeight(size);
        setX(size * x);
        setY(size * y);
        setCircle(size / 4, x * size + size / 2, y * size + size / 2);
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

    public void setCircle(int radius, int x, int y) {
        circle = new Circle(radius);
        circle.setVisible(false);
        circle.setCenterX(x);
        circle.setCenterY(y);
    }

    public Circle getCircle() {
        return circle;
    }

    public void placeMove(Color colorByStoneState) {
        circle.setFill(colorByStoneState);
        circle.setVisible(true);
    }

    public void clearMove() {
        circle.setVisible(false);
    }

}
