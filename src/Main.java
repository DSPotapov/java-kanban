public class Main {

    public static void main(String[] args) {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

        inMemoryTaskManager.createTask(new Task("Задача 1", "Выполнить задачу 1"));
        inMemoryTaskManager.createTask(new Task("Задача 2", "Выполнить задачу 2"));

        Epic epic = new Epic("Эпик 1", "Выполнить эпик 1");
        inMemoryTaskManager.createEpic(epic);
        inMemoryTaskManager.createSubTask(new SubTask("Сабтаска 1 эпик 1", "выполнить сабтаску 1 эпика 1", epic.getId()));
        inMemoryTaskManager.createSubTask(new SubTask("Сабтаска 2 эпик 1", "выполнить сабтаску 2 эпика 1", epic.getId()));

        epic = new Epic("Эпик 2", "Выполнить эпик 2");
        inMemoryTaskManager.createEpic(epic);
        inMemoryTaskManager.createSubTask(new SubTask("Сабтаска 1 эпик 2", "выполнить сабтаску 1 эпика 2", epic.getId()));

        System.out.println("1");
        inMemoryTaskManager.printTasks();

        System.out.println("изм состояние тасок");
        Task task;
        task = inMemoryTaskManager.getTaskById(2);
        task.setTaskStatus(TaskStatus.IN_PROGRESS);
        inMemoryTaskManager.updateTask(task);

        task = inMemoryTaskManager.getTaskById(1);
        task.setTaskStatus(TaskStatus.DONE);
        inMemoryTaskManager.updateTask(task);
        inMemoryTaskManager.printTasks();

        System.out.println("работа с сабтасками 1");
        SubTask subTask = inMemoryTaskManager.getSubTaskById(7);
        subTask.setTaskStatus(TaskStatus.IN_PROGRESS);
        inMemoryTaskManager.updateSubTask(subTask);
        inMemoryTaskManager.printTasks();

        System.out.println("работа с сабтасками 2");
        subTask = inMemoryTaskManager.getSubTaskById(4);
        subTask.setTaskStatus(TaskStatus.DONE);
        inMemoryTaskManager.updateSubTask(subTask);
        inMemoryTaskManager.printTasks();

        System.out.println("работа с сабтасками 3");
        subTask = inMemoryTaskManager.getSubTaskById(5);
        subTask.setTaskStatus(TaskStatus.DONE);
        inMemoryTaskManager.updateSubTask(subTask);
        inMemoryTaskManager.printTasks();

        System.out.println("удаляем стаску");
        inMemoryTaskManager.deleteTask(1);
        inMemoryTaskManager.printTasks();

        System.out.println("удаление эпика");
        inMemoryTaskManager.deleteEpic(6);
        inMemoryTaskManager.printTasks();
    }
}
