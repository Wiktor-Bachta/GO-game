package tp;

import org.junit.Test;

import tp.GameLogic.MoveAnalyzer;
import tp.Server.Session;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MoveAnalyzerTest {

    @Test
    public void testAnalyzeMoveValidMove() {
        Session session = mockSession();
        MoveAnalyzer moveAnalyzer = new MoveAnalyzer(session);
        boolean result = moveAnalyzer.analyzeMove(3, 4);
        assertTrue(result);
    }

    @Test
    public void testAnalyzeMoveInvalidMove() {
        Session session = mockSession();
        MoveAnalyzer moveAnalyzer = new MoveAnalyzer(session);
        moveAnalyzer.analyzeMove(3, 4);
        boolean result = moveAnalyzer.analyzeMove(3, 4);
        assertFalse(result);
    }

    @Test
    public void testKoRuleViolated() {
        Session session = mockSession();
        MoveAnalyzer moveAnalyzer = new MoveAnalyzer(session);
        moveAnalyzer.analyzeMove(0, 1);
        moveAnalyzer.analyzeMove(0, 2);
        moveAnalyzer.analyzeMove(1, 0);
        moveAnalyzer.analyzeMove(1, 1);
        moveAnalyzer.analyzeMove(0, 0);
        boolean result = moveAnalyzer.analyzeMove(0, 1);
        assertFalse(result);
    }

    private Session mockSession() {
        Session session = mock(Session.class);
        when(session.getBoardSize()).thenReturn(19); 
        return session;
    }
}
