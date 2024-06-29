import components.Task;
import managers.FileBackedTaskManager;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileBackedTaskManagerTest {

    @Test
    public void taskToStringTest() {
        Task task = new Task("task1 for test", "test task1", 1);
        String stringTask = FileBackedTaskManager.taskToString(task);
        assertEquals("1,TASK,task1 for test,NEW,test task1", stringTask);

    }

    @Test
    public void taskFromStringTest() {
        String stringTask = "1,TASK,task1 for test,NEW,test task1";
        Task task = new Task("task1 for test", "test task1", 1);
        Task taskFromString = FileBackedTaskManager.taskFromString(stringTask);
        assertEquals(task, taskFromString);
    }


}
