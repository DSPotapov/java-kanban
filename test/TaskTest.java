import components.Task;
import components.TaskStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {
    @Test
    public void tasksShouldBeEquals(){
        Task task1 = new Task("components.Task for test 1", "Testing task 1", 1);
        Task task2 = new Task("components.Task for test 2", "Testing task 2", 1);
        assertEquals(task1, task2, "������ ������");
    }

    @Test
    public void setTaskStatus(){
        Task task = new Task("components.Task for test 1", "Testing task");
        task.setTaskStatus(TaskStatus.IN_PROGRESS);
        assertEquals(TaskStatus.IN_PROGRESS, task.getTaskStatus(), "������ �� ���������");
    }

    @Test
    public void getTaskType(){
        Task task = new Task("components.Task for test 1", "Testing task");
        assertEquals("Task", task.getTaskType());
    }
}