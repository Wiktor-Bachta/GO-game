package tp;

import org.junit.Before;
import org.junit.Test;

import tp.database.DatabaseFacade;
import tp.database.dto.GameHistory;
import tp.database.dto.MoveType;
import tp.gamelogic.MoveAnalyzer;
import tp.gamelogic.StoneState;
import tp.server.ClientHandler;
import tp.server.Session;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SessionTest {
    private Session session;
    private ClientHandler clientHandler1;
    private ClientHandler clientHandler2;
    private DatabaseFacade databaseFacade;
    private MoveAnalyzer moveAnalyzer;

    @Before
    public void setup() {
        clientHandler1 = mock(ClientHandler.class);
        clientHandler2 = mock(ClientHandler.class);
        session = new Session(clientHandler1);
        databaseFacade = mock(DatabaseFacade.class);
        session.setDatabaseFacade(databaseFacade);
        moveAnalyzer = mock(MoveAnalyzer.class);
        session.setMoveAnalyzer(moveAnalyzer);
    }

    @Test
    public void testGetID() {
        assertNotNull(session.getID());
    }

    @Test
    public void testAddPlayer2() {
        assertTrue(session.isAbleToJoin());
        session.addPlayer2(clientHandler2);
        assertFalse(session.isAbleToJoin());
    }

    @Test
    public void testGetPlayer1() {
        assertEquals(clientHandler1, session.getPlayer1());
    }

    @Test
    public void testGetPlayer2() {
        assertNull(session.getPlayer2());
        session.addPlayer2(clientHandler2);
        assertEquals(clientHandler2, session.getPlayer2());
    }

    @Test
    public void testSwapPlayers() {
        session.addPlayer2(clientHandler2);
        assertEquals(clientHandler1, session.getPlayer1());
        assertEquals(clientHandler2, session.getPlayer2());
        session.swapPlayers();
        assertEquals(clientHandler2, session.getPlayer1());
        assertEquals(clientHandler1, session.getPlayer2());
    }

    @Test
    public void testGetMoves() throws IOException {
        List<GameHistory> gameHistory = Arrays.asList(
                new GameHistory(1, MoveType.Move, 0, 0),
                new GameHistory(1, MoveType.Remove, 1, 1),
                new GameHistory(2, MoveType.Pass, -1, -1),
                new GameHistory(2, MoveType.Move, 2, 2)
        );

        // Stub the getGameHistory method to return the list
        when(databaseFacade.getGameHistory(anyString())).thenReturn(gameHistory);

        // Call the getMoves method
        session.loadGameHistory();
        List<String> moves = session.getMoves(1);

        // Verify that the returned list contains the correct moves
        assertEquals(2, moves.size());
        assertEquals("Move;0;0", moves.get(0));
        assertEquals("Remove;1;1", moves.get(1));
    }

    @Test
    public void testAnalyzeMove() {
        when(moveAnalyzer.analyzeMove(1, 1)).thenReturn(true);
        assertTrue(session.analyzeMove(1, 1));
    }

    @Test
    public void testSetPassEndsGame() {
        session.setPassEndsGame(true);
        verify(moveAnalyzer, times(1)).setPassEndsGame(true);
    }

    @Test
    public void testGetPassEndsGame() {
        when(moveAnalyzer.getPassEndsGame()).thenReturn(true);
        assertTrue(session.getPassEndsGame());
    }

    @Test
    public void testGetOnePlayerAgreedToEnd() {
        when(moveAnalyzer.getOnePlayerAgreedToEnd()).thenReturn(true);
        assertTrue(session.getOnePlayerAgreedToEnd());
    }

    @Test
    public void testSetOnePlayerAgreedToEnd() {
        session.setOnePlayerAgreedToEnd(true);
        verify(moveAnalyzer, times(1)).setOnePlayerAgreedToEnd(true);
    }

    @Test
    public void testGetPoints() {
        when(moveAnalyzer.calculatePoints(StoneState.BLACK)).thenReturn(10);
        assertEquals(10, session.getPoints(StoneState.BLACK));
    }

    }
