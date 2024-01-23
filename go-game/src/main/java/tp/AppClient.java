package tp;

import tp.Client.Client;
import tp.Client.GUI.ClientGUI;
import tp.Game.GUI.ChoiceGUI;

import java.io.IOException;

/**
 * App Client runs the client
 */
public class AppClient {
    public static void main(String[] args) throws IOException { 
        ClientGUI clientGUI = new ClientGUI();
        clientGUI.run();
        //client.run();

        //client.stop();

    }
}
