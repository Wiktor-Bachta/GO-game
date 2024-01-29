package tp;

import javafx.scene.paint.Color;
import tp.gamelogic.StoneState;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StoneStateTest {
    @Test
    public void testGetColorByStoneState() {
        assertEquals(Color.BLACK, StoneState.getColorByStoneState(StoneState.BLACK));
        assertEquals(Color.WHITE, StoneState.getColorByStoneState(StoneState.WHITE));
    }
}