package tp.Message;

public class ClientMessageHandler {

    /**
     * Client wont return a response after receiving a message, it will send message only with the move or quit
     */
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

    }

    private void handleLaunch(String msgArray[])
    {
        switch (msgArray[1]) {
            case "Start":
                //TODO: display the board and start the game
                System.out.println("Start Game");
                break;
            case "Error":
                //TODO: display the error message
                System.out.println("Error:"+msgArray[2]);
                break;
            case "Wait":
                //TODO: display the ID to share with user
                System.out.println("Wait for user to join: ID="+msgArray[2]);
                break;
            default:
                System.out.println("Launch: Unknown message type");
                break;
        }

    }
}
