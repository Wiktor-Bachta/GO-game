package tp.Game;

import java.util.Vector;

import tp.Game.GUI.BoardGUI;

public class Board {
    BoardGUI boardGUI;
    Square[][] squares;
    int size;

    public Board(int size) {
        this.size = size;
        squares = new Square[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                squares[i][j] = new Square(i, j);
                squares[i][j].setFill();
            }
        }
        boardGUI = new BoardGUI(size,squares);
    }

    public Square[][] getSquares() {
        return squares;
    }

    public BoardGUI getBoardGUI() {
        return boardGUI;
    }

    public int getSize() {
        return size;
    }
}
