package http.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import managers.TaskManager;

public class BaseHttpHandler implements HttpHandler {

    TaskManager manager;

    public BaseHttpHandler(TaskManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
    }

    protected void sendText(HttpExchange h, String text) throws IOException {
        final int responseCode = 200;
        byte[] resp = text.getBytes(StandardCharsets.UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(responseCode, resp.length);
        h.getResponseBody().write(resp);
        h.close();
    }

    protected void sendOKStatus(HttpExchange h) throws IOException {
        final int responseCode = 201;
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(responseCode, 0);
        h.close();
    }

    protected void sendNotFound(HttpExchange h, String text) throws IOException {
        final int responseCode = 404;
        byte[] resp = text.getBytes(StandardCharsets.UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(responseCode, 0);
        h.getResponseBody().write(resp);
        h.close();

    }

    protected void sendHasInteractions(HttpExchange h) throws IOException {
        final int responseCode = 406;

        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(responseCode, 0);
        h.getResponseBody().write("Задача пересекается с другой".getBytes(StandardCharsets.UTF_8));
        h.close();
    }

    protected void sendInternalServerError(HttpExchange h, String text) throws IOException {
        final int responseCode = 500;

        byte[] resp = text.getBytes(StandardCharsets.UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(responseCode, 0);
        h.getResponseBody().write(resp);
        h.close();
    }
}
