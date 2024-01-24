package tp.Game.GUI;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import tp.Game.Square;
import tp.Message.Message;

public class SquareGUI extends Rectangle {
    Square square;
    Circle circle;

    public SquareGUI(int x, int y, int size, Square sqaure) {
        this.square = sqaure;
        setFill(Color.SANDYBROWN);
        setWidth(size);
        setHeight(size);
        setX(size * x);
        setY(size * y);
        setCircle(x * size + size / 2, y * size + size / 2);
        setOnMouseClicked(event -> {
            sqaure.sendMessage();
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
