package tp.Client.GUI;

import java.io.IOException;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tp.Client.Client;
import tp.Message.Message;

public class ChoiceGUI extends VBox {
    private Client client;
    private Label idLabel;
    private Stage popupStage;

    public ChoiceGUI(Client client) {
        this.client = client;

        Button playWithBotButton = new Button("Play with bot");
        Button playWithUserButton = new Button("Play with user");
        Button exitButton = new Button("Exit");
        VBox playWithUserVBox = new VBox();
        Button createGameButton = new Button("Create game");
        Button joinGameButton = new Button("Join game");
        TextField idTextField = new TextField("Wpisz id");
        idLabel = new Label();
        playWithUserVBox.setPadding(new Insets(10, 10, 10, 10));
        playWithUserVBox.getChildren().addAll(createGameButton, idLabel, joinGameButton, idTextField);

        playWithBotButton.setOnAction(e -> {
            client.sendMessage("Launch;Create;bot");
        });

        playWithUserButton.setOnAction(e -> {
            popupStage = new Stage();
            Scene scene = new Scene(playWithUserVBox, 400, 400);
            popupStage.setScene(scene);
            popupStage.show();
        });

        exitButton.setOnAction(e -> {
            client.sendMessage("Disconnect");
            System.exit(0);
        });

        createGameButton.setOnAction(e -> {
            client.sendMessage("Launch;Create;user");
        });

        joinGameButton.setOnAction(e -> {
            String id = idTextField.getText();
            if (!id.isEmpty()) {
                client.sendMessage("Launch;Join;" + id);
            }
        });

        setSpacing(10);
        setPadding(new Insets(10, 10, 10, 10));
        getChildren().addAll(playWithBotButton, playWithUserButton, exitButton);

    }

    public void displayID(String idMessage) {
        Platform.runLater(() -> {
            idLabel.setText(idMessage);
        });
    }

    public void terminateChoiceGUI() {
        popupStage.close();
    }
}
