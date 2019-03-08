package pa5;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

class Controller implements Initializable {
    private final TableView<Task> f_tasks = new TableView<>();

    @FXML
    private VBox table;

    private TableView<Task> updateTable(TableView<Task> tableView, ObservableList<Task> tasks) {

        TableColumn<Task, String> name = new TableColumn<>("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Task, String> value = new TableColumn<>("Value");
        value.setCellValueFactory(new PropertyValueFactory<>("value"));

        TableColumn<Task, String> start = new TableColumn<>("Value");
        start.setCellValueFactory(new PropertyValueFactory<>("value"));

        TableColumn<Task, String> end = new TableColumn<>("Value");
        end.setCellValueFactory(new PropertyValueFactory<>("value"));

        tableView.setItems(tasks);
        tableView.setEditable(true);

        tableView.getColumns().add(name);
        tableView.getColumns().add(value);
        tableView.getColumns().add(start);
        tableView.getColumns().add(end);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return tableView;
    }

    private ObservableList<Task> createDummyList() {
        ObservableList<Task> tasks = FXCollections.observableArrayList();
        for (int i = 0; i < 20; i++) {
            tasks.add(new Task(String.valueOf(i), i, i, i));
        }
        return tasks;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Task> cat = createDummyList();
        table.getChildren().addAll(updateTable(f_tasks, cat));
    }
}
