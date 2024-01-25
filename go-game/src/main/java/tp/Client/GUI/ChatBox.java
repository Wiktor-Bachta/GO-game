package tp.Client.GUI;

import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import tp.Client.Client;
import tp.Message.Message;

public class ChatBox extends VBox {
    
    private Client client;
    private Label chat;
    private TextField messageField;
    private Button chatButton;

    public ChatBox(Client client) {
        this.client = client;
        chat = new Label();
        chatButton = new Button("Send message");
        messageField = new TextField();
        ScrollPane chatScrollPane = new ScrollPane(chat);
        chatScrollPane.setPrefHeight(400);
        getChildren().addAll(chatScrollPane, messageField, chatButton);

        chatButton.setOnAction(e -> {
            if (!messageField.getText().isBlank()) {
            client.getServerConnection().sendMessage(new Message("Chat;" + client.getGame().getId() + ";" + messageField.getText() + ";"));
            messageField.clear();
            }
        });
    }

    public void addPlayerMessage(String message) {
        Platform.runLater(() -> {
            chat.setText(chat.getText() + "You: " + message + "\n");
        });
    }

    public void addOpponentMessage(String message) {
        Platform.runLater(() -> {
            chat.setText(chat.getText() + "Opponent: " + message + "\n");
        });
    }
}
