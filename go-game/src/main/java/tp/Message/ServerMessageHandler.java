package tp.Message;

import tp.Database.DatabaseFacade;
import tp.Game.SquareState;
import tp.Server.ClientHandler;
import tp.Server.Session;

import java.io.IOException;
import java.util.List;

public class ServerMessageHandler {

    List<Session> sessions;
    private Session currentSession;
    ClientHandler player;

    public ServerMessageHandler(List<Session> sessions, ClientHandler player) {
        this.sessions = sessions;
        this.player = player;
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
                response = handleMove(msgArray[1], msgArray[2]);
                break;
            case "Pass":
                response = handlePass();
                break;
            case "Surrender":
                response = handleSurrender();
                break;
            case "Chat":
                response = handleChat(msgArray[1]);
                break;
            case "Error":
                System.out.println("Error");
                break;
            case "EndDecision":
                handleEndDecision(msgArray[1]);
                break;
            default:
                System.out.println("Unknown message type");
                break;
        }
        return new Message(response);
    }

    private void handleEndDecision(String decision) {
        if ("Accepted".equals(decision)) {
            if (currentSession.getOnePlayerAgreedToEnd() == true) {
                int playerPoints = getPlayerPoints();
                int opponentPoints = getOpponentPoints();
                if (playerPoints > opponentPoints) {
                    sendToPlayer("EndGame;W;" + playerPoints + ";" + opponentPoints);
                    sendToOpponent("EndGame;L;" + opponentPoints + ";" + playerPoints);
                } else if (playerPoints == opponentPoints) {
                    sendToBothPlayers("EndGame;D;" + playerPoints + ";" + playerPoints);
                } else {
                    sendToPlayer("EndGame;L;" + playerPoints + ";" + opponentPoints);
                    sendToOpponent("EndGame;W;" + opponentPoints + ";" + playerPoints);
                }
                // s.getDatabaseFacade().close();
            } else {
                currentSession.setOnePlayerAgreedToEnd(true);
            }
        } else {
            currentSession.setOnePlayerAgreedToEnd(false);
            sendToPlayer("EndDecision;Declined;Wait");
            sendToOpponent("EndDecision;Declined;Move");
        }
    }

    private String handleLaunch(String gameType, String opponent) throws IOException {
        String response = "";

        switch (gameType) {
            case "Create":
                Session session = new Session(player);
                String sessionID = session.getID();

                if ("bot".equals(opponent)) {
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
                    sessions.add(session);
                    currentSession = session;
                    sendToPlayer("Launch;Wait;" + sessionID);
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
                            s.addPlayer2(player);
                            currentSession = s;
                            // s.getDatabaseFacade().open();

                            // randomize who goes first - black always goes first
                            double random = Math.random();

                            if (random < 0.5) {
                                sendToPlayer("Launch;" + "Start;" + sessionIDToJoin + ";" + "Wait");
                                sendToOpponent("Launch;" + "Start;" + sessionIDToJoin + ";" + "Move");
                            } else {
                                sendToPlayer("Launch;" + "Start;" + sessionIDToJoin + ";" + "Move");
                                sendToOpponent("Launch;" + "Start;" + sessionIDToJoin + ";" + "Wait");
                                currentSession.swapPlayers();
                            }

                            System.out.println("Session joined");
                        } else {
                            System.out.println("Session is full");
                            response = "Launch;" + "Error;Session is full;";
                        }
                        sessionExists = true;
                        break;
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
        return "Wait";
    }

    private String handleMove(String xString, String yString) {
        int x = Integer.parseInt(xString);
        int y = Integer.parseInt(yString);

        boolean valid = currentSession.analyzeMove(x, y);
        if (valid) {
            // s.getDatabaseFacade().addMoveToDatabase(sessionID, s.getAndUpdateMoveCount(),
            sendToPlayer("Move;Confirmed;" + x + ";" + y + ";");
            sendToOpponent("Move;New;" + x + ";" + y + ";");
        } else {
            sendToPlayer("Move;Invalid;");
        }
        return "Wait";
    }

    private String handlePass() {
        currentSession.skipTurn();
        // s.getDatabaseFacade().addMoveToDatabase(sessionID,
        // s.getAndUpdateMoveCount());
        if (currentSession.getPassEndsGame() == true) {
            sendToBothPlayers("Pass;End");
            currentSession.setPassEndsGame(false);
        } else {
            currentSession.setPassEndsGame(true);
            sendToOpponent("Pass;Regular");
        }
        return "Wait";
    }

    private String handleChat(String message) throws IOException {
        sendToPlayer("Chat;Player;" + message);
        sendToOpponent("Chat;Opponent;" + message);
        return "Wait";
    }

    private String handleSurrender() {
        int playerPoints = getPlayerPoints();
        int opponentPoints = getOpponentPoints();
        sendToPlayer("EndGame;SL;" + playerPoints + ";" + opponentPoints);
        sendToOpponent("EndGame;SW;" + opponentPoints + ";" + playerPoints);

        return "Wait";
    }

    private void sendToPlayer(String message) {
        try {
            player.getClientConnection().sendMessage(new Message(message));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void sendToOpponent(String message) {
        try {
            if (currentSession.getPlayer1().equals(player)) {
                currentSession.getPlayer2().getClientConnection().sendMessage(new Message(message));
            } else {
                currentSession.getPlayer1().getClientConnection().sendMessage(new Message(message));
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void sendToBothPlayers(String message) {
        sendToPlayer(message);
        sendToOpponent(message);
    }

    private int getPlayerPoints() {
        if (currentSession.getPlayer1() == player) {
            return currentSession.getPoints(SquareState.BLACK);
        }
        return currentSession.getPoints(SquareState.WHITE);
    }

    private int getOpponentPoints() {
        if (currentSession.getPlayer1() == player) {
            return currentSession.getPoints(SquareState.WHITE);
        }
        return currentSession.getPoints(SquareState.BLACK);
    }
}
