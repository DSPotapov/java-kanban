import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    private TaskManager taskManager = Managers.getDefault();

    @Test
    void addNewTask() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", 1);
        final int taskId = taskManager.addNewTask(task);
        final Task savedTask = taskManager.getTaskById(taskId);

        assertNotNull(savedTask, "������ �� �������.");
        assertEquals(task, savedTask, "������ �� ���������.");

        final List<Task> tasks = taskManager.getTasks();

        assertNotNull(tasks, "������ �� ������������.");
        assertEquals(1, tasks.size(), "�������� ���������� �����.");
        assertEquals(task, tasks.get(0), "������ �� ���������.");
    }

    @Test
    public void shouldNotAddEpicAsSubTask() {
        Epic epic = new Epic("Epic for test 1", "Testing of epic 1", taskManager.idGenerator());
        assertEquals(-1, taskManager.);
    }

    @Test
    public void taskShouldNotBeNull() {
        assertEquals(-1, taskManager.addNewTask(null), "������ ������ �� ����� ���� ���������");
    }

    @Test
    public void subTaskShouldNotBeNull() {
        assertEquals(-1, taskManager.addNewSubTask(null), "������ ������ �� ����� ���� ���������");
    }

    @Test
    public void epicShouldNotBeNull() {
        assertEquals(-1, taskManager.addNewEpic(null), "������ ������ �� ����� ���� ���������");
    }
}