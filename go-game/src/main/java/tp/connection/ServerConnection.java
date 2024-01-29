package tp.connection;

import java.io.IOException;
import java.net.Socket;

import tp.message.Message;

public class ServerConnection extends Connection {
    public ServerConnection(String localhost, int port) throws IOException {

        super(localhost, port);
    }
    public ServerConnection(Socket socket) throws Exception {
        super(socket);
    }

    public void sendMessage(Message message) {
        output.println(message.getMessage());
        output.flush(); // flush the stream to ensure that the data has been written to the stream

    }

    public Message getResponse() throws IOException {
        String serverMessage = input.readLine();
        if (serverMessage == null) {
            throw new IOException("Server disconnected");
        }
        return new Message(serverMessage);
    }
}
