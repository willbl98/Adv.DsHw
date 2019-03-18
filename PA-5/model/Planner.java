package pa5.model;

import java.util.ArrayList;

/**
 * A Planner holds information regarding all possible schedules that can be completed with the desired task.
 * For instance, using the class example:
 * <p>
 * Task("1", 5, 1, 4);
 * Task("2", 1, 3, 5);
 * Task("6", 3, 5, 9);
 * <p>
 * Task 6 has enough time to also accomplish Task 1 or Task 2.  The Planner holds both of those potential schedules in
 * the scheduledTasks array.
 * <p>
 * The Planner's unScheduledTasks boolean array is used to determine whether a scheduled task has caused it to inherit
 * other tasks.  This is done to incorporate a principle element of dynamic programming to avoid preforming tasks
 * more than is necessary.
 */
public class Planner {

    private ArrayList<Boolean> unScheduledTasks = new ArrayList<>();
    private ArrayList<Schedule> scheduledTasks = new ArrayList<>();

    /**
     * Holds information regarding all possible schedules that can be completed with the desired task.
     *
     * @param size Used to initialize/create the unScheduledTasks array with a spot for each potential task
     */
    Planner(int size) {
        for (int i = 0; i < size; i++) {
            unScheduledTasks.add(i, true);
        }
    }

    ArrayList<Schedule> getScheduledTasks() {
        return scheduledTasks;
    }

    ArrayList<Boolean> getUnscheduled() {
        return unScheduledTasks;
    }

    boolean isUnscheduled(int i) {
        return unScheduledTasks.get(i);
    }

    void addTask(Task task, int toDoListIndex) {
        Schedule schedule = new Schedule(task);
        unScheduledTasks.set(toDoListIndex, false);
        scheduledTasks.add(schedule);
    }

    void addTasks(Schedule toDoListIndex, ArrayList<Boolean> add) {
        for (int i = 0; i < unScheduledTasks.size(); i++) {
            unScheduledTasks.set(i, add.get(i) && unScheduledTasks.get(i));
        }
        scheduledTasks.add(toDoListIndex);
    }

    @Override
    public String toString() {
        return String.valueOf(scheduledTasks);
    }
}
