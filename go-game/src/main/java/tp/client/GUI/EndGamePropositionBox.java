package tp.client.GUI;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import tp.client.Client;

public class EndGamePropositionBox extends VBox {

    private Button accept;
    private Button decline;
    private Label pointLabel;
    private Client client;

    public EndGamePropositionBox(Client client) {
        this.client = client;

        setMaxWidth(ClientGUI.sideMenuSize - 20);
        setSpacing(10);
        setPadding(new Insets(10, 10, 10, 10));

        accept = new Button("Accept");
        decline = new Button("Decline");
        pointLabel = new Label();
        pointLabel.setWrapText(true);

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
            pointLabel.setText("Do you want to end the game?\n" + "If you don't, your opponent will continue.\n"
                    + "Your points: " + playerPoints + "   Opponent points: " + opponentPoints);
        });
        setVisible(true);
    }

}
