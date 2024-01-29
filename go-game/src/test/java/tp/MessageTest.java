package tp;

import org.junit.Test;

import tp.message.Message;

public class MessageTest {
    @Test
    public void testMessage() {
        Message message = new Message("Hello World!");
        assert(message.getMessage().equals("Hello World!"));
    }
}
