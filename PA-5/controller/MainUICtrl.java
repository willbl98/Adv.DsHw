package pa5.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pa5.model.Task;
import pa5.model.ToDoList;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class MainUICtrl extends Controller implements Initializable {
    private final TableView<Task> f_tasks = new TableView<>();

    private ToDoList toDoList;

    @FXML
    private VBox _tableView_tasks;

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
    private Button _button_removeTask;

    @FXML
    private Button _button_calc;
    private Stage _stage;

    private TableView<Task> updateTable(TableView<Task> tableView, ObservableList<Task> tasks) {

        TableColumn<Task, String> name = new TableColumn<>("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Task, String> value = new TableColumn<>("Value");
        value.setCellValueFactory(new PropertyValueFactory<>("value"));

        TableColumn<Task, String> start = new TableColumn<>("Start");
        start.setCellValueFactory(new PropertyValueFactory<>("start"));

        TableColumn<Task, String> end = new TableColumn<>("End");
        end.setCellValueFactory(new PropertyValueFactory<>("end"));

        tableView.setItems(tasks);

        tableView.getColumns().add(name);
        tableView.getColumns().add(value);
        tableView.getColumns().add(start);
        tableView.getColumns().add(end);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return tableView;
    }

    private void addTask() {
        if (_textField_name.getText().equals("")) return;
        try {
            Task newTask = new Task(
                    _textField_name.getText(),
                    Integer.parseInt(_textField_value.getText()),
                    Integer.parseInt(_textField_start.getText()),
                    Integer.parseInt(_textField_end.getText())
            );
            toDoList.addTask(newTask);
        } catch (NumberFormatException ignored) {
        }
        clearTextFields();
    }

    private void removeTask() {
        if (toDoList.isEmpty()) return;
        TablePosition<?, ?> tablePosition = f_tasks.getSelectionModel().getSelectedCells().get(0);
        int row = tablePosition.getRow();
        Task task = f_tasks.getItems().get(row);
        toDoList.removeTask(task);
        f_tasks.getItems().clear();
    }

    private void clearTextFields() {
        _textField_name.clear();
        _textField_value.clear();
        _textField_start.clear();
        _textField_end.clear();
        _button_calc.requestFocus();
    }

    private void displayResults() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(MainUICtrl.class.getResource("../view/popup.fxml"));

        ResultsCtrl setupMenuController = new ResultsCtrl(toDoList);
        showNewWindow(fxmlLoader, setupMenuController);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        _button_addTask.setOnAction(e -> addTask());
        _button_removeTask.setOnAction(e -> removeTask());
        _button_calc.setOnAction(e -> {
            try {
                displayResults();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        toDoList = new ToDoList();
        _tableView_tasks.getChildren().addAll(updateTable(f_tasks, toDoList.getTasks()));
        f_tasks.setFocusTraversable(false);
        _textField_name.setStyle("-fx-focus-color: transparent;");
        _textField_value.setStyle("-fx-focus-color: transparent;");
        _textField_start.setStyle("-fx-focus-color: transparent;");
        _textField_end.setStyle("-fx-focus-color: transparent;");
    }

    @Override
    protected void setStage(Stage stage) {
        _stage = stage;
    }
}
