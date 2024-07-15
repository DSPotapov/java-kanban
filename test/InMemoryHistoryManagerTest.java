import components.Task;
import managers.InMemoryHistoryManager;
import managers.Managers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    private final InMemoryHistoryManager historyManager = (InMemoryHistoryManager) Managers.getDefaultHistory();

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

    @Test
    public void removeNodeTest(){
        Task task = new Task("Task for test", "Task testing", 1);
        Task task1 = new Task("Task1 for test", "Task1 testing", 2);
        Task task2 = new Task("Task2 for test", "Task2 testing", 3);
        Task task3 = new Task("Task3 for test", "Task3 testing", 4);
        Task task4 = new Task("Task4 for test", "Task4 testing", 5);
        historyManager.add(task);
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.add(task4);

        historyManager.remove(2);
        assertFalse(historyManager.getHistory().contains(task1));

        int id = historyManager.getLast().getId();
        historyManager.remove(id);
        assertFalse(historyManager.getHistory().contains(task4));

        id = historyManager.getFirst().getId();
        historyManager.remove(id);
        assertFalse(historyManager.getHistory().contains(task));

        assertEquals(historyManager.getHistory().size(), 2, "Количество записей в истории некорректное" +
                "");
    }

}