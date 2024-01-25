package tp.Client.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tp.Client.Client;
import tp.Game.Game;
import tp.Game.GUI.BoardGUI;
import tp.Game.GUI.ChoiceGUI;

public class ClientGUI extends Application {

    private Client client;
    private ChoiceGUI choiceGUI;
    private BoardGUI boardGUI;
    private SidePanelGUI sidePanelGUI;
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
            game = client.getGame();
            boardGUI = game.getBoard().getBoardGUI();
            sidePanelGUI = new SidePanelGUI(client);
            BorderPane layout = new BorderPane();
            layout.setCenter(boardGUI.getPane());
            layout.setRight(sidePanelGUI);
            if (clientColor == ClientColor.BLACK) {
                sidePanelGUI.labelUpdateMove();
            } else {
                sidePanelGUI.labelUpdateWait();
            }
            stage.setScene(new Scene(layout, 1000, 760));
        });

    }

    public void placePlayerMove(int x, int y) {
        game.getBoard().getSquares()[x][y].placeMove(x, y, ClientColor.getPlayerSquareState(clientColor));
    }

    public void placeOpponentMove(int x, int y) {
        game.getBoard().getSquares()[x][y].placeMove(x, y, ClientColor.getOpponentSquareState(clientColor));
    }

    public void clearMove(int x, int y) {
        game.getBoard().getSquares()[x][y].clearMove(x, y);
    }

    public Game getGame() {
        return game;
    }

    public SidePanelGUI getSidePanelGUI() {
        return sidePanelGUI;
    }

    public void senOpponentChat(String string) {
        sidePanelGUI.getChatBox().addOpponentMessage(string);
    }

    public void sendPlayerChat(String string) {
        sidePanelGUI.getChatBox().addPlayerMessage(string);
    }

    public void showEndGameProposition(int playerPoints, int opponentPoints) {
        sidePanelGUI.showEndGameProposition(playerPoints, opponentPoints);
    }
}