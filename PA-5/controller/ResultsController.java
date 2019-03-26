package pa5.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import pa5.PA_5_Source;
import pa5.model.Schedule;
import pa5.model.Task;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Window which displays information regarding the tasks which yield the
 */
public class ResultsController implements Initializable {
    private final PA_5_Source.ToDoList toDoList;

    // GUI fields
    @FXML
    private TextArea _textArea_output;

    ResultsController(PA_5_Source.ToDoList toDoList) {
        this.toDoList = toDoList;
    }

    // Output all 'legitimate' combinations, ie, those that maximize tasks in the amount of time
    private void outputMax() {
        for (Schedule s : toDoList.calcMaxProfit()) {
            String names = s.getTasks().stream().map(Task::getName)
                    .collect(Collectors.joining("->"));
            _textArea_output.appendText("Max Profit: " + names + " with a max of " + s.getProfit());
            _textArea_output.appendText("\n");
        }
        _textArea_output.appendText("\n");
    }


    // Output all 'legitimate' combinations, ie, combinations of tasks with no conflicts and in which no combination
    // is a subset of another
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

    // Initialize/assign each GUI element to a method
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        toDoList.sortList();
        outputMax();
        outputAllLegitimate();
    }
}
