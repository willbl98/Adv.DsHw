package pa5.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pa5.PA_5_Source;
import pa5.model.Task;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the Main GUI.  Allows user to enter and clear task data.
 */
public class UserInController implements Initializable {
    private final TableView<Task> taskTableView = new TableView<>(); // table for user entered tasks
    private PA_5_Source.ToDoList _toDoList;  // Class to process user tasks

    @FXML
    private VBox _tableView_tasks; // holds table

    @FXML
    private TextField _textField_name;

    @FXML
    private TextField _textField_value;

    @FXML
    private TextField _textField_start;

    @FXML
    private TextField _textField_end;

    @FXML
    private Button _button_addTask;

    @FXML
    private Button _button_bulkAdd;

    @FXML
    private Button _button_removeTask;

    @FXML
    private Button _button_removeAllTasks;

    @FXML
    private Button _button_calc;

    @FXML
    private Button _button_sort;

    // Create table of tasks to hold and display user data of Task objects
    private TableView<Task> createTable(TableView<Task> tableView, ObservableList<Task> tasks) {

        // Create a column for each Task field
        TableColumn<Task, String> name = new TableColumn<>("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Task, String> value = new TableColumn<>("Value");
        value.setCellValueFactory(new PropertyValueFactory<>("value"));

        TableColumn<Task, String> start = new TableColumn<>("Start");
        start.setCellValueFactory(new PropertyValueFactory<>("start"));

        TableColumn<Task, String> end = new TableColumn<>("End");
        end.setCellValueFactory(new PropertyValueFactory<>("end"));

        tableView.setItems(tasks);

        // Add fields
        tableView.getColumns().add(name);
        tableView.getColumns().add(value);
        tableView.getColumns().add(start);
        tableView.getColumns().add(end);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return tableView;
    }

    // Use information from the text fields to add data to Task table
    private void addTask() {
        // Check if name exists
        if (_textField_name.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Task must contain a name");
            alert.setTitle("Task Data error");
            alert.showAndWait();
            return;
        } else if (_toDoList.taskAlreadyExists(_textField_name.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Task already added");
            alert.setTitle("Task Data error");
            alert.showAndWait();
            return;
        }
        // Check if numeric fields are valid
        try {
            Task newTask = new Task(
                    _textField_name.getText(),
                    Integer.parseInt(_textField_value.getText()),
                    Integer.parseInt(_textField_start.getText()),
                    Integer.parseInt(_textField_end.getText())
            );
            _toDoList.addTask(newTask);
        } catch (NumberFormatException ignored) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid task data.\n" +
                    "Tasks must include numeric values for Start,End and Value.");
            alert.setTitle("Task Data error");
            alert.showAndWait();
        }
        clearTextFields();
    }

    // Opens a Window for user to enter tasks as csv style data:
    // For example: Task2,1,3,4 where Task2 is the name, 1 is the value and 3 & 4 are the start and end times
    private void addTaskBulk() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(UserInController.class.getResource("/pa5/view/textEntry.fxml"));
        Stage popupStage = new Stage();
        BulkEntryController setupMenuController = new BulkEntryController(_toDoList.getTasks(), popupStage);
        fxmlLoader.setController(setupMenuController);

        Parent layout = fxmlLoader.load();
        Scene scene = new Scene(layout);

        popupStage.setTitle("Bulk Entries");
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }

    // Remove highlighted table task
    private void removeTask() {
        if (_toDoList.isEmpty()) return;
        TablePosition<?, ?> tablePosition = taskTableView.getSelectionModel().getSelectedCells().get(0);
        int row = tablePosition.getRow();
        Task task = taskTableView.getItems().get(row);
        _toDoList.removeTask(task);
    }

    // Remove all tasks from table
    private void removeAll() {
        taskTableView.getItems().clear();
    }

    // Clear fields and reset prompt text for next data entry after a task is added
    private void clearTextFields() {
        _textField_name.clear();
        _textField_name.promptTextProperty().setValue("Name e.g. Task1");

        _textField_value.clear();
        _textField_value.promptTextProperty().setValue("Value e.g. 400");

        _textField_start.clear();
        _textField_start.promptTextProperty().setValue("End Time e.g. 3");

        _textField_end.clear();
        _textField_end.promptTextProperty().setValue("End Time e.g. 5");

        _button_calc.requestFocus();
    }

    // Opens a new window and displays the max profit Schedule as well as all legitimate schedules
    private void displayResults() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(UserInController.class.getResource("/pa5/view/popup.fxml"));

        ResultsController setupMenuController = new ResultsController(_toDoList);
        fxmlLoader.setController(setupMenuController);

        Parent layout = fxmlLoader.load();
        Scene scene = new Scene(layout);
        Stage popupStage = new Stage();

        popupStage.setTitle("PA-5 Results");
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }

    // Initialize/assign each GUI element to a method
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Assign buttons to methods
        _button_addTask.setOnAction(e -> addTask());
        _button_removeTask.setOnAction(e -> removeTask());
        _button_sort.setOnAction(e -> _toDoList.sortList());
        _button_removeAllTasks.setOnAction(e -> removeAll());
        _button_bulkAdd.setOnAction(e -> {
            try {
                addTaskBulk();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        _button_calc.setOnAction(e -> {
            try {
                displayResults();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        // Create and initialize ToDoList object
        _toDoList = new PA_5_Source.ToDoList();
        _tableView_tasks.getChildren().addAll(createTable(taskTableView, _toDoList.getTasks()));

        // Remove cyan highlighting from text fields
        taskTableView.setFocusTraversable(false);
        _textField_name.setStyle("-fx-focus-color: transparent;");
        _textField_value.setStyle("-fx-focus-color: transparent;");
        _textField_start.setStyle("-fx-focus-color: transparent;");
        _textField_end.setStyle("-fx-focus-color: transparent;");
    }
}
