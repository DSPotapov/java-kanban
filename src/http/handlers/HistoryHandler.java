package http.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import http.HttpTaskServer;
import managers.TaskManager;

import java.io.IOException;
import java.util.List;

import components.Task;

public class HistoryHandler extends BaseHttpHandler {
    Gson gson = HttpTaskServer.getGson();

    public HistoryHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod =  exchange.getRequestMethod();
        if (requestMethod.equalsIgnoreCase("GET")){
            List<Task> history = manager.getHistory();
            sendText(exchange, gson.toJson(history));
        } else {
            sendNotFound(exchange, "Проверьте корректность запроса.");
        }
    }
}
