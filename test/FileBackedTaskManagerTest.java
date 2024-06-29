import components.Task;
import managers.FileBackedTaskManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;

public class FileBackedTaskManagerTest {
    private File file;
    private FileBackedTaskManager taskManager;

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
