package pa5.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import pa5.model.Task;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Allows user to enter several tasks in a csv style manner by opening a text window. Example entries:
 * <p>
 * Task1,400,13,16
 * Task2,200,11,17
 */
public class BulkEntryController implements Initializable {
    private final ObservableList<Task> tasks;
    private final Stage stage;
    @FXML
    private TextArea _textArea_input;

    @FXML
    private Button _button_addAll;

    /**
     * @param tasks tasks to hold user entries
     * @param stage textArea window. Close if entered fields are valid when user selects Add All
     */
    BulkEntryController(ObservableList<Task> tasks, Stage stage) {
        this.tasks = tasks;
        this.stage = stage;
    }

    // Parse text into tasks
    private boolean parseUserData() {
        String[] str = _textArea_input.getText().split("\n");
        try {
            for (String s : str) {
                String[] taskData = s.split(",");
                if (tasks.stream().map(Task::getName).anyMatch(taskData[0]::equals)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Task already added");
                    alert.setTitle("Task Data error");
                    alert.showAndWait();
                    return false;
                }
                tasks.add(new Task(taskData[0], Integer.parseInt(taskData[1]),
                        Integer.parseInt(taskData[2]), Integer.parseInt(taskData[3])));
            }
        } catch (ArrayIndexOutOfBoundsException e1) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid task data.\n" +
                    "Tasks must include numeric values for Start,End and Value and a name");
            alert.setTitle("Task Data error");
            alert.showAndWait();
            return false;
        } catch (NumberFormatException e2) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid task data.\n" +
                    "Tasks must include numeric values for Start,End and Value.");
            alert.setTitle("Task Data error");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        _button_addAll.setOnAction(e -> {
            if (parseUserData()) {
                stage.close();
            }
        });
    }
}
