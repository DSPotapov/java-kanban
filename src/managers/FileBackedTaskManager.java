package managers;

import components.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    private void save() {
        Map<Integer, String> taskMap = collectAllTasks();

        try {
            if (!file.isFile()) {
                throw new ManagerSaveException("По указаному пути нет файла.", file);
            }
            FileWriter writer = new FileWriter(file);
            writer.write("id,type,name,status,description,epic\n");

            for (Entry<Integer, String> entry : taskMap.entrySet()) {
                writer.append(entry.getValue());
                writer.append("\n");
            }
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        String tasksFromFile = "";
        try {
            tasksFromFile = Files.readString(file.toPath());
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
                    restoredTaskManager.addNewTask(task);
                }
                case EPIC -> {
                    Epic epic = epicFromString(stringTasks[i]);
                    restoredTaskManager.addNewTask(epic);
                }
                case SUBTASK -> {
                    SubTask subTask = subTaskFromString(stringTasks[i]);
                    restoredTaskManager.addNewTask(subTask);
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
                + task.getDescription();
    }

    String subTaskToString(SubTask subTask) {
        return taskToString(subTask) + "," + subTask.getEpicId();
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
            taskMap.put(task.getId(), taskToString(task));
        }

        for (SubTask task : subTasks.values()) {
            taskMap.put(task.getId(), subTaskToString(task));
        }

        return taskMap;
    }

    public static Task taskFromString(String value) {
        //id,type,name,status,description
        String[] taskFields = value.split(",");
        int id = Integer.parseInt(taskFields[0]);
        TaskType taskType = TaskType.valueOf(taskFields[1]);
        String name = taskFields[2];
        TaskStatus taskStatus = TaskStatus.valueOf(taskFields[3]);
        String description = taskFields[4];

        Task task = new Task(name, description, id);
        task.setTaskType(taskType);
        task.setTaskStatus(taskStatus);

        return task;
    }

    static Epic epicFromString(String value) {
        //id,type,name,status,description
        String[] taskFields = value.split(",");
        int id = Integer.parseInt(taskFields[0]);
        TaskType taskType = TaskType.valueOf(taskFields[1]);
        String name = taskFields[2];
        TaskStatus taskStatus = TaskStatus.valueOf(taskFields[3]);
        String description = taskFields[4];

        Epic epic = new Epic(name, description, id);
        epic.setTaskType(taskType);
        epic.setTaskStatus(taskStatus);

        return epic;
    }

    static SubTask subTaskFromString(String value) {
        //id,type,name,status,description,epic
        String[] taskFields = value.split(",");
        int id = Integer.parseInt(taskFields[0]);
        TaskType taskType = TaskType.valueOf(taskFields[1]);
        String name = taskFields[2];
        TaskStatus taskStatus = TaskStatus.valueOf(taskFields[3]);
        String description = taskFields[4];

        int epicId = Integer.parseInt(taskFields[5]);
        SubTask subTask = new SubTask(name, description, id, epicId);
        subTask.setTaskType(taskType);
        subTask.setTaskStatus(taskStatus);

        return subTask;
    }

    @Override
    public int addNewTask(Task newTask) {
        int result = super.addNewTask(newTask);
        if (result > 0) {
            save();
        }
        return result;
    }

    @Override
    public int addNewSubTask(SubTask newSubTask) {
        int result = super.addNewSubTask(newSubTask);
        if (result > 0) {
            save();
        }
        return result;
    }

    @Override
    public int addNewEpic(Epic newEpic) {
        int result = super.addNewEpic(newEpic);
        if (result > 0) {
            save();
        }
        return result;
    }

    @Override
    public Task getTaskById(int id) {
        Task task = tasks.getOrDefault(id, null);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public SubTask getSubTaskById(int id) {
        SubTask task = subTasks.getOrDefault(id, null);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic task = epics.getOrDefault(id, null);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        super.updateSubTask(subTask);
        save();
    }

    @Override
    public void deleteTask(int taskId) {
        super.deleteTask(taskId);
        save();
    }

    @Override
    public void deleteSubTask(int subTaskId) {
        super.deleteSubTask(subTaskId);
        save();
    }

    @Override
    public void deleteEpic(int epicId) {
        super.deleteEpic(epicId);
        save();
    }
}
