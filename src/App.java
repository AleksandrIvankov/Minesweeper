import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

/**
 * Created by Admin on 29.10.2017.
 */
public class App extends Application {

    public static void main(String[] args) {

        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setScene(new MyScene(9, 9, 10));
        primaryStage.setResizable(false);
        primaryStage.show();

    }
}
