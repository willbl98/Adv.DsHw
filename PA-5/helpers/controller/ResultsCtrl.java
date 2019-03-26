package pa5.helpers.controller;

import pa5.PA_5_Source;
import pa5.helpers.model.Schedule;
import pa5.helpers.model.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ResultsCtrl extends Controller implements Initializable {
    private final PA_5_Source.ToDoList toDoList;

    @FXML
    private TextArea _textArea_output;

    ResultsCtrl(PA_5_Source.ToDoList toDoList) {
        this.toDoList = toDoList;
    }

    protected void setStage(Stage stage) {
    }

    // Output all 'legitimate' combinations, ie, those that maximize tasks in the amount of time
    private void outputMax() {
        Schedule s = toDoList.calcMaxProfit();
        String names = s.getTasks().stream().map(Task::getName)
                .collect(Collectors.joining("->"));
        _textArea_output.appendText("Max Profit: " + names + " with a max of " + s.getProfit());
        _textArea_output.appendText("\n\n");
    }

    private void outputAllLegitimate() {
        _textArea_output.appendText("All Legitimate Schedule Options:\n");
        for (ArrayList<Schedule> schedules : toDoList.findAllSchedules().values()) {
            for (Schedule s : schedules) {
                String names = s.getTasks().stream().map(Task::getName)
                        .collect(Collectors.joining("->"));
                _textArea_output.appendText(names + " with a total earnings of " + s.getProfit());
                _textArea_output.appendText("\n");
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        toDoList.sortList();
        outputMax();
        outputAllLegitimate();
    }
}
