package tp.Client.GUI;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import tp.Client.Client;

public class EndGamePanelGUI extends VBox {

    private Client client;
    private Button exitButton;
    private Button replayButton;
    private Button nextMoveButton;
    private Label gameResultLabel;
    private Label scoreLabel;
    private int replayMoveNumber;

    public EndGamePanelGUI(Client client, String result, int playerPoints, int opponentPoints) {
        this.client = client;
        gameResultLabel = new Label(result);
        scoreLabel = new Label("Your score: " + playerPoints + "\nOpponent score: " + opponentPoints);
        exitButton = new Button("Exit");
        replayButton = new Button("Replay");
        nextMoveButton = new Button("Next move");
        exitButton.setOnAction(e -> {
            client.getClientGUI().reset();
        });
        replayButton.setOnAction(e -> {
            replayMoveNumber = 1;
            getChildren().remove(replayButton);
            getChildren().add(nextMoveButton);
            client.getClientGUI().getBoardGUI().reset();
        });
        nextMoveButton.setOnAction(e -> {
            client.sendMessage("Replay;" + replayMoveNumber);
            replayMoveNumber++;
        });
        getChildren().addAll(gameResultLabel, scoreLabel, exitButton, replayButton);
    }

    public void resetReplay() {
        Platform.runLater(() -> {
            getChildren().remove(nextMoveButton);
            getChildren().add(replayButton);
        });
    }

}
