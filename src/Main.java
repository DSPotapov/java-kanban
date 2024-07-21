import components.Epic;
import components.SubTask;
import components.Task;
import managers.TaskManager;

public class Main {

    public static void main(String[] args) {

    }

    private static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        for (Task task : manager.getTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Epic epic : manager.getEpics()) {
            System.out.println(epic);

            for (SubTask subTask : manager.getEpicSubTasks(epic.getId())) {
                System.out.println("--> " + subTask);
            }
        }
        System.out.println("Подзадачи:");
        for (SubTask subtask : manager.getSubTasks()) {
            System.out.println(subtask);
        }

        if (manager.getHistory().isEmpty()) {
            System.out.println("История пустая");
        } else {
            System.out.println("История:");
            for (Task task : manager.getHistory()) {
                System.out.println(task);
            }
        }
    }
}
