package pa5;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

class Schedule {
    private final ObservableList<Task> tasks = FXCollections.observableArrayList();
    Schedule(){}

    ObservableList<Task> getTasks() {
        return tasks;
    }

    void addTask(Task newTask) {
        tasks.add(newTask);
    }
}
