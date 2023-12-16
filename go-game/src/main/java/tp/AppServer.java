package tp;

import tp.Server.Server;

/**
 * App Server runs the server
 *
 */
public class AppServer
{
    public static void main( String[] args )
    {
        Server server = new Server();
        System.out.println( "Server run" );
        server.run();

        server.stop();
        System.out.println( "Server stop" );

    }
}
