package tp.Game.GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tp.Game.Board;


public class ChoiceGUI extends Application {

    HBox pane;
    Button playBot;
    Button playUser;
    Button exit;

    public ChoiceGUI() {
        pane = new HBox(10);
        playBot = new Button("Play with a bot");
        playUser = new Button("Play with user");
        exit = new Button("exit");
        pane.getChildren().addAll(playBot, playUser, exit);
    }

    @Override 
    public void start(Stage primaryStage) {

        Scene scene = new Scene(pane, 400,400);
        primaryStage.setResizable(false);
        primaryStage.setTitle("choiceGUI");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
