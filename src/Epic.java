import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Integer> subTaskIds = new ArrayList<>();

    public Epic(String name, String description, int id) {
        super(name, description, id);
        this.taskType = "Epic";
    }

    public Epic(String name, String description) {
        super(name, description);
        this.taskType = "Epic";
    }

    public void addSubTaskId(int subTaskId){
        subTaskIds.add(subTaskId);
    }

    public void deleteSubTaskId(int subTaskId){
        subTaskIds.remove(subTaskId);
    }

    public List<Integer> getSubTaskIds() {
        return subTaskIds;
    }
}
