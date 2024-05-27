import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    private TaskManager taskManager = Managers.getDefault();

    @Test
    void addNewTask() {
        Task task = new Task("Test addNewTask", "Test addNewTask description");
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
    void addNewSubTask() {
        Epic epic = new Epic(
                "Test addNewEpic",
                "Test addNewEpic description");
        taskManager.addNewEpic(epic);
        SubTask subTask = new SubTask(
                "Test addNewSubTask",
                "Test addNewSubTask description",
                epic.getId());
        final int taskId = taskManager.addNewSubTask(subTask);
        final Task savedTask = taskManager.getSubTaskById(taskId);

        assertNotNull(savedTask, "������ �� �������.");
        assertEquals(subTask, savedTask, "������ �� ���������.");

        final List<SubTask> subTasks = taskManager.getSubTasks();

        assertNotNull(subTasks, "������ �� ������������.");
        assertEquals(1, subTasks.size(), "�������� ���������� �����.");
        assertEquals(subTask, subTasks.get(0), "������ �� ���������.");
    }

    @Test
    void addNewEpic() {
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpic description");
        final int taskId = taskManager.addNewEpic(epic);
        final Epic savedTask = taskManager.getEpicById(taskId);

        assertNotNull(savedTask, "������ �� �������.");
        assertEquals(epic, savedTask, "������ �� ���������.");

        final List<Epic> epics = taskManager.getEpics();

        assertNotNull(epics, "������ �� ������������.");
        assertEquals(1, epics.size(), "�������� ���������� �����.");
        assertEquals(epic, epics.get(0), "������ �� ���������.");
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