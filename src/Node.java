public class Node {

    Task prevTask;
    Task nextTask;
    Task thisTask;

    public Node(Task task){
        thisTask = task;
    }

    public Task getValue(){
        return thisTask;
    }
}
