package tp.Game.GUI;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class SquareGUI extends Rectangle {
    Pane pane;

    public SquareGUI(int x, int y) {
        setOnMouseClicked(new MyMouseHandler());
    }

    public void placePawn(Color color) {
        setFill(color);
    }

    class MyMouseHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent e) {
            SquareGUI square = (SquareGUI) e.getSource();
            square.setFill(Color.RED);
        }

    }
}
