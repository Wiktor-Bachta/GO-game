package tp;

import org.junit.Before;
import org.junit.Test;

import tp.client.Client;
import tp.client.ClientState;
import tp.client.GUI.ClientGUI;
import tp.client.GUI.Board.BoardGUI;

import static org.mockito.Mockito.*;

public class BoardGuiTest {

    private Client client;
    private BoardGUI boardGUI;

    @Before
    public void setup() {
        client = mock(Client.class);
        boardGUI = new BoardGUI(client, 19);
    }

    @Test
    public void testSendMessage() {
        // Stub the getState method to return ClientState.DOING_MOVE
        when(client.getState()).thenReturn(ClientState.DOING_MOVE);

        boardGUI.sendMessage("1;1");

        verify(client, times(1)).sendMessage("Move;1;1");
    }
}
