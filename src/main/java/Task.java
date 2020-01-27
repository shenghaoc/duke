import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task {
    protected String description;
    protected boolean isDone;
    protected String time = "-1";
    protected LocalDate processedTime = LocalDate.MIN;

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
        return processedTime.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
    }

    public boolean hasTime() {
        return false;
    }

}