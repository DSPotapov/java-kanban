import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private static int taskId = 1;

    private static HashMap<Integer, Task> tasks = new HashMap<>();
    private static HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private static HashMap<Integer, Epic> epics = new HashMap<>();

    public static int idGenerator() {
        return taskId++;
    }

    public void clearTasks() {
        tasks.clear();
        subTasks.clear();
        epics.clear();
        taskId = 1;
    }

    public static void createTask(Task newTask) {
        tasks.put(newTask.getId(), newTask);
    }

    public static void createSubTask(SubTask newSubTask) {
        subTasks.put(newSubTask.getId(), newSubTask);
        epics.get(newSubTask.getEpicId()).addSubTaskId(newSubTask.getId());
    }

    public static void createEpic(Epic newEpic) {
        epics.put(newEpic.getId(), newEpic);
    }

    public static void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public static void updateSubTask(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
        int epicId = subTask.getEpicId();
        checkoutEpicStatus(epicId);
    }

    private static void checkoutEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        ArrayList<Integer> ids = epic.getSubTaskIds();
        boolean isDone = true;
        for (int id : ids) {
            TaskStatus subTaskStatus = subTasks.get(id).getTaskStatus();
            if (!TaskStatus.DONE.equals(subTaskStatus)) {
                isDone = false;
                if (!TaskStatus.NEW.equals(subTaskStatus)) {
                    epic.setTaskStatus(TaskStatus.IN_PROGRESS);
                    return;
                }
            }
        }

        if (isDone) {
            epic.setTaskStatus(TaskStatus.DONE);
        }
    }

    public static void deleteTask(int taskId) {
        tasks.remove(taskId);
    }

    public static void deleteSubTask(int subTaskId) {
        SubTask subTask = subTasks.get(subTaskId);
        int epicId = subTask.getEpicId();
        Epic epic = epics.get(epicId);
        epic.deleteSubTaskId(subTaskId);
        subTasks.remove(subTaskId);
        if (epic.getSubTaskIds().isEmpty()) {
            epics.remove(epicId);
        }
    }

    public static void deleteEpic(int epicId) {
        Epic epic = epics.get(epicId);
        ArrayList<Integer> ids = epic.getSubTaskIds();
        for (int id : ids) {
            subTasks.remove(id);
        }
        epics.remove(epicId);
    }

    public static int getTaskId() {
        return taskId;
    }

    public static HashMap<Integer, SubTask> getSubTasks() {
        return subTasks;
    }

    public static HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    public static HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public static ArrayList<SubTask> getSubTasksByEpic(int epicId) {
        ArrayList<Integer> subTaskIds = epics.get(epicId).getSubTaskIds();
        ArrayList<SubTask> subTasks = new ArrayList<>();
        for (int id : subTaskIds) {
            subTasks.add(subTasks.get(id));
        }
        return subTasks;
    }

    public static Task getTaskById(int id) {
        return tasks.getOrDefault(id, null);
    }

    public static SubTask getSubTaskById(int id) {
        return subTasks.getOrDefault(id, null);
    }

    public static Epic getEpicById(int id) {
        return epics.getOrDefault(id, null);
    }
}
