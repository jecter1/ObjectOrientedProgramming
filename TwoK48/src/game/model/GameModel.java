package game.model;

import game.view.GameView;
import javafx.stage.Stage;

public class GameModel {
    private final GameView gameView;

    private final int[][] cell;
    private int score;

    public GameModel(Stage stage) {
        gameView = new GameView(stage, this);
        cell = new int[HEIGHT][WIDTH];
    }

    public int[][] getCells() { return cell; }

    public int getScore() { return score; }

    public void reset() {
        score = 0;

        for(int y = 0; y < HEIGHT; ++y) {
            for (int x = 0; x < WIDTH; ++x) {
                cell[y][x] = CELL_EMPTY;
            }
        }

        generateCell();
        generateCell();

        gameView.update();
    }

    public void swipeUp() {
        boolean isActionPerformed = false;

        for (int x = 0; x < WIDTH; ++x) {
            for (int y = 0; y < HEIGHT; ++y) {
                if (cell[y][x] != CELL_EMPTY) continue;

                for (int yy = y + 1; yy < HEIGHT; ++yy) {
                    if (cell[yy][x] == CELL_EMPTY) continue;

                    cell[y][x] = cell[yy][x];
                    cell[yy][x] = CELL_EMPTY;

                    isActionPerformed = true;
                    break;
                }
            }

            for (int y = 0; y < HEIGHT - 1; ++y) {
                if (cell[y][x] == CELL_EMPTY) continue;

                if (cell[y][x] == cell[y + 1][x]) {
                    cell[y][x] += cell[y + 1][x];
                    cell[y + 1][x] = CELL_EMPTY;

                    score += cell[y][x];
                    isActionPerformed = true;
                }
            }

            if (!isActionPerformed) continue;

            for (int y = 0; y < HEIGHT; ++y) {
                if (cell[y][x] != CELL_EMPTY) continue;

                for (int yy = y + 1; yy < HEIGHT; ++yy) {
                    if (cell[yy][x] == CELL_EMPTY) continue;

                    cell[y][x] = cell[yy][x];
                    cell[yy][x] = CELL_EMPTY;

                    isActionPerformed = true;
                    break;
                }
            }
        }

        if (isActionPerformed) generateCell();
        gameView.update();
    }

    public void swipeDown() {
        boolean isActionPerformed = false;

        for (int x = 0; x < WIDTH; ++x) {
            for (int y = HEIGHT - 1; y >= 0; --y) {
                if (cell[y][x] != CELL_EMPTY) continue;

                for (int yy = y - 1; yy >= 0; --yy) {
                    if (cell[yy][x] == CELL_EMPTY) continue;

                    cell[y][x] = cell[yy][x];
                    cell[yy][x] = CELL_EMPTY;

                    isActionPerformed = true;
                    break;
                }
            }

            for (int y = HEIGHT - 1; y >= 1; --y) {
                if (cell[y][x] == CELL_EMPTY) continue;

                if (cell[y][x] == cell[y - 1][x]) {
                    cell[y][x] += cell[y - 1][x];
                    cell[y - 1][x] = CELL_EMPTY;

                    score += cell[y][x];
                    isActionPerformed = true;
                }
            }

            if (!isActionPerformed) continue;

            for (int y = HEIGHT - 1; y >= 0; --y) {
                if (cell[y][x] != CELL_EMPTY) continue;

                for (int yy = y - 1; yy >= 0; --yy) {
                    if (cell[yy][x] == CELL_EMPTY) continue;

                    cell[y][x] = cell[yy][x];
                    cell[yy][x] = CELL_EMPTY;

                    isActionPerformed = true;
                    break;
                }
            }
        }

        if (isActionPerformed) generateCell();
        gameView.update();
    }

