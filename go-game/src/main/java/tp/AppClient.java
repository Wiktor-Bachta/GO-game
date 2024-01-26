package tp;

import tp.Client.GUI.ClientGUI;

import java.io.IOException;

/**
 * App Client runs the client
 */
public class AppClient {
    public static void main(String[] args) throws IOException { 
        ClientGUI clientGUI = new ClientGUI();
        clientGUI.run();
    }
}
