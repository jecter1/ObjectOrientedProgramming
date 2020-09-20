package game.view;

import game.model.GameModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Board extends Parent {
    GridPane gridPane;
    Label[][] labelCell = new Label[GameModel.HEIGHT][GameModel.WIDTH];

    public Board(int height, int width) {
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);

        int borderSize, cellSize;
        if ((height / GameModel.HEIGHT) < (width / GameModel.WIDTH)) {
            borderSize = (int) (height / GameModel.HEIGHT * BORDER_RATIO);
            cellSize = (height - borderSize * (GameModel.HEIGHT + 1)) / GameModel.HEIGHT;
        } else {
            borderSize = (int) (width / GameModel.WIDTH * BORDER_RATIO);
            cellSize = (width - borderSize * (GameModel.WIDTH + 1)) / GameModel.WIDTH;
        }

        double paddingUp, paddingDown, paddingRight, paddingLeft;
        paddingUp = paddingDown = (height - GameModel.HEIGHT * cellSize - (GameModel.HEIGHT - 1) * borderSize) / 2.0;
        paddingLeft = paddingRight = (width - GameModel.WIDTH * cellSize - (GameModel.WIDTH - 1) * borderSize) / 2.0;
        gridPane.setPadding(new Insets(paddingUp, paddingDown, paddingRight, paddingLeft));

        gridPane.setVgap(borderSize);
        gridPane.setHgap(borderSize);

        for (int y = 0; y < GameModel.HEIGHT; ++y) {
            for (int x = 0; x < GameModel.WIDTH; ++x) {
                labelCell[y][x] = new Label();
                labelCell[y][x].setPrefHeight(cellSize);
                labelCell[y][x].setPrefWidth(cellSize);
                labelCell[y][x].setAlignment(Pos.CENTER);
                labelCell[y][x].setBorder(GameView.DEFAULT_BORDER);

                labelCell[y][x].setTextAlignment(TextAlignment.CENTER);
                labelCell[y][x].setFont(new Font(GameView.DEFAULT_FONT, CELL_FONT_SIZE));

                gridPane.add(labelCell[y][x], x, y);
            }
        }

        getChildren().add(gridPane);
    }

    public void update(int[][] cell) {
        for (int y = 0; y < GameModel.HEIGHT; ++y) {
            for (int x = 0; x < GameModel.WIDTH; ++x) {
                if (cell[y][x] == GameModel.CELL_EMPTY) {
                    labelCell[y][x].setBackground(GameView.DEFAULT_BACKGROUND);
                    labelCell[y][x].setText(EMPTY_STRING);
                } else {
                    labelCell[y][x].setBackground(
                            new Background(
                                    new BackgroundFill(
                                            cellColor(cell[y][x]),
                                            GameView.CORNER_RADII_NULL,
                                            GameView.INSETS_NULL)));
                    labelCell[y][x].setText(Integer.toString(cell[y][x]));
                }
            }
        }
    }

    private Color cellColor(int cellVal) {
        // x[i] = 2^i => x'[i] = i - 1
        // i = 1, 2, ...
        double log2cell = Math.log(cellVal) / Math.log(2) - 1;

        final double MAX_R = 0.75;
        final double MAX_G = 0.40;
        final double MAX_B = 1.00;

        final double MIN_R = 0.25;
        final double MIN_G = 0.10;
        final double MIN_B = 0.25;

        final double STRETCH_R = 4.0;
        final double STRETCH_G = 8.0;
        final double STRETCH_B = 16.0;

        double R = (MAX_R - MIN_R) / Math.exp(log2cell / STRETCH_R) + MIN_R;
        double G = (MAX_G - MIN_G) / Math.exp(log2cell / STRETCH_G) + MIN_G;
        double B = (MAX_B - MIN_B) / Math.exp(log2cell / STRETCH_B) + MIN_B;

        return Color.color(R, G, B);
    }

    private final static String EMPTY_STRING = "";

    private final static int CELL_FONT_SIZE = 35;

    private final static double BORDER_RATIO = 1.0 / 25.0;
}
