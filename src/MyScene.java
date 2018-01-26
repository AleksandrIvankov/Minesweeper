import javafx.scene.Scene;

/**
 * Created by Admin on 29.10.2017.
 */
public class MyScene extends Scene {

    public MyScene(int width, int height, int bombs) {
        super(new GameRoot(width, height, bombs), width * 35 + width - 11, 64+height*35+height);
        getStylesheets().add("app.css");
    }
}