import components.Epic;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
//    components.Epic epic = new components.Epic("components.Epic for test 0", "Testing components.Epic 0");
//    components.SubTask subTask = new components.SubTask("components.SubTask for test 0", "Test subtask 0", 1,0);

    @Test
    public void epicsShouldBeEquals() {
        Epic epic1 = new Epic("components.Epic for test 1", "Testing epic 1", 1);
        Epic epic2 = new Epic("components.Epic for test 2", "Testing epic 2", 1);
        assertEquals(epic1, epic2, "Ёпики разные");
    }
}