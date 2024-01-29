package tp.client.GUI;

import javax.swing.GroupLayout.Alignment;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import tp.client.Client;
import tp.client.ClientState;
import tp.message.Message;

public class SidePanelGUI extends VBox {

    private Client client;
    private Label turnInfo;
    private Button passButton;
    private Button resignButton;
    private ChatBox chatBox;
    private EndGamePropositionBox endGameBox;

    public SidePanelGUI(Client client) {
        this.client = client;

        setMinWidth(ClientGUI.sideMenuSize);
        setSpacing(5);
        setPadding(new Insets(10, 10, 10, 10));

        turnInfo = new Label();
        turnInfo.setFont(new Font(20));
        passButton = new Button("Pass");
        resignButton = new Button("Resign");
        chatBox = new ChatBox(client);
        endGameBox = new EndGamePropositionBox(client);
        endGameBox.setVisible(false);

        getChildren().addAll(turnInfo, passButton, resignButton, chatBox, endGameBox);

        passButton.setOnAction(e -> {
            if (client.getState() == ClientState.DOING_MOVE) {
                client.sendMessage("Pass");
                client.nextState();
            }
        });

        resignButton.setOnAction(e -> {
            if (client.getState() == ClientState.DOING_MOVE) {
                client.sendMessage("Surrender");
            }
        });

    }

    public void labelUpdateMove() {
        Platform.runLater(() -> {
            turnInfo.setText("Your move");
        });
    }

    public void labelUpdateWait() {
        Platform.runLater(() -> {
            turnInfo.setText("Opponent's move");
        });
    }

    public ChatBox getChatBox() {
        return chatBox;
    }

    public void showEndGameProposition(int playerPoints, int opponentPoints) {
        endGameBox.show(playerPoints, opponentPoints);
    }

    public void hideEndGameProposition() {
        endGameBox.setVisible(false);
    }
}
