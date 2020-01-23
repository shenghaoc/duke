public class Task {
    protected String description;
    protected boolean isDone;
    protected String time = "-1";

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getDescription() {
        return description;
    }

    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    public void markAsDone() {
        isDone = true;
    }

    public String getTaskIcon() {
        return "0";
    }

    public String getTime() {
        return time;
    }

    public boolean hasTime() {
        return false;
    }

    //...
}