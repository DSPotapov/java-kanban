package components;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Integer> subTaskIds = new ArrayList<>();

    private LocalDateTime endTime;


    public Epic(String name, String description) {
        super(name, description);
        this.taskType = TaskType.EPIC;
    }

    public Epic(String name, String description, int id) {
        super(name, description, id);
        this.taskType = TaskType.EPIC;
    }

    public Epic(String name, String description, LocalDateTime startTime) {
        super(name, description);
        this.startTime = startTime;
        this.taskType = TaskType.EPIC;
    }

    public Epic(String name, String description, LocalDateTime startTime, Duration duration) {
        super(name, description, startTime);
        this.duration = duration;
        this.taskType = TaskType.EPIC;
    }

    public Epic(String name, String description, int id, LocalDateTime startTime) {
        super(name, description, id);
        this.startTime = startTime;
    }

    public Epic(String name, String description, int id, LocalDateTime startTime, Duration duration) {
        super(name, description, id, startTime);
        this.duration = duration;
    }

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
