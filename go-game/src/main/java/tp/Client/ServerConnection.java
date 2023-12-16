package tp.Client;

import tp.Game.Move;
import tp.Message.Message;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnection {
    private final Socket serverSocket;
    private final PrintWriter output;
    private final BufferedReader input;
    public ServerConnection(String localhost, int port) throws IOException {

        this.serverSocket = new Socket(localhost, port);
        this.output = new PrintWriter(serverSocket.getOutputStream());
        this.input = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
    }

    public void sendMessage(Move move) {
        output.println(move.getMove());
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
