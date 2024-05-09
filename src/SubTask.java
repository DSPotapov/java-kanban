public class SubTask extends Epic {

    private int epicId;

    public SubTask(String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
        this.taskType = "SubTask";
    }

    public int getEpicId() {
        return epicId;
    }

}
