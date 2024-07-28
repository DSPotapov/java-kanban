package managers;

import components.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    private void save() throws IOException {
        Map<Integer, String> taskMap = collectAllTasks();
        FileWriter writer = null;
        if (!file.exists()) {
            throw new ManagerSaveException("По указаному пути нет файла.", file);
        }
        writer = new FileWriter(file);
        writer.write("id,type,name,status,description,startTime,duration,epic\n");

        for (Entry<Integer, String> entry : taskMap.entrySet()) {
            writer.write(entry.getValue());
            writer.write("\n");
        }
        writer.close();
    }

    public static FileBackedTaskManager loadFromFile(File file) throws IOException {
        String tasksFromFile = "";
        try {
            if (!file.exists()) {
                throw new ManagerSaveException("Такого файла не существует", file);
            }
            tasksFromFile = Files.readString(file.toPath());
        } catch (ManagerSaveException e) {
            System.out.println("Ошибка чтения из файла: " + e);
        } catch (IOException e) {
            System.out.println("e.getMessage() = " + e.getMessage());
        }

        String[] stringTasks = tasksFromFile.split("\n");
        FileBackedTaskManager restoredTaskManager = new FileBackedTaskManager(file);

        for (int i = 1; i < stringTasks.length; i++) {
            TaskType taskType = TaskType.valueOf(stringTasks[i].split(",")[1]);
            switch (taskType) {
                case TASK -> {
                    Task task = taskFromString(stringTasks[i]);
                    restoredTaskManager.createTask(task);
                }
                case EPIC -> {
                    Epic epic = epicFromString(stringTasks[i]);
                    restoredTaskManager.createTask(epic);
                }
                case SUBTASK -> {
                    SubTask subTask = subTaskFromString(stringTasks[i]);
                    restoredTaskManager.createTask(subTask);
                }
            }
        }

        return restoredTaskManager;
    }

    public static String taskToString(Task task) {
        return task.getId() + ","
                + task.getTaskType() + ","
                + task.getName() + ","
                + task.getTaskStatus() + ","
                + task.getDescription() + ","
                + task.getStartTime() + ","
                + task.getDuration().toMinutes();
    }

    public static String subTaskToString(SubTask subTask) {

        return taskToString(subTask) + "," + subTask.getEpicId();
    }

    public static String epicToString(Epic epic) {
        return taskToString(epic) + "," + epic.getEndTime();
    }

    /**
     * собираем таски сабтаски и эпики в одну общую мапу
     *
     * @return мап id таски : строковое представление таски
     */
    public Map<Integer, String> collectAllTasks() {
        final Map<Integer, String> taskMap = new HashMap<>();

        for (Task task : tasks.values()) {
            taskMap.put(task.getId(), taskToString(task));
        }

        for (Epic task : epics.values()) {
            taskMap.put(task.getId(), epicToString(task));
        }

        for (SubTask task : subTasks.values()) {
            taskMap.put(task.getId(), subTaskToString(task));
        }

        return taskMap;
    }

    public static Task taskFromString(String value) {
        //[0]id,[1]type,[2]name,[3]status,[4]description,[5]startTime,[6]duration
        String[] taskFields = value.split(",");
        int id = Integer.parseInt(taskFields[0]);
        TaskType taskType = TaskType.valueOf(taskFields[1]);
        String name = taskFields[2];
        TaskStatus taskStatus = TaskStatus.valueOf(taskFields[3]);
        String description = taskFields[4];
        LocalDateTime startTime = LocalDateTime.parse(taskFields[5]);
        Duration duration = Duration.ofMinutes(Integer.parseInt(taskFields[6]));


        Task task = new Task(name, description, id, startTime, duration);
        task.setTaskType(taskType);
        task.setTaskStatus(taskStatus);

        return task;
    }

    static Epic epicFromString(String value) {
        //[0]id,[1]type,[2]name,[3]status,[4]description,[5]startTime,[6]duration
        String[] taskFields = value.split(",");
        int id = Integer.parseInt(taskFields[0]);
        TaskType taskType = TaskType.valueOf(taskFields[1]);
        String name = taskFields[2];
        TaskStatus taskStatus = TaskStatus.valueOf(taskFields[3]);
        String description = taskFields[4];
        LocalDateTime startTime = LocalDateTime.parse(taskFields[5]);
        Duration duration = Duration.ofMinutes(Integer.parseInt(taskFields[6]));

        Epic epic = new Epic(name, description, id, startTime, duration);
        epic.setTaskType(taskType);
        epic.setTaskStatus(taskStatus);

        return epic;
    }

    static SubTask subTaskFromString(String value) {
        //[0]id,[1]type,[2]name,[3]status,[4]description,[5]startTime,[6]duration,[7]epicId
        String[] taskFields = value.split(",");
        int id = Integer.parseInt(taskFields[0]);
        TaskType taskType = TaskType.valueOf(taskFields[1]);
        String name = taskFields[2];
        TaskStatus taskStatus = TaskStatus.valueOf(taskFields[3]);
        String description = taskFields[4];
        LocalDateTime startTime = LocalDateTime.parse(taskFields[5]);
        Duration duration = Duration.ofMinutes(Integer.parseInt(taskFields[6]));

        int epicId = Integer.parseInt(taskFields[7]);
        SubTask subTask = new SubTask(name, description, id, epicId);
        subTask.setTaskType(taskType);
        subTask.setTaskStatus(taskStatus);

        return subTask;
    }

    @Override
    public int createTask(Task newTask) throws IOException {
        int result = super.createTask(newTask);
        if (result > 0) {
            save();
        }
        return result;
    }

    @Override
    public int createSubTask(SubTask newSubTask) throws IOException {
        int result = super.createSubTask(newSubTask);
        if (result > 0) {
            save();
        }
        return result;
    }

    @Override
    public int createEpic(Epic newEpic) throws IOException {
        int result = super.createEpic(newEpic);
        if (result > 0) {
            save();
        }
        return result;
    }

    @Override
    public void updateTask(Task task) throws IOException {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateSubTask(SubTask subTask) throws IOException {
        super.updateSubTask(subTask);
        save();
    }

    @Override
    public void deleteTask(int taskId) throws IOException {
        super.deleteTask(taskId);
        save();
    }

    @Override
    public void deleteSubTask(int subTaskId) throws IOException {
        super.deleteSubTask(subTaskId);
        save();
    }

    @Override
    public void deleteEpic(int epicId) throws IOException {
        super.deleteEpic(epicId);
        save();
    }
}
