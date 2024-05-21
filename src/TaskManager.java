import java.util.ArrayList;

public interface TaskManager {
    void createTask(Task newTask);

    void createSubTask(SubTask newSubTask);

    void createEpic(Epic newEpic);

    void updateTask(Task task);

    void updateSubTask(SubTask subTask);

    void deleteTask(int taskId);

    void deleteSubTask(int subTaskId);

    void deleteEpic(int epicId);

    void printTasks();

    ArrayList<SubTask> getSubTasks();

    ArrayList<Epic> getEpics();

    ArrayList<Task> getTasks();

    ArrayList<SubTask> getSubTasksByEpic(int epicId);

    Task getTaskById(int id);

    SubTask getSubTaskById(int id);

    Epic getEpicById(int id);
}
