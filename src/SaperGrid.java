import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Created by Admin on 29.10.2017.
 */
public class SaperGrid extends GridPane {

    public final int WITHD;
    public final int HEIGHT;
    private final int BOMBS;
    private final Tile[][] tiles;
    private boolean setBombs = true;
    private Timeline timeline;
    private boolean lost;
    private long startTime;

    public SaperGrid(int withd, int height, int bombs) {
        setAlignment(Pos.CENTER);
        WITHD = withd;
        HEIGHT = height;
        BOMBS = bombs;
        setHgap(1);
        setVgap(1);
        tiles = new Tile[WITHD][HEIGHT];
        for (int i = 0; i < WITHD; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Tile tile = new Tile();
                tiles[i][j] = tile;
                add(tile, i, j);
                final int x = i;
                final int y = j;
                tile.setOnMouseClicked(event -> onClick(x, y, event));
            }
        }
        Platform.runLater(() -> updateCount());
    }

    private void onClick(int x, int y, MouseEvent event) {
        if (setBombs) {
            setBombs = false;
            int n = 0;
            while (n < BOMBS) {
                int i = (int) (Math.random() * WITHD);
                int j = (int) (Math.random() * HEIGHT);
                if ((i != x || j != y) && !tiles[i][j].isBomb()) {
                    tiles[i][j].setBomb(true);
                    n++;
                }
            }
            setTimer();
        }
        if (event.getButton() == MouseButton.PRIMARY && !tiles[x][y].isChecked()) {
            tiles[x][y].tileClicked(x, y, tiles);
            if (!lost) {
                checkMyWin();
            }
        } else if (event.getButton() == MouseButton.SECONDARY) {
            tiles[x][y].setChecked(!tiles[x][y].isChecked());
            updateCount();
        }
    }

    private void checkMyWin() {
        int n = 0;
        for (int i = 0; i < WITHD; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (!tiles[i][j].isOpened()) {
                    n++;
                }
            }
        }
        if (n == BOMBS) {
            win();
        }
    }

    private void win() {
        MyAudio.getInstance().playWin();
        long time = System.currentTimeMillis() - startTime;
        timeline.stop();
        for (int i = 0; i < WITHD; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                tiles[i][j].setOnMouseClicked(null);
            }
        }
        Leaderboard leaderboard = MyDB.getInstance().getLeaderboard();
        List<Record> records;
        switch (BOMBS) {
            case 10:
                records = leaderboard.getNovice();
                break;
            case 40:
                records = leaderboard.getAdvanced();
                break;
            default:
                records = leaderboard.getHard();
                break;
        }
        long max = 0;
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).getTime() > max) {
                max = records.get(i).getTime();
            }
        }
        if (max > time || records.size() < 10) {
            Stage stage = new Stage();
            HBox root = new HBox();
            TextField textField = new TextField("enter your name");
            Button button = new Button("Submit");
            root.getChildren().addAll(textField, button);
            button.setOnAction(event -> {
                Record record = new Record();
                record.setTime(time);
                record.setName(textField.getText());
                records.add(record);
                Collections.sort(records);
                if (records.size() > 10) {
                    records.remove(10);
                }
                MyDB.getInstance().saveLeaderboard(leaderboard);
                stage.close();
                new LeaderGui(leaderboard, BOMBS).show();
            });
            stage.setScene(new Scene(root));
            stage.setAlwaysOnTop(true);
            stage.show();
        }
    }

    private void setTimer() {
        Label timer = (Label) ((BorderPane) ((GameRoot) getParent()).getChildren().get(1)).getRight();
        startTime = System.currentTimeMillis();
        timeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            final DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            df.setMinimumFractionDigits(2);
            df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
            timer.setText(df.format((System.currentTimeMillis() - startTime) / 1000f));
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void lost(int x, int y) {
        lost = true;
        MyAudio.getInstance().playBomb();
        for (int i = 0; i < WITHD; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                tiles[i][j].setOnMouseClicked(null);
                timeline.stop();
                if (tiles[i][j].isBomb()) {
                    tiles[i][j].drawBomb(x == i && y == j);
                }
            }
        }
    }

    public void updateCount() {
        Label bombsLeft = (Label) ((BorderPane) ((GameRoot) getParent()).getChildren().get(1)).getLeft();
        int n = 0;
        for (int i = 0; i < WITHD; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (tiles[i][j].isChecked())
                    n++;
            }
        }
        bombsLeft.setText(BOMBS - n + "");
    }
}
