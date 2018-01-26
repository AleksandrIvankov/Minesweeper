import java.io.*;

public class MyDB {

    private static MyDB instance;
    private final File file;

    private MyDB() {
        file = new File("leaderboard.db");
    }

    public static MyDB getInstance() {
        if (instance == null) {
            instance = new MyDB();
        }
        return instance;
    }

    public Leaderboard getLeaderboard() {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ignored) {
            }
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return ((Leaderboard) ois.readObject());
        } catch (IOException | ClassNotFoundException e) {
            return new Leaderboard();
        }
    }

    public void saveLeaderboard(Leaderboard leaderboard) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(leaderboard);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
