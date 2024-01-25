package tp.Client.GUI;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import tp.Client.Client;
import tp.Client.ClientState;
import tp.Message.Message;

public class SidePanelGUI extends VBox {

    private Client client;
    private Label turnInfo;
    private Label specialInfo;
    private Button passButton;
    private Button resignButton;
    private ChatBox chatBox;

    public SidePanelGUI(Client client) {
        this.client = client;

        setAlignment(getAlignment());
        setSpacing(5);
        setPadding(new Insets(10, 10, 10, 10));

        turnInfo = new Label();
        turnInfo.setFont(new Font(20));
        specialInfo = new Label();
        passButton = new Button("Pass");
        resignButton = new Button("Resign");
        chatBox = new ChatBox(client);

        getChildren().addAll(turnInfo, passButton, resignButton, specialInfo, chatBox);

        passButton.setOnAction(e -> {
            if (client.getState() == ClientState.DOING_MOVE) {
                client.getServerConnection().sendMessage(new Message("Pass;" + client.getClientGUI().getGame().getId()));
                client.nextState();
            }
        });

        resignButton.setOnAction(e -> {
            client.getServerConnection()
                    .sendMessage(new Message("Surrender;" + client.getClientGUI().getGame().getId()));
        });

    }

    public void labelUpdateMove() {
        Platform.runLater(() -> {
            turnInfo.setText("Twój ruch");
        });
    }

    public void labelUpdateWait() {
        Platform.runLater(() -> {
            turnInfo.setText("Ruch przeciwnika");
        });
    }

    public void updateSpecialInfo(String message) {
        Platform.runLater(() -> {
            specialInfo.setText(message);
        });
    }

    public ChatBox getChatBox() {
        return chatBox;
    }
}
