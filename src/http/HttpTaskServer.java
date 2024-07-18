package http;

import com.sun.net.httpserver.HttpServer;
import http.handlers.*;
import managers.HistoryManager;
import managers.Managers;
import managers.TaskManager;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {

        HttpServer httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);

        File file = File.createTempFile("temp", "csv");
        TaskManager manager = Managers.getFileBackedTaskManager(file);

        httpServer.createContext("/tasks", new TaskHandler(manager));
        httpServer.createContext("/subtasks", new SubTaskHandler(manager));
        httpServer.createContext("/epics", new EpicHandler(manager));
        httpServer.createContext("/history", new HistoryHandler(manager));
        httpServer.createContext("/prioritized", new PrioritizedHandler(manager));

        httpServer.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }
}