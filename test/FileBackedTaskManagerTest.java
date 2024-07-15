import components.Task;
import managers.FileBackedTaskManager;
import managers.ManagerSaveException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    public void managerSaveExceptionTest() {
        assertThrows(ManagerSaveException.class, () -> {
            File file = new File("temp");
            if (!file.exists()) {
                System.out.println(file);
                throw new ManagerSaveException("Файл с таким именем не существует", file);
            }
        }, "Попытка записи в каталог вместо файла должно приводить к исключению");
    }

}
