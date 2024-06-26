package managers;

import java.nio.file.Path;

/*
утилитарный класс
 */
public class Managers {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static TaskManager getFileBackedTaskManager(Path path) {
        return new FileBackedTaskManager(path);
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
