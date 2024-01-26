package tp.Client.GUI;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import tp.Client.Client;

public class EndGamePropositionBox extends VBox {

    private Button accept;
    private Button decline;
    private Label pointLabel;
    private Client client;

    public EndGamePropositionBox(Client client) {
        this.client = client;


        setMaxWidth(200);
        accept = new Button("Accept");
        decline = new Button("Decline");
        pointLabel = new Label();

        accept.setOnAction(e -> {
            client.sendMessage("EndDecision;Accepted");
            setVisible(false);
        });

        decline.setOnAction(e -> {
            client.sendMessage("EndDecision;Declined");
            setVisible(false);
        });

        getChildren().addAll(pointLabel, accept, decline);

    }

    public void show(int playerPoints, int opponentPoints) {
        Platform.runLater(() -> {
            pointLabel.setText(("Do you want to end the game?\n" + "If you don't you opponent will continue\n"
                + "Your points: " + playerPoints + "\nOpponent points: " + opponentPoints));
        });
        setVisible(true);
    }

}
