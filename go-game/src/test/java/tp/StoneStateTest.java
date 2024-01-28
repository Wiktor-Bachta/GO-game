package tp;

import javafx.scene.paint.Color;
import org.junit.Test;
import tp.Game.StoneState;

import static org.junit.Assert.assertEquals;

public class StoneStateTest {
    @Test
    public void testGetColorByStoneState() {
        assertEquals(Color.BLACK, StoneState.getColorByStoneState(StoneState.BLACK));
        assertEquals(Color.WHITE, StoneState.getColorByStoneState(StoneState.WHITE));
    }
}