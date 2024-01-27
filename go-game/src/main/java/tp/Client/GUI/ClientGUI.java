package tp.Client.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import tp.Client.Client;
import tp.Client.ClientState;
import tp.Game.SquareState;
import tp.Game.GUI.BoardGUI;

public class ClientGUI extends Application {

    private Client client;
    private ChoiceGUI choiceGUI;
    private BoardGUI boardGUI;
    private SidePanelGUI sidePanelGUI;
    private EndGamePanelGUI endGamePanelGUI;
    private ClientColor clientColor;
    private BorderPane layout;

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

    public void reset() {
        client.setCurrentSessionID(null);
        choiceGUI = new ChoiceGUI(client);
        Scene scene = new Scene(choiceGUI, 300, 200);
        stage.setScene(scene);
        stage.show();
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
            boardGUI = new BoardGUI(client);
            sidePanelGUI = new SidePanelGUI(client);
            layout = new BorderPane();
            layout.setCenter(boardGUI);
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
        boardGUI.placeMove(x, y, ClientColor.getPlayerSquareState(clientColor));
    }

    public void placeOpponentMove(int x, int y) {
        boardGUI.placeMove(x, y, ClientColor.getOpponentSquareState(clientColor));
    }

    public void placeMove(int x, int y, SquareState state) {
        boardGUI.placeMove(x, y, state);
    }

    public void clearMove(int x, int y) {
        boardGUI.clearMove(x, y);
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

    public void endGame(String result, int playerPoints, int opponentPoints) {
        client.setState(ClientState.NOT_IN_GAME);
        Platform.runLater(() -> {
            endGamePanelGUI = new EndGamePanelGUI(client, result, playerPoints, opponentPoints);
            layout.setRight(endGamePanelGUI);
        });
    }

    public BoardGUI getBoardGUI() {
        return boardGUI;
    }

    public void resetReplay() {
        endGamePanelGUI.resetReplay();
    }
}