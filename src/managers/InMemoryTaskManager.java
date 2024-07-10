package managers;

import components.Epic;
import components.SubTask;
import components.Task;
import components.TaskStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    static int taskId = 1;

    protected final Map<Integer, Task> tasks = new HashMap<>();
    protected final Map<Integer, SubTask> subTasks = new HashMap<>();
    protected final Map<Integer, Epic> epics = new HashMap<>();

    protected final HistoryManager historyManager = Managers.getDefaultHistory();

    int idGenerator() {
        return taskId++;
    }

    @Override
    public int addNewTask(Task newTask) {
        //TODO создать исключение
        //TODO добавить проверку на пересечение с другими задачами по времени
        if (newTask == null) {
            System.out.println("Ошибка, задачи не существует");
            return -1;
        }
        int id = idGenerator();
        newTask.setId(id);
        tasks.put(id, newTask);
        return id;
    }

    @Override
    public int addNewSubTask(SubTask newSubTask) {
        //TODO создать исключение
        //TODO добавить проверку на пересечение с другими задачами по времени
        if (newSubTask == null) {
            System.out.println("Ошибка, задачи не существует");
            return -1;
        }
        int id = idGenerator();
        newSubTask.setId(id);
        subTasks.put(id, newSubTask);
        Epic epic = epics.get(newSubTask.getEpicId());
        epic.addSubTaskId(id);
        if (TaskStatus.DONE.equals(epic.getTaskStatus())) {
            epic.setTaskStatus(TaskStatus.NEW);
        }
        //обновляем время эпика
        calculateEpicTiming(epic.getId());
        return id;
    }

    @Override
    public int addNewEpic(Epic newEpic) {
        if (newEpic == null) {
            System.out.println("Ошибка, задачи не существует");
            return -1;
        }
        int id = idGenerator();
        newEpic.setId(id);
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
        //обновляем время эпика
        calculateEpicTiming(epicId);
    }

    /**
     * обновляем статус эпика
     **/
    private void checkoutEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        List<Integer> ids = epic.getSubTaskIds();
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

    /**
     * определяем время начала работы над эпиком планирумое окончание и длительность, от сабтасок
     **/
    private void calculateEpicTiming(int epicId) {

        Epic epic = epics.get(epicId);
        LocalDateTime epicStartTime = LocalDateTime.MAX;
        LocalDateTime epicEndTime = LocalDateTime.MIN;
        List<Integer> subTaskIds = epic.getSubTaskIds();

        for (Integer id : subTaskIds) {
            SubTask subTask = subTasks.get(id);

            if (subTask.getStartTime().isBefore(epicStartTime)) {
                epicStartTime = subTask.getStartTime();
            }
            if (subTask.getEndTime().isAfter(epicEndTime)) {
                epicEndTime = subTask.getEndTime();
            }
        }

        epic.setStartTime(epicStartTime);
        epic.setEndTime(epicEndTime);
        epic.setDuration(Duration.between(epicStartTime, epicEndTime));
    }

    @Override
    public void deleteTask(int taskId) {
        tasks.remove(taskId);
        historyManager.remove(taskId);
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
        historyManager.remove(subTaskId);
        //обновляем время эпика
        calculateEpicTiming(epicId);
    }

    @Override
    public void deleteEpic(int epicId) {
        Epic epic = epics.get(epicId);
        List<Integer> subTaskIds = epic.getSubTaskIds();
        for (int id : subTaskIds) {
            subTasks.remove(id);
            historyManager.remove(id);
        }
        epics.remove(epicId);
        historyManager.remove(epicId);
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
    public List<SubTask> getSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public List<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<SubTask> getEpicSubTasks(int epicId) {
        List<Integer> subTaskIds = epics.get(epicId).getSubTaskIds();
        List<SubTask> epicSubTasks = new ArrayList<>();
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
