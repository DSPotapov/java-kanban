import components.Epic;
import components.SubTask;
import components.Task;
import components.TaskStatus;
import managers.Managers;
import managers.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryTaskManagerTest {

    private TaskManager taskManager;

    @BeforeEach
    public void beforeTest() {
        taskManager = Managers.getDefault();

    }

    @Test
    void addNewTaskTest() throws IOException {
        Task task = new Task("Test addNewTask", "Test addNewTask description");
        final int taskId = taskManager.createTask(task);
        final Task savedTask = taskManager.getTaskById(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void addNewSubTaskTest() throws IOException {
        Epic epic = new Epic(
                "Test addNewEpic",
                "Test addNewEpic description");
        taskManager.createEpic(epic);
        SubTask subTask = new SubTask(
                "Test addNewSubTask",
                "Test addNewSubTask description",
                epic.getId());
        final int taskId = taskManager.createSubTask(subTask);
        final Task savedTask = taskManager.getSubTaskById(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(subTask, savedTask, "Задачи не совпадают.");

        final List<SubTask> subTasks = taskManager.getSubTasks();

        assertNotNull(subTasks, "Задачи не возвращаются.");
        assertEquals(1, subTasks.size(), "Неверное количество задач.");
        assertEquals(subTask, subTasks.get(0), "Задачи не совпадают.");
    }

    @Test
    public void addNewEpicTest() throws IOException {
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpic description");
        final int taskId = taskManager.createEpic(epic);
        final Epic savedTask = taskManager.getEpicById(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(epic, savedTask, "Задачи не совпадают.");

        final List<Epic> epics = taskManager.getEpics();

        assertNotNull(epics, "Задачи не возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество задач.");
        assertEquals(epic, epics.get(0), "Задачи не совпадают.");
    }

    @Test
    public void updateTaskTest() throws IOException {
        Task task = new Task("task for updateTaskTest", "testing task for updateTaskTest");
        taskManager.createTask(task);
        int id = task.getId();
        task.setDescription("new description of the task");
        task.setName("new name of the task");
        taskManager.updateTask(task);
        task = taskManager.getTaskById(id);
        assertEquals(task.getDescription(), "new description of the task");
        assertEquals(task.getName(), "new name of the task");
    }

    @Test
    public void taskShouldNotBeNull() throws IOException {
        assertEquals(-1, taskManager.createTask(null), "Пустая задача не может быть добавлена");
    }

    @Test
    public void subTaskShouldNotBeNull() throws IOException {
        assertEquals(-1, taskManager.createSubTask(null), "Пустая задача не может быть добавлена");
    }

    @Test
    public void epicShouldNotBeNull() throws IOException {
        assertEquals(-1, taskManager.createEpic(null), "Пустая задача не может быть добавлена");
    }

    @Test
    public void shouldAddAndDeleteSubTask() throws IOException {
        Epic epic = new Epic("epic for test", "testing epic");
        taskManager.createEpic(epic);
        SubTask subTask = new SubTask("subtask for epic", "testing subtask", epic.getId());
        taskManager.createSubTask(subTask);
        int id = subTask.getId();
        assertTrue(epic.isSubTaskId(id));
        taskManager.deleteSubTask(id);
        assertFalse(epic.isSubTaskId(id));
    }

    @Test
    public void checkoutEpicStatusTest() throws IOException {
        LocalDateTime startTime = LocalDateTime.now();
        Epic epic = new Epic("epic for test", "testing epic", 1);
        taskManager.createEpic(epic);
        SubTask subTask = new SubTask("subtask0 for epic", "testing subtask0", 10, startTime, epic.getId());
        startTime = startTime.plusSeconds(3600);
        SubTask subTask1 = new SubTask("subtask1 for epic", "testing subtask1", 11, startTime, epic.getId());
        startTime = startTime.plusSeconds(3600);
        SubTask subTask2 = new SubTask("subtask2 for epic", "testing subtask2", 12, startTime, epic.getId());
        taskManager.createSubTask(subTask);
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        assertEquals(TaskStatus.NEW, epic.getTaskStatus());

        subTask1.setTaskStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateSubTask(subTask1);
        assertEquals(TaskStatus.IN_PROGRESS, epic.getTaskStatus());

        subTask.setTaskStatus(TaskStatus.DONE);
        taskManager.updateSubTask(subTask);
        assertEquals(TaskStatus.IN_PROGRESS, epic.getTaskStatus());

        subTask1.setTaskStatus(TaskStatus.DONE);
        taskManager.updateSubTask(subTask1);
        subTask2.setTaskStatus(TaskStatus.DONE);
        taskManager.updateSubTask(subTask2);
        assertEquals(TaskStatus.DONE, epic.getTaskStatus());
    }

    @Test
    public void checkTimeInterceptionTest() {

        Task task = new Task("task for updateTaskTest0", "testing task0 for updateTaskTest",
                LocalDateTime.of(2024, 1, 1, 0, 0),
                Duration.ofMinutes(60));
        Task task1 = new Task("task for updateTaskTest1", "testing task1 for updateTaskTest",
                LocalDateTime.of(2024, 1, 1, 2, 0),
                Duration.ofMinutes(60));
        Task task2 = new Task("task for updateTaskTest2", "testing task2 for updateTaskTest",
                LocalDateTime.of(2024, 1, 1, 0, 0),
                Duration.ofMinutes(60));
        Task task3 = new Task("task for updateTaskTest3", "testing task3 for updateTaskTest",
                LocalDateTime.of(2024, 1, 1, 0, 30),
                Duration.ofMinutes(60));

        assertFalse(taskManager.checkTimeInterception(task, task1), "Время задачь не должно пересекаться");
        assertTrue(taskManager.checkTimeInterception(task2, task3), "Время задачь должно пересекаться");

    }

}