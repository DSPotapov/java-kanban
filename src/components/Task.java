package components;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    protected String name;
    protected String description;
    protected int id;
    // продолжительность задачи, оценка того, сколько времени она займёт в минутах(секундах), по умолчанию: сутки
    protected Duration duration = Duration.ofMinutes(15);
    // дата и время, когда предполагается приступить к выполнению задачи, по умолчанию: дата создания
    protected LocalDateTime startTime = LocalDateTime.now();
    protected TaskStatus taskStatus = TaskStatus.NEW;
    protected TaskType taskType = TaskType.TASK;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Task(String name, String description, LocalDateTime startTime) {
        this(name, description);
        this.startTime = startTime;
    }

    public Task(String name, String description, LocalDateTime startTime, Duration duration) {
        this(name, description, startTime);
        this.duration = duration;
    }

    public Task(String name, String description, int id) {
        this(name, description);
        this.id = id;
    }

    public Task(String name, String description, int id, LocalDateTime startTime) {
        this(name, description, id);
        this.startTime = startTime;
    }

    public Task(String name, String description, int id, LocalDateTime startTime, Duration duration) {
        this(name, description, id, startTime);
        this.duration = duration;
    }

    /**
     * дата и время завершения задачи, которые рассчитываются исходя из startTime и duration
     **/
    public LocalDateTime getEndTime() {
        return startTime.plusSeconds(duration.getSeconds());
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(name, description, id, taskStatus);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return this.id == ((Task) o).getId();
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", taskStatus=" + taskStatus +
                ", taskType='" + taskType + '\'' +
                ", startTime=" + startTime +
                ", duration=" + duration +
                ", endTime=" + getEndTime() +
                "}\n";
    }
}
