package tp.Client.GUI;

import tp.Game.StoneState;

public enum ClientColor {
    BLACK,
    WHITE;

    public static StoneState getPlayerStoneState(ClientColor color) {
        if (color == ClientColor.BLACK) {
            return StoneState.BLACK;
        }
        return StoneState.WHITE;
    }

    public static StoneState getOpponentStoneState(ClientColor color) {
        if (color == ClientColor.BLACK) {
            return StoneState.WHITE;
        }
        return StoneState.BLACK;
    }
}

