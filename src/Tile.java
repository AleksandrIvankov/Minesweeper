import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Created by Admin on 29.10.2017.
 */
public class Tile extends StackPane {

    private boolean bomb;
    private boolean opened;
    private boolean checked;

    public Tile() {
        getStyleClass().add("tile");
    }

    public void tileClicked(int x, int y, Tile[][] tiles) {
        if (bomb) {
            ((SaperGrid) getParent()).lost(x,y);
        } else {
            openTile(x, y, tiles);
        }
    }

    private void openTile(int x, int y, Tile[][] tiles) {
        opened = true;
        getStyleClass().add("opened");
        setOnMouseClicked(null);
        SaperGrid grid = (SaperGrid) getParent();
        int n = 0;
        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
                if (i < 0 || i > grid.WITHD - 1 || j < 0 || j > grid.HEIGHT - 1 || (i == x && j == y)) {
                    continue;
                } else {
                    if (tiles[i][j].bomb) {
                        n++;
                    }
                }
            }
        }
        if (n > 0) {
            Label label = new Label(n + "");
            Paint fill = Color.WHITE;
            switch (n) {
                case 1:
                    fill = Color.BLUE;
                    break;
                case 2:
                    fill = Color.GREEN;
                    break;
                case 3:
                    fill = Color.DARKRED;
                    break;
                case 4:
                    fill = Color.YELLOW;
                    break;
                case 5:
                    fill = Color.PURPLE;
                    break;
                case 6:
                    fill = Color.AQUAMARINE;
                    break;
                case 7:
                    fill = Color.AZURE;
                    break;
            }
            label.setTextFill(fill);
            label.getStyleClass().add("count");
            getChildren().add(label);
        } else {
            for (int i = x - 1; i < x + 2; i++) {
                for (int j = y - 1; j < y + 2; j++) {
                    if (i < 0 || i > grid.WITHD - 1 || j < 0 || j > grid.HEIGHT - 1 || (i == x && j == y)) {
                        continue;
                    }
                    if (!tiles[i][j].opened)
                        tiles[i][j].openTile(i, j, tiles);
                }
            }
        }
    }

    public void setBomb(boolean bomb) {
        this.bomb = bomb;
    }

    public boolean isBomb() {
        return bomb;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        if (checked) {
            getStyleClass().add("checked");
        } else {
            getStyleClass().remove("checked");
        }
    }

    public void drawBomb(boolean lost) {
        Label label = new Label("X");
        label.getStyleClass().add("count");
        if (lost) {
            label.setTextFill(Color.RED);
        }
        getChildren().add(label);
    }

    public boolean isOpened() {
        return opened;
    }
}
