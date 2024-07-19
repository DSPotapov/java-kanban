package http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpServer;
import http.handlers.*;
import managers.Managers;
import managers.TaskManager;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.LocalDateTime;

public class HttpTaskServer {
    private static final int PORT = 8080;
    HttpServer httpServer;

    public HttpTaskServer(TaskManager manager, int port) throws IOException {
        httpServer = HttpServer.create(new InetSocketAddress(port), 0);

        httpServer.createContext("/tasks", new TaskHandler(manager));
        httpServer.createContext("/subtasks", new SubTaskHandler(manager));
        httpServer.createContext("/epics", new EpicHandler(manager));
        httpServer.createContext("/history", new HistoryHandler(manager));
        httpServer.createContext("/prioritized", new PrioritizedHandler(manager));
    }

    public HttpTaskServer(TaskManager manager) throws IOException {
        this(manager, PORT);
    }

    public static void main(String[] args) throws IOException {

        File file = File.createTempFile("temp", "csv");
        TaskManager manager = Managers.getFileBackedTaskManager(file);
        HttpTaskServer httpTaskServer = new HttpTaskServer(manager);

        httpTaskServer.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }

    public static Gson getGson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Duration.class, new DurationAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
    }

    public void start() {
        httpServer.start();
    }

    public void stop() {
        httpServer.stop(0);
    }
}