package managers;

import components.Epic;
import components.SubTask;
import components.Task;

import java.nio.file.Path;

public class FileBackedTaskManager extends InMemoryTaskManager {
    Path path;

    public FileBackedTaskManager(Path path) {
        this.path = path;
    }

    private boolean save() {
        // TODO соханять состояние менеджера в файл path

        return false;
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
