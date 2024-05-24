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

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    public void shouldNotAddEpicAsSubTask() {
        Epic epic = new Epic("Epic for test 1", "Testing of epic 1", taskManager.idGenerator());
        assertEquals(-1, taskManager.);
    }

    @Test
    public void taskShouldNotBeNull() {
        assertEquals(-1, taskManager.addNewTask(null), "Пустая задача не может быть добавлена");
    }

    @Test
    public void subTaskShouldNotBeNull() {
        assertEquals(-1, taskManager.addNewSubTask(null), "Пустая задача не может быть добавлена");
    }

    @Test
    public void epicShouldNotBeNull() {
        assertEquals(-1, taskManager.addNewEpic(null), "Пустая задача не может быть добавлена");
    }
}