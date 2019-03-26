package pa5.model;

import java.util.ArrayList;

/**
 * A LegitimateScheduleSet holds information regarding all possible schedules that can be completed with the desired task.
 * For instance, using the in-class example:
 * <p>
 * Task("1", 5, 1, 4);
 * Task("2", 1, 3, 5);
 * Task("6", 3, 5, 9);
 * <p>
 * Task 6 has enough time to also accomplish Task 1 or Task 2 -- but it cannot do both without time conflicts. Its can
 * only produce a schedule containing tasks 1 & 6 or tasks 2 & 6.
 * The LegitimateScheduleSet holds both of these schedules, {[1,6], [2,6]} as they are both legitimate possibilities even
 * if there profits are different/not maximized.
 * <p>
 * The LegitimateScheduleSet's unScheduledTasks boolean array is used to determine whether a scheduled task has caused it to
 * inherit other tasks from previous schedules. For instance:
 * <p>
 * If Task5 allows for scheduling Task3, and Task8 allows for scheduling Task5, unScheduledTasks would alert the
 * findAllSchedules in the PA-5-Source file that Task3 is already scheduled for Task8 since it has inherited it from
 * Task5 and can be skipped. This is done to incorporate a principle element of dynamic programming, to avoid preforming
 * tasks more than is necessary by tracking previously completed steps.
 */
public class LegitimateScheduleSet {

    private ArrayList<Boolean> unScheduledTasks = new ArrayList<>();
    private ArrayList<Schedule> legitimatedSchedules = new ArrayList<>();

    // Holds information regarding all possible schedules that can be completed with the desired task.
    public LegitimateScheduleSet(int size) {
        // Initialize/create the unScheduledTasks array with a spot for each potential task
        for (int i = 0; i < size; i++) {
            unScheduledTasks.add(i, true);
        }
    }

    public ArrayList<Schedule> getLegitimatedSchedules() {
        return legitimatedSchedules;
    }

    public ArrayList<Boolean> getUnscheduled() {
        return unScheduledTasks;
    }

    public boolean isUnscheduled(int i) {
        return unScheduledTasks.get(i);
    }

    /**
     * Add a single tasks to legitimatedSchedules list
     * @param task tasks to add
     * @param taskListIndex index to mark as false, ie, it is not unscheduled
     */
    public void addTask(Task task, int taskListIndex) {
        Schedule schedule = new Schedule(task);
        unScheduledTasks.set(taskListIndex, false);
        legitimatedSchedules.add(schedule);
    }

    /**
     * Merge a list of scheduled tasks
     * @param schedule schedule containing list of tasks
     * @param unScheduledTasks list marking tasks as either scheduled or unscheduled
     */
    public void addSchedules(Schedule schedule, ArrayList<Boolean> unScheduledTasks) {
        for (int i = 0; i < this.unScheduledTasks.size(); i++) {
            this.unScheduledTasks.set(i, unScheduledTasks.get(i) && this.unScheduledTasks.get(i));
        }
        legitimatedSchedules.add(schedule);
    }

    @Override
    public String toString() {
        return String.valueOf(legitimatedSchedules);
    }
}
