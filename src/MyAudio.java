import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URISyntaxException;

public class MyAudio {

    private static MyAudio instance;

    private MyAudio() {

    }

    public static MyAudio getInstance() {
        if (instance == null) {
            instance = new MyAudio();
        }
        return instance;
    }

    public void playGo() {
        play("test.mp3");
    }

    public void playBomb() {
        play("bomb.mp3");
    }

    public void playWin() {
        play("excellent.mp3");
    }

    private void play(String name) {
        try {
            new MediaPlayer(new Media(getClass().getResource(name).toURI().toString())).play();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
