package pa5;

public class Task {
    private final String name;
    private final int value;
    private final int start;
    private final int end;

    Task(String name, int value, int start, int end) {
        this.name = name;
        this.value = value;
        this.start = start;
        this.end = end;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
