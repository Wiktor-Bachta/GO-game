package tp;

import org.junit.Before;
import org.junit.Test;

import tp.gamelogic.MoveAnalyzer;
import tp.gamelogic.StoneState;
import tp.server.Session;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MoveAnalyzerTest {

    private Session session;

    @Before
    public void mockSession() {
        session = mock(Session.class);
        when(session.getBoardSize()).thenReturn(19); 
    }

    @Test
    public void testAnalyzeMoveValidMove() {
        MoveAnalyzer moveAnalyzer = new MoveAnalyzer(session);
        boolean result = moveAnalyzer.analyzeMove(3, 4);
        assertTrue(result);
    }

    @Test
    public void testAnalyzeMoveInvalidMove() {
        MoveAnalyzer moveAnalyzer = new MoveAnalyzer(session);
        moveAnalyzer.analyzeMove(3, 4);
        boolean result = moveAnalyzer.analyzeMove(3, 4);
        assertFalse(result);
    }

    @Test
    public void testKoRuleViolated() {
        MoveAnalyzer moveAnalyzer = new MoveAnalyzer(session);
        moveAnalyzer.analyzeMove(0, 1);
        moveAnalyzer.analyzeMove(0, 2);
        moveAnalyzer.analyzeMove(1, 0);
        moveAnalyzer.analyzeMove(1, 1);
        moveAnalyzer.analyzeMove(0, 0);
        boolean result = moveAnalyzer.analyzeMove(0, 1);
        assertFalse(result);
    }

    @Test
    public void testCalculatePoints() {
        MoveAnalyzer moveAnalyzer = new MoveAnalyzer(session);
        moveAnalyzer.analyzeMove(0, 1);
        moveAnalyzer.analyzeMove(0, 17);
        moveAnalyzer.analyzeMove(1, 0);
        moveAnalyzer.analyzeMove(1,18);
        moveAnalyzer.analyzeMove(17, 18);
        moveAnalyzer.analyzeMove(18, 17);
        assertEquals(moveAnalyzer.calculatePoints(StoneState.BLACK), 1);
        assertEquals(moveAnalyzer.calculatePoints(StoneState.WHITE), 1);
    }
}
