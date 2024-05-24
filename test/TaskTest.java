import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {
    @Test
    public void tasksShouldBeEquals(){
        Task task1 = new Task("Task for test 1", "Testing task 1", 1);
        Task task2 = new Task("Task for test 2", "Testing task 2", 1);
        assertEquals(task1, task2, "Задачи разные");
    }
}
