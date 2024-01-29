package tp;

import java.io.IOException;

import tp.client.GUI.ClientGUI;

/**
 * App Client runs the client
 */
public class AppClient {
    public static void main(String[] args) throws IOException { 
        ClientGUI clientGUI = new ClientGUI();
        clientGUI.run();
    }
}
