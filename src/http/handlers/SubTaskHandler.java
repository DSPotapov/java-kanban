package http.handlers;

import com.sun.net.httpserver.HttpExchange;
import managers.TaskManager;

import java.io.IOException;

public class SubTaskHandler extends BaseHttpHandler {
    public SubTaskHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }
}
