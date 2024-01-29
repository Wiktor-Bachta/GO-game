package tp;

import org.junit.Before;
import org.junit.Test;

import tp.client.Client;
import tp.client.ClientState;
import tp.client.GUI.ClientGUI;
import tp.client.GUI.SidePanelGUI;
import tp.message.ClientMessageHandler;
import tp.message.Message;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class ClientMessageHandlerTest {
    private ClientMessageHandler clientMessageHandler;
    private Client client;

    @Before
    public void setUp() {
        client = mock(Client.class);
        ClientGUI clientGUI = mock(ClientGUI.class);
        when(client.getClientGUI()).thenReturn(clientGUI);
        when(clientGUI.getSidePanelGUI()).thenReturn(mock(SidePanelGUI.class));
        clientMessageHandler = new ClientMessageHandler(client);
    }

    @Test
    public void testHandleMessageLaunch() throws IOException {
        Message message = new Message("Launch;Start;1;Move;1;1");
        clientMessageHandler.handleMessage(message);
        verify(client, times(1)).setState(ClientState.DOING_MOVE);
    }

    @Test
    public void testHandleReplay() throws IOException {
        Message message = new Message("Replay;2;Move;1;1");
        clientMessageHandler.handleMessage(message);
        verify(client, times(1)).getClientGUI();
    }

    @Test
    public void testHandleMessageMove() throws IOException {
        Message message = new Message("Move;Confirmed;1;1");
        clientMessageHandler.handleMessage(message);
        doNothing().when(client).nextState();
        verify(client, times(2)).getClientGUI();
        //verify(client, times(1)).setState(ClientState.WAITING_FOR_MOVE);
    }

    @Test
    public void testHandleMessageEndDecision() throws IOException {
        Message message = new Message("EndDecision;Accepted");
        clientMessageHandler.handleMessage(message);
        verify(client, times(0)).setState(ClientState.WAITING_FOR_MOVE);
    }

    @Test
    public void testHandleMessageEndGame() throws IOException {
        Message message = new Message("EndGame;Win;10;20");
        doNothing().when(client).stop();
        clientMessageHandler.handleMessage(message);
        verify(client, times(1)).getClientGUI();
    }

    @Test
    public void testHandleMessageChat() throws IOException {
        Message message = new Message("Chat;Player;Hello");
        clientMessageHandler.handleMessage(message);
        verify(client, times(1)).getClientGUI();
    }

    @Test
    public void testHandleMessageError() throws IOException {
        Message message = new Message("Error;Invalid move");
        clientMessageHandler.handleMessage(message);
        verify(client, times(0)).displayMessage("Error: Invalid move");
    }
}