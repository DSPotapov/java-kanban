package components;

import java.time.Duration;
import java.time.LocalDateTime;

public class SubTask extends Task {

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    private int epicId = -1;
    private LocalDateTime endTime;

    public SubTask(String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
        this.taskType = TaskType.SUBTASK;
        endTime = startTime.plus(duration);
    }

    public SubTask(String name, String description, int id, int epicId) {
        this(name, description, epicId);
        this.id = id;
    }

    public SubTask(String name, String description, int id, LocalDateTime startTime, int epicId) {
        this(name, description, id, epicId);
        this.startTime = startTime;
    }

    public SubTask(String name, String description, int id, LocalDateTime startTime, Duration duration, int epicId) {
        this(name, description, id, startTime, epicId);
        this.duration = duration;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", taskStatus='" + taskStatus + '\'' +
                ", taskType='" + taskType + '\'' +
                ", epicId=" + epicId +
                "}\n";
    }
}
