import java.util.ArrayList;

/**
 * Class to represent a list of tasks
 */
public class TaskList {

    ArrayList<Task> tasks = new ArrayList<>();

    /**
     * Returns current list of tasks
     * @return current list of tasks
     */
    protected ArrayList<Task> getUpdatedTasks() {
        return tasks;
    }

    /**
     * Calls function to get description for specific task in list
     * @param index of specific task
     * @return description of specific task
     */
    public String getDescription(int index) {
        return tasks.get(index).getDescription();
    }

    /**
     * Calls function to get status icon for specific task in list
     *
     * @param index of specific task
     * @return status icon of specific task
     */
    public String getStatusIcon(int index) {
        return tasks.get(index).getStatusIcon();
    }

    /**
     * Calls function to get task icon for specific task in list
     *
     * @param index of specific task
     * @return task icon of specific task
     */
    public String getTaskIcon(int index) {
        return tasks.get(index).getTaskIcon();
    }


    /**
     * Add new to do to list and return its index
     *
     * @param input text entered by user
     * @return index of new deadline
     * @throws DukeException
     */
    protected int addToDo(String input) throws DukeException {
        if (input.length() <= ("todo".length() + 1)) {
            throw new DukeException();
        }
        tasks.add(new ToDo(input.substring("todo".length() + 1)));
        return tasks.size() - 1;
    }

    /**
     * Add new deadline to list and return its index
     *
     * @param input text entered by user
     * @return index of new deadline
     */
    protected int addDeadline(String input) {
        int trigger = input.indexOf('/');
        tasks.add(new Deadline(input.substring("deadline".length() + 1, trigger - 1),
                input.substring(trigger + "/by ".length())));
        return tasks.size() - 1;
    }

    /**
     * Add new event to list and return its index
     *
     * @param input text entered by user
     * @return index of new event
     */
    protected int addEvent(String input) {
        int trigger = input.indexOf('/');
        tasks.add(new Event(input.substring("event".length() + 1, trigger - 1),
                input.substring(trigger + "/at ".length())));
        return tasks.size() - 1;
     }

    /**
     * Calls function for specific task to mark itself as done
     *
     * @param taskNumber Index for task to be marked as done
     */
    protected void addDone(int taskNumber) {
        tasks.get(taskNumber).markAsDone();
    }

    /**
     * Deletes specific task at index from list
     *
     * @param taskNumber index of task to be deleted
     */
    protected void delete(int taskNumber) {
        tasks.remove(taskNumber);
    }

    /**
     * Returns current number of tasks
     * @return number of tasks
     */
    protected int getTaskCount() {
        return tasks.size();
    }

}
