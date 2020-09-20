package game.view;

import game.controller.GameController;
import game.model.GameModel;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GameView {
    private final GameModel gameModel;

    private final Stage stage;

    private final Board board;
    private final Score score;

    public GameView(Stage stage, GameModel gameModel) {
        this.gameModel = gameModel;
        GameController gameController = new GameController(gameModel);

        this.stage = stage;

        Group group = new Group();

        board = new Board((int) (SCENE_HEIGHT * BOARD_HEIGHT_RATIO), SCENE_WIDTH);
        board.setLayoutY(SCENE_HEIGHT * PANEL_HEIGHT_RATIO);

        score = new Score((int) (SCENE_HEIGHT * PANEL_HEIGHT_RATIO * PANEL_ELEMENT_HEIGHT_RATIO),
                          (int) (SCENE_WIDTH * PANEL_ELEMENT_WIDTH_RATIO));
        score.setLayoutX(SCENE_WIDTH * (1 - 2 * PANEL_ELEMENT_WIDTH_RATIO) / 3);
        score.setLayoutY(SCENE_HEIGHT * PANEL_HEIGHT_RATIO * (1 - PANEL_ELEMENT_HEIGHT_RATIO) / 2);

        Button restartButton = new Button(RESTART_TEXT);
        restartButton.setOnAction(actionEvent -> gameController.onRestartButton());
        restartButton.setPrefHeight(SCENE_HEIGHT * PANEL_HEIGHT_RATIO * PANEL_ELEMENT_HEIGHT_RATIO);
        restartButton.setPrefWidth(SCENE_WIDTH * PANEL_ELEMENT_WIDTH_RATIO);
        restartButton.setLayoutX(SCENE_WIDTH * (2 - PANEL_ELEMENT_WIDTH_RATIO) / 3);
        restartButton.setLayoutY(SCENE_HEIGHT * PANEL_HEIGHT_RATIO * (1 - PANEL_ELEMENT_HEIGHT_RATIO) / 2);
        restartButton.setBackground(DEFAULT_BACKGROUND_BUTTON);
        restartButton.setBorder(DEFAULT_BORDER_BUTTON);
        restartButton.setFont(new Font(DEFAULT_FONT, FONT_BUTTON_SIZE));

        group.getChildren().add(board);
        group.getChildren().add(score);
        group.getChildren().add(restartButton);

        Scene scene = new Scene(group, SCENE_WIDTH, SCENE_HEIGHT);
        scene.setOnKeyPressed(gameController);

        stage.setScene(scene);
    }

    public void update() {
        board.update(gameModel.getCells());
        score.update(gameModel.getScore());
        stage.show();
    }

    private final static String RESTART_TEXT = "Restart";

    private final static int SCENE_HEIGHT = 600;
    private final static int SCENE_WIDTH = 500;

    private final static double BOARD_HEIGHT_RATIO = 5.0 / 6.0;
    private final static double PANEL_HEIGHT_RATIO = 1.0 / 6.0;

    private final static double PANEL_ELEMENT_HEIGHT_RATIO = 8.0 / 10.0;
    private final static double PANEL_ELEMENT_WIDTH_RATIO = 2.0 / 5.0;

    private final static int FONT_BUTTON_SIZE = 20;

    static final CornerRadii CORNER_RADII_NULL = null;
    static final Insets INSETS_NULL = null;
    static final BorderWidths BORDER_WIDTHS_NULL = null;

    static final String DEFAULT_FONT = "verdana";


    static final Background DEFAULT_BACKGROUND = new Background(new BackgroundFill(Color.BISQUE,
                                                                                   CORNER_RADII_NULL,
                                                                                   INSETS_NULL));

    static final Border DEFAULT_BORDER = new Border(new BorderStroke(Color.BLACK,
                                                                     BorderStrokeStyle.SOLID,
                                                                     CORNER_RADII_NULL,
                                                                     BORDER_WIDTHS_NULL,
                                                                     INSETS_NULL));

    static final CornerRadii CORNER_RADII_BUTTON = new CornerRadii(20.0);

    static final Background DEFAULT_BACKGROUND_BUTTON = new Background(new BackgroundFill(Color.PALEGOLDENROD,
                                                                                          CORNER_RADII_BUTTON,
                                                                                          INSETS_NULL));

    static final Border DEFAULT_BORDER_BUTTON = new Border(new BorderStroke(Color.BLACK,
                                                                            BorderStrokeStyle.SOLID,
                                                                            CORNER_RADII_BUTTON,
                                                                            BORDER_WIDTHS_NULL,
                                                                            INSETS_NULL));
}
