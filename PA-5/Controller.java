package pa5;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

class Controller implements Initializable {
    private final TableView<Task> f_tasks = new TableView<>();

    private Schedule schedule;

    @FXML
    private VBox _tableView_tasks;

    @FXML
    private TextField _textField_name;

    @FXML
    private TextField _textField_value;

    @FXML
    private TextField _textField_duration;

    @FXML
    private Button _button_addTask;

    private TableView<Task> updateTable(TableView<Task> tableView, ObservableList<Task> tasks) {

        TableColumn<Task, String> name = new TableColumn<>("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Task, String> value = new TableColumn<>("Value");
        value.setCellValueFactory(new PropertyValueFactory<>("value"));

        TableColumn<Task, String> duration = new TableColumn<>("Duration");
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));

        tableView.setItems(tasks);

        tableView.getColumns().add(name);
        tableView.getColumns().add(value);
        tableView.getColumns().add(duration);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return tableView;
    }

    private void addTask() {
        Task newTask = new Task(_textField_name.getText(), Integer.parseInt(_textField_value.getText()),
                Integer.parseInt(String.valueOf(_textField_duration.getText())));
        schedule.addTask(newTask);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        _button_addTask.setOnAction(e -> addTask());
        schedule = new Schedule();
        _tableView_tasks.getChildren().addAll(updateTable(f_tasks, schedule.getTasks()));
    }
}
