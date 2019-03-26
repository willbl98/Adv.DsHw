package pa5;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pa5.controller.UserInController;
import pa5.model.LegitimateScheduleSet;
import pa5.model.Schedule;
import pa5.model.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class PA_5_Source extends Application {

    // Display the GUI elements
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(PA_5_Source.class.getResource("view/pa5.fxml"));
        UserInController userInController = new UserInController();
        fxmlLoader.setController(userInController);
        Parent layout = fxmlLoader.load();
        Scene scene = new Scene(layout);
        primaryStage.setTitle("PA-5");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Run program
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * ToDoList is made an inner class to make finding the 'main' code for the assignment easier.
     * ToDoList represents all the available tasks the user needs/wants to do.  It contains a method, calcMaxProfit
     * which determines which set of tasks (Schedule) will provide the most profit.  It also contains a method,
     * findAllSchedules, to determine which sets of tasks can be accomplished that maximized the available time but
     * not necessarily the profit.
     */
    public static class ToDoList {
        // List of user entered tasks.  Updates as user enters/removes tasks
        private final ObservableList<Task> tasks = FXCollections.observableArrayList();

        public ObservableList<Task> getTasks() {
            return tasks;
        }

        public boolean taskAlreadyExists(String name) {
            return tasks.stream().map(Task::getName).anyMatch(name::equals);
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

            // Initialize map/list used for the dynamic programming solution
            ArrayList<LegitimateScheduleSet> legitimateScheduleSets = new ArrayList<>(initDPMap());
            HashMap<Integer, ArrayList<Schedule>> results = new HashMap<>();

            // Begin with the second task since first is added during initialization
            for (int i = 1; i < tasks.size(); i++) {
                LegitimateScheduleSet legitimateScheduleSet = new LegitimateScheduleSet(tasks.size());
                // Iterate through tasks that occur prior to the current task
                for (int j = i - 1; j >= 0; j--) {
                /* Check if the previous task's end time conflicts with the currents start time.
                   Then check if that task has already been scheduled, i.e., the current task
                   has inherited the task from another previous task.

                   For example, if task if task 4 allow for doing task 1, and task 7 allows for
                   doing task 4, then there is no need to check if 7 can do 1 since it inherits it
                   from 4.
                */
                    // Add all the possible combinations from this schedule to the legitimateScheduleSet
                    if (hasPrevious(i, j) && legitimateScheduleSet.isUnscheduled(j)) {
                        for (Schedule inheritedSchedule : legitimateScheduleSets.get(j).getLegitimatedSchedules()) {
                            Schedule currentSchedule = new Schedule(inheritedSchedule, tasks.get(i));
                            legitimateScheduleSet.addSchedules(currentSchedule, legitimateScheduleSets.get(j).getUnscheduled());
                        }
                        // Removes tasks from results which can be completed prior to other tasks. Avoids results that
                        // contains sets which are subsets of another
                        results.remove(j);
                    }
                }
                // There is no tasks that can be accomplished prior to this current 1
                if (legitimateScheduleSet.getLegitimatedSchedules().isEmpty()) {
                    legitimateScheduleSet.addTask(tasks.get(i), i);
                }
                results.put(i, legitimateScheduleSet.getLegitimatedSchedules());
                legitimateScheduleSets.add(i, legitimateScheduleSet);
            }
            return results;
        }

        /**
         * Calculate max profit and the tasks involved using the dynamic programming method discussed in class.
         *
         * @return Return the max profit and the tasks involved
         */
        public ArrayList<Schedule> calcMaxProfit() {

            // Initial value profit from the first task, i.e. the task with the smalled end time.
            ArrayList<Schedule> maxProfitTasks = new ArrayList<>(tasks.size());
            maxProfitTasks.add(0, new Schedule(tasks.get(0)));

            // Holds top profit schedules in case there are multiple results that maximize profits
            ArrayList<Schedule> results = new ArrayList<>();

            // Traverse in order of tasks end times
            for (int i = 1; i < tasks.size(); i++) {
                Schedule schedule = new Schedule(tasks.get(i));
                int value = tasks.get(i).getValue();
                for (int j = i - 1; j >= 0; j--) {
                    // If task has a previous task it can accmplish without a time conflict, add it the schedule
                    if (hasPrevious(i, j)) {
                        value += maxProfitTasks.get(j).getProfit();
                        schedule.mergeSchedule(maxProfitTasks.get(j));
                        break;
                    }
                }
                // If the value of the current schedule and its first previous penitential schedules exceeds the current
                // max profit, update the value. Else keep the current max profit and scheduled tasks.
                if (value >= maxProfitTasks.get(i - 1).getProfit()) {
                    if (value == maxProfitTasks.get(i - 1).getProfit()) {
                        maxProfitTasks.add(i, schedule);
                        results.add(schedule);
                    } else {
                        maxProfitTasks.add(i, schedule);
                        results.clear();
                        results.add(schedule);
                    }
                } else {
                    maxProfitTasks.add(i, maxProfitTasks.get(i - 1));
                }
            }
            return results;
        }

        /**
         * Initialized the map used for the dynamic programming solution
         *
         * @return map with the first task, the one with the smallest end time, added to the map
         */
        private ArrayList<LegitimateScheduleSet> initDPMap() {
            ArrayList<LegitimateScheduleSet> dpArray = new ArrayList<>();
            LegitimateScheduleSet legitimateScheduleSet = new LegitimateScheduleSet(tasks.size());
            legitimateScheduleSet.addTask(tasks.get(0), 0);
            dpArray.add(0, legitimateScheduleSet);
            return dpArray;
        }
    }
}
