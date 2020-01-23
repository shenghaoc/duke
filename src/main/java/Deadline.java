public class Deadline extends Task {

    public Deadline(String description, String time) {
        super(description);
        this.time = time;
    }

    public String getTaskIcon() {
        return "D";
    }

    public boolean hasTime() {
        return true;
    }
}
