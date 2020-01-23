public class Event extends Task {

    public Event(String description, String time) {
        super(description);
        this.time = time;
    }

    public String getTaskIcon() {
        return "E";
    }

    public boolean hasTime() {
        return true;
    }
}