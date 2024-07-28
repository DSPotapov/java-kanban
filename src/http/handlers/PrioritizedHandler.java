package http.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import components.Task;
import http.HttpTaskServer;
import managers.TaskManager;

import java.io.IOException;
import java.util.List;

public class PrioritizedHandler extends BaseHttpHandler {
    Gson gson = HttpTaskServer.getGson();

    public PrioritizedHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        if (requestMethod.equalsIgnoreCase("GET")) {
            List<Task> prioritizedTasks = manager.getPrioritizedTasks();
            sendText(exchange, gson.toJson(prioritizedTasks));
        } else {
            sendNotFound(exchange, "Проверьте корректность запроса.");
        }
    }
}
