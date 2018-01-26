import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * Created by Admin on 29.10.2017.
 */
public class Header extends BorderPane {
    public Header() {
        setCenter(new Label("Kappa123"));
        Label bombLeft = new Label("0");
        bombLeft.getStyleClass().add("timer");
        bombLeft.setAlignment(Pos.CENTER_LEFT);
        setLeft(bombLeft);
        setPrefHeight(50);
        Label timer = new Label("0.00");
        timer.getStyleClass().add("timer");
        timer.setAlignment(Pos.CENTER_RIGHT);
        setRight(timer);
        setPrefHeight(50);
    }
}
