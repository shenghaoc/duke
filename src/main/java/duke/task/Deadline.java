package duke.task;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private boolean dateProcessed = true;

    /**
     * Constructor for Deadline class.
     * @param description details regarding task
     * @param time by which task has to be completed
     */
    public Deadline(String description, String time) {
        super(description);
        this.time = time;
        try {
            processedTime = LocalDate.parse(time);
        } catch (DateTimeException e) {
            dateProcessed = false;
        }
    }

    public String getTaskIcon() {
        return "D";
    }

    public boolean hasTime() {
        return true;
    }

    @Override
    public String getTime() {
        return dateProcessed
                ? processedTime.format(DateTimeFormatter.ofPattern("MMM d yyyy"))
                : time;
    }

    @Override
    public String toString() {
        return "[D][" + getStatusIcon() + "] " + description + (hasTime() ? " (by: " + time + ")" : "");
    }
}
