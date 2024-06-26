import components.Epic;
import components.SubTask;
import components.Task;
import components.TaskStatus;
import managers.Managers;
import managers.TaskManager;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        //1. Создайте две задачи, эпик с тремя подзадачами и эпик без подзадач.
        Task task0 = new Task("task0 for test", "testing task0");
        taskManager.addNewTask(task0);
        Task task1 = new Task("task1 for test", "testing task1");
        taskManager.addNewTask(task1);
        Epic epic0 = new Epic("epic0 for test", "testing epic0");
        taskManager.addNewEpic(epic0);
        SubTask subTask0 = new SubTask("subTask0 for test", "testing subTask0", epic0.getId());
        SubTask subTask1 = new SubTask("subTask1 for test", "testing subTask1", epic0.getId());
        SubTask subTask2 = new SubTask("subTask2 for test", "testing subTask1", epic0.getId());
        taskManager.addNewSubTask(subTask0);
        taskManager.addNewSubTask(subTask1);
        taskManager.addNewSubTask(subTask2);
        Epic epic1 = new Epic("epic1 for test", "testing epic1");
        taskManager.addNewEpic(epic1);

        //2.  Запросите созданные задачи несколько раз в разном порядке.
        //3.  После каждого запроса выведите историю и убедитесь, что в ней нет повторов.
        System.out.println("Запрос task0");
        taskManager.getTaskById(task0.getId());
        System.out.println("----------------- \nhistory update:");
        System.out.println(taskManager.getHistory());

        System.out.println("Запрос task0");
        taskManager.getTaskById(task0.getId());
        System.out.println("----------------- \nhistory update:");
        System.out.println(taskManager.getHistory());

        System.out.println("Запрос task1");
        taskManager.getTaskById(task1.getId());
        System.out.println("----------------- \nhistory update:");
        System.out.println(taskManager.getHistory());

        System.out.println("Запрос epic0");
        taskManager.getEpicById(epic0.getId());
        System.out.println("----------------- \nhistory update:");
        System.out.println(taskManager.getHistory());

        System.out.println("Запрос subTask0 (epic0)");
        taskManager.getSubTaskById(subTask0.getId());
        System.out.println("----------------- \nhistory update:");
        System.out.println(taskManager.getHistory());

        System.out.println("Запрос subTask0 (epic0)");
        taskManager.getSubTaskById(subTask0.getId());
        System.out.println("----------------- \nhistory update:");
        System.out.println(taskManager.getHistory());

        System.out.println("Запрос subTask1 (epic0)");
        taskManager.getSubTaskById(subTask1.getId());
        System.out.println("----------------- \nhistory update:");
        System.out.println(taskManager.getHistory());

        System.out.println("Запрос task0");
        taskManager.getTaskById(task0.getId());
        System.out.println("----------------- \nhistory update:");
        System.out.println(taskManager.getHistory());

        System.out.println("Запрос subTask2 (epic0)");
        taskManager.getSubTaskById(subTask2.getId());
        System.out.println("----------------- \nhistory update:");
        System.out.println(taskManager.getHistory());

        System.out.println("Запрос task1");
        taskManager.getTaskById(task1.getId());
        System.out.println("----------------- \nhistory update:");
        System.out.println(taskManager.getHistory());

        System.out.println("Запрос epic1");
        taskManager.getEpicById(epic1.getId());
        System.out.println("----------------- \nhistory update:");
        System.out.println(taskManager.getHistory());

        //4. Удалите задачу, которая есть в истории, и проверьте, что при печати она не будет выводиться.
        System.out.println("Удаляем task1");
        taskManager.deleteTask(task1.getId());
        System.out.println(taskManager.getHistory());
        System.out.println("Удаляем subTask2");
        taskManager.deleteTask(subTask2.getId());
        System.out.println(taskManager.getHistory());

        //5.Удалите эпик с тремя подзадачами и убедитесь, что из истории удалился как сам эпик, так и все его подзадачи.
        System.out.println("Удаляем эпик с сабтасками");
        taskManager.deleteEpic(epic0.getId());
        System.out.println(taskManager.getHistory());

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

    public static void localTest1() {
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
        printAllTasks(manager);

        System.out.println("удаление эпика");
        manager.deleteEpic(6);
        printAllTasks(manager);
    }
}
