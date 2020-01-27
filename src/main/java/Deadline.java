import java.time.LocalDate;

public class Deadline extends Task {

    public Deadline(String description, String time) {
        super(description);
        this.time = time;
        processedTime = LocalDate.parse(time);
    }

    public String getTaskIcon() {
        return "D";
    }

    public boolean hasTime() {
        return true;
    }
}
