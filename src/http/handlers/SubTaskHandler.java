package http.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import components.Epic;
import components.SubTask;
import http.HttpTaskServer;
import managers.TaskManager;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class SubTaskHandler extends BaseHttpHandler {
    public SubTaskHandler(TaskManager manager) {
        super(manager);
    }

    enum Endpoint {
        GET_SUBTASKS, GET_SUBTASK_BY_ID, CREATE_SUBTASK, UPDATE_SUBTASK, DELETE_SUBTASK, GET_EPIC, UNKNOWN
    }

    Gson gson = HttpTaskServer.getGson();

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        Endpoint endpoint = getEndpoint(exchange.getRequestURI().getPath(), exchange.getRequestMethod());
        switch (endpoint) {
            case GET_SUBTASKS -> {
                System.out.println("GET_SUBTASKS");
                sendText(exchange, manager.getSubTasks().toString());
            }
            case GET_SUBTASK_BY_ID -> {
                System.out.println("GET_SUBTASK_BY_ID");
                int id = Integer.parseInt(exchange.getRequestURI().getPath().split("/")[2]);
                SubTask subTask = manager.getSubTaskById(id);
                if (subTask == null) {
                    sendNotFound(exchange, "Задача " + id + " не найдена.");
                    break;
                }
                sendText(exchange, gson.toJson(subTask));
            }
            case CREATE_SUBTASK -> {
                System.out.println("CREATE_SUBTASK");
                SubTask newSubTask = parseSubTask(exchange);
                System.out.println("newSubTask.toString() = " + newSubTask.toString());
                try {
                    if (manager.createSubTask(newSubTask) == -1) {
                        System.out.println("manager.createSubTask(newSubTask) == -1");
                        sendHasInteractions(exchange);
                    } else {
                        System.out.println("создаем подзадачу");
                        sendText(exchange, "Задача: " + newSubTask.toString() + " создана.");
                    }
                } catch (Exception e) {
                    sendInternalServerError(exchange, "Ошибка при добавлении задачи: " + e.getClass() + e.getMessage());
                }
            }
            case UPDATE_SUBTASK -> {
                System.out.println("UPDATE_SUBTASK");
                SubTask subTask = parseSubTask(exchange);

                try {
                    if (manager.getSubTaskById(subTask.getId()) != null) {
                        manager.updateSubTask(subTask);
                        sendOKStatus(exchange);
                    } else {
                        sendNotFound(
                                exchange,
                                "Задача " + subTask + " не существует.");
                    }
                } catch (IOException e) {
                    sendInternalServerError(exchange, e.getMessage());
                }
            }
            case DELETE_SUBTASK -> {
                System.out.println("DELETE_SUBTASK");
                SubTask subTask = parseSubTask(exchange);
                try {
                    manager.deleteSubTask(subTask.getId());
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
                    return Endpoint.GET_SUBTASK_BY_ID;
                } else {
                    return Endpoint.GET_SUBTASKS;
                }
            }
            case "POST" -> {
                if (pathParts.length == 3) {
                    return Endpoint.UPDATE_SUBTASK;
                } else {
                    return Endpoint.CREATE_SUBTASK;
                }
            }
            case "DELETE" -> {
                return Endpoint.DELETE_SUBTASK;
            }
        }
        return Endpoint.UNKNOWN;
    }

    protected SubTask parseSubTask(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();

        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        SubTask subTask = null;
        try {
            subTask = gson.fromJson(body, SubTask.class);
        } catch (Exception e) {
            System.out.println("Сериализация SubTask сломалась " + e.getClass() + e.getMessage());
        }
        return subTask;
    }
}
