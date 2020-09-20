package game.controller;

import game.model.GameModel;

import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

public class GameController implements EventHandler<KeyEvent>{
    private final GameModel gameModel;

    public GameController(GameModel gameModel){
        this.gameModel = gameModel;
    }

    public void onRestartButton() {
        gameModel.reset();
    }

    @Override
    public void handle(KeyEvent key) {
        switch (key.getCode()) {
            case W -> gameModel.swipeUp();
            case S -> gameModel.swipeDown();
            case D -> gameModel.swipeRight();
            case A -> gameModel.swipeLeft();
        }
    }
}
