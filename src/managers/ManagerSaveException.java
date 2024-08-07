package managers;

import java.io.File;
import java.io.IOException;

public class ManagerSaveException extends IOException {
    protected final File file;

    public ManagerSaveException(String message, File file) {
        super(message);
        this.file = file;
    }

    @Override
    public String getMessage() {
        return "Запись по пути " + file.getAbsolutePath() + " невозможна: " + super.getMessage();
    }
}
