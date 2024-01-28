package tp;

import org.junit.Before;
import org.junit.Test;
import tp.Game.StoneState;
import tp.Server.BotIntelligence;

import static org.junit.Assert.*;

public class BotIntelligenceTest {
    private BotIntelligence botIntelligence;

    @Before
    public void setup() {
        botIntelligence = new BotIntelligence(19);
    }

    @Test
    public void testPlaceOpponentMove() {
        botIntelligence.placeOpponentMove(1, 1);
        assertEquals(StoneState.BLACK, botIntelligence.getBoard()[1][1].getState());
    }

    @Test
    public void testClearStone() {
        botIntelligence.placeOpponentMove(1, 1);
        botIntelligence.clearStone(1, 1);
        assertEquals(StoneState.EMPTY, botIntelligence.getBoard()[1][1].getState());
    }

    @Test
    public void testPlaceBotMove() {
        botIntelligence.placeBotMove(1, 1);
        assertNotEquals(StoneState.EMPTY, botIntelligence.getBoard()[1][1].getState());
    }

    @Test
    public void testGetMove() {
        String move = botIntelligence.getMove();
        String[] moveSplit = move.split(";");
        int x = Integer.parseInt(moveSplit[0]);
        int y = Integer.parseInt(moveSplit[1]);
        assertEquals(StoneState.EMPTY, botIntelligence.getBoard()[x][y].getState());
    }
}