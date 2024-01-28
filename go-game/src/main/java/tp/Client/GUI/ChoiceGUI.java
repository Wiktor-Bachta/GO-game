package tp.Client.GUI;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import tp.Client.Client;

public class ChoiceGUI extends VBox {
    private Client client;

    private Button playWithBotButton;
    private Button playWithUserButton;
    private Button exitButton;
    private Button createPlayerGameButton;
    private Button createBotGameButton;
    private Button joinGameButton;
    private Button goBackButton;
    private ChoiceBox boardSizeChoiceBox;
    private TextField idTextField;
    private Label idLabel;
    private Label sizeLabel;

    public ChoiceGUI(Client client) {
        this.client = client;

        playWithBotButton = new Button("Play with bot");
        playWithUserButton = new Button("Play with user");
        exitButton = new Button("Exit");
        createPlayerGameButton = new Button("Create game");
        createBotGameButton = new Button("Create game");
        joinGameButton = new Button("Join game");
        goBackButton = new Button("Go Back");
        boardSizeChoiceBox = new ChoiceBox<>();
        boardSizeChoiceBox.getItems().addAll("19", "13", "9");
        boardSizeChoiceBox.setValue("19");
        idTextField = new TextField("Wpisz id");
        idLabel = new Label();
        sizeLabel = new Label("Pick a size");

        createBotGameButton.setOnAction(e -> {
            client.sendMessage("Launch;Create;bot;" + boardSizeChoiceBox.getValue());
        });

        playWithBotButton.setOnAction(e -> {
            switchToPlayWithBot();
        });

        playWithUserButton.setOnAction(e -> {
            switchToPlayWithUser();
        });

        exitButton.setOnAction(e -> {
            client.sendMessage("Disconnect");
            client.stop();
            System.exit(0);
        });

        createPlayerGameButton.setOnAction(e -> {
            client.sendMessage("Launch;Create;user;" + boardSizeChoiceBox.getValue());
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
        getChildren().addAll(sizeLabel, boardSizeChoiceBox, createPlayerGameButton, idLabel, idTextField,
                joinGameButton, goBackButton);
    }

    private void switchToPlayWithBot() {
        getChildren().clear();
        getChildren().addAll(sizeLabel, boardSizeChoiceBox, createBotGameButton, goBackButton);
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
