package tp.Game;

import javafx.scene.paint.Color;

public enum StoneState {
    BLACK,
    WHITE,
    EMPTY;

    public static Color getColorByStoneState(StoneState state) {
        if (state == StoneState.BLACK) {
            return Color.BLACK;
        }
        return Color.WHITE;
    }
}