    public void swipeRight() {
        boolean isActionPerformed = false;

        for (int y = 0; y < HEIGHT; ++y) {
            for (int x = WIDTH - 1; x >= 0; --x) {
                if (cell[y][x] != CELL_EMPTY) continue;

                for (int xx = x - 1; xx >= 0; --xx) {
                    if (cell[y][xx] == CELL_EMPTY) continue;

                    cell[y][x] = cell[y][xx];
                    cell[y][xx] = CELL_EMPTY;

                    isActionPerformed = true;
                    break;
                }
            }

            for (int x = WIDTH - 1; x >= 1; --x) {
                if (cell[y][x] == CELL_EMPTY) continue;

                if (cell[y][x] == cell[y][x - 1]) {
                    cell[y][x] += cell[y][x - 1];
                    cell[y][x - 1] = CELL_EMPTY;

                    score += cell[y][x];
                    isActionPerformed = true;
                }
            }

            if (!isActionPerformed) continue;

            for (int x = WIDTH - 1; x >= 0; --x) {
                if (cell[y][x] != CELL_EMPTY) continue;

                for (int xx = x - 1; xx >= 0; --xx) {
                    if (cell[y][xx] == CELL_EMPTY) continue;

                    cell[y][x] = cell[y][xx];
                    cell[y][xx] = CELL_EMPTY;

                    isActionPerformed = true;
                    break;
                }
            }
        }

        if (isActionPerformed) generateCell();
        gameView.update();
    }

    public void swipeLeft() {
        boolean isActionPerformed = false;

        for (int y = 0; y < HEIGHT; ++y) {
            for (int x = 0; x < WIDTH; ++x) {
                if (cell[y][x] != CELL_EMPTY) continue;

                for (int xx = x + 1; xx < WIDTH; ++xx) {
                    if (cell[y][xx] == CELL_EMPTY) continue;

                    cell[y][x] = cell[y][xx];
                    cell[y][xx] = CELL_EMPTY;

                    isActionPerformed = true;
                    break;
                }
            }

            for (int x = 0; x < WIDTH - 1; ++x) {
                if (cell[y][x] == CELL_EMPTY) continue;

                if (cell[y][x] == cell[y][x + 1]) {
                    cell[y][x] += cell[y][x + 1];
                    cell[y][x + 1] = CELL_EMPTY;

                    score += cell[y][x];
                    isActionPerformed = true;
                }
            }

            if (!isActionPerformed) continue;

            for (int x = 0; x < WIDTH; ++x) {
                if (cell[y][x] != CELL_EMPTY) continue;

                for (int xx = x + 1; xx < HEIGHT; ++xx) {
                    if (cell[y][xx] == CELL_EMPTY) continue;

                    cell[y][x] = cell[y][xx];
                    cell[y][xx] = CELL_EMPTY;

                    isActionPerformed = true;
                    break;
                }
            }
        }

        if (isActionPerformed) generateCell();
        gameView.update();
    }

    private void generateCell() {
        int x, y;

        do {
            y = (int)(Math.random() * HEIGHT);
            x = (int)(Math.random() * WIDTH);
        } while (cell[y][x] != CELL_EMPTY);

        cell[y][x] = (Math.random() < CHANCE_RARE) ? CELL_RARE : CELL_COMMON;
    }

    private boolean isLose() {
        for (int y = 0; y < HEIGHT; ++y) {
            for (int x = 0; x < WIDTH; ++x) {
                if (cell[y][x] == CELL_EMPTY) {
                    return false;
                }
            }
        }

        for (int y = 0; y < HEIGHT; ++y) {
            for (int x = 0; x < WIDTH; ++x) {
                int cellVal = cell[y][x];

                if ((y > 0) && (cellVal == cell[y - 1][x])) {
                    return false;
                }
                if ((x > 0) && (cellVal == cell[y][x - 1])) {
                    return false;
                }
                if ((y < HEIGHT - 1) && (cellVal == cell[y + 1][x])) {
                    return false;
                }
                if ((x < WIDTH - 1) && (cellVal == cell[y][x + 1])) {
                    return false;
                }
            }
        }

        return true;
    }

    public static final int HEIGHT = 4;
    public static final int WIDTH  = 4;

    private static final double CHANCE_RARE = 0.25;

    public  static final int CELL_EMPTY  = 0;
    private static final int CELL_COMMON = 2;
    private static final int CELL_RARE   = 4;
}
