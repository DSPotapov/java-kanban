import components.Epic;
import components.SubTask;
import components.Task;
import managers.Managers;
import managers.TaskManager;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryTaskManagerTest {

    private TaskManager taskManager = Managers.getDefault();

    @Test
    void addNewTaskTest() {
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
    void addNewSubTaskTest() {
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
    public void addNewEpicTest() {
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
    public void updateTaskTest(){
        Task task = new Task("task for updateTaskTest", "testing task for updateTaskTest");
        taskManager.addNewTask(task);
        int id = task.getId();
        task.setDescription("new description of the task");
        task.setName("new name of the task");
        taskManager.updateTask(task);
        task = taskManager.getTaskById(id);
        assertEquals(task.getDescription(),"new description of the task");
        assertEquals(task.getName(),"new name of the task");
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

    @Test
    public void shouldAddAndDeleteSubTask(){
        Epic epic = new Epic("epic for test", "testing epic");
        taskManager.addNewEpic(epic);
        SubTask subTask = new SubTask("subtask for epic", "testing subtask", epic.getId());
        taskManager.addNewSubTask(subTask);
        int id = subTask.getId();
        assertTrue(epic.isSubTaskId(id));
        taskManager.deleteSubTask(id);
        assertFalse(epic.isSubTaskId(id));
    }

}