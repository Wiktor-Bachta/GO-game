package tp.Client.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tp.Client.Client;
import tp.Game.Game;
import tp.Game.GUI.BoardGUI;
import tp.Game.GUI.ChoiceGUI;

public class ClientGUI extends Application {

    private Client client;
    private ChoiceGUI choiceGUI;
    private BoardGUI boardGUI;
    private ClientColor clientColor;
    private Game game;

    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        client = new Client(this);
        client.run();
        choiceGUI = new ChoiceGUI(client);
        Scene scene = new Scene(choiceGUI, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void run() {
        launch();
    }

    public Client getClient() {
        return client;
    }

    public ChoiceGUI getChoiceGUI() {
        return choiceGUI;
    }

    public void setClientColor(ClientColor color) {
        this.clientColor = color;
    }


    public void setBoardGUI(BoardGUI boardGUI) {
        this.boardGUI = boardGUI;
    }

    public void displayBoard() {
        Platform.runLater(() -> {
            choiceGUI.terminateChoiceGUI();
            game = new Game(client);
            boardGUI = game.getBoard().getBoardGUI();
            stage.setScene(new Scene(boardGUI.getPane(), 800, 800));
        });

    }
}