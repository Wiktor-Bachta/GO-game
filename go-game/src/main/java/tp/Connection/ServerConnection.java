package tp.Connection;

import tp.Connection.Connection;
import tp.Game.Move;
import tp.Message.Message;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
