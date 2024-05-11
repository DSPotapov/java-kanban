public class SubTask extends Task {

    private int epicId;

    public SubTask(String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
        this.taskType = "SubTask";
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
                "}";
    }
}
