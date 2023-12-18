package tp.Game.GUI;

import org.junit.Test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tp.Game.Board;

public class BoardGUITest extends Application {

    @Override 
    public void start(Stage primaryStage) {
        int size = 19;
        int pixelSize = size * 40;
        Board board = new Board(size, pixelSize);
        Scene scene = new Scene(board.getBoardGUI().getPane(), pixelSize, pixelSize);
        primaryStage.setResizable(false);
        primaryStage.setTitle("BoardGUITest");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
