package tp.Message;

import tp.Client.Client;
import tp.Client.ClientState;

public class ClientMessageHandler {

    /**
     * Client wont return a response after receiving a message, it will send message only with the move or quit
     */
    private Client client;

    public ClientMessageHandler(Client client) {
        this.client = client;
    }

    public void handleMessage(Message message) {
        String msg = message.getMessage();
        String[] msgArray = msg.split(";");
        String msgType = msgArray[0];

        switch (msgType) {
            case "Launch":
                handleLaunch(msgArray);
                break;
            case "Disconnect":
                System.out.println("Disconnect");
                break;
            case "Move":
                handleMove(msgArray);
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
            case "Wait":
                handleWait();
                break;
            default:
                System.out.println("Unknown message type");
                break;
        }

    }

    private void handleLaunch(String msgArray[]) {
        switch (msgArray[1]) {
            case "Start":
                //TODO: display the board and start the game
                System.out.println("Start Game");
                client.getGame().setId(msgArray[2]);

                if(msgArray[3].equals("Move"))
                    client.setState(ClientState.DOING_MOVE);
                else
                    client.setState(ClientState.WAITING_FOR_MOVE);

                client.displayBoard();
                break;
            case "Error":
                //TODO: display the error message
                System.out.println("Error:" + msgArray[2]);
                client.displayError(msgArray[2]);
                break;
            case "Wait":
                //TODO: display the ID to share with user
                //System.out.println("Wait for user to join: ID: " + msgArray[2]);
                client.displayMessage("Wait for user to join: ID: " + msgArray[2]);
                break;
            default:
                System.out.println("Launch: Unknown message type");
                client.displayError("Launch: Unknown message type");
                break;
        }

    }

    private void handleMove(String msgArray[]) {
        /**
         * TODO: display the board with updated the move
         */
        System.out.println("Get Move: " + msgArray[1] + ";" + msgArray[2]);
        client.setState(ClientState.DOING_MOVE);

    }

    private void handleWait() {
        /**
         *  Wait for user to make a move
         */
        System.out.println("Wait for move");
    }
}
