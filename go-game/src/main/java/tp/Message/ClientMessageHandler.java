package tp.Message;

import tp.Client.Client;
import tp.Client.ClientState;
import tp.Client.GUI.ClientColor;
import tp.Game.StoneState;

import java.io.IOException;

public class ClientMessageHandler implements MessageHandler {

    /**
     * Client wont return a response after receiving a message, it will send message
     * only with the move or quit
     */
    private Client client;

    public ClientMessageHandler(Client client) {
        this.client = client;
    }

    @Override
    public void handleMessage(Message message) throws IOException {
        String msg = message.getMessage();
        String[] msgArray = msg.split(";");
        String msgType = msgArray[0];

        switch (msgType) {
            case "Launch":
                handleLaunch(msgArray);
                break;
            case "Disconnect":
                handleDisconnect();
                break;
            case "Move":
                handleMove(msgArray);
                break;
            case "Pass":
                handlePass(msgArray);
                break;
            case "Chat":
                handleChat(msgArray);
                break;
            case "Error":
                handleError();
                break;
            case "Wait":
                handleWait();
                break;
            case "EndDecision":
                handleEndDecision(msgArray);
                break;
            case "EndGame":
                handleEndGame(msgArray);
                break;
            case "Replay":
                handleReplay(msgArray);
                break;
            default:
                System.out.println(msg);
                System.out.println("Unknown message type");
                break;
        }
    }

    private void handleReplay(String[] msgArray) {
        int moveNumber = Integer.parseInt(msgArray[1]);
        switch (msgArray[2]) {
            case "Move":
                // black moves
                if (moveNumber % 2 == 1) {
                    client.getClientGUI().placeMove(Integer.parseInt(msgArray[3]), Integer.parseInt(msgArray[4]),
                            StoneState.BLACK);
                } else {
                    client.getClientGUI().placeMove(Integer.parseInt(msgArray[3]), Integer.parseInt(msgArray[4]),
                            StoneState.WHITE);
                }
                break;
            case "Remove":
                client.getClientGUI().clearMove(Integer.parseInt(msgArray[3]), Integer.parseInt(msgArray[4]));
                break;
            case "Pass":
                break;
            case "Done":
                client.getClientGUI().resetReplay();
                break;
        }
    }

    private void handleEndGame(String[] msgArray) {
        String result = "";
        switch (msgArray[1]) {
            case "W":
                result = "You Won!";
                break;
            case "L":
                result = "You lost.";
                break;
            case "D":
                result = "It's a draw.";
                break;
            case "SW":
                result = "Win by opponent surrender.";
                break;
            case "SL":
                result = "Loss by surrender.";
                break;
        }
        client.getClientGUI().endGame(result, Integer.parseInt(msgArray[2]), Integer.parseInt(msgArray[3]));
    }

    private void handleEndDecision(String[] msgArray) {
        switch (msgArray[1]) {
            case "Declined":
                if (msgArray[2].equals("Move")) {
                    client.setMove();
                    client.getClientGUI().getSidePanelGUI().hideEndGameProposition();
                } else {
                    client.setWait();
                }
                break;

            case "Accepted":
                break;
        }
    }

    private void handleChat(String[] msgArray) {
        switch (msgArray[1]) {
            case "Player":
                client.getClientGUI().sendPlayerChat(msgArray[2]);
                break;

            case "Opponent":
                client.getClientGUI().senOpponentChat(msgArray[2]);
                break;
        }
    }

    private void handleLaunch(String msgArray[]) throws IOException {
        switch (msgArray[1]) {
            case "Start":
                client.setCurrentSessionID(msgArray[2]);
                int size = Integer.parseInt(msgArray[4]);
                if (msgArray[3].equals("Move")) {
                    client.getClientGUI().setClientColor(ClientColor.BLACK);
                    client.setState(ClientState.DOING_MOVE);
                    client.getClientGUI().displayBoard(size);
                } else {
                    client.setState(ClientState.WAITING_FOR_MOVE);
                    client.getClientGUI().setClientColor(ClientColor.WHITE);
                    client.getClientGUI().displayBoard(size);
                }
                break;
            case "Wait":
                // client.displayMessage("Wait for user to join: ID: " + msgArray[2]);
                client.getClientGUI().getChoiceGUI().displayID("Wait for user to join: ID: " + msgArray[2]);
                break;
        }
    }

    private void handleMove(String msgArray[]) {
        switch (msgArray[1]) {
            case "Confirmed":
                client.getClientGUI().placePlayerMove(Integer.parseInt(msgArray[2]), Integer.parseInt(msgArray[3]));
                client.setState(ClientState.WAITING_FOR_MOVE);
                client.getClientGUI().getSidePanelGUI().labelUpdateWait();
                break;
            case "New":
                client.getClientGUI().placeOpponentMove(Integer.parseInt(msgArray[2]), Integer.parseInt(msgArray[3]));
                client.setState(ClientState.DOING_MOVE);
                client.getClientGUI().getSidePanelGUI().labelUpdateMove();
                break;
            case "Remove":
                client.getClientGUI().clearMove(Integer.parseInt(msgArray[2]), Integer.parseInt(msgArray[3]));
                break;
            case "Invalid":
                break;
        }
    }

    private void handleDisconnect() {
        /**
         * TODO: tu trzeba stowrzyc gui z informacja o rozlaczeniu i moze czekanie np 10
         * sekund na ponowne polaczenie
         * jak nie to wyjscie z gry
         */
    }

    private void handleWait() {
        /**
         * Wait for user to make a move
         */
        System.out.println("Wait for move");
    }

    private void handlePass(String[] msgArray) {
        switch (msgArray[1]) {
            case "Regular":
                client.getClientGUI().getSidePanelGUI().updateSpecialInfo("Opponent passed");
                client.nextState();
                break;
            case "End":
                client.getClientGUI().showEndGameProposition(0, 0);
                client.setState(ClientState.WAITING_FOR_MOVE);
                break;
        }

    }

    private void handleError() {
        /**
         * TODO: tu trzeba stworzyc gui z errorem
         */
        System.out.println("Error");
    }
}
