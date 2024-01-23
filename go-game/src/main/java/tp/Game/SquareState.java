package tp.Game;

import javafx.scene.paint.Color;

public enum SquareState {
    BLACK,
    WHITE,
    EMPTY;

    public static Color getColorBySquareState(SquareState state) {
        if (state == SquareState.BLACK) {
            return Color.BLACK;
        }
        return Color.WHITE;
    }
}
