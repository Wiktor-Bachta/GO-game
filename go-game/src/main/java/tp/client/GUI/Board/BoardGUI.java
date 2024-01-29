package tp.client.GUI.Board;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import tp.client.Client;
import tp.client.ClientState;
import tp.client.GUI.ClientGUI;
import tp.gamelogic.StoneState;

public class BoardGUI extends Pane {

    private Client client;

    SquareGUI[][] squaresGUI;
    Line[] verticalLines;
    Line[] horizontaLines;
    int size;
    int pixelSize;
    int squareSize;

    public BoardGUI(Client client, int size) {
        this.size = size;
        pixelSize = getPixelSize(size);
        squareSize = getSqarePixelSize(size);
        this.client = client;

        squaresGUI = new SquareGUI[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                squaresGUI[i][j] = new SquareGUI(i, j, this, squareSize);
                getChildren().add(squaresGUI[i][j]);
            }
        }
        verticalLines = new Line[size];
        horizontaLines = new Line[size];
        for (int i = 0; i < size; i++) {
            verticalLines[i] = new Line((i + 1 / 2f) * squareSize, squareSize / 2, (i + 1 / 2f) * squareSize,
                    pixelSize - squareSize / 2);
            horizontaLines[i] = new Line(squareSize / 2, (i + 1 / 2f) * squareSize, pixelSize - squareSize / 2,
                    (i + 1 / 2f) * squareSize);
            getChildren().addAll(verticalLines[i], horizontaLines[i]);
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                getChildren().add(squaresGUI[i][j].getCircle());
            }
        }
    }

    public void sendMessage(String move) {
        if (client.getState() == ClientState.DOING_MOVE) {
            client.sendMessage("Move;" + move);
        }
    }

    public void placeMove(int x, int y, StoneState playerStoneState) {
        squaresGUI[x][y].placeMove(StoneState.getColorByStoneState(playerStoneState));
    }

    public void clearMove(int x, int y) {
        squaresGUI[x][y].clearMove();
    }

    public void reset() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                squaresGUI[i][j].clearMove();
            }
        }
    }

    private int getPixelSize(int size) {
        return getSqarePixelSize(size) * size;
    }

    private int getSqarePixelSize(int size) {
        return ClientGUI.boardSize / size;
    }

    public Client getClient() {
        return client;
    }
}
