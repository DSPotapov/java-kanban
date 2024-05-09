import java.util.HashMap;

public class TaskManager {
    private static int TaskId = 1;

    HashMap<Integer, Task> tasks = new HashMap<>();

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public void clearTasks(){
        tasks.clear();
    }

    public Task getTaskById(int id){
        return tasks.getOrDefault(id, null);
    }
}
