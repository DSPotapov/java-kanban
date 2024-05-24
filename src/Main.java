public class Main {

    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

        manager.addNewTask(new Task("������ 1", "��������� ������ 1", manager.idGenerator()));
        manager.addNewTask(new Task("������ 2", "��������� ������ 2", manager.idGenerator()));

        Epic epic = new Epic("���� 1", "��������� ���� 1", manager.idGenerator());
        manager.addNewEpic(epic);
        manager.addNewSubTask(new SubTask("�������� 1 ���� 1", "��������� �������� 1 ����� 1", manager.idGenerator(), epic.getId()));
        manager.addNewSubTask(new SubTask("�������� 2 ���� 1", "��������� �������� 2 ����� 1", manager.idGenerator(), epic.getId()));

        epic = new Epic("���� 2", "��������� ���� 2", manager.idGenerator());
        manager.addNewEpic(epic);
        manager.addNewSubTask(new SubTask("�������� 1 ���� 2", "��������� �������� 1 ����� 2", manager.idGenerator(), epic.getId()));

        System.out.println("1");
        printAllTasks(manager);

        System.out.println("��� ��������� �����");
        Task task;
        task = manager.getTaskById(2);
        task.setTaskStatus(TaskStatus.IN_PROGRESS);
        manager.updateTask(task);

        task = manager.getTaskById(1);
        task.setTaskStatus(TaskStatus.DONE);
        manager.updateTask(task);
        printAllTasks(manager);

        System.out.println("������ � ���������� 1");
        SubTask subTask = manager.getSubTaskById(7);
        subTask.setTaskStatus(TaskStatus.IN_PROGRESS);
        manager.updateSubTask(subTask);
        printAllTasks(manager);

        System.out.println("������ � ���������� 2");
        subTask = manager.getSubTaskById(4);
        subTask.setTaskStatus(TaskStatus.DONE);
        manager.updateSubTask(subTask);
        printAllTasks(manager);

        System.out.println("������ � ���������� 3");
        subTask = manager.getSubTaskById(5);
        subTask.setTaskStatus(TaskStatus.DONE);
        manager.updateSubTask(subTask);
        printAllTasks(manager);

        System.out.println("������� ������");
        manager.deleteTask(1);
        printAllTasks(manager);;

        System.out.println("�������� �����");
        manager.deleteEpic(6);
        printAllTasks(manager);
    }

    private static void printAllTasks(TaskManager manager) {
        System.out.println("������:");
        for (Task task : manager.getTasks()) {
            System.out.println(task);
        }
        System.out.println("�����:");
        for (Task epic : manager.getEpics()) {
            System.out.println(epic);

            for (Task task : manager.getEpicSubTasks(epic.getId())) {
                System.out.println("--> " + task);
            }
        }
        System.out.println("���������:");
        for (Task subtask : manager.getSubTasks()) {
            System.out.println(subtask);
        }

        System.out.println("�������:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}
