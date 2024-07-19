package http;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import http.handlers.*;
import managers.Managers;
import managers.TaskManager;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    private static final int PORT = 8080;
    HttpServer httpServer;

    public HttpTaskServer() throws IOException {
        httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
    }

    public HttpTaskServer(int port) throws IOException {
        httpServer = HttpServer.create(new InetSocketAddress(port), 0);
    }


    public static void main(String[] args) throws IOException {
        HttpTaskServer httpTaskServer = new HttpTaskServer();
        File file = File.createTempFile("temp", "csv");
        TaskManager manager = Managers.getFileBackedTaskManager(file);

        httpTaskServer.createContext("/tasks", new TaskHandler(manager));
        httpTaskServer.createContext("/subtasks", new SubTaskHandler(manager));
        httpTaskServer.createContext("/epics", new EpicHandler(manager));
        httpTaskServer.createContext("/history", new HistoryHandler(manager));
        httpTaskServer.createContext("/prioritized", new PrioritizedHandler(manager));

        httpTaskServer.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }

    public void start() {
        httpServer.start();
    }

    public void stop() {
        httpServer.stop(0);
    }

    public void createContext(String path, HttpHandler handler) {
        httpServer.createContext(path, handler);
    }
}