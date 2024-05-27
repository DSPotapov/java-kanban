import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubTaskTest {
    Epic epic = new Epic("Epic for test 1", "Testing epic 1");

    @Test
    public void subTasksShouldBeEquals() {
        SubTask subTask1 = new SubTask("SubTask for test 1", "Testing SubTask 1", 1, 0);
        SubTask subTask2 = new SubTask("SubTask for test 2", "Testing SubTask 2", 1, 0);
        assertEquals(subTask1, subTask2, "сабтаски разные");
    }
}