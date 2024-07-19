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
        switch (endpoint) {
            case GET_TASKS -> sendText(exchange, manager.getTasks().toString());
            case GET_TASK_BY_ID -> {
                int id = Integer.parseInt(exchange.getRequestURI().getPath().split("/")[2]);
                Task task = manager.getTaskById(id);
                if (task == null) {
                    sendNotFound(exchange, "Задача " + id + " не найдена.");
                    break;
                }
                sendText(exchange, task.toString());
            }
            case CREATE_TASK -> {
                Task newTask = parseTask(exchange);
                try {
                    if (manager.createTask(newTask) == -1) {
                        sendHasInteractions(exchange);
                    } else {
                        manager.createTask(newTask);
                        sendText(exchange, "Задача: " + newTask.toString() + " создана.");
                    }
                } catch (IOException e) {
                    sendInternalServerError(exchange, e.getMessage());
                }
            }
            case UPDATE_TASK -> {
                Task task = parseTask(exchange);
                try {
                    if (manager.getTasks().contains(task)) {
                        manager.updateTask(task);
                        sendOKStatus(exchange);
                    } else {
                        sendNotFound(
                                exchange,
                                "Задача " + task + " не существует.");
                    }
                } catch (IOException e) {
                    sendInternalServerError(exchange, e.getMessage());
                }
            }
            case DELETE_TASK -> {
                Task task = parseTask(exchange);
                try {
                    manager.deleteTask(task.getId());
                    sendOKStatus(exchange);
                } catch (IOException e) {
                    sendInternalServerError(exchange, e.getMessage());
                }
            }
            default -> sendNotFound(exchange, "Проверьте корректность запроса.");
        }
    }

    private Endpoint getEndpoint(String requestPath, String requestMethod) {
        String[] pathParts = requestPath.split("/");

        switch (requestMethod) {
            case "GET" -> {
                if (pathParts.length == 3) {
                    return Endpoint.GET_TASK_BY_ID;
                } else {
                    return Endpoint.GET_TASKS;
                }
            }
            case "POST" -> {
                if (pathParts.length == 3) {
                    return Endpoint.UPDATE_TASK;
                } else {
                    return Endpoint.CREATE_TASK;
                }
            }
            case "DELETE" -> {
                return Endpoint.DELETE_TASK;
            }
        }
        return Endpoint.UNKNOWN;
    }

    protected Task parseTask(HttpExchange exchange) {
        exchange.getRequestBody();
        return gson.fromJson(String.valueOf(exchange.getRequestBody()), Task.class);
    }
}
