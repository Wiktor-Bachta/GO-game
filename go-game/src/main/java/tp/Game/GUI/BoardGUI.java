package tp.Game.GUI;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import tp.Game.Square;

public class BoardGUI {

    Pane pane;
    SquareGUI[][] squaresGUI;
    Line[] verticalLines;
    Line[] horizontaLines;
    int size;
    int pixelSize;
    int squareSize;

    public BoardGUI(int size, Square[][] squares) {
        pixelSize = 760;
        this.size = size;
        squareSize = pixelSize / size;
        pane = new Pane();
        squaresGUI = new SquareGUI[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                squaresGUI[i][j] = squares[i][j].getSquareGUI();
                pane.getChildren().add(squaresGUI[i][j]);
                squaresGUI[i][j].setWidth(squareSize);
                squaresGUI[i][j].setHeight(squareSize);
                squaresGUI[i][j].setX(squareSize * i);
                squaresGUI[i][j].setY(squareSize * j);
            }
        }
        verticalLines = new Line[size];
        horizontaLines = new Line[size];
        for (int i = 0; i < size ; i++) {
            verticalLines[i] = new Line((i + 1/2f) * squareSize, squareSize / 2, (i + 1/2f) * squareSize, pixelSize - squareSize / 2);
            horizontaLines[i] = new Line(squareSize / 2, (i + 1/2f) * squareSize, pixelSize - squareSize / 2, (i + 1/2f) * squareSize);
            pane.getChildren().addAll(verticalLines[i], horizontaLines[i]);
        }
    }

    public Pane getPane() {
        return pane;
    }

}
