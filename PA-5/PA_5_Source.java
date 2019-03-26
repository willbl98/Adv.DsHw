package pa5;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pa5.helpers.controller.MainUICtrl;
import pa5.helpers.model.AggregateSchedule;
import pa5.helpers.model.Schedule;
import pa5.helpers.model.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class PA_5_Source extends Application {

    // Display the GUI elements
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(PA_5_Source.class.getResource("helpers/view/pa5.fxml"));
        MainUICtrl mainUICtrl = new MainUICtrl();
        fxmlLoader.setController(mainUICtrl);
        Parent layout = fxmlLoader.load();
        Scene scene = new Scene(layout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Run program
    public static void main(String[] args) {
        launch(args);
        // Here for testing convenience
        ToDoList toDoList = new ToDoList();
        System.out.println("Max Value: " + toDoList.calcMaxProfit());
        System.out.println(toDoList.findAllSchedules());
        System.exit(0);
    }

    // ToDoList is made an inner class to make finding the 'main' code for the assignment easier
    public static class ToDoList {
        private ObservableList<Task> tasks = FXCollections.observableArrayList();

        public ToDoList() {
            // Values from in-class example. Should be 13 as max profit. Here for convenience during tests
            tasks.add(new Task("1", 5, 1, 4));
            tasks.add(new Task("2", 1, 3, 5));
            tasks.add(new Task("3", 8, 0, 6));
            tasks.add(new Task("4", 4, 4, 7));
            tasks.add(new Task("7", 2, 6, 10));
            tasks.add(new Task("8", 4, 8, 11));
            tasks.add(new Task("5", 6, 3, 8));
            tasks.add(new Task("6", 3, 5, 9));
        }

        public ObservableList<Task> getTasks() {
            return tasks;
        }

        public void addTask(Task newTask) {
            tasks.add(newTask);
        }

        public void removeTask(Task task) {
            tasks.remove(task);
        }

        public boolean isEmpty() {
            return tasks.isEmpty();
        }

        private boolean hasPrevious(int n, int m) {
            if (n == 0) return false;
            return tasks.get(n).getStart() >= tasks.get(m).getEnd();
        }

        // Allows user to input tasks in any order. Sorts based on end time.
        public void sortList() {
            tasks.sort((Task t1, Task t2) -> t2.compareEndTime(t1));
        }

        // Bottom up method to display all 'legitimate' sets of tasks. A legitimate set (1) includes as many tasks as
        // possible regardless of the total earning and (2) no two sets can be subsets of each other.
        public HashMap<Integer, ArrayList<Schedule>> findAllSchedules() {
            // Initialize dp map/list
            ArrayList<AggregateSchedule> aggregateSchedules = new ArrayList<>(initDPMap());
            HashMap<Integer, ArrayList<Schedule>> results = new HashMap<>();
            // Begin with the first task
            for (int i = 1; i < tasks.size(); i++) {
                AggregateSchedule aggregateSchedule = new AggregateSchedule(tasks.size());
                // Iterate through tasks that occur prior to the current task
                for (int j = i - 1; j >= 0; j--) {
                /* Check if the previous task's end time conflicts with the currents start time.
                   Then check if that task has already been scheduled, i.e., the current task
                   has inherited the task from another previous task.

                   For example, if task if task 4 allow for doing task 1, and task 7 allows for
                   doing task 4, then there is no need to check if 7 can do 1 since it inherits it
                   from 4.
                */
                    // Add all the possible combinations from this schedule to the aggregateSchedule
                    if (hasPrevious(i, j) && aggregateSchedule.isUnscheduled(j)) {
                        for (Schedule inheritedSchedule : aggregateSchedules.get(j).getScheduledTasks()) {
                            Schedule currentSchedule = new Schedule(inheritedSchedule, tasks.get(i));
                            aggregateSchedule.addTasks(currentSchedule, aggregateSchedules.get(j).getUnscheduled());
                        }
                        // Removes tasks from results which can be completed prior to other tasks. Avoids results that
                        // contains sets which are subsets of another
                        results.remove(j);
                    }
                }
                // There is no tasks that can be accomplished prior to this current 1
                if (aggregateSchedule.getScheduledTasks().isEmpty()) {
                    aggregateSchedule.addTask(tasks.get(i), i);
                }
                results.put(i, aggregateSchedule.getScheduledTasks());
                aggregateSchedules.add(i, aggregateSchedule);
            }
            return results;
        }

        // Calculate max profit and the tasks involved using the dynamic programming method discussed in class.
        public Schedule calcMaxProfit() {
            Schedule[] maxProfitTasks = new Schedule[tasks.size()];
            maxProfitTasks[0] = new Schedule(tasks.get(0));

            for (int i = 1; i < tasks.size(); i++) {
                Schedule schedule = new Schedule(tasks.get(i));
                int value = tasks.get(i).getValue();
                for (int j = i - 1; j >= 0; j--) {
                    if (hasPrevious(i, j)) {
                        value += maxProfitTasks[j].getProfit();
                        schedule.mergeSchedule(maxProfitTasks[j]);
                        break;
                    }
                }
                if (value > maxProfitTasks[i - 1].getProfit()) {
                    maxProfitTasks[i] = schedule;
                } else {
                    maxProfitTasks[i] = maxProfitTasks[i - 1];
                }
            }
            return maxProfitTasks[tasks.size() - 1];
        }

        // Adds first task, the one with the smallest end time, to the map
        private ArrayList<AggregateSchedule> initDPMap() {
            ArrayList<AggregateSchedule> dpArray = new ArrayList<>();
            AggregateSchedule aggregateSchedule = new AggregateSchedule(tasks.size());
            aggregateSchedule.addTask(tasks.get(0), 0);
            dpArray.add(0, aggregateSchedule);
            return dpArray;
        }
    }
}
