package tp;

import org.junit.Before;
import org.junit.Test;
import tp.Connection.ClientConnection;
import tp.Connection.ServerConnection;
import tp.Database.DatabaseFacade;
import tp.Message.Message;
import tp.Message.ServerMessageHandler;
import tp.Server.Bot;
import tp.Server.ClientHandler;
import tp.Server.Session;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class ServerMessageHandlerTest {
    private ServerMessageHandler serverMessageHandler;
    private ClientHandler clientHandler;
    private List<Session> sessions;
    private Session currentSession;
    @Before
    public void setUp() {
        clientHandler = mock(ClientHandler.class);
        sessions = new ArrayList<>();
        currentSession = mock(Session.class);
        sessions.add(currentSession);
        when(clientHandler.getClientConnection()).thenReturn(mock(ClientConnection.class));
        serverMessageHandler = new ServerMessageHandler(sessions, clientHandler);
    }

    @Test
    public void testHandleLaunchCreate() throws IOException {
        clientHandler = mock(ClientHandler.class);
        sessions = new ArrayList<>();
        serverMessageHandler = new ServerMessageHandler(sessions, clientHandler);
        String[] msgArray = {"Launch", "Create", "sessionID", "19"};
        assert serverMessageHandler != null;
        when(clientHandler.getClientConnection()).thenReturn(mock(ClientConnection.class));
        serverMessageHandler.handleLaunch(msgArray);
        verify(clientHandler, times(1)).getClientConnection();
    }

    @Test
    public void testHandleLaunchJoin() throws IOException, InstantiationException, IllegalAccessException {
        clientHandler = mock(ClientHandler.class);
        sessions = new ArrayList<>();
        serverMessageHandler = new ServerMessageHandler(sessions, clientHandler);
        String[] msgArray = {"Launch", "Join", "sessionID"};
        when(clientHandler.getClientConnection()).thenReturn(mock(ClientConnection.class));
        serverMessageHandler.handleLaunch(msgArray);
        verify(clientHandler, times(1)).getClientConnection();
    }

    @Test
    public void testHandleLaunchJoinBot() throws IOException, InstantiationException, IllegalAccessException {
        clientHandler = mock(ClientHandler.class);
        sessions = new ArrayList<>();
        serverMessageHandler = new ServerMessageHandler(sessions, clientHandler);
        String[] msgArray = {"Launch", "Join", "sessionID"};
        when(clientHandler.getClientConnection()).thenReturn(mock(ClientConnection.class));
        serverMessageHandler.handleLaunch(msgArray);
        verify(clientHandler, times(1)).getClientConnection();
    }

    @Test
    public void handleMessageTest() throws IOException {
        // Create a spy of serverMessageHandler
        ServerMessageHandler spyServerMessageHandler = spy(serverMessageHandler);

        // Create a Message object
        Message message = new Message("Launch;Join;sessionID");
        doNothing().when(spyServerMessageHandler).handleLaunch(any());
        // Call the handleMessage method on the spy
        spyServerMessageHandler.handleMessage(message);

        // Verify that handleLaunch was called
        verify(spyServerMessageHandler, times(1)).handleLaunch(any());

        message = new Message("Move;1;1");
        doNothing().when(spyServerMessageHandler).handleMove(any(),any());
        // Call the handleMessage method on the spy
        spyServerMessageHandler.handleMessage(message);

        // Verify that handleLaunch was called
        verify(spyServerMessageHandler, times(1)).handleMove(any(), any());

        message = new Message("Pass;1;1");
        doNothing().when(spyServerMessageHandler).handlePass();
        // Call the handleMessage method on the spy
        spyServerMessageHandler.handleMessage(message);

        // Verify that handleLaunch was called
        verify(spyServerMessageHandler, times(1)).handlePass();

        message = new Message("Surrender;1;1");
        doNothing().when(spyServerMessageHandler).handleSurrender();
        // Call the handleMessage method on the spy
        spyServerMessageHandler.handleMessage(message);

        // Verify that handleLaunch was called
        verify(spyServerMessageHandler, times(1)).handleSurrender();

        message = new Message("Chat;1;1");
        doNothing().when(spyServerMessageHandler).handleChat(any());
        // Call the handleMessage method on the spy
        spyServerMessageHandler.handleMessage(message);

        // Verify that handleLaunch was called
            verify(spyServerMessageHandler, times(1)).handleChat(any());

message = new Message("Error;1;1");
        doNothing().when(spyServerMessageHandler).handleError(any());
        // Call the handleMessage method on the spy
        spyServerMessageHandler.handleMessage(message);

        // Verify that handleLaunch was called
            verify(spyServerMessageHandler, times(0)).handleError(any());

message = new Message("EndDecision;1;1");
        doNothing().when(spyServerMessageHandler).handleEndDecision(any());
        // Call the handleMessage method on the spy
        spyServerMessageHandler.handleMessage(message);

        // Verify that handleLaunch was called
            verify(spyServerMessageHandler, times(1)).handleEndDecision(any());
    }

    @Test
    public void testHandleMove() throws IOException {
        String[] msgArray = {"Move", "Confirmed", "1", "1"};
        Session session = mock(Session.class);
        when(session.analyzeMove(1, 1)).thenReturn(true);
        sessions.add(session);
        serverMessageHandler.setCurrentSession(mock(Session.class));

        serverMessageHandler.handleMove(msgArray[2], msgArray[3]);

        verify(serverMessageHandler.getCurrentSession(), times(1)).analyzeMove(1, 1);
    }

    @Test
    public void testHandleEndDecision()
    {
        String decision = "Accepted";

        Session session = mock(Session.class);
        sessions.add(session);
        serverMessageHandler.setCurrentSession(mock(Session.class));
        serverMessageHandler.handleEndDecision(decision);
        verify(serverMessageHandler.getCurrentSession(), times(1)).setOnePlayerAgreedToEnd(true);

    }
}