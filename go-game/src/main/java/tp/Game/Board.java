package tp.Game;

import tp.Game.GUI.BoardGUI;

public class Board {
    Game game;
    BoardGUI boardGUI;
    Square[][] squares;
    int size;

    public Board(int size, int pixelSize) {
        this.size = size;
        squares = new Square[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                squares[i][j] = new Square(i, j, this);
            }
        }
        boardGUI = new BoardGUI(size, pixelSize, squares);
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

    public void sendMessage(String string) {
        //;
    }
}
