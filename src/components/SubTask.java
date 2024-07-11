package components;

import java.time.Duration;
import java.time.LocalDateTime;

public class SubTask extends Task {

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    private int epicId = -1;

    public SubTask(String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
        this.taskType = TaskType.SUBTASK;
    }

    public SubTask(String name, String description, int id, int epicId) {
        super(name, description, id);
        this.epicId = epicId;
        this.taskType = TaskType.SUBTASK;
    }

    public SubTask(String name, String description, LocalDateTime startTime, Duration duration, int epicId) {
        super(name, description, startTime, duration);
        this.epicId = epicId;
        this.taskType = TaskType.SUBTASK;
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
