import components.Epic;
import components.SubTask;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubTaskTest {
    Epic epic = new Epic("components.Epic for test 1", "Testing epic 1");

    @Test
    public void subTasksShouldBeEquals() {
        SubTask subTask1 = new SubTask("components.SubTask for test 1", "Testing components.SubTask 1", 1, 0);
        SubTask subTask2 = new SubTask("components.SubTask for test 2", "Testing components.SubTask 2", 1, 0);
        assertEquals(subTask1, subTask2, "сабтаски разные");
    }
}