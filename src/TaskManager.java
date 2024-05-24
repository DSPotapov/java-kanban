import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    int idGenerator();
    int addNewTask(Task newTask);

    int addNewSubTask(SubTask newSubTask);

    int addNewEpic(Epic newEpic);

    void updateTask(Task task);

    void updateSubTask(SubTask subTask);

    void deleteTask(int taskId);

    void deleteSubTask(int subTaskId);

    void deleteEpic(int epicId);

    void printTasks();

    ArrayList<SubTask> getSubTasks();

    ArrayList<Epic> getEpics();

    ArrayList<Task> getTasks();

    ArrayList<SubTask> getEpicSubTasks(int epicId);

    Task getTaskById(int id);

    SubTask getSubTaskById(int id);

    Epic getEpicById(int id);

    List<Task> getHistory();
}
