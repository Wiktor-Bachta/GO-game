package tp.Message;

import tp.Client.Client;
import tp.Client.ClientState;
import tp.Client.GUI.ClientColor;

import java.io.IOException;

public class ClientMessageHandler {

    /**
     * Client wont return a response after receiving a message, it will send message
     * only with the move or quit
     */
    private Client client;

    public ClientMessageHandler(Client client) {
        this.client = client;
    }

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
            case "Surrender":
                handleSurrender(msgArray[1]);
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
            default:
                System.out.println(msg);
                System.out.println("Unknown message type");
                break;
        }

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
                client.getGame().setId(msgArray[2]);

                if (msgArray[3].equals("Move")) {
                    client.getClientGUI().setClientColor(ClientColor.BLACK);
                    client.setState(ClientState.DOING_MOVE);
                    client.getClientGUI().displayBoard();
                } else {
                    client.setState(ClientState.WAITING_FOR_MOVE);
                    client.getClientGUI().setClientColor(ClientColor.WHITE);
                    client.getClientGUI().displayBoard();
                }
                break;
            case "Error":
                client.displayError(msgArray[2]);
                // client.getGame().launch(); // Launch again
                break;
            case "Wait":
                // client.displayMessage("Wait for user to join: ID: " + msgArray[2]);
                client.getClientGUI().getChoiceGUI().displayID("Wait for user to join: ID: " + msgArray[2]);

                /*
                 * while(true)
                 * {
                 * Message serverMessage = client.getServerConnection().getResponse();
                 * handleMessage(serverMessage);
                 * break;
                 * }
                 */
                break;
            default:
                client.displayError("Launch: Unknown message type");
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
        // System.out.println("Get Move: " + msgArray[1] + ";" + msgArray[2]);
        // client.setState(ClientState.DOING_MOVE);

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

    private void handleSurrender(String result) {
        /**
         * User surrendered
         */
        if (result.equals("L"))
            System.out.println("You surrendered. You lost!");
        else if (result.equals("W"))
            System.out.println("User surrendered. You won!");
        client.getGame().stopGame();
        /**
         * TODO: tu trzeba stworzyc gui z informacja o wygranej i moze propozycja
         * rewanzu albo wyjscie z gry
         */
    }

    private void handleError() {
        /**
         * TODO: tu trzeba stworzyc gui z errorem
         */
        System.out.println("Error");
    }
}
