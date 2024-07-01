package managers;

import java.io.File;
import java.io.IOException;

public class ManagerSaveException extends IOException {
    File file;
    public ManagerSaveException(String message){
        super(message);
    }

    public ManagerSaveException(File file, String message){
        super(message);
        this.file = file;
    }
    @Override
    public String getMessage(){
        return "Запись по пути " + file.getAbsolutePath() + " невозможна: " + getMessage();
    }
}
