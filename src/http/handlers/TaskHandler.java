package http.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import components.Task;
import managers.TaskManager;

import java.io.IOException;

public class TaskHandler extends BaseHttpHandler {
    enum Endpoint {
        GET_TASKS, GET_TASK_BY_ID, CREATE_TASK, UPDATE_TASK, DELETE_TASK, UNKNOWN
    }

    Gson gson = new Gson();

    public TaskHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        Endpoint endpoint = getEndpoint(exchange.getRequestURI().getPath(), exchange.getRequestMethod());

        // TODO добавить проверки на пересечения задачи (CREATE_TASK, UPDATE_TASK) и отсутствие задачи (GET_TASK_BY_ID)
        switch (endpoint) {
            case GET_TASKS -> sendText(exchange, manager.getTasks().toString());

            case GET_TASK_BY_ID -> {
                int id = Integer.parseInt(exchange.getRequestURI().getPath().split("/")[2]);
                sendText(exchange, manager.getTaskById(id).toString());
            }

            case CREATE_TASK -> {
                Task newTask = parseTask(exchange);
                if (manager.createTask(newTask) == -1) {
                    sendHasInteractions(exchange);
                } else {
                    manager.createTask(newTask);
                    sendText(exchange, "Задача: " + newTask.toString() + " создана.");
                }
            }

            case UPDATE_TASK -> handlePostComments(exchange);
            case DELETE_TASK -> handlePostComments(exchange);
            default -> handlePostComments(exchange);
        }
    }

    private Endpoint getEndpoint(String requestPath, String requestMethod) {
        String[] pathParts = requestPath.split("/");

        if (requestMethod.equals("GET")) {
            if (pathParts.length == 3) {
                return Endpoint.GET_TASK_BY_ID;
            } else {
                return Endpoint.GET_TASKS;
            }
        }
        if (requestMethod.equals("POST")) {
            if (pathParts.length == 3) {
                return Endpoint.UPDATE_TASK;
            } else {
                return Endpoint.CREATE_TASK;
            }
        }
        if (requestMethod.equals("DELETE")) {
            return Endpoint.DELETE_TASK;
        }
        return Endpoint.UNKNOWN;
    }

    protected Task parseTask(HttpExchange exchange) {
        exchange.getRequestBody();
        return gson.fromJson(String.valueOf(exchange.getRequestBody()), Task.class);
    }
}
