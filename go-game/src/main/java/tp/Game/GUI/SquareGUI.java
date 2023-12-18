package tp.Game.GUI;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class SquareGUI extends Rectangle {
    Circle circle;

    public SquareGUI(int x, int y, int size) {
        setFill(Color.SANDYBROWN);
        setWidth(size);
        setHeight(size);
        setX(size * x);
        setY(size * y);
        setCircle(x * size + size / 2, y * size + size / 2);
        setOnMouseClicked(event -> {
            circle.setVisible(true);
            circle.setFill(Color.BLACK);
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
        circle.setFill(Color.TRANSPARENT);
        circle.setVisible(false);
        circle.setCenterX(x);
        circle.setCenterY(y);
    }

    public Circle getCircle() {
        return circle;
    }
}
