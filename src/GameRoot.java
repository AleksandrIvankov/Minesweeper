import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.URISyntaxException;

/**
 * Created by Admin on 29.10.2017.
 */
public class GameRoot extends VBox {

    public GameRoot(int withd, int height, int bombs) {
        getChildren().add(new GameMenu(withd, height, bombs));
        getChildren().add(new Header());
        getChildren().add(new SaperGrid(withd, height, bombs));
        MyAudio.getInstance().playGo();
    }
}
