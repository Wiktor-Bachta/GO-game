package tp.message;

import tp.database.DatabaseFacade;
import tp.gamelogic.StoneState;
import tp.server.Bot;
import tp.server.ClientHandler;
import tp.server.Session;

import java.io.IOException;
import java.util.List;

import org.hibernate.type.IntegerType;

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
    public void handleMessage(Message message) throws IOException {
        String msg = message.getMessage();
        String[] msgArray = msg.split(";");
        String msgType = msgArray[0];
        switch (msgType) {
            case "Launch":
                handleLaunch(msgArray);
                break;
            case "Move":
                handleMove(msgArray[1], msgArray[2]);
                break;
            case "Pass":
                handlePass();
                break;
            case "Surrender":
                handleSurrender();
                break;
            case "Chat":
                handleChat(msgArray[1]);
                break;
            case "Error":
                System.out.println("Error");
                break;
            case "EndDecision":
                handleEndDecision(msgArray[1]);
                break;
            case "Disconnect":
                handleDisconnect();
                break;
            case "Replay":
                handleReplay(msgArray[1]);
                break;
            default:
                System.out.println("Unknown message type");
                break;
        }
    }

    private void handleReplay(String moveNumber) throws IOException {
        int number = Integer.parseInt(moveNumber);
        if (number == 1) {
            currentSession.loadGameHistory();
        }
        for (String move : currentSession.getMoves(number)) {
            sendToPlayer("Replay;" + number + ";" + move);
        }
        if (currentSession.getMoves(number).isEmpty()) {
            sendToPlayer("Replay;0;Done");
        }
    }

    private void handleDisconnect() {
        player.stopGame();
    }

    public void handleEndDecision(String decision) {
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
                currentSession.getDatabaseFacade().close();
            } else {
                sendToOpponent("EndDecision;Accepted");
                currentSession.setOnePlayerAgreedToEnd(true);
            }
        } else {
            currentSession.setOnePlayerAgreedToEnd(false);
            sendToPlayer("EndDecision;Declined;Wait");
            sendToOpponent("EndDecision;Declined;Move");
            currentSession.setCurrentStoneState(getOpponentStoneState());
        }
    }

    public void handleLaunch(String[] msgArray) throws IOException {
        String gameType = msgArray[1];
        String opponent = msgArray[2];
        switch (gameType) {
            case "Create":
                Session session = new Session(player);
                String sessionID = session.getID();
                int size = Integer.parseInt(msgArray[3]);
                session.setBoardSize(size);

                if ("bot".equals(opponent)) {
                    currentSession = session;
                    System.out.println("Play with bot");
                    sessions.add(session);
                    new Bot(sessionID, size).run();
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
                            s.getDatabaseFacade().open();
                            size = s.getBoardSize();

                            // randomize who goes first - black always goes first
                            double random = Math.random();

                            if (random < 0.5) {
                                sendToPlayer("Launch;" + "Start;" + sessionIDToJoin + ";" + "Wait;" + size);
                                sendToOpponent("Launch;" + "Start;" + sessionIDToJoin + ";" + "Move;" + size);
                            } else {
                                sendToPlayer("Launch;" + "Start;" + sessionIDToJoin + ";" + "Move;" + size);
                                sendToOpponent("Launch;" + "Start;" + sessionIDToJoin + ";" + "Wait;" + size);
                                currentSession.swapPlayers();
                            }
                            s.createMoveAnalyzer();
                        } else {
                            sendToPlayer("Launch;Error;Session is full;");
                        }
                        sessionExists = true;
                        break;
                    }
                }
                if (!sessionExists) {
                    sendToPlayer("Launch;Error;Session does not exist;");
                }
                break;
        }
    }

    public void handleMove(String xString, String yString) {
        int x = Integer.parseInt(xString);
        int y = Integer.parseInt(yString);

        boolean valid = currentSession.analyzeMove(x, y);
        if (valid) {
            currentSession.getDatabaseFacade().addMoveToDatabase(currentSession.getID(),
                    currentSession.getAndUpdateMoveCount(), x, y);
            sendToPlayer("Move;Confirmed;" + x + ";" + y + ";");
            sendToOpponent("Move;New;" + x + ";" + y + ";");
        } else {
            sendToPlayer("Move;Invalid;");
        }
    }

    public void handlePass() {
        currentSession.skipTurn();
        currentSession.getDatabaseFacade().addMoveToDatabase(currentSession.getID(),
                currentSession.getAndUpdateMoveCount());
        if (currentSession.getPassEndsGame() == true) {
            int playerPoints = getPlayerPoints();
            int opponentPoints = getOpponentPoints();
            sendToPlayer("Pass;End;" + playerPoints + ";" + opponentPoints);
            sendToOpponent("Pass;End;" + opponentPoints + ";" + playerPoints);
            currentSession.setPassEndsGame(false);
        } else {
            currentSession.setPassEndsGame(true);
            sendToOpponent("Pass;Regular");
        }
    }

    public void handleChat(String message) throws IOException {
        sendToPlayer("Chat;Player;" + message);
        sendToOpponent("Chat;Opponent;" + message);
    }

    public void handleSurrender() {
        int playerPoints = getPlayerPoints();
        int opponentPoints = getOpponentPoints();
        sendToPlayer("EndGame;SL;" + playerPoints + ";" + opponentPoints);
        sendToOpponent("EndGame;SW;" + opponentPoints + ";" + playerPoints);
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
            return currentSession.getPoints(StoneState.BLACK);
        }
        return currentSession.getPoints(StoneState.WHITE);
    }

    private int getOpponentPoints() {
        if (currentSession.getPlayer1() == player) {
            return currentSession.getPoints(StoneState.WHITE);
        }
        return currentSession.getPoints(StoneState.BLACK);
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public void handleError(Object any) {
    }

    public void setCurrentSession(Session session) {
        this.currentSession = session;
    }

    public ClientHandler getClientHandler() {
        return player;
    }

    private StoneState getOpponentStoneState() {
        if (currentSession.getPlayer1() == player) {
            return StoneState.WHITE;
        }
        return StoneState.BLACK;
    }
}
