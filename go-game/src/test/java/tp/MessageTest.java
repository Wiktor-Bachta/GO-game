package tp;

import org.junit.Test;
import tp.Message.Message;

public class MessageTest {
    @Test
    public void testMessage() {
        Message message = new Message("Hello World!");
        assert(message.getMessage().equals("Hello World!"));
    }
}
