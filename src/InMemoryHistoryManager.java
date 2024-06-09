import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    private Map<Integer, Node> history = new HashMap<>();

    @Override
    public void add(Task task) {
        if (task == null) return;
        int id = task.getId();
        remove(id);
        history.put(id, new Node(task));
    }

    @Override
    public void remove(int id) {
        history.remove(id);
    }

    /**
     * ���������� ������ getHistory ������ ������������� ������ �� �������� ������ � ArrayList ��� ������������ ������.
     */
    @Override
    public List<Task> getHistory() {
        List<Task> historyList = new ArrayList<>();
        for (Node node : history.values()){
            historyList.add(node.getValue());
        }
        return historyList;
    }
}
