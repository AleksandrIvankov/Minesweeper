import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

/**
 * Created by Admin on 29.10.2017.
 */
public class GameMenu extends MenuBar {

    public GameMenu(int withd, int height, int bombs) {
        Menu file = new Menu("File");
        MenuItem newGame = new MenuItem("New Game");
        MenuItem leaderboard = new MenuItem("Leaderboard");
        MenuItem novice = new MenuItem("Novice");
        MenuItem advanced = new MenuItem("Advanced");
        MenuItem hard = new MenuItem("Hard");
        MenuItem exit = new MenuItem("Exit");
        novice.setOnAction(event -> onNewGame(9, 9, 10));
        advanced.setOnAction(event -> onNewGame(16, 16, 40));
        hard.setOnAction(event -> onNewGame(30, 16, 99));
        leaderboard.setOnAction(event -> new LeaderGui(MyDB.getInstance().getLeaderboard(), bombs).show());
        newGame.setOnAction(event -> onNewGame(withd, height, bombs));
        Platform.runLater(() -> getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.F2) {
                onNewGame(withd, height, bombs);
            }
        }));
        exit.setOnAction(event -> System.exit(0));

        file.getItems().addAll(newGame, leaderboard, new SeparatorMenuItem(), novice, advanced, hard, new SeparatorMenuItem(), exit);
        getMenus().add(file);
    }

    private void onNewGame(int withd, int height, int bombs) {
        ((Stage) getScene().getWindow()).setScene(new MyScene(withd, height, bombs));
    }
}
