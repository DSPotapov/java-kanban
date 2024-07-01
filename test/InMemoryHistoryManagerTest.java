import components.Task;
import managers.InMemoryHistoryManager;
import managers.Managers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InMemoryHistoryManagerTest {
    private InMemoryHistoryManager historyManager = (InMemoryHistoryManager) Managers.getDefaultHistory();

    @BeforeEach
    public void clearHistory() {
        historyManager.clearHistory();
    }

    @Test
    void addTest() {
        Task task = new Task("Task for test", "Task testing", 1);
        historyManager.add(task);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "history is empty");
        assertEquals(1, history.size());
    }

    @Test
    public void linkLastTest() {
        Task task = new Task("Task for test", "Task testing", 1);
        Task task1 = new Task("Task1 for test", "Task1 testing", 2);
        historyManager.add(task);
        historyManager.add(task1);
        assertEquals(historyManager.getLast(), task1, "couldn't get tail in history");
    }

    @Test
    public void getFirstTest() {
        Task task = new Task("Task for test", "Task testing", 1);
        Task task1 = new Task("Task1 for test", "Task1 testing", 2);
        historyManager.add(task);
        historyManager.add(task1);
        assertEquals(historyManager.getFirst(), task, "couldn't get head of history");
    }

    @Test
    public void shouldNotBeDuplicatesInHistory() {
        Task task = new Task("task for updateTaskTest", "testing task for updateTaskTest");
        historyManager.add(task);
        historyManager.add(task);
        assertEquals(1, historyManager.getHistory().size(), "found duplicate in history");
    }

}