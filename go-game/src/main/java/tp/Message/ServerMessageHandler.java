package tp.Message;

import tp.Game.Move;
import tp.Server.ClientHandler;
import tp.Server.Session;

import java.io.IOException;
import java.util.List;

public class ServerMessageHandler {

    List<Session> sessions;
    ClientHandler clientHandler;
    public ServerMessageHandler(List<Session> sessions, ClientHandler clientHandler) {
        this.sessions = sessions;
        this.clientHandler = clientHandler;
    }

    /**
     * Server automatically handles the message and returns a response, it doesnt wait for anything
     *
     */
    public Message handleMessage(Message message) throws IOException {
        String msg = message.getMessage();
        String[] msgArray = msg.split(";");
        String msgType = msgArray[0];
        String response = "";
        switch (msgType) {
            case "Launch":
                response = handleLaunch(msgArray[1], msgArray[2]);
                break;
            case "Disconnect":
                System.out.println("Disconnect");
                break;
            case "Move":
                response = handleMove(msgArray);
                break;
            case "Pass":
                response = handlePass(msgArray[1]);
                break;
            case "Surrender":
                response = handleSurrender(msgArray[1]);
                break;
            case "Chat":
                System.out.println("Chat");
                break;
            case "Error":
                System.out.println("Error");
                break;
            default:
                System.out.println("Unknown message type");
                break;
        }
        return new Message(response);
    }

    private String handleLaunch(String gameType, String opponent) throws IOException {
        String response="";

        switch (gameType) {
            case "Create":
                Session session = new Session(clientHandler);
                String sessionID = session.getID();

                if(opponent.equals("bot")) {
                    System.out.println("Play with bot");
                    session.addPlayer2();
                    sessions.add(session);

                    // randomize who goes first - black always goes first
                    int random = (int)(Math.random() * 2);
                    if(random == 0) {
                        response = "Launch;"+"Start;"+sessionID+";"+"Wait";
                        session.getPlayer1().getClientConnection().sendMessage(new Message(response));
                        response = "Launch;"+"Start;"+sessionID+";"+"Move";
                    }
                    else {
                        response = "Launch;"+"Start;"+sessionID+";"+"Move";
                        session.getPlayer2().getClientConnection().sendMessage(new Message(response));
                        response = "Launch;"+"Start;"+sessionID+";"+"Wait";
                    }

                } else {
                    System.out.println("Play with user");
                    //TODO: display ID to share with user
                    sessions.add(session);
                    response = "Launch;"+"Wait;"+sessionID+";";
                }
                break;
            case "Join":
                System.out.println("Join");
                String sessionIDToJoin = opponent;

                boolean sessionExists = false;
                for(Session s : sessions) {
                    if(s.getID().equals(sessionIDToJoin)) {        // name collision bc opponent is gameID for join msg and opponent type for create msg
                        if(s.isAbleToJoin()) {
                            s.addPlayer2(clientHandler);

                            // randomize who goes first - black always goes first
                            double random = Math.random();

                            if(random < 0.5) {
                                response = "Launch;"+"Start;"+sessionIDToJoin+";"+"Wait";
                                s.getPlayer1().getClientConnection().sendMessage(new Message(response));
                                response = "Launch;"+"Start;"+sessionIDToJoin+";"+"Move";
                            }
                            else {
                                response = "Launch;"+"Start;"+sessionIDToJoin+";"+"Move";
                                s.getPlayer1().getClientConnection().sendMessage(new Message(response));
                                response = "Launch;"+"Start;"+sessionIDToJoin+";"+"Wait";
                            }

                            System.out.println("Session joined");
                        } else {
                            System.out.println("Session is full");
                            response = "Launch;"+"Error;Session is full;";
                        }
                        sessionExists = true;
                    }
                }
                if(!sessionExists) {
                    System.out.println("Session does not exist");
                    response = "Launch;"+"Error;Session does not exist;";
                }
                break;
            default:
                System.out.println("Unknown launch message");
                response = "Launch;"+"Error;Unknown launch message;";
                break;
        }
        return response;
    }

    private String handleMove(String msgArray[]) throws IOException {
        String x = msgArray[1];
        String y = msgArray[2];
        String sessionID = msgArray[3];

        Move move = new Move(x+";"+y);


        for(Session s : sessions) {
            if(s.getID().equals(sessionID)) {
                Message response = s.analyzeMove(move);
                if(s.getPlayer1().equals(clientHandler)) {
                    s.getPlayer2().getClientConnection().sendMessage(response);
                } else {
                    s.getPlayer1().getClientConnection().sendMessage(response);
                }
            }
        }

        return "Wait";
    }

    private String handlePass(String sessionID) throws IOException {
        for(Session s : sessions) {
            if(s.getID().equals(sessionID)) {
                if(s.getPlayer1().equals(clientHandler)) {
                    s.getPlayer2().getClientConnection().sendMessage(new Message("Pass"));
                } else {
                    s.getPlayer1().getClientConnection().sendMessage(new Message("Pass"));
                }
            }
        }
        return "Wait";
    }

    private String handleSurrender(String sessionID) throws IOException {
        for(Session s : sessions) {
            if(s.getID().equals(sessionID)) {
                if(s.getPlayer1().equals(clientHandler)) {
                    s.getPlayer2().getClientConnection().sendMessage(new Message("Surrender;W"));   // W - win
                } else {
                    s.getPlayer1().getClientConnection().sendMessage(new Message("Surrender;W"));   // W - win
                }
            }
        }
        return "Surrender;L";  // L - lose
    }
}
