import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subTaskIds = new ArrayList<>();

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

    public ArrayList<Integer> getSubTaskIds() {
        return subTaskIds;
    }
}
