import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;


public class LeaderGui extends Stage {


    public LeaderGui(Leaderboard leaderboard, int bombs) {
        VBox root = new VBox();
        HBox top = new HBox();
        Button novice = new Button("novice");
        Button advanced = new Button("advanced");
        Button hard = new Button("hard");
        novice.setOnAction(event -> {
            root.getChildren().remove(1);
            root.getChildren().add(fillTable(leaderboard, 10));
        });
        advanced.setOnAction(event -> {
            root.getChildren().remove(1);
            root.getChildren().add(fillTable(leaderboard, 40));
        });
        hard.setOnAction(event -> {
            root.getChildren().remove(1);
            root.getChildren().add(fillTable(leaderboard, 99));
        });
        top.getChildren().addAll(novice, advanced, hard);
        root.getChildren().addAll(top, fillTable(leaderboard, bombs));
        Scene scene = new Scene(root, 440, 200);
        setResizable(false);
        scene.getStylesheets().add("leader.css");
        setScene(scene);
        setAlwaysOnTop(true);
    }

    private VBox fillTable(Leaderboard leaderboard, int bombs) {
        VBox vBox = new VBox();
        List<Record> records;
        switch (bombs) {
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
        HBox top = new HBox();
        top.getChildren().addAll(new Label("#"), new Label("Name"), new Label("Time"));
        vBox.getChildren().add(top);
        int n = 1;
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
        for (Record record : records) {
            HBox hBox = new HBox();
            hBox.getChildren().addAll(new Label(n + ""), new Label(record.getName()), new Label(df.format(record.getTime() / 1000f)));
            vBox.getChildren().add(hBox);
            n++;
        }
        return vBox;
    }
}
