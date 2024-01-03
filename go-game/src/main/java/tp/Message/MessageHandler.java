package tp.Message;

import tp.Server.ClientHandler;
import tp.Server.Session;

import java.util.List;
import java.util.Scanner;

public class MessageHandler {

    List<Session> sessions;
    ClientHandler clientHandler;
    public MessageHandler(List<Session> sessions, ClientHandler clientHandler) {
        this.sessions = sessions;
        this.clientHandler = clientHandler;
    }

    public Message handleMessage(Message message) {
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
                System.out.println("Move");
                break;
            case "Pass":
                System.out.println("Pass");
                break;
            case "Surrender":
                System.out.println("Surrender");
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

    private String handleLaunch(String gameType, String opponent) {
        String response="";

        switch (gameType) {
            case "Create":
                Session session = new Session(clientHandler);
                String sessionID = session.getID();

                if(opponent.equals("bot")) {
                    System.out.println("Play with bot");
                    session.addPlayer2();
                    sessions.add(session);

                    response = "Launch;"+"Start;";
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
                            response = "Launch;"+"Start;";
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



}
