public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        taskManager.createTask(new Task("Задача 1", "Выполнить задачу 1"));
        taskManager.createTask(new Task("Задача 2", "Выполнить задачу 2"));

        Epic epic = new Epic("Эпик 1", "Выполнить эпик 1");
        taskManager.createEpic(epic);
        taskManager.createSubTask(new SubTask("Сабтаска 1 эпик 1", "выполнить сабтаску 1 эпика 1", epic.getId()));
        taskManager.createSubTask(new SubTask("Сабтаска 2 эпик 1", "выполнить сабтаску 2 эпика 1", epic.getId()));

        epic = new Epic("Эпик 2", "Выполнить эпик 2");
        taskManager.createEpic(epic);
        taskManager.createSubTask(new SubTask("Сабтаска 1 эпик 2", "выполнить сабтаску 1 эпика 2", epic.getId()));

        System.out.println("1");
        taskManager.printTasks();

        System.out.println("изм состояние тасок");
        Task task;
        task = taskManager.getTaskById(2);
        task.setTaskStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateTask(task);

        task = taskManager.getTaskById(1);
        task.setTaskStatus(TaskStatus.DONE);
        taskManager.updateTask(task);
        taskManager.printTasks();

        System.out.println("работа с сабтасками 1");
        SubTask subTask = taskManager.getSubTaskById(7);
        subTask.setTaskStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateSubTask(subTask);
        taskManager.printTasks();

        System.out.println("работа с сабтасками 2");
        subTask = taskManager.getSubTaskById(4);
        subTask.setTaskStatus(TaskStatus.DONE);
        taskManager.updateSubTask(subTask);
        taskManager.printTasks();

        System.out.println("работа с сабтасками 3");
        subTask = taskManager.getSubTaskById(5);
        subTask.setTaskStatus(TaskStatus.DONE);
        taskManager.updateSubTask(subTask);
        taskManager.printTasks();

        System.out.println("удаляем стаску");
        taskManager.deleteTask(1);
        taskManager.printTasks();

        System.out.println("удаление эпика");
        taskManager.deleteEpic(6);
        taskManager.printTasks();
    }
}
