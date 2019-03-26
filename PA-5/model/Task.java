package pa5.model;

/**
 * Represents a user specified Task to be completed.  Tasks have a name, a value/profit, and a start and end time.
 *
 * Start and End time should follow the format of: 0,1,2,3... etc. not 0:00, 1:00, 2:00...
 */
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

    // Compares end times when sorting tasks
    public int compareEndTime(Task task) {
        return Integer.compare(task.getEnd(), getEnd());
    }

    @Override
    public String toString() {
        return getName();
    }
}
