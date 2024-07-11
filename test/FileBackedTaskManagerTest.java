import components.Task;
import managers.FileBackedTaskManager;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileBackedTaskManagerTest {

    @Test
    public void taskToStringTest() {
        LocalDateTime startTime = LocalDateTime.now();
        Duration duration = Duration.ofSeconds(24 * 60 * 60);
        Task task = new Task("task1 for test", "test task1", startTime, duration);
        task.setId(1);
        String stringTask = FileBackedTaskManager.taskToString(task);
        assertEquals("1,TASK,task1 for test,NEW,test task1," + startTime + "," + duration.toMinutes(), stringTask);
    }

    @Test
    public void taskFromStringTest() {
        LocalDateTime startTime = LocalDateTime.now();
        Duration duration = Duration.ofMinutes(24 * 60);
        String stringTask = "1,TASK,task1 for test,NEW,test task1," + startTime + "," + duration.toMinutes();
        Task task = new Task("task1 for test", "test task1", startTime, duration);
        task.setId(1);
        Task taskFromString = FileBackedTaskManager.taskFromString(stringTask);
        assertEquals(task, taskFromString);
    }


}
