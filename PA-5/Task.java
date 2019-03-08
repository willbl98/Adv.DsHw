package pa5;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Task {
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty value;
    private final SimpleIntegerProperty duration;

    Task(String name, int value, int duration) {
        this.name = new SimpleStringProperty(name);
        this.value = new SimpleIntegerProperty(value);
        this.duration = new SimpleIntegerProperty(duration);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public int getValue() {
        return value.get();
    }

    public SimpleIntegerProperty valueProperty() {
        return value;
    }

    public int getDuration() {
        return duration.get();
    }

    public SimpleIntegerProperty durationProperty() {
        return duration;
    }
}
