package tp.Connection;

import tp.Message.Message;
import java.io.IOException;
import java.net.Socket;

public class ClientConnection extends Connection {
    public ClientConnection(Socket clientSocket) throws IOException {
        super(clientSocket);
    }
    @Override
    public Message getResponse() throws IOException {
        String clientMessage = input.readLine();
        if (clientMessage == null) {
            throw new IOException("Client disconnected");
        }
        return new Message(clientMessage);
    }

    @Override
    public void sendMessage(Message message) throws IOException {
        output.println(message.getMessage());
        output.flush(); // flush the stream to ensure that the data has been written to the stream
    }
}
