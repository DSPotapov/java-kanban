import java.util.Objects;

public class Task {
    protected String name;
    protected String description;
    protected final int id;
    protected TaskStatus taskStatus;

    protected String taskType = "Task";

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.id = InMemoryTaskManager.idGenerator();
        this.taskStatus = TaskStatus.NEW;
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

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public String getTaskType() {
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
    public final boolean equals(Object o){
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
                "}";
    }
}
