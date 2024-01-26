package tp.Message;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import tp.Client.ClientState;
import tp.Server.Bot;

public class BotMessageHandler implements MessageHandler {

    private Bot bot;

    public BotMessageHandler(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void handleMessage(Message message) throws IOException {
        try {
            // delay
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String msg = message.getMessage();
        String[] msgArray = msg.split(";");
        String msgType = msgArray[0];

        switch (msgType) {
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
            default:
                System.out.println("Unknown message type: " + msg);
                break;
        }

    }

    private void handleMove(String[] msgArray) {
        switch (msgArray[1]) {
            case "Confirmed":
                bot.placeBotMove(Integer.parseInt(msgArray[2]), Integer.parseInt(msgArray[3]));
                break;
            case "New":
                bot.placeOpponentMove(Integer.parseInt(msgArray[2]), Integer.parseInt(msgArray[3]));
                bot.sendMessage("Move;" + bot.getMove());
                break;
            case "Remove":
                bot.clearStone(Integer.parseInt(msgArray[2]), Integer.parseInt(msgArray[3]));
                break;
            case "Invalid":
                bot.sendMessage("Move;" + bot.getMove());
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
        bot.stop();
    }

    private void handlePass(String[] msgArray) {
        switch (msgArray[1]) {
            case "Regular":
                // passes if opponent passes
                bot.sendMessage("Pass");
                break;
            case "End":
                // agrees to end the game
                bot.sendMessage("EndDecision;Accepted");
                break;
        }
    }

}
