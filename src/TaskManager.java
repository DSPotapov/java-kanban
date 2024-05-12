import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private static int taskId = 1;

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();

    public static int idGenerator() {
        return taskId++;
    }

    public void createTask(Task newTask) {
        tasks.put(newTask.getId(), newTask);
    }

    public void createSubTask(SubTask newSubTask) {
        subTasks.put(newSubTask.getId(), newSubTask);
        Epic epic = epics.get(newSubTask.getEpicId());
        epic.addSubTaskId(newSubTask.getId());
        if (TaskStatus.DONE.equals(epic.getTaskStatus())) {
            epic.setTaskStatus(TaskStatus.NEW);
        }
        epics.get(newSubTask.getEpicId()).addSubTaskId(newSubTask.getId());

    }

    public void createEpic(Epic newEpic) {
        epics.put(newEpic.getId(), newEpic);
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateSubTask(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
        int epicId = subTask.getEpicId();
        checkoutEpicStatus(epicId);
    }

    private void checkoutEpicStatus(int epicId) {
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

    public void deleteTask(int taskId) {
        tasks.remove(taskId);
    }

    public void deleteSubTask(int subTaskId) {
        SubTask subTask = subTasks.get(subTaskId);
        int epicId = subTask.getEpicId();
        Epic epic = epics.get(epicId);
        epic.deleteSubTaskId(subTaskId);
        subTasks.remove(subTaskId);
        if (epic.getSubTaskIds().isEmpty()) {
            epic.setTaskStatus(TaskStatus.NEW);
        }
    }

    public void deleteEpic(int epicId) {
        Epic epic = epics.get(epicId);
        ArrayList<Integer> ids = epic.getSubTaskIds();
        for (int id : ids) {
            subTasks.remove(id);
        }
        epics.remove(epicId);
    }

    public void printTasks() {
        System.out.println("Список задач:");
        System.out.println(getTasks());
        System.out.println("Список эпиков:");
        System.out.println(getEpics());
        System.out.println("Список подзадач:");
        System.out.println(getSubTasks());
        System.out.println();
    }

    public ArrayList<SubTask> getSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<SubTask> getSubTasksByEpic(int epicId) {
        ArrayList<Integer> subTaskIds = epics.get(epicId).getSubTaskIds();
        ArrayList<SubTask> subTasks = new ArrayList<>();
        for (int id : subTaskIds) {
            subTasks.add(subTasks.get(id));
        }
        return subTasks;
    }

    public Task getTaskById(int id) {
        return tasks.getOrDefault(id, null);
    }

    public SubTask getSubTaskById(int id) {
        return subTasks.getOrDefault(id, null);
    }

    public Epic getEpicById(int id) {
        return epics.getOrDefault(id, null);
    }
}
