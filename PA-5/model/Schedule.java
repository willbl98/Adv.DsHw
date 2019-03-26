package pa5.model;

import java.util.ArrayList;

/**
 * Represents a grouping of tasks which can be completed without time conflicts. Contains the aforementioned
 * tasks as well as their combined profit.
 *
 * and example Schedule object would contain: (1000, [Task1, Task6, Task8]), where 1000 is the profit and the list
 * contains the tasks involved
 */
public class Schedule {
    private ArrayList<Task> scheduledTasks = new ArrayList<>();
    private int profit;

    public Schedule(Task task) {
        profit = task.getValue();
        addTaskToSchedule(task);
    }

    // Used when initializing a schedule and merging it
    public Schedule(Schedule scheduledTasks, Task task) {
        // Add previously calculated tasks
        this.scheduledTasks.addAll(scheduledTasks.getTasks());
        // Add current task
        addTaskToSchedule(task);
        // Applies dynamic programming principle to reuse previous profit sum
        profit = scheduledTasks.getProfit() + task.getValue();
    }

    public ArrayList<Task> getTasks() {
        return scheduledTasks;
    }

    public int getProfit() {
        return profit;
    }

    private void addTaskToSchedule(Task task) {
        scheduledTasks.add(task);
    }

    // Since dynamic programming is used, can simply merge previously compatible schedules and without having to
    // recalculate them.
    public void mergeSchedule(Schedule schedule) {
        scheduledTasks.addAll(0, schedule.getTasks());
        profit += schedule.getProfit();
    }

    @Override
    public String toString() {
        return scheduledTasks + " " + profit;
    }
}
