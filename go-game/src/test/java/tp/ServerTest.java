package tp;

import org.junit.Test;
import tp.Server.Server;

public class ServerTest {
    @Test
    public void testServer() {
        Server server = new Server();
        server.stop();
        assert(!server.isRunning());
    }
}
