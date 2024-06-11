package managers;

import components.Task;

import java.util.List;

public interface HistoryManager {
    public void add(Task task);
    void remove(int id);
    public List<Task> getHistory();
    public void clearHistory();
    public Task getFirst();
    public Task getLast();
}
