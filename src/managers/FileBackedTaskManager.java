package managers;

import components.Epic;
import components.SubTask;
import components.Task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FileBackedTaskManager extends InMemoryTaskManager {
    File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    private void save() {
        // TODO соханять состояние менеджера в файл path
        /*
            файл каждый раз полностью перезаписывается
            1. пишем строку заголовков
            2. пишет таски, эпики, сабтаски
            3. чтобы записать данные по порядку айдишников, нужно сначала
            записать таски в Map<id, String> потом записать в файл
            4. получить список keys из общей мапы тасок, отсортировать и
            проходя по отсортированному списку вызывать соответствующий элемент мапы
            и записывать в файл по порядку
         */

        Map<Integer, String> taskMap = collectAllTasks();
        Set<Integer> ids = taskMap.keySet();

        Arrays.sort(ids);

        try (FileWriter writer = new FileWriter(file)) {
            writer.write("id,type,name,status,description,epic\n");

        } catch(IOException e) {
            System.out.println(e.getMessage());
        }

    }

    static FileBackedTaskManager loadFromFile(File file){
        return new FileBackedTaskManager(file);
    }

    public String taskToString(Task task){
        String taskString = task.getId() + ", "
                + task.getTaskType() + ", "
                + task.getName() + ", "
                + task.getTaskStatus() + ", "
                + task.getDescription();
        return taskString;
    }

    public String subTaskToString(SubTask task){
        String taskString = task.getId() + ", "
                + task.getTaskType() + ", "
                + task.getName() + ", "
                + task.getTaskStatus() + ", "
                + task.getDescription() + ", "
                + task.getEpicId();
        return taskString;
    }

    /**
     * собираем таски сабтаски и эпики в одну общую мапу
     * @return мап id таски : строковое представление таски
     */
    public Map<Integer, String> collectAllTasks(){
        Map<Integer, String> taskMap = new HashMap<>();

        for (Task task : tasks.values()){
            taskMap.put(task.getId(), taskToString(task));
        }

        for (Epic task : epics.values()){
            taskMap.put(task.getId(), taskToString(task));
        }

        for (SubTask task : subTasks.values()){
            taskMap.put(task.getId(), subTaskToString(task));
        }

        return taskMap;
    }

    public Task fromString(String value){
        //TODO метод создания задачи из строки
        return null;
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
