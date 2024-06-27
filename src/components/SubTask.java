package components;

import components.Task;

public class SubTask extends Task {

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    private int epicId;

    public SubTask(String name, String description, int id, int epicId) {
        super(name, description, id);
        this.epicId = epicId;
        this.taskType = TaskType.SUBTASK;
    }

    public SubTask(String name, String description, int epicId) {
        super(name, description);
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
