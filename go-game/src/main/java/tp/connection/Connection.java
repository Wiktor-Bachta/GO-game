package tp.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import tp.message.Message;

public abstract class Connection {
    protected final Socket socket;
    protected final PrintWriter output;
    protected final BufferedReader input;

    public Connection(String localhost, int port) throws IOException {
        this.socket = new Socket(localhost, port);
        this.output = new PrintWriter(socket.getOutputStream());
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.output = new PrintWriter(socket.getOutputStream());
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public abstract Message getResponse() throws IOException;
    public abstract void sendMessage(Message message) throws IOException;
}
