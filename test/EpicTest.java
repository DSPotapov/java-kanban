import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
//    Epic epic = new Epic("Epic for test 0", "Testing Epic 0");
//    SubTask subTask = new SubTask("SubTask for test 0", "Test subtask 0", 1,0);

    @Test
    public void epicsShouldBeEquals() {
        Epic epic1 = new Epic("Epic for test 1", "Testing epic 1", 1);
        Epic epic2 = new Epic("Epic for test 2", "Testing epic 2", 1);
        assertEquals(epic1, epic2, "Ёпики разные");
    }
}