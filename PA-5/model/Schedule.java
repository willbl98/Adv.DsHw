package pa5.model;

import java.util.ArrayList;

/**
 * Represents a grouping of tasks which can be completed without schedule conflicts. Contains the aforementioned
 * tasks as well as their combined profit.
 */
public class Schedule {
    private ArrayList<Task> scheduledTasks = new ArrayList<>();
    private int profit;

    Schedule(Task task) {
        profit = task.getValue();
        addTask(task);
    }

    Schedule(Schedule scheduledTasks, Task task) {
        this.scheduledTasks.addAll(scheduledTasks.getTasks());
        addTask(task);
        // Applies dp principle to reuse previous profit sum
        profit = scheduledTasks.getProfit() + task.getValue();
    }

    public ArrayList<Task> getTasks() {
        return scheduledTasks;
    }

    public int getProfit() {
        return profit;
    }

    private void addTask(Task task) {
        scheduledTasks.add(task);
    }

    void mergeSchedule(Schedule schedule) {
        scheduledTasks.addAll(0, schedule.getTasks());
        profit += schedule.getProfit();
    }

    @Override
    public String toString() {
        return scheduledTasks + " " + profit;
    }
}
