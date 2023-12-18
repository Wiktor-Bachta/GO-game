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
        Board board = new Board(19);
        Scene scene = new Scene(board.getBoardGUI().getPane(), 760, 760);
        primaryStage.setResizable(false);
        primaryStage.setTitle("BoardGUITest");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
