package managers;

import components.Epic;
import components.SubTask;
import components.Task;

import java.util.List;

public interface TaskManager {

    int createTask(Task newTask);

    int createSubTask(SubTask newSubTask);

    int createEpic(Epic newEpic);

    void updateTask(Task task);

    void updateSubTask(SubTask subTask);

    void deleteTask(int taskId);

    void deleteSubTask(int subTaskId);

    void deleteEpic(int epicId);

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
}
