import java.io.Externalizable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Leaderboard implements Serializable {

    private List<Record> novice;
    private List<Record> advanced;
    private List<Record> hard;

    public Leaderboard() {
        novice = new ArrayList<>();
        advanced = new ArrayList<>();
        hard = new ArrayList<>();
    }

    public List<Record> getNovice() {
        return novice;
    }

    public List<Record> getAdvanced() {
        return advanced;
    }

    public List<Record> getHard() {
        return hard;
    }
}
