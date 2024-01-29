package tp;

import org.junit.Before;
import org.junit.Test;

import tp.message.BotMessageHandler;
import tp.message.Message;
import tp.server.Bot;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class BotMessageHandlerTest {

    private Bot bot;
    private BotMessageHandler botMessageHandler;

    @Before
    public void setup() {
        bot = mock(Bot.class);
        botMessageHandler = new BotMessageHandler(bot);
    }

    @Test
    public void testHandleMessageMove() throws IOException {
        Message message = new Message("Move;Confirmed;1;1");
        botMessageHandler.handleMessage(message);
        verify(bot, times(1)).placeBotMove(1, 1);
    }

    @Test
    public void testHandleMessageEndDecision() throws IOException {
        Message message = new Message("EndDecision;Declined");
        botMessageHandler.handleMessage(message);
        verify(bot, times(1)).sendMessage("Move;" + bot.getMove());
    }

    @Test
    public void testHandleMessageEndGame() throws IOException {
        Message message = new Message("EndGame");
        botMessageHandler.handleMessage(message);
        verify(bot, times(1)).sendMessage("Disconnect");
        verify(bot, times(1)).stop();
    }

    @Test
    public void testHandleMessagePass() throws IOException {
        Message message = new Message("Pass;Regular");
        botMessageHandler.handleMessage(message);
        verify(bot, times(1)).sendMessage("Pass");
    }

    @Test
    public void testHandleMessageLaunch() throws IOException {
        Message message = new Message("Launch;Start;1;Move");
        botMessageHandler.handleMessage(message);
        verify(bot, times(1)).sendMessage("Move;" + bot.getMove());
    }
}