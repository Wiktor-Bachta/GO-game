package tp.message;

import java.io.IOException;

public interface MessageHandler {
    void handleMessage(Message message) throws IOException ;
}
