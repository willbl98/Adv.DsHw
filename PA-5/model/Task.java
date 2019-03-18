package pa5.model;

@SuppressWarnings("WeakerAccess")
public class Task {
    private final String name;
    private final int value;
    private final int start;
    private final int end;

    public Task(String name, int value, int start, int end) {
        this.name = name;
        this.value = value;
        this.start = start;
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return getName();
    }
}
