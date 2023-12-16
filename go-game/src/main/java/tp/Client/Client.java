package tp.Client;

import java.io.*;
import java.net.*;

public class Client {
    public void run() {
        try (Socket socket = new Socket("localhost", 8000);
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String messageToSend = "Hello Server!";
            output.println(messageToSend);

            String serverResponse = input.readLine();
            System.out.println("Received from server: " + serverResponse);

            System.out.println("Client run");
        } catch (IOException e) {
            System.out.println("Client exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void stop() {

    }
}