package managers;

import components.Epic;
import components.SubTask;
import components.Task;

import java.io.IOException;
import java.util.List;

public interface TaskManager {

    int createTask(Task newTask) throws IOException;

    int createSubTask(SubTask newSubTask) throws IOException;

    int createEpic(Epic newEpic) throws IOException;

    void updateTask(Task task) throws IOException;

    void updateSubTask(SubTask subTask) throws IOException;

    void deleteTask(int taskId) throws IOException;

    void deleteSubTask(int subTaskId) throws IOException;

    void deleteEpic(int epicId) throws IOException;

    boolean checkTimeInterception(Task task1, Task task2);

    void printTasks();

    List<SubTask> getSubTasks();

    List<Epic> getEpics();

    List<Task> getTasks();

    List<SubTask> getEpicSubTasks(int epicId);

    Task getTaskById(int id);

    SubTask getSubTaskById(int id);

    Epic getEpicById(int id);

    List<Task> getHistory();

    void clearAllTasks();

    void updateEpic(Epic epic) throws IOException;
}
