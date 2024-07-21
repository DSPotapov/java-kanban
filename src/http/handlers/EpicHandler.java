package http.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import components.Epic;
import http.HttpTaskServer;
import managers.TaskManager;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class EpicHandler extends BaseHttpHandler {
    enum Endpoint {
        GET_EPICS, GET_EPIC_BY_ID, CREATE_EPIC, UPDATE_EPIC, DELETE_EPIC, GET_EPIC_SUBTASKS, UNKNOWN
    }

    Gson gson = HttpTaskServer.getGson();

    public EpicHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        Endpoint endpoint = getEndpoint(exchange.getRequestURI().getPath(), exchange.getRequestMethod());
        switch (endpoint) {
            case GET_EPICS -> {
                System.out.println("GET_EPICS");
                sendText(exchange, manager.getEpics().toString());
            }
            case GET_EPIC_BY_ID -> {
                System.out.println("GET_EPIC_BY_ID");
                int id = Integer.parseInt(exchange.getRequestURI().getPath().split("/")[2]);
                Epic epic = manager.getEpicById(id);
                if (epic == null) {
                    sendNotFound(exchange, "Задача " + id + " не найдена.");
                    break;
                }
                sendText(exchange, epic.toString());
            }
            case CREATE_EPIC -> {
                System.out.println("CREATE_EPIC");
                Epic newEpic = parseEpic(exchange);
                System.out.println("newEpic = " + newEpic);
                try {
                    if (manager.createEpic(newEpic) == -1) {
                        sendHasInteractions(exchange);
                    } else {
                        System.out.println("создаем эпик");
                        sendText(exchange, "Задача: " + newEpic.toString() + " создана.");
                    }
                } catch (IOException e) {
                    sendInternalServerError(exchange, "Ошибка при добавлении задачи: " + e.getMessage());
                }
            }
            case UPDATE_EPIC -> {
                System.out.println("UPDATE_EPIC");
                Epic epic = parseEpic(exchange);

                try {
                    if (manager.getEpicById(epic.getId()) != null) {
                        manager.updateEpic(epic);
                        sendOKStatus(exchange);
                    } else {
                        sendNotFound(
                                exchange,
                                "Задача " + epic + " не существует.");
                    }
                } catch (IOException e) {
                    sendInternalServerError(exchange, e.getMessage());
                }
            }
            case DELETE_EPIC -> {
                System.out.println("DELETE_EPIC");
                Epic epic = parseEpic(exchange);
                try {
                    manager.deleteEpic(epic.getId());
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
                    return Endpoint.GET_EPIC_BY_ID;
                } else {
                    return Endpoint.GET_EPICS;
                }
            }
            case "POST" -> {
                if (pathParts.length == 3) {
                    return Endpoint.UPDATE_EPIC;
                } else {
                    return Endpoint.CREATE_EPIC;
                }
            }
            case "DELETE" -> {
                return Endpoint.DELETE_EPIC;
            }
        }
        return Endpoint.UNKNOWN;
    }

    protected Epic parseEpic(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        Epic epic = null;
        try {
            epic = gson.fromJson(body, Epic.class);
        } catch (Exception e) {
            System.out.println("Сериализация Epic сломалась " + e.getClass() + e.getMessage());
        }
        return epic;
    }
}
