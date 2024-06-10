package managers;

import components.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    private final Map<Integer, Node<Task>> history = new HashMap<>();
    private Node<Task> head = null;
    private Node<Task> tail = null;
    private int historySize = 0;

    public void linkLast(Node<Task> newNode) {
        Node<Task> previousNode = tail;
        tail = newNode;
        previousNode.next = newNode;
        newNode.prev = previousNode;
    }

    public void removeNode(Node<Task> node) {
        if (historySize == 1) {
            tail = null;
            head = null;
            return;
        }

        Node<Task> prevNode = node.prev;
        Node<Task> nextNode = node.next;

        if (prevNode == null) {
            head = nextNode;
        } else if (nextNode == null) {
            tail = prevNode;
        } else {
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
        }
    }

    @Override
    public void add(Task task) {

        if (task == null) return;

        int id = task.getId();
        Node<Task> node = new Node<>(task);

        if (historySize == 0) {
            head = node;
            tail = node;
        } else {
            if (history.containsKey(id)) {
                history.remove(id);
                removeNode(node);
                historySize--;
            }
            linkLast(node);
        }

        history.put(id, node);
        historySize++;
    }

    @Override
    public void remove(int id) {

    }

    private List<Task> getTasks() {
        List<Task> historyList = new ArrayList<>();

        for (Node<Task> node : history.values()) {
            historyList.add(node.getValue());
        }

        return historyList;
    }

    /**
     * ���������� ������ getHistory ������ ������������� ������ �� �������� ������ � ArrayList ��� ������������ ������.
     */
    @Override
    public List<Task> getHistory() {
        return getTasks();
    }
}
