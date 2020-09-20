package game.view;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Score extends Parent {
    Label label;

    public Score(int height, int width) {
        label = new Label();
        label.setPrefHeight(height);
        label.setPrefWidth(width);
        label.setFont(new Font(GameView.DEFAULT_FONT, FONT_SIZE));
        label.setTextAlignment(TextAlignment.CENTER);
        label.setAlignment(Pos.CENTER);
        label.setBackground(GameView.DEFAULT_BACKGROUND);
        label.setBorder(GameView.DEFAULT_BORDER);

        getChildren().add(label);
    }

    public void update(int score) {
        label.setText(SCORE_PREFIX + score);
    }

    private static final String SCORE_PREFIX = "Score: ";

    private static final int FONT_SIZE = 20;
}
