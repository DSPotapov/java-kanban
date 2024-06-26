package components;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Integer> subTaskIds = new ArrayList<>();

    public Epic(String name, String description, int id) {
        super(name, description, id);
        this.taskType = TaskType.EPIC;
    }

    public Epic(String name, String description) {
        super(name, description);
        this.taskType = TaskType.EPIC;
    }

    public void addSubTaskId(int subTaskId) {
        subTaskIds.add(subTaskId);
    }

    public void deleteSubTaskId(int subTaskId) {
        subTaskIds.remove((Integer) subTaskId);
    }

    public List<Integer> getSubTaskIds() {
        return subTaskIds;
    }

    public boolean isSubTaskId(int id) {
        return subTaskIds.contains((Integer) id);
    }
}
