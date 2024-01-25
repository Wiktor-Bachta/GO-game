package tp.Client.GUI;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import tp.Client.Client;

public class EndGamePanelGUI extends VBox {

    private Client client;
    private Button exitButton;
    private Button replayButton;
    private Label gameResultLabel;
    private Label scoreLabel;

    public EndGamePanelGUI(Client client, String result, int playerPoints, int opponentPoints) {
        this.client = client;
        gameResultLabel = new Label(result);
        scoreLabel = new Label("Your score: " + playerPoints + "\nOpponent score: " + opponentPoints);
        exitButton = new Button("Exit");
        replayButton = new Button("Replay");
        exitButton.setOnAction(e -> {
            client.getClientGUI().reset();
        });
        replayButton.setOnAction(e -> {

        });
        getChildren().addAll(gameResultLabel, scoreLabel, replayButton, exitButton);
    }

}
