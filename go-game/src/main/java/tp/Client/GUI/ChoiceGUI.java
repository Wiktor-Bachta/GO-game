package tp.Client.GUI;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import tp.Client.Client;

public class ChoiceGUI extends VBox {
    private Client client;

    private Button playWithBotButton;
    private Button playWithUserButton;
    private Button exitButton;
    private Button createGameButton;
    private Button joinGameButton;
    private Button goBackButton;
    private TextField idTextField;
    private Label idLabel;

    public ChoiceGUI(Client client) {
        this.client = client;

        playWithBotButton = new Button("Play with bot");
        playWithUserButton = new Button("Play with user");
        exitButton = new Button("Exit");
        createGameButton = new Button("Create game");
        joinGameButton = new Button("Join game");
        goBackButton = new Button("Go Back");
        idTextField = new TextField("Wpisz id");
        idLabel = new Label();

        playWithBotButton.setOnAction(e -> {
            client.sendMessage("Launch;Create;bot");
        });

        playWithUserButton.setOnAction(e -> {
            switchToPlayWithUser();
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

        goBackButton.setOnAction(e -> {
            switchToMainMenu();
        });

        setSpacing(10);
        setPadding(new Insets(10, 10, 10, 10));
        getChildren().addAll(playWithBotButton, playWithUserButton, exitButton);

    }

    private void switchToPlayWithUser() {
        getChildren().clear();
        getChildren().addAll(createGameButton, idLabel, joinGameButton, idTextField, goBackButton);
    }

    private void switchToMainMenu() {
        getChildren().clear();
        getChildren().addAll(playWithBotButton, playWithUserButton, exitButton);
    }

    public void displayID(String idMessage) {
        Platform.runLater(() -> {
            idLabel.setText(idMessage);
        });
    }
}
