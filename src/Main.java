public class Main {

    public static void main(String[] args) {
        TaskManager.createTask(new Task("Задача 1", "Выполнить задачу 1"));
        TaskManager.createTask(new Task("Задача 2", "Выполнить задачу 2"));

        Epic epic = new Epic("Эпик 1", "Выполнить эпик 1");
        TaskManager.createEpic(epic);
        TaskManager.createSubTask(new SubTask("Сабтаска 1 эпик 1", "выполнить сабтаску 1 эпика 1", epic.getId()));
        TaskManager.createSubTask(new SubTask("Сабтаска 2 эпик 1", "выполнить сабтаску 2 эпика 1", epic.getId()));

        epic = new Epic("Эпик 2", "Выполнить эпик 2");
        TaskManager.createEpic(epic);
        TaskManager.createSubTask(new SubTask("Сабтаска 1 эпик 2", "выполнить сабтаску 1 эпика 2", epic.getId()));

        System.out.println("1");
        printTasks();

        System.out.println("изм состояние тасок");
        Task task;
        task = TaskManager.getTaskById(2);
        task.setTaskStatus(TaskStatus.IN_PROGRESS);
        TaskManager.updateTask(task);

        task = TaskManager.getTaskById(1);
        task.setTaskStatus(TaskStatus.DONE);
        TaskManager.updateTask(task);
        printTasks();

        System.out.println("работа с сабтасками 1");
        SubTask subTask = TaskManager.getSubTaskById(7);
        subTask.setTaskStatus(TaskStatus.IN_PROGRESS);
        TaskManager.updateSubTask(subTask);
        printTasks();

        System.out.println("работа с сабтасками 2");
        subTask = TaskManager.getSubTaskById(4);
        subTask.setTaskStatus(TaskStatus.DONE);
        TaskManager.updateSubTask(subTask);
        printTasks();

        System.out.println("работа с сабтасками 3");
        subTask = TaskManager.getSubTaskById(5);
        subTask.setTaskStatus(TaskStatus.DONE);
        TaskManager.updateSubTask(subTask);
        printTasks();

        System.out.println("удаляем стаску");
        TaskManager.deleteTask(1);
        printTasks();

        System.out.println("удаление эпика");
        TaskManager.deleteEpic(6);
        printTasks();
    }

    private static void printTasks(){
        System.out.println("Список задач:");
        System.out.println(TaskManager.getTasks());
        System.out.println("Список эпиков:");
        System.out.println(TaskManager.getEpics());
        System.out.println("Список подзадач:");
        System.out.println(TaskManager.getSubTasks());
        System.out.println();
    }
}
