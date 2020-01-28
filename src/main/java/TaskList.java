import java.util.ArrayList;

public class TaskList {

    ArrayList<Task> tasks = new ArrayList<>();
    int taskCount = 0;

    protected ArrayList<Task> getUpdatedTasks() {
        return tasks;
    }

    public String getDescription(int index) {
        return tasks.get(index).getDescription();
    }

    public String getStatusIcon(int index) {
        return tasks.get(index).getStatusIcon();
    }

    public String getTaskIcon(int index) {
        return tasks.get(index).getTaskIcon();
    }


    protected int addToDo(String input) throws DukeException {
        if (input.length() <= ("todo".length() + 1)) {
            throw new DukeException();
        }
        tasks.add(new ToDo(input.substring("todo".length() + 1)));
        taskCount++;
        return taskCount - 1;
    }

    protected int addDeadline(String input) {
        int trigger = input.indexOf('/');
        tasks.add(new Deadline(input.substring("deadline".length() + 1, trigger - 1),
                input.substring(trigger + "/by ".length())));
        taskCount++;
        return taskCount - 1;
    }

    protected int addEvent(String input) {
        int trigger = input.indexOf('/');
        tasks.add(new Event(input.substring("event".length() + 1, trigger - 1),
                input.substring(trigger + "/at ".length())));
        taskCount++;
        return taskCount - 1;
     }

    protected void addDone(int taskNumber) {
        tasks.get(taskNumber).markAsDone();
    }

    protected void delete(int taskNumber) {
        taskCount--;
        tasks.remove(taskNumber);
    }

    protected int getTaskCount() {
        return taskCount;
    }

}
