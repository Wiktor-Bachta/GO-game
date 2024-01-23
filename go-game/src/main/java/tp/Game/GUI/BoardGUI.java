package tp.Game.GUI;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import tp.Client.Client;
import tp.Game.Square;

public class BoardGUI {

    private Client client;

    Pane pane;
    SquareGUI[][] squaresGUI;
    Line[] verticalLines;
    Line[] horizontaLines;
    int size;
    int pixelSize;
    int squareSize;

    public BoardGUI( int size, int pixelSize, Square[][] squares) {
        //this.client = client;

        this.pixelSize = pixelSize;
        this.size = size;
        squareSize = pixelSize / size;
        pane = new Pane();
        squaresGUI = new SquareGUI[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                squares[i][j].setSquareGUI(new SquareGUI(i, j, squareSize, squares[i][j]));
                squaresGUI[i][j] = squares[i][j].getSquareGUI();
                pane.getChildren().add(squaresGUI[i][j]);
                pane.getChildren().add(squaresGUI[i][j].getCircle());
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
