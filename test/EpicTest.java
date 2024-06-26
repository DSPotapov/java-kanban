import components.Epic;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    @Test
    public void epicsShouldBeEquals() {
        Epic epic1 = new Epic("Epic for test 1", "Testing epic 1", 1);
        Epic epic2 = new Epic("Epic for test 2", "Testing epic 2", 1);
        assertEquals(epic1, epic2, "Эпики разные");
    }

    @Test
    public void deleteSubTask() {
        Epic epic = new Epic("Epic for test", "Test epic");

    }
}