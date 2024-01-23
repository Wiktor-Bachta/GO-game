package tp.Client.GUI;

import tp.Game.SquareState;

public enum ClientColor {
    BLACK,
    WHITE;

    public static SquareState getPlayerSquareState(ClientColor color) {
        if (color == ClientColor.BLACK) {
            return SquareState.BLACK;
        }
        return SquareState.WHITE;
    }

    public static SquareState getOpponentSquareState(ClientColor color) {
        if (color == ClientColor.BLACK) {
            return SquareState.WHITE;
        }
        return SquareState.BLACK;
    }
}

