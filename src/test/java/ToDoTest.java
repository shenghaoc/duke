import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ToDoTest {

    @Test
    void getTaskIcon() {
        assertEquals("T", new ToDo("").getTaskIcon());
    }
}