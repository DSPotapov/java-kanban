import java.util.HashMap;

public class TaskManager {
    private static int taskId = 1;

    private static HashMap<Integer, Task> tasks = new HashMap<>();
    private static HashMap<Integer, Task> subTasks = new HashMap<>();
    private static HashMap<Integer, Task> epics = new HashMap<>();

    public static int idGenerator(){
        return taskId++;
    }

    public void clearTasks() {
        tasks.clear();
        subTasks.clear();
        epics.clear();
        taskId = 1;
    }

    public void createTask(Task newTask){
        tasks.put(newTask.getId(), newTask);
    }
    public void createSubTask(SubTask newSubTask){
        subTasks.put(newSubTask.getId(), newSubTask);
    }
    public void createEpic(Epic newEpic){
        epics.put(newEpic.getId(), newEpic);
    }

    public static int getTaskId() {
        return taskId;
    }

    public static HashMap<Integer, Task> getSubTasks() {
        return subTasks;
    }

    public static HashMap<Integer, Task> getEpics() {
        return epics;
    }

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public Task getTaskById(int id) {
        return tasks.getOrDefault(id, null);
    }
}
