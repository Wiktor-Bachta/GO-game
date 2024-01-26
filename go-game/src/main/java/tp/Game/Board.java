package tp.Game;

import tp.Client.Client;
import tp.Client.ClientState;
import tp.Game.GUI.BoardGUI;
import tp.Message.Message;

public class Board {
    Client client;
    BoardGUI boardGUI;
    Square[][] squares;
    int size = 19;
    int pixelSize = 19 * 40;

    public Board(Client client) {
        this.client = client;
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
        if (client.getState() == ClientState.DOING_MOVE) {
            client.getServerConnection().sendMessage(new Message("Move;" + string));
        }
    }
}
