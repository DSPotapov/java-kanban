package components;

public class Node<Task> {

    public Task data;
    public Node<Task> next;
    public Node<Task> prev;

    public Node(Task data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }

    public Task getValue() {
        return data;
    }


}
