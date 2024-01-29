package tp.message;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import tp.server.Bot;

public class BotMessageHandler implements MessageHandler {

    private Bot bot;
    int invalidInARow = 0;

    public BotMessageHandler(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void handleMessage(Message message) throws IOException {
        try {
            // delay
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String msg = message.getMessage();
        String[] msgArray = msg.split(";");
        String msgType = msgArray[0];

        switch (msgType) {
            case "Launch":
                handleLaunch(msgArray);
                break;
            case "Move":
                handleMove(msgArray);
                break;
            case "Pass":
                handlePass(msgArray);
                break;
            case "EndDecision":
                handleEndDecision(msgArray);
                break;
            case "EndGame":
                handleEndGame();
                break;
            case "Chat":
                handleChat(msgArray[1]);
                break;
            default:
                System.out.println("Unknown message type: " + msg);
                break;
        }

    }

    private void handleChat(String sender) {
        if ("Opponent".equals(sender)) {
            bot.sendMessage("Chat;Don't distract me. I am thinking.");
        }
    }

    private void handleMove(String[] msgArray) {
        switch (msgArray[1]) {
            case "Confirmed":
                bot.placeBotMove(Integer.parseInt(msgArray[2]), Integer.parseInt(msgArray[3]));
                invalidInARow = 0;
                break;
            case "New":
                bot.placeOpponentMove(Integer.parseInt(msgArray[2]), Integer.parseInt(msgArray[3]));
                bot.sendMessage("Move;" + bot.getMove());
                break;
            case "Remove":
                bot.clearStone(Integer.parseInt(msgArray[2]), Integer.parseInt(msgArray[3]));
                break;
            case "Invalid":
                if (invalidInARow > 5) {
                    bot.sendMessage("Surrender");
                    bot.sendMessage("Chat;I give up.");
                } else {
                    bot.sendMessage("Move;" + bot.getMove());
                    invalidInARow++;
                }
                break;
        }
    }

    private void handleEndDecision(String[] msgArray) {
        switch (msgArray[1]) {
            // will always accept to end so it can only get Declined;Move
            case "Declined":
                bot.sendMessage("Move;" + bot.getMove());
                break;
            case "Accepted":
                break;
        }
    }

    private void handleEndGame() {
        bot.sendMessage("Disconnect");
        bot.stop();
    }

    private void handlePass(String[] msgArray) {
        switch (msgArray[1]) {
            case "Regular":
                // passes if opponent passes
                bot.sendMessage("Pass");
                bot.sendMessage("Chat;I pass as well.");
                break;
            case "End":
                // agrees to end the game
                bot.sendMessage("Chat;Let's end this");
                bot.sendMessage("EndDecision;Accepted");
                break;
        }
    }

    private void handleLaunch(String[] msgArray) {
        switch (msgArray[1]) {

            case "Start":
                if (msgArray[3].equals("Move")) {
                    bot.sendMessage("Move;" + bot.getMove());
                }
                break;
            case "Wait":
                break;
        }
    }
}
