package tp.Connection;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class Connection {
    private final Socket serverSocket;
    private final PrintWriter output;
    private final BufferedReader input;

    public Connection(Socket serverSocket, PrintWriter output, BufferedReader input) {
        this.serverSocket = serverSocket;
        this.output = output;
        this.input = input;
    }
}
