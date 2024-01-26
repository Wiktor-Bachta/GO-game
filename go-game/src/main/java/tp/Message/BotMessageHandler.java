package tp.Message;

import java.io.IOException;

import tp.Server.Bot;

public class BotMessageHandler implements MessageHandler {

    private Bot bot;

    public BotMessageHandler(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void handleMessage(Message message) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleMessage'");
    }
    
}
