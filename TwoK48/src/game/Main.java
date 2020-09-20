package game;

import game.model.GameModel;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) {
        GameModel gameModel = new GameModel(primaryStage);
        gameModel.reset();
    }
}