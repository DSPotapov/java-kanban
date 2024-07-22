import com.google.gson.Gson;
import components.Epic;
import components.SubTask;
import components.Task;
import http.HttpTaskServer;
import managers.Managers;
import managers.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HistoryHandlerTest {
    HttpTaskServer httpTaskServer;
    TaskManager manager;
    HttpClient httpClient;
    Gson gson = HttpTaskServer.getGson();
    String path = "http://localhost:8080/";


    public HistoryHandlerTest() throws IOException {
        manager = Managers.getDefault();
        httpTaskServer = new HttpTaskServer(manager, 8080);
        httpClient = HttpClient.newHttpClient();
    }

    @BeforeEach
    public void setUp() {
        manager.clearAllTasks();
        httpTaskServer.start();
    }

    @AfterEach
    public void shutDown() {
        httpTaskServer.stop();
    }

    @Test
    public void getHistoryTest() throws IOException, InterruptedException {
        Epic epic = new Epic("Epic 1", "Testing epic 1", 1);
        String taskJson = gson.toJson(epic);
        System.out.println("taskJson = " + taskJson);

        // создаём HTTP-клиент и запрос
        URI url = URI.create(path + "epics");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .version(HttpClient.Version.HTTP_1_1)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(200, response.statusCode());
        // создаём задачу
        SubTask subTask = new SubTask("Test 2", "Testing subTask 2", 2, 1);
        // конвертируем её в JSON
        taskJson = gson.toJson(subTask);

        // создаём HTTP-клиент и запрос
        url = URI.create(path + "subtasks");
        request = HttpRequest.newBuilder()
                .uri(url)
                .version(HttpClient.Version.HTTP_1_1)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .header("Content-Type", "application/json")
                .build();

        // вызываем рест, отвечающий за создание задач
        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(200, response.statusCode());

        url = URI.create(path + "epics/" + epic.getId());
        request = HttpRequest.newBuilder()
                .uri(url)
                .version(HttpClient.Version.HTTP_1_1)
                .GET()
                .header("Content-Type", "application/json")
                .build();

        // вызываем рест, отвечающий за создание задач
        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(200, response.statusCode());

        url = URI.create(path + "history");
        request = HttpRequest.newBuilder()
                .uri(url)
                .version(HttpClient.Version.HTTP_1_1)
                .GET()
                .header("Content-Type", "application/json")
                .build();

        // вызываем рест, отвечающий за создание задач
        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(200, response.statusCode());

        System.out.println("response = " + response.body());
        assertEquals(manager.getHistory(), gson.fromJson(response.body(), Task.class));
    }
}
