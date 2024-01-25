package tp.Message;

import tp.Database.DatabaseFacade;
import tp.Game.Move;
import tp.Game.SquareState;
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
     * Server automatically handles the message and returns a response, it doesnt
     * wait for anything
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
                response = handleChat(msgArray[1], msgArray[2]);
                break;
            case "Error":
                System.out.println("Error");
                break;
            case "EndDecision":
                handleEndDecision(msgArray);
                break;
            default:
                System.out.println("Unknown message type");
                break;
        }
        return new Message(response);
    }

    private void handleEndDecision(String[] msgArray) {
        Session s;
        try {
            s = getSession(msgArray[1]);
            switch (msgArray[2]) {
                case "Accepted":
                    if (s.getOnePlayerAgreedToEnd() == true) {
                        int playerPoints = getPlayerPoints(s);
                        int opponentPoints = getOpponentPoints(s);
                        if (playerPoints > opponentPoints) {
                            sendToPlayer("EndGame;W;" + playerPoints + ";" + opponentPoints);
                            sendToOpponent(msgArray[1], "EndGame;L;" + opponentPoints + ";" + playerPoints);
                        } else if (playerPoints == opponentPoints) {
                            sendToBothPlayers(msgArray[1], "EndGame;D;" + playerPoints + ";" + playerPoints);
                        } else {
                            sendToPlayer("EndGame;L;" + playerPoints + ";" + opponentPoints);
                            sendToOpponent(msgArray[1], "EndGame;W;" + opponentPoints + ";" + playerPoints);
                        }
                    } else {
                        s.setOnePlayerAgreedToEnd(true);
                    }
                    break;
                case "Declined":
                    s.setOnePlayerAgreedToEnd(false);
                    sendToPlayer("EndDecision;Declined;Wait");
                    sendToOpponent(msgArray[1], "EndDecision;Declined;Move");
                    break;
            }
        } catch (Exception e) {

        }
    }

    private String handleLaunch(String gameType, String opponent) throws IOException {
        String response = "";

        switch (gameType) {
            case "Create":
                Session session = new Session(clientHandler);
                String sessionID = session.getID();

                if (opponent.equals("bot")) {
                    System.out.println("Play with bot");
                    session.addPlayer2();
                    sessions.add(session);

                    // randomize who goes first - black always goes first
                    int random = (int) (Math.random() * 2);
                    if (random == 0) {
                        response = "Launch;" + "Start;" + sessionID + ";" + "Wait";
                        session.getPlayer1().getClientConnection().sendMessage(new Message(response));
                        response = "Launch;" + "Start;" + sessionID + ";" + "Move";
                    } else {
                        response = "Launch;" + "Start;" + sessionID + ";" + "Move";
                        session.getPlayer2().getClientConnection().sendMessage(new Message(response));
                        response = "Launch;" + "Start;" + sessionID + ";" + "Wait";
                    }

                } else {
                    System.out.println("Play with user");
                    // TODO: display ID to share with user
                    sessions.add(session);
                    response = "Launch;" + "Wait;" + sessionID + ";";
                }
                break;
            case "Join":
                System.out.println("Join");
                String sessionIDToJoin = opponent;

                boolean sessionExists = false;
                for (Session s : sessions) {
                    if (s.getID().equals(sessionIDToJoin)) { // name collision bc opponent is gameID for join msg and
                                                             // opponent type for create msg
                        if (s.isAbleToJoin()) {
                            s.addPlayer2(clientHandler);

                            // randomize who goes first - black always goes first
                            double random = Math.random();

                            if (random < 0.5) {
                                response = "Launch;" + "Start;" + sessionIDToJoin + ";" + "Wait";
                                s.getPlayer1().getClientConnection().sendMessage(new Message(response));
                                s.setFirstPlayer(s.getPlayer2());
                                response = "Launch;" + "Start;" + sessionIDToJoin + ";" + "Move";
                            } else {
                                response = "Launch;" + "Start;" + sessionIDToJoin + ";" + "Move";
                                s.getPlayer1().getClientConnection().sendMessage(new Message(response));
                                s.setFirstPlayer(s.getPlayer1());
                                response = "Launch;" + "Start;" + sessionIDToJoin + ";" + "Wait";
                            }

                            System.out.println("Session joined");
                        } else {
                            System.out.println("Session is full");
                            response = "Launch;" + "Error;Session is full;";
                        }
                        sessionExists = true;
                    }
                }
                if (!sessionExists) {
                    System.out.println("Session does not exist");
                    response = "Launch;" + "Error;Session does not exist;";
                }
                break;
            default:
                System.out.println("Unknown launch message");
                response = "Launch;" + "Error;Unknown launch message;";
                break;
        }
        return response;
    }

    private String handleMove(String msgArray[]) throws IOException {
        String x = msgArray[1];
        String y = msgArray[2];
        String sessionID = msgArray[3];

        Move move = new Move(x + ";" + y);

        for (Session s : sessions) {
            if (s.getID().equals(sessionID)) {
                boolean valid = s.analyzeMove(move);
                if (valid) {
                    DatabaseFacade.addMoveToDatabase(sessionID, s.getAndUpdateMoveCount(), Integer.parseInt(x),
                            Integer.parseInt(y));
                    if (s.getPlayer1().equals(clientHandler)) {
                        Message response = new Message("Move;Confirmed;" + msgArray[1] + ";" + msgArray[2] + ";");
                        s.getPlayer1().getClientConnection().sendMessage(response);
                        response = new Message("Move;New;" + msgArray[1] + ";" + msgArray[2] + ";");
                        s.getPlayer2().getClientConnection().sendMessage(response);
                    } else {
                        Message response = new Message("Move;Confirmed;" + msgArray[1] + ";" + msgArray[2] + ";");
                        s.getPlayer2().getClientConnection().sendMessage(response);
                        response = new Message("Move;New;" + msgArray[1] + ";" + msgArray[2] + ";");
                        s.getPlayer1().getClientConnection().sendMessage(response);
                    }
                } else {
                    Message response = new Message("Move;Invalid;");
                    clientHandler.getClientConnection().sendMessage(response);
                }
            }
        }

        return "Wait";
    }

    private String handlePass(String sessionID) throws IOException {
        Session s;
        try {
            s = getSession(sessionID);
            s.skipTurn();
            DatabaseFacade.addMoveToDatabase(sessionID, s.getAndUpdateMoveCount());
            if (s.getPassEndsGame() == true) {
                sendToBothPlayers(sessionID, "Pass;End");
                s.setPassEndsGame(false);
            } else {
                s.setPassEndsGame(true);
                sendToOpponent(sessionID, "Pass;Regular");
            }
        } catch (Exception e) {
        }
        return "Wait";
    }

    private String handleChat(String sessionID, String message) throws IOException {
        sendToPlayer("Chat;Player;" + message);
        sendToOpponent(sessionID, "Chat;Opponent;" + message);
        return "Wait";
    }

    private String handleSurrender(String sessionID) {
        Session s;
        try {
            s = getSession(sessionID);
            int playerPoints = getPlayerPoints(s);
            int opponentPoints = getOpponentPoints(s);
            sendToPlayer("EndGame;SL;" + playerPoints + ";" + opponentPoints);
            sendToOpponent(sessionID, "EndGame;SW;" + opponentPoints + ";" + playerPoints);
        } catch (Exception e) {

        }
        return "Wait"; // L - lose
    }

    private void sendToPlayer(String message) {
        try {
            clientHandler.getClientConnection().sendMessage(new Message(message));
        } catch (IOException e) {
        }
    }

    private void sendToOpponent(String sessionID, String message) {
        for (Session s : sessions) {
            if (s.getID().equals(sessionID)) {
                try {
                    if (s.getPlayer1().equals(clientHandler)) {
                        s.getPlayer2().getClientConnection().sendMessage(new Message(message));
                    } else {
                        s.getPlayer1().getClientConnection().sendMessage(new Message(message));
                    }
                } catch (IOException e) {
                }
            }
        }
    }

    private void sendToBothPlayers(String sessionID, String message) {
        sendToPlayer(message);
        sendToOpponent(sessionID, message);
    }

    private Session getSession(String sessionID) throws Exception {
        for (Session s : sessions) {
            if (s.getID().equals(sessionID)) {
                return s;
            }
        }
        throw new Exception();
    }

    private int getPlayerPoints(Session s) {
        if (s.isFirstPlayer(clientHandler)) {
            return s.getPoints(SquareState.BLACK);
        }
        return s.getPoints(SquareState.WHITE);
    }

    private int getOpponentPoints(Session s) {
        if (s.isFirstPlayer(clientHandler)) {
            return s.getPoints(SquareState.WHITE);
        }
        return s.getPoints(SquareState.BLACK);
    }
}
