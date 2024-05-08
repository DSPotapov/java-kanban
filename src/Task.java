public class Task {
    private final String name;
    private final String description;
    private final int id;
    private TaskStatus taskStatus;

    public Task(String name, String description, int id) {
        this.name = name;
        this.description = description;
        this.id = id;
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

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }
}
