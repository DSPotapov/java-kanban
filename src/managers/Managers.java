package managers;

import managers.HistoryManager;
import managers.InMemoryHistoryManager;
import managers.InMemoryTaskManager;

/*
утилитарный класс
 */
public class Managers {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
