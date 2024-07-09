package components;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Integer> subTaskIds = new ArrayList<>();
    private LocalDateTime endTime;


    public Epic(String name, String description, int id) {
        super(name, description, id);
        this.taskType = TaskType.EPIC;
    }

    public Epic(String name, String description) {
        super(name, description);
        this.taskType = TaskType.EPIC;
    }

    public void addSubTaskId(int subTaskId) {

        subTaskIds.add(subTaskId);
        calculateStartEndAndDurationTimes();
    }

    public void deleteSubTaskId(int subTaskId) {
        subTaskIds.remove((Integer) subTaskId);
        calculateStartEndAndDurationTimes();
    }

    public List<Integer> getSubTaskIds() {
        return subTaskIds;
    }

    public boolean isSubTaskId(int id) {
        return subTaskIds.contains((Integer) id);
    }

    public void calculateStartEndAndDurationTimes(){
        //TODO из списка сабтасок найти с самым ранним началом и самым поздним окончанием вычислить разницу
    }
}
