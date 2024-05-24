import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private static int taskId = 1;

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();

    private HistoryManager historyManager = Managers.getDefaultHistory();

    public int idGenerator() {
        return taskId++;
    }

    @Override
    public int addNewTask(Task newTask) {
        if (newTask == null) {
            System.out.println("Ошибка, задачи не существует");
            return -1;
        }
        int id = newTask.getId();
        tasks.put(id, newTask);
        return id;
    }

    @Override
    public int addNewSubTask(SubTask newSubTask) {
        if (newSubTask == null) {
            System.out.println("Ошибка, задачи не существует");
            return -1;
        }
        int id = newSubTask.getId();
        subTasks.put(id, newSubTask);
        Epic epic = epics.get(newSubTask.getEpicId());
        epic.addSubTaskId(id);
        if (TaskStatus.DONE.equals(epic.getTaskStatus())) {
            epic.setTaskStatus(TaskStatus.NEW);
        }
        epics.get(newSubTask.getEpicId()).addSubTaskId(id);

        return id;
    }

    @Override
    public int addNewEpic(Epic newEpic) {
        if (newEpic == null) {
            System.out.println("Ошибка, задачи не существует");
            return -1;
        }
        int id = newEpic.getId();
        epics.put(id, newEpic);
        return id;
    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
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

    @Override
    public void deleteTask(int taskId) {
        tasks.remove(taskId);
    }

    @Override
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

    @Override
    public void deleteEpic(int epicId) {
        Epic epic = epics.get(epicId);
        ArrayList<Integer> ids = epic.getSubTaskIds();
        for (int id : ids) {
            subTasks.remove(id);
        }
        epics.remove(epicId);
    }

    @Override
    public void printTasks() {
        System.out.println("Список задач:");
        System.out.println(getTasks());
        System.out.println("Список эпиков:");
        System.out.println(getEpics());
        System.out.println("Список подзадач:");
        System.out.println(getSubTasks());
        System.out.println();
    }

    @Override
    public ArrayList<SubTask> getSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<SubTask> getEpicSubTasks(int epicId) {
        ArrayList<Integer> subTaskIds = epics.get(epicId).getSubTaskIds();
        ArrayList<SubTask> epicSubTasks = new ArrayList<>();
        for (int id : subTaskIds) {
            epicSubTasks.add(subTasks.get(id));
        }
        return epicSubTasks;
    }

    @Override
    public Task getTaskById(int id) {
        Task task = tasks.getOrDefault(id, null);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public SubTask getSubTaskById(int id) {
        SubTask task = subTasks.getOrDefault(id, null);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic task = epics.getOrDefault(id, null);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

}
