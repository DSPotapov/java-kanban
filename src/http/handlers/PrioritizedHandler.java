package http.handlers;

import com.sun.net.httpserver.HttpExchange;
import managers.TaskManager;

import java.io.IOException;

public class PrioritizedHandler extends BaseHttpHandler {
    public PrioritizedHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }
}
