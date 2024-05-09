import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subTasks = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
        this.taskType = "Epic";
    }

    public void addSubTask(Integer subTaskId){
        subTasks.add(subTaskId);
    }
}
