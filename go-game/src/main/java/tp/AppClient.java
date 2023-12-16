package tp;

import tp.Client.Client;

import java.io.IOException;

/**
 * App Client runs the client
 *
 */
public class AppClient {
    public static void main( String[] args ) throws IOException {
        Client client = new Client();
        System.out.println( "Client run" );
        client.run();

        client.stop();
        System.out.println( "Client stop" );

    }
}
