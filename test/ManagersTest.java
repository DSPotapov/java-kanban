import managers.InMemoryHistoryManager;
import managers.InMemoryTaskManager;
import managers.Managers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    void getDefault() {
        assertTrue(Managers.getDefault() instanceof InMemoryTaskManager);
    }

    @Test
    void getDefaultHistory() {
        assertTrue(Managers.getDefaultHistory() instanceof InMemoryHistoryManager);
    }
}