package tp.Client.GUI;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import tp.Client.Client;
import tp.Message.Message;

public class SidePanelGUI extends VBox {

    private Client client;
    private Label turnInfo;
    private Label specialInfo;
    private Button passButton;
    private Button resignButton;

    public SidePanelGUI(Client client) {
        this.client = client;

        setWidth(200);
        setSpacing(5);
        setPadding(new Insets(10, 10, 10, 10));

        turnInfo = new Label();
        turnInfo.setFont(new Font(20));
        specialInfo = new Label();
        passButton = new Button("Pass");
        resignButton = new Button("Resign");
        getChildren().addAll(turnInfo, passButton, resignButton, specialInfo);

        passButton.setOnAction(e -> {
            client.getServerConnection().sendMessage(new Message("Pass;" + client.getClientGUI().getGame().getId()));
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
}
