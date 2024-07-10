package components;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Integer> subTaskIds = new ArrayList<>();

    private LocalDateTime endTime;

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime newEndTime) {
        endTime = newEndTime;
    }

    public void setStartTime(LocalDateTime newStartTime) {
        startTime = newStartTime;
    }


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
