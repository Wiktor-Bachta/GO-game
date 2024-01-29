package tp.client.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import tp.client.Client;
import tp.client.ClientColor;
import tp.client.ClientState;
import tp.client.GUI.Board.BoardGUI;
import tp.gamelogic.StoneState;

public class ClientGUI extends Application {

    public static final int boardSize = 600;
    public static final int sideMenuSize = 300;

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
        Scene scene = new Scene(choiceGUI, 250, 250);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void reset() {
        client.setCurrentSessionID(null);
        choiceGUI = new ChoiceGUI(client);
        Scene scene = new Scene(choiceGUI, 250, 250);
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

    public void displayBoard(int size) {
        Platform.runLater(() -> {
            boardGUI = new BoardGUI(client, size);
            sidePanelGUI = new SidePanelGUI(client);
            layout = new BorderPane();
            layout.setCenter(boardGUI);
            layout.setRight(sidePanelGUI);
            if (clientColor == ClientColor.BLACK) {
                sidePanelGUI.labelUpdateMove();
            } else {
                sidePanelGUI.labelUpdateWait();
            }
            stage.setScene(new Scene(layout, boardSize + sideMenuSize, boardSize));
        });

    }

    public void placePlayerMove(int x, int y) {
        boardGUI.placeMove(x, y, ClientColor.getPlayerStoneState(clientColor));
    }

    public void placeOpponentMove(int x, int y) {
        boardGUI.placeMove(x, y, ClientColor.getOpponentStoneState(clientColor));
    }

    public void placeMove(int x, int y, StoneState state) {
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

    public void sendSystemChat(String string) {
        sidePanelGUI.getChatBox().addSystemMessage(string);
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

    public void labelUpdateMove() {
        sidePanelGUI.labelUpdateMove();
    }

    public void labelUpdateWait() {
        sidePanelGUI.labelUpdateWait();
    }
}