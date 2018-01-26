import java.io.Serializable;

public class Record implements Serializable, Comparable<Record> {

    private String name;
    private long time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public int compareTo(Record o) {
        if (time > o.getTime())
            return 1;
        if (time < o.getTime())
            return -1;
        return 0;
    }
}
