import components.Epic;
import components.SubTask;
import components.Task;
import components.TaskStatus;
import managers.Managers;
import managers.TaskManager;

public class Main {

    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

        manager.addNewTask(new Task("Задача 1", "Выполнить задачу 1"));
        manager.addNewTask(new Task("Задача 2", "Выполнить задачу 2"));

        Epic epic = new Epic("Эпик 1", "Выполнить эпик 1");
        manager.addNewEpic(epic);
        manager.addNewSubTask(new SubTask("Сабтаска 1 эпик 1", "выполнить сабтаску 1 эпика 1", epic.getId()));
        manager.addNewSubTask(new SubTask("Сабтаска 2 эпик 1", "выполнить сабтаску 2 эпика 1", epic.getId()));

        epic = new Epic("Эпик 2", "Выполнить эпик 2");
        manager.addNewEpic(epic);
        manager.addNewSubTask(new SubTask("Сабтаска 1 эпик 2", "выполнить сабтаску 1 эпика 2", epic.getId()));

        System.out.println("1");
        printAllTasks(manager);

        System.out.println("изм состояние тасок");
        Task task;
        task = manager.getTaskById(2);
        task.setTaskStatus(TaskStatus.IN_PROGRESS);
        manager.updateTask(task);

        task = manager.getTaskById(1);
        task.setTaskStatus(TaskStatus.DONE);
        manager.updateTask(task);
        printAllTasks(manager);

        System.out.println("работа с сабтасками 1");
        SubTask subTask = manager.getSubTaskById(7);
        subTask.setTaskStatus(TaskStatus.IN_PROGRESS);
        manager.updateSubTask(subTask);
        printAllTasks(manager);

        System.out.println("работа с сабтасками 2");
        subTask = manager.getSubTaskById(4);
        subTask.setTaskStatus(TaskStatus.DONE);
        manager.updateSubTask(subTask);
        printAllTasks(manager);

        System.out.println("работа с сабтасками 3");
        subTask = manager.getSubTaskById(5);
        subTask.setTaskStatus(TaskStatus.DONE);
        manager.updateSubTask(subTask);
        printAllTasks(manager);

        System.out.println("удаляем стаску");
        manager.deleteTask(1);
        printAllTasks(manager);;

        System.out.println("удаление эпика");
        manager.deleteEpic(6);
        printAllTasks(manager);
    }

    private static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        for (Task task : manager.getTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Task epic : manager.getEpics()) {
            System.out.println(epic);

            for (Task task : manager.getEpicSubTasks(epic.getId())) {
                System.out.println("--> " + task);
            }
        }
        System.out.println("Подзадачи:");
        for (Task subtask : manager.getSubTasks()) {
            System.out.println(subtask);
        }

        System.out.println("История:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}
